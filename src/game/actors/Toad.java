package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.SpeakAction;
import game.actions.TradeAction;
import game.auxiliary.Status;

public class Toad extends Actor {

    /**
     * Constructor.
     */
    public Toad() {
        super("Toad", 'O', 100);
    }

    /**
     * Returns a new collection of the Actions that the otherActor can do to Toad.
     *
     * @param otherActor the Actor that might be trading or speaking
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of Actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actionList = new ActionList();

        if (otherActor.hasCapability(Status.TRADABLE)) {
            for (int i = 0; i < 3; i++) {
                TradeAction tradeAction = new TradeAction(i);
                actionList.add(tradeAction);   //add 3 TradeActions with 3 separate hotkeys & menu descriptions
            }
        }
        if (otherActor.hasCapability(Status.SPEAKABLE)) {
            actionList.add(new SpeakAction(this));
        }
        return actionList;
    }

    /**
     * Return a DoNothingAction each turn, since Toad does not need to do anything by its own.
     *
     * @param actions    collection of possible Actions for Toad
     * @param lastAction The Action Toad took last turn
     * @param map        the map containing the Toad
     * @param display    the I/O object to which messages may be written
     * @return DoNothingAction
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

}
