package game.grounds;

import edu.monash.fit2099.engine.positions.Location;
import game.auxiliary.Status;
import game.auxiliary.Utils;
import game.actors.Goomba;

/***
 * a Sprout object
 * @see Tree
 */
public class Sprout extends Tree {

  /**
   * Fall damage incurred from unsuccessful jump
   */
  private final int FALL_DAMAGE = 10;
  /***
   * Probability of successful jump
   */
  private final double SUCCESS_RATE = 0.9;

  /**
   * Constructor
   *
   */
  public Sprout(){
    super('+');
  }

  /**
   * Sprout can experience the joy of time. If some conditions are met, sprout can either grow into Sapling or
   * spawn a Goomba on its location.
   *
   * @param location The location of the Sprout
   */
  @Override
  public void tick(Location location) {
    super.tick(location);

    if (!this.hasCapability(Status.HAS_RESET)){   //if this Sprout has not been reset before
        if (getAge() == 10) {
            location.setGround(new Sapling());
        }
        if (Utils.probability(0.1) && !location.containsAnActor()) {
            spawnGoomba(location);
        }
    }
  }

  /**
   * Spawn a Goomba at the current location.
   *
   * @param location current location of Sprout
   */
  public void spawnGoomba(Location location){
    location.addActor(new Goomba());
  }

  /**
   * Getter for Sprout fall damage.
   *
   * @return FALL_DAMAGE
   */
  @Override
  public int getFallDamage() {
    return FALL_DAMAGE;
  }

  /**
   * Getter for Sprout jump success rate.
   *
   * @return SUCCESS_RATE
   */
  @Override
  public double getSuccessRate() {
    return SUCCESS_RATE;
  }

  /**
   * Assign a string representation for Sprout.
   *
   * @return "Sprout", the string representation
   */
  public String toString(){
    return "Sprout";
  }
}
