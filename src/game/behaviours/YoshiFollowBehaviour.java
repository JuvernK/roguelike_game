package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.jump.JumpAction;
import game.jump.JumpManager;

public class YoshiFollowBehaviour extends FollowBehaviour {

  /**
   * Constructor matching the superclass.
   *
   * @param actor the Actor to follow
   */
  public YoshiFollowBehaviour(Actor actor) {
    super(actor);
  }

  /**
   * Allow partner to follow the target actor and jump wherever actor jumps.
   *
   * @param actor the Actor acting
   * @param map the GameMap containing the Actor
   * @return null or MoveActorAction or JumpAction if the conditions is met.
   * @see FollowBehaviour#distance(Location, Location)
   */
  @Override
  public Action getAction(Actor actor, GameMap map) {
    if (!map.contains(getTarget()) || !map.contains(actor)) //map does not contain actor and target
      return null;

    Location here = map.locationOf(actor);
    Location there = map.locationOf(getTarget());
    int currentDistance = distance(here, there);

    for (Exit exit : here.getExits()) {
      Location destination = exit.getDestination(); 	//retrieve the destination
      Ground targetToJump = destination.getGround(); 	//retrieve the ground of the destination
      int newDistance = distance(destination, there);

      if (destination.canActorEnter(actor)) {
        if (newDistance < currentDistance) {
          return new MoveActorAction(destination, exit.getName());
        }
      }
      //if the ground is jumpable
      else if (JumpManager.getInstance().getJumpables().contains(targetToJump)) {
        if (newDistance < currentDistance) {
          return new JumpAction(destination, exit.getName());
        }
      }
    }
    return null;
  }
}


