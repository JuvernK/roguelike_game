package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Koopa;
import game.items.SuperMushroom;

public class CrushAction extends Action {

    private String direction;
    private Koopa target;   //the Koopa to be crushed

    /**
     * Constructor, initializes the direction and target to attack.
     *
     * @param actor the target to perform crush action
     * @param direction the direction of the target
     */
    public CrushAction(Koopa actor, String direction) {
        this.target = actor;
        this.direction = direction;
    }

    /**
     * Remove the target and create a SuperMushroom at the target's location
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the description of the action when player has performed CrushAction
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location targetLocation = map.locationOf(target);   //retrieve the location of the target
        map.removeActor(target);    //remove target from the map
        targetLocation.addItem(new SuperMushroom());   //add a new SuperMushroom at the target's location
        return actor + " crushed "+ target;
    }

    /**
     * Returns a descriptive string.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player crushes Koopa at North"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " crushes " + target + " at " + direction;
    }

}
