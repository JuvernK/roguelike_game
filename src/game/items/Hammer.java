package game.items;

import edu.monash.fit2099.engine.weapons.WeaponItem;

public class Hammer extends WeaponItem {

    /**
     * Constructor, initializes the name, display character, damage, verb and hit rate.
     */
    public Hammer() {
        super("Hammer", '%', 60, "hits", 90);
    }

}
