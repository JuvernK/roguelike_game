package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.RefillAction;
import game.actors.Player;
import game.items.Bottle;
import game.items.PowerWater;

public class PowerFountain extends Ground {

    /**
     * Constructor.
     */
    public PowerFountain() {
        super('A');
    }

    /**
     * Return a list of actions that the actor can perform on the current PowerFountain.
     *
     * @param actor the Actor acting
     * @param location the current Location of the PowerFountain
     * @param direction the direction of the PowerFountain from the Actor
     * @return a new list of actions that the actor can perform
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        if (actor instanceof Player && location.getActor() ==actor) {
            Bottle bottle = ((Player) actor).getBottle();       //obtain the bottle from player's inventory
            actions.add(new RefillAction(bottle, new PowerWater()));
        }

        return actions;
    }

    /**
     * Allow PowerFountain to block thrown objects.
     *
     * @return true
     */
    @Override
    public boolean blocksThrownObjects() {
        return true;
    }

    /**
     * Assign a string representation for PowerFountain.
     *
     * @return "Power Fountain", the string representation
     */
    public String toString(){
        return "Power Fountain";
    }

}
