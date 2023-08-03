package game.items;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.auxiliary.Status;

public class Wrench extends WeaponItem {

    /**
     * Constructor, a CRUSH capability is added to its capability set.
     */
    public Wrench() {
        super("Wrench", '?',50 ,"strike ", 80);
        this.addCapability(Status.CRUSH);
    }

}
