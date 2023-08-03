package game.grounds;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.FlyingKoopa;
import game.auxiliary.Status;
import game.auxiliary.Utils;
import game.actors.Koopa;

/***
 * A Mature tree
 * @see Tree
 */
public class Mature extends Tree {
  /**
   * Fall damage incurred from unsuccessful jump
   */
  private final int FALL_DAMAGE = 30;
  /**
   * Probability of successful jump
   */
  private final double SUCCESS_RATE = 0.7;
  /**
   * Number of turns experienced by mature tree
   */
  private int turns;

  /**
   * Constructor
   *
   */
  public Mature(){
    super('T');
  }

  /**
   * Mature tree can experience the joy of time. If some conditions are met, mature can either, wither and die, spawn a
   * Koopa or FlyingKoopa on its location, or grow new sprout on surrounding fertile grounds
   *
   * @param location The location of the Mature tree
   */
  @Override
  public void tick(Location location) {
    super.tick(location);
    turns++;

    if (!this.hasCapability(Status.HAS_RESET)) {   //if this Sprout has not been reset before
        if (Utils.probability(0.2)) {
            location.setGround(new Dirt());
        }
        if (Utils.probability(0.15) && !location.containsAnActor()) {
            spawnKoopa(location);
        }
        if (turns % 5 == 0 & turns > 0) {
            for (Exit exit : location.getExits()) {
              if (exit.getDestination().getGround().hasCapability(Status.FERTILE) && Utils.probability(Utils.randomProbability()))
                  exit.getDestination().setGround(new Sprout());
            }
        }
    }
  }

  /**
   * Spawns a Koopa or a Flying Koopa at the current location.
   *
   * @param location current location of Mature
   */
  public void spawnKoopa(Location location){
      //has a 50% chance of spawning either Koopa or Flying Koopa
      if (Utils.probability(0.5))
          location.addActor(new Koopa());
      else
          location.addActor(new FlyingKoopa());
  }

  /**
   * Getter for Mature tree fall damage
   *
   * @return FALL_DAMAGE
   */
  @Override
  public int getFallDamage() {
    return FALL_DAMAGE;
  }

  /**
   * Getter for Mature jump success rate
   *
   * @return SUCCESS_RATE
   */
  @Override
  public double getSuccessRate() {
    return SUCCESS_RATE;
  }

  /**
   * Assign a string representation for Mature.
   *
   * @return "Mature", the string representation
   */
  public String toString(){
    return "Mature";
  }

}
