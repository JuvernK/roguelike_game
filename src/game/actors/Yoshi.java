package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.auxiliary.Status;
import game.behaviours.*;
import game.items.YoshiEgg;
import game.reset.Resettable;

import java.util.HashMap;
import java.util.Map;

public class Yoshi extends Actor implements Resettable {

    private final Map<Integer, Behaviour> behaviours = new HashMap<>();

    /**
     * Constructor.
     *
     * @param actor the actor to follow
     */
    public Yoshi(Actor actor) {
        super("Yoshi", '&', 100 );
        this.behaviours.put(5, new YoshiFollowBehaviour(actor));     //following player has a higher priority than attacking
        this.behaviours.put(10, new YoshiAttackBehaviour());
        this.addCapability(Status.HOSTILE_TO_ENEMY);
        this.registerInstance();      //add to reset manager
    }

    /**
     * Create and return an intrinsic weapon for Yoshi.
     *
     * @return a freshly-instantiated IntrinsicWeapon
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(15, "bites");
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for Yoshi
     * @param lastAction The Action Yoshi took last turn
     * @param map        the map containing the Yoshi
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        boolean doNothing = false;

        if (this.hasCapability(Status.RESET_CAPABLE)) {
            //remove Yoshi from the map and spawn a new YoshiEgg at a random location on the map
            map.removeActor(this);
            YoshiEgg egg = new YoshiEgg();
            egg.spawnEgg(map);
            this.removeCapability(Status.RESET_CAPABLE);
            doNothing = true;
        }

        if (!doNothing) {	//if there is no scenario where the actor does nothing
            for (Behaviour behaviour : behaviours.values()) {
                Action action = behaviour.getAction(this, map);
                if (action != null)
                    return action;
            }
        }
        return new DoNothingAction();
    }

    /**
     * Returns a new collection of the Actions that the otherActor can do to the current Yoshi.
     *
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // allow any actor except the Player to attack Yoshi
        if (!otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this,direction));
        }
        return actions;
    }

    /**
     * Assign a string representation for Yoshi.
     *
     * @return the name of Yoshi and its current hp
     */
    @Override
    public String toString() {
        return super.toString() + this.printHp();
    }

    /**
     * Allows any classes that use this interface to reset abilities, attributes, and/or items.
     */
    @Override
    public void resetInstance() {
        this.addCapability(Status.RESET_CAPABLE);
    }
}
