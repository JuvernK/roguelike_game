package game.jump;

import edu.monash.fit2099.engine.positions.Ground;

import java.util.ArrayList;

public class JumpManager {

    /**
     * An ArrayList that stores jumpable grounds
     */
    private ArrayList<Jumpable> jumpablesList;

    /**
     * Store an instance of JumpManager (for static factory method)
     */
    private static JumpManager instance;

    /**
     * Private constructor for static factory method
     */
    private JumpManager() { // 1-  private to prevent anyone else from instantiating
      jumpablesList = new ArrayList<>();
    }

    /**
     * A static factory method that instantiates a JumpManager object.
     *
     * @return instance - an instance of JumpManager class
     */
    public static JumpManager getInstance() {
      if (instance == null) {
        instance = new JumpManager();
      }
      return instance;
    }

    /**
     * Method that adds a jumpable ground to the ArrayList.
     *
     * @param item - a jumpable ground
     * @throws NullPointerException - if item can't be added
     */
    public void appendJumpables(Jumpable item) {
      this.jumpablesList.add(item);
    }

    /**
     * Accessor for jumpablesList.
     *
     * @return jumpablesList
     */
    public ArrayList<Jumpable> getJumpables() {
      return new ArrayList<Jumpable>(this.jumpablesList);
    }

    /**
     * Getter that returns a jumpable item if it is found to be jumpable.
     *
     * @param groundObj the ground object to be checked
     * @return jumpable the ground object that can be found in jumpable grounds; null if otherwise.
     */
    public Jumpable getJumpable(Ground groundObj){
      for(Jumpable jumpable : jumpablesList){
        if(jumpable.equals(groundObj)) {
          return jumpable;
        }
      }
      return null;
    }
}

