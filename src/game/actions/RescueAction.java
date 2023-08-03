package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class RescueAction extends Action {

    /**
     * Perform the action of rescuing the princess, the actor executing this action is removed from the map.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);     //remove actor (Player) from the map -> Game over

        String result = "Mario: I saved-a Princess Peach, and I got-a Bowser while I was-a there. \n" +
                "Congratulations! Mario has successfully defeated Bowser and rescued the Princess.";
        return result;
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Rescue the princess!!!";
    }
}
