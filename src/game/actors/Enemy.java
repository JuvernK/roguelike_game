package game.actors;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.auxiliary.Status;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.behaviours.FollowBehaviour;
import game.reset.Resettable;

import java.util.HashMap;
import java.util.Map;

public abstract class Enemy extends Actor implements Resettable {

  private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour
  private Actor target;   //the target to attack

  /**
   * Constructor, add a new AttackBehaviour and register the instance to the Reset Manager.
   *
   * @param name the name of the Enemy
   * @param displayChar the character that will represent the Enemy in the display
   * @param hitPoints the Enemy's starting hit points
   */
  public Enemy(String name, char displayChar, int hitPoints){
    super(name, displayChar, hitPoints);
    this.behaviours.put(5, new AttackBehaviour());      //all enemies have AttackBehaviour
    this.registerInstance();                            //add instance to ResetManager
  }

  /**
   * Accessor for behaviours.
   *
   * @return the HashMap containing a list of behaviours.
   */
  public Map<Integer, Behaviour> getBehaviours() {
    return behaviours;
  }

  /**
   * Accessor for target.
   *
   * @return the target to attack
   */
  public Actor getTarget(){
    return target;
  }

  /**
   * Mutator for target.
   *
   * @param actor the target to attack
   */
  public void setTarget(Actor actor) {
    target = actor;
  }

  /**
   * To make all the child class of Enemy resettable
   */
  @Override
  public void resetInstance() { this.addCapability(Status.RESET_CAPABLE); }

  /**
   * Abstract method to implement the reset, since each Enemy may have different reset implementation.
   *
   * @param map the GameMap the Enemy is on
   * @return a boolean indicating whether instance has been reset
   */
  public abstract boolean reset(GameMap map);

  /**
   * To print the name and current hp of Enemy.
   *
   * @return string representation of the Enemy
   */
  @Override
  public String toString() {
    return super.toString() + this.printHp();
  }

  /**
   * Returns a new collection of the Actions that the otherActor can do to the current Enemy.
   *
   * @param otherActor the Actor that might perform an action.
   * @param direction  String representing the direction of the other Actor
   * @param map        current GameMap
   * @return list of actions
   */
  @Override
  public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
    ActionList actions = new ActionList();
    // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
    if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
      actions.add(new AttackAction(this,direction));
    }
    return actions;
  }

  /**
   * Add a new FollowBehaviour to the behaviour list of the instance after it attacks.
   */
  public void addFollowBehaviour() {
    Behaviour behaviour = behaviours.get(5);                  //retrieve the AttackBehaviour in behaviours list
    setTarget( ((AttackBehaviour) behaviour).getTarget() );   //retrieve and set the target attacked
    behaviours.put(7, new FollowBehaviour(target));           //add a new FollowBehaviour to behaviours list
  }

}
