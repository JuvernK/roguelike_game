package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class TeleportAction extends Action {

    private static Location origin, destination;  //static to allow origin and destination to change whenever Player teleports
    private int mapNumber;  //1 = Forest Zone, 2 = Lava Zone

    /**
     * Constructor to initialize TeleportAction for WarpPipe in Forest Zone.
     *
     * @param origin the current Location of the WarpPipe
     * @param destination the location of the destination WarpPipe
     * @param map an integer to indicate the map the WarpPipe is on
     */
    public TeleportAction(Location origin, Location destination, int map) {
        this.origin = origin;
        this.destination = destination;
        this.mapNumber = map;
    }

    /**
     * Constructor to initialize TeleportAction for WarpPipe in Lava Zone.
     *
     * @param map an integer to indicate the map the WarpPipe is on
     */
    public TeleportAction(int map) {
        this.mapNumber = map;
    }

    /**
     * Perform the teleporting action. The actor on the target WarpPipe location will be removed, then the actor on the
     * current WarpPipe will be teleported to the destination.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        if (mapNumber == 1) {           //Forest Zone
            if (map.isAnActorAt(destination))
                map.removeActor(map.getActorAt(destination));     //kill the Enemy on the destination WarpPipe
            map.moveActor(actor, destination);      //teleport to the destination WarpPipe
        }
        else if (mapNumber == 2) {      //Lava Zone
            if (map.isAnActorAt(origin))
                map.removeActor(map.getActorAt(origin));          //kill the Enemy on the destination WarpPipe
            map.moveActor(actor, origin);           //teleport back to the origin
        }

        return actor + " teleported to the destination Warp Pipe";
    }

    /**
     * Returns a descriptive string.
     *
     * @param actor the actor performing the action
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " teleports using the Warp Pipe he standing on";
    }

}
