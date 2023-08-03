package game.grounds;

import edu.monash.fit2099.engine.positions.Location;
import game.auxiliary.Status;
import game.items.Coin;
import game.auxiliary.Utils;

/***
 * a Sapling object
 * @see Tree
 */
public class Sapling extends Tree {
  /**
   * Fall damage incurred from unsuccessful jump
   */
  private final int FALL_DAMAGE = 20;
  /***
   * Probability of successful jump
   */
  private final double SUCCESS_RATE = 0.8;

  /**
   * Constructor
   *
   */
  public Sapling(){
    super('t');
  }

  /**
   * Sapling can experience the joy of time. If some conditions are met, sapling can either, grow into mature tree or
   * spawn a coin in its location.
   *
   * @param location The location of the Sapling
   */
  public void tick(Location location) {
    super.tick(location);

    if (!this.hasCapability(Status.HAS_RESET)) {   //if this Sprout has not been reset before
        if (getAge() == 20) {
            location.setGround(new Mature());
        }
        if (Utils.probability(0.1)) {
            spawnCoin(location);
        }
    }
  }

  /**
   * Spawn a Coin at the current location.
   *
   * @param location current location of Sapling
   */
  public void spawnCoin(Location location){
    location.addItem(new Coin(20));
  }

  /**
   * Getter for Sapling fall damage.
   *
   * @return FALL_DAMAGE
   */
  @Override
  public int getFallDamage() {
    return FALL_DAMAGE;
  }

  /**
   * Getter for Sapling jump success rate.
   *
   * @return SUCCESS_RATE
   */
  @Override
  public double getSuccessRate() {
    return SUCCESS_RATE;
  }

  /**
   * Assign a string representation for Sapling.
   *
   * @return "Sapling", the string representation
   */
  public String toString(){
    return "Sapling";
  }
}
