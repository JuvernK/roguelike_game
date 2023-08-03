package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.RescueAction;
import game.auxiliary.Status;

public class PrincessPeach extends Actor {

    /**
     * Constructor.
     *
     */
    public PrincessPeach() { super("Princess Peach", 'P', 99999); }

    /**
     * Returns a new collection of the Actions that the otherActor can do to the current PrincessPeach.
     *
     * @param otherActor the Actor that might be performing a RescueAction
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of Actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        if (otherActor.hasCapability(Status.END_GAME)) {   //otherActor possesses a key
            actions.add(new RescueAction());
        }
        return actions;
    }

    /**
     * Return a DoNothingAction each turn, since PrincessPeach does not need to do anything by its own.
     *
     * @param actions    collection of possible Actions for PrincessPeach
     * @param lastAction The Action PrincessPeach took last turn
     * @param map        the map containing the PrincessPeach
     * @param display    the I/O object to which messages may be written
     * @return DoNothingAction
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

}
