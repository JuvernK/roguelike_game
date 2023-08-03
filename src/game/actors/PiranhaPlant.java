package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.auxiliary.Status;
import game.behaviours.Behaviour;

public class PiranhaPlant extends Enemy {

  /**
   * Constructor.
   *
   */
  public PiranhaPlant() {
    super("Piranha Plant", 'Y', 150);
  }

  /**
   * Create and return the intrinsic weapon of Piranha Plant.
   *
   * @return a freshly-instantiated IntrinsicWeapon
   */
  @Override
  protected IntrinsicWeapon getIntrinsicWeapon() {
    return new IntrinsicWeapon(90, "chomps");   //50% hit rate and 90 damage
  }

  /**
   * Select and return an action to perform on the current turn.
   *
   * @param actions    collection of possible Actions for this PiranhaPlant
   * @param lastAction The Action this PiranhaPlant took last turn
   * @param map        the map containing the PiranhaPlant
   * @param display    the I/O object to which messages may be written
   * @return the Action to be performed
   */
  @Override
  public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
    boolean doNothing = false;

    if (reset(map)) {    	// determine if Piranha Plant can be reset, then reset it
      doNothing = true;
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
   * Method to implement the reset for PiranhaPlant.
   *
   * @param map the GameMap the PiranhaPlant is on
   * @return a boolean indicating whether instance has been reset
   */
  @Override
  public boolean reset(GameMap map) {
    boolean flag = false;

    if (this.hasCapability(Status.RESET_CAPABLE)) {
      this.resetMaxHp(this.getMaxHp() + 50);  //increase hp by an additional 50 hp
      this.heal(this.getMaxHp());                     //heal to maximum hp
      this.removeCapability(Status.RESET_CAPABLE);
      flag = true;
    }
    return flag;
  }

}
