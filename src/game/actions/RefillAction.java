package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.*;

public class RefillAction extends Action {

    private Bottle magicBottle;      //the bottle to refill the water
    private Water targetWater;       //the water provided by the fountain

    /**
     * Constructor.
     *
     * @param magicBottle the Bottle to fill up
     * @param water the Water to fill into the magicBottle
     */
    public RefillAction(Bottle magicBottle, Water water) {
        this.magicBottle = magicBottle;
        this.targetWater = water;
    }

    /**
     * Add the water into the Bottle.
     *
     * @param actor the actor performing the action
     * @param map the map the actor is on
     * @return a description of what happened that can be displayed to the user
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        magicBottle.addToBottle(targetWater);

        return actor + " refilled bottle with " + targetWater;
    }

    /**
     * Returns a descriptive string to display on the menu.
     *
     * @param actor the actor performing the action
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " refills his bottle with " + targetWater;
    }

}
