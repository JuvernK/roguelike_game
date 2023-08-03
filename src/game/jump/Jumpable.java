package game.jump;

public interface Jumpable{

  /**
   * Allow any class that has this method to return fall damage.
   */
  int getFallDamage();

  /**
   * Allow any class that has this method to return jump success rate.
   */
  double getSuccessRate();

  /**
   * A default interface method that register current instance to the JumpManager.
   * It allows corresponding class uses to be able to return fall damage and jump success rate.
   */
  default void addToJumpableList() { JumpManager.getInstance().appendJumpables(this); }

}
