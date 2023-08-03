package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.actions.ConsumeAction;
import game.auxiliary.Status;

/**
 * Abstract base class for MagicItem
 */
public abstract class MagicItem extends Item {

    private boolean consumable;
    private Status status = null;          //the Status that the MagicItem holds

    /***
     * Constructor, initialize the consumability of the item, add a ConsumeAction if it is consumable.
     *
     * @param name the name of the MagicItem
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param consumable true if the item can be consumed using ConsumeAction
     */
    public MagicItem(String name, char displayChar, boolean consumable) {
        super(name, displayChar, false);     //portable returns false to prevent MagicItem from being pickup up and dropped
        this.consumable = consumable;
        this.isConsumable();   //add a new ConsumeAction if it is consumable
    }

    /**
     * If the item is consumable, the player will be allowed to perform ConsumeAction when the player stands
     * above the item.
     */
    public void isConsumable(){
        if (consumable)
            this.addAction(new ConsumeAction(this));
    }

    /**
     * Getter for the Status of the MagicItem.
     *
     * @return status of the MagicItem
     */
    public Status getStatus(){
        return status;
    }

    /**
     * Setter for the Status of the MagicItem.
     *
     * @param status status of the MagicItem
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * To provide a buff to the actor.
     *
     * @param actor the actor to give buff to
     */
    public void buff(Actor actor) { }

}
