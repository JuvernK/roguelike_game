package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.auxiliary.Status;
import game.items.MagicItem;

public class ConsumeAction extends Action {

    private MagicItem targetItem; //MagicItem to be consumed

    /**
     * Constructor, initializes the item to be consumed.
     *
     * @param item the target item to consume
     */
    public ConsumeAction (MagicItem item){
        this.targetItem = item;
    }

    /**
     * Consume a MagicItem, and add capability to the actor by retrieving the item's status.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the description of the action when player has uses ConsumeAction.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        //actor add capabilities through getStatus() method which provide the buff.
        actor.addCapability(this.targetItem.getStatus());
        targetItem.buff(actor);   //add buff (if any) to player
        String result = actor + " has consumed " + targetItem;

        if (actor.getInventory().contains(this.targetItem)) {      //if targetItem in inventory
            actor.removeItemFromInventory(targetItem);
            result += " in inventory";
        }

        else if (map.locationOf(actor).getItems().contains(this.targetItem)){  //if targetItem on ground
            map.locationOf(actor).removeItem(this.targetItem);
            result += " from the ground";
        }

        if (this.targetItem.getStatus() == Status.IMMUNITY) {  //if targetItem is a Power Star
            if (actor.hasCapability(Status.HOSTILE_TO_ENEMY))  //if actor is a Player
                ((Player) actor).resetImmuneTurns();    //reset the immune turns in Player to 10 turns
        }

        return result;
    }

    /**
     * Returns a descriptive string to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player decides to consume SuperMushroom"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " decides to consume " + targetItem;
    }

}
