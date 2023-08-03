package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Player;
import game.auxiliary.Status;

public class Lava extends Ground {

  /**
   * Constructor.
   */
  public Lava() {
    super('L');
  }

  /**
   * Allow only actors that are hostile to enemy to walk into the Lava's location.
   *
   * @param actor the Actor to check
   * @return true if actor is hostile to enemy
   */
  @Override
  public boolean canActorEnter(Actor actor) {
    return actor.hasCapability(Status.HOSTILE_TO_ENEMY);  //only Player can step on the Lava
  }

  /**
   * Allow Lava to experience the joy of time, and burn any actor standing on it.
   *
   * @param location The location of the Lava
   * @see Player#die(Location)
   */
  @Override
  public void tick(Location location) {
    if (location.containsAnActor()) {
      Actor burnedActor = location.getActor();   //the actor standing on the Lava

      burnedActor.hurt(15);  //inflict 15 damage per turn to the actor
      System.out.println("The floor is Lava!! " + burnedActor + " lost 15 hp by stepping on it"); //description

      if (!burnedActor.isConscious()) {     //if the actor is not conscious (<= 0 hp)
        if (burnedActor instanceof Player)
          ((Player) burnedActor).die(location);         //Player dies
        else
          location.map().removeActor(burnedActor);      //remove actor from the map
      }
    }
  }

    /**
     * Assign a string representation for Lava.
     *
     * @return "Lava", the string representation
     */
    @Override
    public String toString () {
      return "Lava";
    }

}
