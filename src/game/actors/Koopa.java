package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.CrushAction;
import game.actions.AttackAction;
import game.auxiliary.Status;
import game.behaviours.*;

public class Koopa extends Enemy {

  /**
   * Constructor with no parameter, a new WanderBehaviour and SHELL capability is added.
   */
  public Koopa() {
    super("Koopa", 'K', 100);
    getBehaviours().put(10, new WanderBehaviour());   //add a WanderBehaviour to Koopa behaviours list
    this.addCapability(Status.SHELL);
  }

  /**
   * Constructor to initialize Koopa based on the given parameters.
   *
   * @param name the name to be assigned to the Koopa
   * @param displayChar the character that will represent the Koopa in the display
   * @param hitPoints the starting hit points for Koopa
   */
  public Koopa(String name, char displayChar, int hitPoints) {
    super(name, displayChar, hitPoints);
    getBehaviours().put(10, new WanderBehaviour());   //add a WanderBehaviour to Koopa behaviours list
    this.addCapability(Status.SHELL);
  }

  /**
   * Creates and returns an intrinsic weapon for Koopa
   *
   * @return a new IntrinsicWeapon
   */
  @Override
  public IntrinsicWeapon getIntrinsicWeapon() {
    return new IntrinsicWeapon(30, "punches");
  }

  /**
   * Returns a new collection of the Actions that the otherActor can do to Koopa.
   *
   * @param otherActor the Actor that might be performing attack
   * @param direction  String representing the direction of the other Actor
   * @param map        current GameMap
   * @return actions that can be done to Koopa
   */
  @Override
  public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
    ActionList actions = new ActionList();

    // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
    if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
        if (this.isConscious()) {
          actions.add(new AttackAction(this, direction));
        }
    }
    //If Koopa has a DORMANT status
    if (this.hasCapability(Status.DORMANT)) {
        if (otherActor.hasCapability(Status.CRUSH))     //If otherActor(Player) has CRUSH status
            actions.add(new CrushAction(this, direction));
        else {
            actions.add(new DoNothingAction());
        }
    }
    return actions;
  }

  /**
   * Select and return an action to perform on the current turn.
   *
   * @param actions    collection of possible Actions for Koopa
   * @param lastAction The Action Koopa took last turn
   * @param map        the map containing the Koopa
   * @param display    the I/O object to which messages may be written
   * @return actions that can be performed by Koopa
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    boolean doNothing = false;

    if (reset(map)) {    	// determine if Koopa can be reset, then reset Koopa
      doNothing = true;
    }

    //if Koopa is not conscious & does not have DORMANT status
    if (!this.isConscious() && !this.hasCapability(Status.DORMANT)) {
      this.addCapability(Status.DORMANT);
      this.setDisplayChar('D');
      doNothing = true;
    }

    if (lastAction instanceof AttackAction) {	//determine if lastAction is an AttackAction
      this.addFollowBehaviour();				//add a new FollowBehaviour to behaviours list
    }

    if (!doNothing) {    //if there is no scenario where the actor does nothing
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
   * @param map the GameMap Koopa is on
   * @return a boolean indicating whether instance has been reset
   */
  @Override
  public boolean reset(GameMap map) {	//remove Koopa from the GameMap from it can be reset
    boolean flag = false;

    if (this.hasCapability(Status.RESET_CAPABLE)) {
      map.removeActor(this);      //remove - kill the Koopa
      this.removeCapability(Status.RESET_CAPABLE);
      flag = true;
    }
    return flag;
  }

}
