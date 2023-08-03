package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropItemAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Yoshi;
import game.auxiliary.Status;
import java.util.Random;

public class YoshiEgg extends Item {

  private int turns = 7;

  /***
   * Constructor.
   *
   */
  public YoshiEgg() {
    super("Yoshi Egg", '@', true);
    this.addCapability(Status.EGG);
  }


  /***
   * Yoshi Egg in inventory can experience the joy of time, it will break and be removed from the inventory
   * if the player has been attacked.
   *
   * @param currentLocation The location of the actor carrying this Item
   * @param actor The actor carrying this Item
   */
  @Override
  public void tick(Location currentLocation, Actor actor) {
    super.tick(currentLocation, actor);
    turns--;

    //if egg breaks, a new egg is formed in a different location
    if (actor.hasCapability(Status.NOSHI)){
      actor.removeItemFromInventory(this);
      spawnEgg(currentLocation.map());
      actor.removeCapability(Status.NOSHI);
    }

    if (turns <= 0 && !actor.hasCapability(Status.NOSHI)){
      actor.removeItemFromInventory(this);
      actor.removeCapability(Status.NOSHI);
      boolean flag = true;                            //to ensure yoshi is only created once

      for (Exit exit : currentLocation.getExits()){   //get all the exits
        if (!exit.getDestination().containsAnActor() && exit.getDestination().canActorEnter(actor) && flag){
          exit.getDestination().addActor(new Yoshi(actor));
          flag = false;
        }
      }
    }
  }

  /**
   * Assign a string representation for Yoshi Egg.
   *
   * @return The name of Yoshi Egg and its remaining turns before it hatches.
   */
  @Override
  public String toString(){
    return super.toString() + " - " + turns + " turns remaining till it hatches";
  }

  /**
   * Make Yoshi Egg un-droppable.
   *
   * @return null
   */
  public DropItemAction getDropAction(Actor actor) {
    return null;
  }

  /**
   * Spawn(Add) a new YoshiEgg at a random location on the first map.
   *
   * @param map the map to spawn the egg (can only be Forest Zone)
   */
  public void spawnEgg(GameMap map) {
    Random rand = new Random();
    map.at(rand.nextInt(80), rand.nextInt(18)).addItem(this);
  }

}
