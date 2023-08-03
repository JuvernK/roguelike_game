package game.actions;

import game.actors.Yoshi;
import game.items.YoshiEgg;
import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actors.Bowser;
import game.actors.Player;
import game.auxiliary.Status;
import game.items.Fire;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * The direction of incoming attack.
	 */
	protected String direction;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 * @param direction the direction of incoming attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	/**
	 * Perform the Action.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		String result;
		Weapon weapon = actor.getWeapon();
		int damage = weapon.damage();

		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		//when Bowser attack is successful, add a fire on the ground where the target stands
		fireAttack(actor, map);

		//if actor is not immune
		if (!actor.hasCapability(Status.IMMUNITY)) {

			if (!target.hasCapability(Status.IMMUNITY)) {
				//only hurt target if it has no immunity
				target.hurt(damage);
				result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

				//remove the TALL status of target
				if (target.hasCapability(Status.TALL))
					target.removeCapability(Status.TALL);
			}
			else
				result = actor + " attack is useless...";
		}
		else {
			target.hurt(9999);   //damage the target higher than its max hp
			result = actor + " kills " + target + " immediately!";   //all attacks are useless if target has immunity
		}

		//when Player is carrying the Yoshi Egg
		if (target.hasCapability(Status.EGG)){
			target.addCapability(Status.NOSHI);
			result += " Oops! Yoshi Egg is broken D: respawning...";
		}

		if (!target.isConscious()) {
			ActionList dropActions = new ActionList();
			// drop all items
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction(actor));
			for (Action drop : dropActions)
				drop.execute(target, map);

			// remove actor if not Koopa
			if (target.hasCapability(Status.SHELL)){
				result += System.lineSeparator() + target + " goes into dormant state.";
			}
			else {
				if (target instanceof Player){
					((Player) target).die(map.locationOf(target));		//Player dies
				}
				else if (target instanceof Yoshi) {
					map.removeActor(target);
					YoshiEgg egg = new YoshiEgg();
					egg.spawnEgg(map);		//spawn a new YoshiEgg at a random Location on the map
					result += " Oops! Yoshi died D: respawning egg...";
				}
				else {
					map.removeActor(target);
					result += System.lineSeparator() + target + " is killed.";
				}
			}
		}
		return result;
	}

	/**
	 * To add a Fire at the Location of target attacked. Only Bowser can drop a Fire.
	 *
 	 * @param actor the actor attacking
	 * @param map the map the actor is on
	 */
	public void fireAttack(Actor actor, GameMap map) {
		if (actor instanceof Bowser) {
			map.locationOf(target).addItem(new Fire());  	//add a Fire at target's location when Bowser attacks
		}
	}

	/**
	 * Returns a descriptive string.
	 *
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target + " at " + direction;
	}

}
