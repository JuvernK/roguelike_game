package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import game.actors.PiranhaPlant;
import game.auxiliary.Status;
import game.jump.JumpAction;
import game.jump.Jumpable;
import game.reset.Resettable;

public class WarpPipe extends Ground implements Jumpable, Resettable {

  private int turn = 0;
  private Location destination;
  private int mapNumber;      //to indicate which map the WarpPipe is on (1 = Forest, 2 = Lava)

  /**
   * Constructor to initialize WarpPipe in Forest Zone, the destination is specified since there is only one destination to go.
   *
   * @param destination location of destination WarpPipe
   */
  public WarpPipe(Location destination) {   //initialize WarpPipe in forestZone
    super('C');
    this.destination = destination;
    mapNumber = 1;
    this.registerInstance();    //add to reset manager
    this.addToJumpableList();
  }

  /**
   * Constructor to initialize WarpPipe in Lava Zone, since there is no specific destination to teleport.
   */
  public WarpPipe() {
    super('C');
    mapNumber = 2;
    this.registerInstance();    //add to reset manager
    this.addToJumpableList();
  }

  /**
   * Allow WarpPipe to experience the joy of time. Spawn a PiranhaPlant on its location in the second turn of the game or
   * when WarpPipe is reset.
   *
   * @param location The location of the WarpPipe
   */
  @Override
  public void tick(Location location) {
    turn++;
    if (turn == 1) {  //on the second turn
      location.addActor(new PiranhaPlant());
    }

    if (this.hasCapability(Status.RESET_CAPABLE)) {   //spawn a new Piranha Plant when it has been reset
      if (!location.containsAnActor()) {  //if there is no actor on that location
        location.addActor(new PiranhaPlant());
      }
      this.removeCapability(Status.RESET_CAPABLE);
    }
  }

  /**
   * Return a list of actions that the actor can perform on the current WarpPipe.
   *
   * @param actor the Actor acting
   * @param location the current Location of the WarpPipe
   * @param direction the direction of the WarpPipe from the Actor
   * @return a new list of actions that the actor can perform
   */
  @Override
  public ActionList allowableActions(Actor actor, Location location, String direction) {
    ActionList actions = new ActionList();

    if (location.containsAnActor()) {     //if the actor is on the WarpPipe
      if (actor.hasCapability(Status.HOSTILE_TO_ENEMY)) {     //if the actor is Player or Yoshi

        if (mapNumber == 1)       //pipe is in Forest Zone
          actions.add(new TeleportAction(location, destination, mapNumber));

        else if (mapNumber == 2)  //pipe is in Lava Zone
          actions.add(new TeleportAction(mapNumber));
      }
    }
    else {
      actions.add(new JumpAction(location, direction));
    }
    return actions;
  }

  /**
   * Allow only actors that can fly to walk into the WarpPipe's location.
   *
   * @param actor the Actor to check
   * @return true if actor can fly, false otherwise
   */
  @Override
  public boolean canActorEnter(Actor actor) {
    return actor.hasCapability(Status.FLY);            //only FlyingKoopa can enter normally
  }

  /**
   * Allow WarpPipe to be reset.
   */
  @Override
  public void resetInstance() {
    this.addCapability(Status.RESET_CAPABLE);
  }

  /**
   * Retrieve the fall damage when jump fails.
   *
   * @return 0, no fall damage
   */
  @Override
  public int getFallDamage() {
    return 0;
  }

  /***
   * Retrieve the jump success rate, jump is always successful after the PiranhaPlant spawned.
   *
   * @return 0 at the start of the game, 1 after PiranhaPlant spawned
   */
  @Override
  public double getSuccessRate() {
    if (turn == 0)
      return 0;
    else
      return 1;
  }

  /**
   * Assign a string representation for WarpPipe.
   * @return "Warp Pipe", the string representation
   */
  @Override
  public String toString() {
    return "Warp Pipe";
  }

}
