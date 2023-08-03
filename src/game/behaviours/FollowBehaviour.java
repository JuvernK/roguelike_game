package game.behaviours;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import game.auxiliary.Status;
import game.jump.JumpAction;
import game.jump.JumpManager;

/**
 * A class that figures out a MoveAction that will move the actor one step 
 * closer to a target Actor.
 */
public class FollowBehaviour implements Behaviour {

	/***
	 * the target to follow
	 */
	private final Actor target;

	/**
	 * Constructor.
	 * 
	 * @param subject the Actor to follow
	 */
	public FollowBehaviour(Actor subject) {
		this.target = subject;
	}

	/**
	 * Allow the actor to follow the target actor.
	 *
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return null or a MoveActorAction if the conditions is met.
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		if (!map.contains(target) || !map.contains(actor))
			return null;

		Location here = map.locationOf(actor);
		Location there = map.locationOf(target);
		int currentDistance = distance(here, there);

		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination(); 	//retrieve the destination
			int newDistance = distance(destination, there);

			if (destination.canActorEnter(actor)) {
				if (newDistance < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}
		return null;
	}

	/**
	 * Compute the Manhattan distance between two locations.
	 * 
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	public int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}

	/**
	 * Retrieve the target.
	 *
	 * @return the target to follow
	 */
	public Actor getTarget(){
		return target;
	}
}