package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropItemAction;
import edu.monash.fit2099.engine.items.Item;
import game.auxiliary.Status;

public class Key extends Item {

    /***
     * Constructor, add a capability to end the game.
     */
    public Key() {
        super("Key", 'k', true);
        this.addCapability(Status.END_GAME);   //will be added to Player's capabilities when Player picks up the Key
    }

}
