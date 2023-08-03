package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.auxiliary.Status;

public class PowerStar extends MagicItem{

    private int turns;      //the lifetime of the PowerStar

    /**
     * Constructor, the remaining turns and status are initialized.
     */
    public PowerStar() {
        super("Power Star", '*', true);
        super.setStatus(Status.IMMUNITY);
        this.turns = 10;
    }

    /***
     * PowerStar on Ground can experience the joy of time, it will be removed from the map after no more turns left.
     *
     * @param currentLocation The location of the PowerStar on the ground
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        turns--;
        if (turns <= 0)
            currentLocation.removeItem(this);
    }

    /***
     * PowerStar in inventory can experience the joy of time, it will be removed from the inventory
     * after no more turns left.
     *
     * @param currentLocation The location of the actor carrying this Item
     * @param actor The actor carrying this Item
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        turns--;
        if (turns <= 0)
            actor.removeItemFromInventory(this);
    }

    /**
     * Assign a string representation for PowerStar.
     *
     * @return The name of Power Star and its remaining turns before it is removed.
     */
    public String toString(){
        return super.toString() + " - " + turns + " turns remaining";
    }

}
