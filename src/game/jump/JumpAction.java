package game.jump;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.auxiliary.Status;
import game.auxiliary.Utils;

/**
 * An action that lets the Player jump to high ground
 * @see Action
 */
public class JumpAction extends Action {

  /**
   * Target location
   */
  protected Location jumpToLocation;
  /**
   * One of the 8-d navigation
   */
  protected String direction;
  /**
   * Or the command key
   */
  protected String hotKey;

  /**
   * Constructor, initializes the destination, direction and hotkey.
   *
   * @param jumpToLocation, the destination of the jump
   * @param direction, the direction of the jump
   */
  public JumpAction(Location jumpToLocation, String direction, String hotKey){
    this.jumpToLocation = jumpToLocation;
    this.direction = direction;
    this.hotKey = hotKey;
  }

  /**
   * Constructor, initializes the destination and direction.
   *
   * @param jumpToLocation, the destination of the jump
   * @param direction, the direction of the jump
   */
  public JumpAction(Location jumpToLocation, String direction){
    this.jumpToLocation = jumpToLocation;
    this.direction = direction;
    this.hotKey = null;
  }

  /**
   * Perform the action. Allow the actor to jump to a Jumpable ground.
   *
   * @param actor The actor performing the action.
   * @param map The map the actor is on.
   * @return a String message whether the player has jumped successfully or failed
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    Jumpable ground = JumpManager.getInstance().getJumpable(jumpToLocation.getGround());
    String message;

    if (!actor.hasCapability(Status.TALL) && Utils.probability(1 - ground.getSuccessRate())) {
      actor.hurt(ground.getFallDamage());
      message =  actor + " jumped failed...\n" + actor + " loses " + ground.getFallDamage() + "HP";
    }
    else {
      map.moveActor(actor, jumpToLocation);
      message = actor + " jumped successfully!";
    }
    return message;
  }

  /**
   * Print a menu description to execute JumpAction.
   *
   * @param actor The actor performing the action.
   * @return a String description of the action
   */
  @Override
  public String menuDescription(Actor actor) {
    return actor + " jumps " + direction + " to " + jumpToLocation.getGround() +" (" + jumpToLocation.x() + "," + jumpToLocation.y() + ")";
  }

}
