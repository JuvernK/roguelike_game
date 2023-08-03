package game.reset;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class ResetAction extends Action {

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        ResetManager.getInstance().run();
        return "Reset is successful.";
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text to put on the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Reset the game.";
    }

    /**
     * Returns the key used in the menu to trigger this Action.
     *
     * @return "r", the hotkey
     */
    @Override
    public String hotkey() {
        return "r";
    }
}
