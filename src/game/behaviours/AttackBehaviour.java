package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.auxiliary.Status;

public class AttackBehaviour implements Behaviour{

    private Actor target;

    /**
     * Allow enemy to attack player when the player is located at its exits.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return null or an AttackAction when an attack is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        for (Exit exit : map.locationOf(actor).getExits()) {  //for each exit in the available exits
            if (exit.getDestination().containsAnActor()) {    //if the location contains an Actor
                this.target = exit.getDestination().getActor();

                // if target can be attacked (by player)
                if (target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                    return new AttackAction(target, exit.getName());
                }
            }
        }
        return null;
    }

    /**
     * Retrieve the target.
     *
     * @return the target to attack
     */
    public Actor getTarget(){    //method to return the target
        return this.target;
    }

}
