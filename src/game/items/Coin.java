package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.PickUpCoinAction;
import game.auxiliary.Status;
import game.reset.Resettable;

public class Coin extends Item implements Resettable {
  private int value;

  /**
   * Constructor with a parameter. Register instance to Reset Manager and allow PickUpCoinAction to be performed on
   * the initialized Coin instance.
   *
   * @param value integer value of the Coin.
   */
  public Coin(int value){
    super("coin", '$', false);   //portable = false to return null for getPickUpAction() & getDropAction()
    this.value = value;
    this.addAction(new PickUpCoinAction(this));
    this.registerInstance();   //add instance to ResetManager
  }

  /**
   * Getter for the coin's value.
   *
   * @return the value of the coin
   */
  public Integer getCoinValue(){
    return value;
  }

  /**
   * Coin on Ground can experience the joy of time, reset it when possible.
   *
   * @param currentLocation The location of the coin on the map.
   */
  @Override
  public void tick(Location currentLocation) {
    super.tick(currentLocation);

    if (this.hasCapability(Status.RESET_CAPABLE)){  //determine if Coin can be reset
      currentLocation.removeItem(this);
      this.removeCapability(Status.RESET_CAPABLE);
    }
  }

  /**
   * Allow Coin to be able to reset.
   */
  @Override
  public void resetInstance() {
    this.addCapability(Status.RESET_CAPABLE);
  }

}
