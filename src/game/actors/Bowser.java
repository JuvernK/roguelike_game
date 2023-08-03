package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.auxiliary.Status;
import game.behaviours.Behaviour;
import game.items.Key;

import java.util.Random;

public class Bowser extends Enemy {

    private Location initialLocation;   //reference to the initial Location where Bowser stands when game starts
    private Random random = new Random();

    /**
     * Constructor, initialize the starting location and adds to a Key to its inventory.
     *
     */
    public Bowser(Location location) {
        super("Bowser", 'B', 500);
        this.addItemToInventory(new Key());            //key to drop when Bowser dies
        initialLocation = location;
    }

    /**
     * Creates and returns the intrinsic weapon of Bowser.
     *
     * @return a freshly-instantiated IntrinsicWeapon
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(80, "punch");   //50% hit rate and 80 damage
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for Bowser
     * @param lastAction The Action Bowser took last turn.
     * @param map        the map containing the Bowser
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn (ActionList actions, Action lastAction, GameMap map, Display display) {
        boolean doNothing = false;

        if (reset(map)) {    	// determine if Bowser can be reset, then reset Bowser
            doNothing = true;
        }

        if (lastAction instanceof AttackAction){	//determine if lastAction is an AttackAction
            this.addFollowBehaviour();				//add a new FollowBehaviour to behaviours list
        }

        if (!doNothing) {	//if there is no scenario where the actor does nothing
            for (Behaviour behaviour : getBehaviours().values()) {
                Action action = behaviour.getAction(this, map);
                if (action != null)
                    return action;
            }
        }
        return new DoNothingAction();
    }

    /**
     * Method to implement the reset.
     *
     * @param map the GameMap Bowser is on
     * @return a boolean indicating whether this instance has been reset
     */
    @Override
    public boolean reset(GameMap map) {
        boolean flag = false;

        if (this.hasCapability(Status.RESET_CAPABLE)) {
            //check if there is an actor at the initial location
            if (!map.isAnActorAt(initialLocation)) {
                map.moveActor(this, initialLocation);     //move Bowser back to original position
            }
            else {
                //if the actor at the initial Location is not Bowser
                if (map.getActorAt(initialLocation) != this) {
                    Location randomLocation = map.at(3, 3);
                    int random_x, random_y;
                    while (map.isAnActorAt(randomLocation)) { //keep looping until obtain a location with no actor on it
                        //choose a random Location with an x-coordinate in range of (1-20) and a y-coordinate in range of (1-10)
                        random_x = random.nextInt(20) + 1;
                        random_y = random.nextInt(10) + 1;
                        randomLocation = map.at(random_x, random_y);
                    }
                    map.moveActor(map.getActorAt(initialLocation), randomLocation); //move the actor to a random Location
                    map.moveActor(this, initialLocation);   //move Bowser back to its original position
                }
            }
            this.heal(this.getMaxHp());             //heal to maximum hp
            this.getBehaviours().remove(7);     //remove FollowBehaviour from behaviours list
            this.removeCapability(Status.RESET_CAPABLE);
            flag = true;
        }
        return flag;
    }

}
