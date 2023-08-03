package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import game.auxiliary.Status;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	/**
	 * Constructor, add a FERTILE capability to Dirt.
	 */
	public Dirt() {
		super('.');
		this.addCapability(Status.FERTILE);
	}

	/**
	 * Assign a string representation for Dirt.
	 *
	 * @return "Dirt", the string representation
	 */
	@Override
	public String toString() {
		return "Dirt";
	}
}
