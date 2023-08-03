package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.actors.Enemy;

public class YoshiAttackBehaviour implements Behaviour{

  /**
   * Allow Yoshi to attack enemy when the enemy is located at its exits.
   *
   * @param actor the Actor acting
   * @param map the GameMap containing the Actor
   * @return null or an AttackAction when an attack is possible
   */
  @Override
  public Action getAction(Actor actor, GameMap map) {
    for (Exit exit : map.locationOf(actor).getExits()) {  //for each exit in the available exits
      if (exit.getDestination().containsAnActor()) {    //if the location contains an Actor
        Actor target = exit.getDestination().getActor();

        if (target instanceof Enemy) {
          return new AttackAction(target, exit.getName());
        }
      }
    }
    return null;
  }
}
