package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.AttackAction;
import game.auxiliary.Utils;
import game.behaviours.Behaviour;
import game.auxiliary.Status;
import game.behaviours.WanderBehaviour;

/**
 * A little fungus guy.
 */
public class Goomba extends Enemy {

	/**
	 * Constructor, add a new WanderBehaviour to behaviours list.
	 */
	public Goomba() {
		super("Goomba", 'g', 20);
		getBehaviours().put(10, new WanderBehaviour());   //add a WanderBehaviour to Goomba behaviours list
	}

	/**
	 * Creates and returns an intrinsic weapon.
	 *
	 * @return an intrinsic weapon for Goomba
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(10, "kick");   //Hit Rate=50% is default in the IntrinsicWeapon class
	}

	/**
	 * Select and return an action to perform on the current turn.
	 *
	 * @see Actor#playTurn(ActionList, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		boolean doNothing = false;

		if (reset(map)) {    	// determine if Goomba can be reset, then reset Goomba
			doNothing = true;
		}

		if (Utils.probability(0.1)) {   		// 10% chance to suicide every turn
			map.removeActor(this);
			display.println("Goomba kills itself :(");
			doNothing = true;
		}

		if (lastAction instanceof AttackAction) {	//determine if lastAction is an AttackAction
			this.addFollowBehaviour();				//add a new FollowBehaviour to behaviours list
		}

		if (!doNothing) {	//if there is no scenario where the actor does nothing
			for (Behaviour behaviour : getBehaviours().values()) {
				Action action = behaviour.getAction(this, map);
				if (action != null)
					return action;
			}
		}
		return new DoNothingAction();
	}

	/**
	 * Method to implement the reset.
	 *
	 * @param map the GameMap Goomba is on
	 * @return a boolean indicating whether instance has been reset
	 */
	@Override
	public boolean reset(GameMap map) {	//remove Goomba from the GameMap from it can be reset
		boolean flag = false;

		if (this.hasCapability(Status.RESET_CAPABLE)) {
			map.removeActor(this);		//remove - kill the Goomba
			this.removeCapability(Status.RESET_CAPABLE);
			flag = true;
		}
		return flag;
	}

}
