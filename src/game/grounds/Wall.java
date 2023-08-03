package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DestroyAction;
import game.auxiliary.Status;
import game.jump.JumpAction;
import game.jump.Jumpable;

/**
 * a Wall object
 * @see Ground
 * @see Jumpable
 */
public class Wall extends Ground implements Jumpable {

	/**
	 * Fall damage incurred from unsuccessful jump
	 */
	private final int FALL_DAMAGE = 20;
	/**
	 * Probability of successful jump
	 */
	private final double SUCCESS_RATE = 0.8;

	/**
	 * Constructor, add Wall to list of jumpable objects
	 *
	 */
	public Wall() {
		super('#');
		this.addToJumpableList();
	}

	/**
	 * Allow only actors that can fly to walk into the Wall's location.
	 *
	 * @param actor the Actor to check
	 * @return true if actor can fly, false otherwise
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return actor.hasCapability(Status.FLY);			//only FlyingKoopa can enter normally
	}

	/**
	 * Allow Wall to block all thrown objects.
	 *
	 * @return true
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	/**
	 * Return a list of actions that can be done towards Wall.
	 *
	 * @param actor the Actor acting
	 * @param location the current Location of Wall
	 * @param direction the direction of the Wall from the Actor
	 * @return list of actions that can be performed to Wall
	 */
	@Override
	public ActionList allowableActions(Actor actor, Location location, String direction) {
		ActionList actions = new ActionList();

		if (location.containsAnActor()){
			return actions;
		}

		if (actor.hasCapability(Status.IMMUNITY))
			actions.add(new DestroyAction(location, direction));
		else
			actions.add(new JumpAction(location, direction));

		return actions;
	}

	/**
	 * Getter for Wall's fall damage.
	 *
	 * @return FALL_DAMAGE
	 */
	@Override
	public int getFallDamage() {
		return FALL_DAMAGE;
	}

	/**
	 * Getter for Wall jump success rate.
	 *
	 * @return SUCCESS_RATE
	 */
	@Override
	public double getSuccessRate() {
		return SUCCESS_RATE;
	}

	/**
	 * Assign a string representation for Wall.
	 *
	 * @return "Wall", the string representation
	 */
	public String toString(){
		return "Wall";
	}

}
