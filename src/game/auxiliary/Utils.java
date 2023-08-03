package game.auxiliary;

import java.util.Random;

/**
 * Class that manages probability
 */
public class Utils {

  /**
   * Determine whether an event occurs.
   *
   * @param probabilityOfEvent a double value in range of 0.0 to 1.0, which is the probability that the event happens
   * @return true if the event occurs, false otherwise
   */
  public static boolean probability(double probabilityOfEvent){
    Random rand = new Random();
    double d = rand.nextDouble();
    return d < probabilityOfEvent;
  }

  /**
   * Generate a random probability.
   *
   * @return a random double between 0.0 and 1.0
   */
  public static double randomProbability(){
    Random prob = new Random();
    double d = prob.nextDouble();
    return d;
  }

}
