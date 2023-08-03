package game.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Player;

public class Fire extends Item {

    private int remainingTurns;     //lifetime of the Fire

    /***
     * Constructor, initialize the remaining turns.
     */
    public Fire() {
        super("fire", 'v', false);
        remainingTurns = 3;
    }

    /**
     * Inform the Fire on the Ground the passage of time, the actor standing on it will be burned each turn and the
     * Fire instance will be removed from the map after no more turns left.
     *
     * @param currentLocation The location of the Fire.
     */
    @Override
    public void tick(Location currentLocation) {
        //remove the Fire if there is no remaining turns
        if (remainingTurns <= 0) {
            currentLocation.removeItem(this);
        }
        // if the fire Location contains an actor
        if (currentLocation.containsAnActor()) {
            Actor burnedActor = currentLocation.getActor();
            burnedActor.hurt(20);    // deal 20 damage per turn to the actor
            System.out.println(currentLocation.getActor() + " lost 20 hp from the fire");  //description for visibility

            if (!burnedActor.isConscious()) {  //if the actor is not conscious (<= 0 hp)
                if (burnedActor instanceof Player){
                    ((Player) burnedActor).die(currentLocation);    //Player dies
                }
                else {
                    ActionList dropActions = new ActionList();
                    // drop all items from the actor's inventory
                    for (Item item : burnedActor.getInventory())
                        dropActions.add(item.getDropAction(burnedActor));
                    for (Action drop : dropActions)
                        drop.execute(burnedActor, currentLocation.map());
                    currentLocation.map().removeActor(burnedActor);      //remove actor from the map
                }
            }
        }
        remainingTurns--;   //the remaining turns decrease by 1 on each tick
    }
}
