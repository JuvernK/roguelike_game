package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Dirt;
import game.items.Coin;

public class DestroyAction extends Action{
  /**
   * Target location
   */
  protected Location moveToLocation;
  /**
   * One of the 8-d navigation
   */
  protected String direction;
  /**
   * Or the command key
   */
  protected String hotKey;

  /**
   * Constructor to create an Action that will move the Actor to a Location and destroy it in a given Direction, using
   * a given menu hotkey.
   *
   * Note that this constructor does not check whether the supplied Location is actually in the given direction
   * from the Actor's current location.  This allows for (e.g.) teleporters, etc.
   *
   * @param moveToLocation the destination of the move
   * @param direction the direction of the move (to be displayed in menu)
   * @param hotKey the menu hotkey for this move
   */
  public DestroyAction(Location moveToLocation, String direction, String hotKey) {
    this.moveToLocation = moveToLocation;
    this.direction = direction;
    this.hotKey = hotKey;
  }

  /**
   * Constructor to create an Action that will move the Actor to a Location and destroy it in a given Direction.
   * A currently-unused menu hotkey will be assigned.
   *
   * Note that this constructor does not check whether the supplied Location is actually in the given direction
   * from the Actor's current location.  This allows for (e.g.) teleporters, etc.
   *
   * @param moveToLocation Location to move to
   * @param direction String describing the direction to move in, e.g. "north"
   */
  public DestroyAction(Location moveToLocation, String direction) {
    this.moveToLocation = moveToLocation;
    this.direction = direction;
    this.hotKey = null;
  }

  /**
   * Allow the Actor to be moved.
   *
   * @param actor The actor performing the action.
   * @param map The map the actor is on.
   * @return a description of the Action suitable for the menu
   * @see Action#execute(Actor, GameMap)
   */
  @Override
  public String execute(Actor actor, GameMap map) {
    map.moveActor(actor, moveToLocation);
    moveToLocation.setGround(new Dirt());
    moveToLocation.addItem(new Coin(5));
    return menuDescription(actor);
  }

  /**
   * Return a description to display in the menu.
   *
   * @param actor The actor performing the action.
   * @return a String, e.g. "Player moves east"
   */
  @Override
  public String menuDescription(Actor actor) {
    return actor + " moves " + direction;
  }

  /**
   * Returns this Action's hotkey.
   *
   * @return the hotkey
   */
  @Override
  public String hotkey() {
    return hotKey;
  }

}
