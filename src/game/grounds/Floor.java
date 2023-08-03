package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.auxiliary.Status;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

	/**
	 * Constructor.
	 */
	public Floor() {
		super('_');
	}

	/**
	 * Allow only actors that are hostile to enemy to walk into the Floor's location.
	 *
	 * @param actor the Actor to check
	 * @return true if actor is hostile to enemy
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		//only Player, Yoshi and FlyingKoopa can step on the Floor
		boolean canEnter = actor.hasCapability(Status.HOSTILE_TO_ENEMY) || actor.hasCapability(Status.FLY);
		return canEnter;
	}

	/**
	 * Assign a string representation for Floor.
	 *
	 * @return "Floor", the string representation
	 */
	@Override
	public String toString() {
		return "Floor";
	}

}
