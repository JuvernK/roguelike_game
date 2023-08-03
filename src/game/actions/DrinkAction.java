package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Bottle;
import game.items.Water;

public class DrinkAction extends Action {

    private Bottle bottle;

    /**
     * Constructor.
     *
     * @param bottle the Bottle to drink from
     */
    public DrinkAction(Bottle bottle){
        this.bottle = bottle;
    }

    /**
     * Retrieve and remove the water from the bottle, then add the effect of drinking the water to the actor.
     *
     * @param actor The actor performing the action
     * @param map The map the actor is on
     * @return a description of what happened that can be displayed to the user
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Water water = bottle.popFromBottle();
        water.waterEffect(actor);   //add the effects of drinking the water to player

        return actor + " drank " + water + " and he feels more powerful!";
    }

    /**
     * Returns a descriptive string to display on the menu.
     *
     * @param actor the actor performing the action
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " decides to drink from " + bottle;
    }

}
