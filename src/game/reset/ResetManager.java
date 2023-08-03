package game.reset;

import java.util.ArrayList;
import java.util.List;

/**
 * A global Singleton manager that does soft-reset on the instances.
 * HINT: refer to Bootcamp Week 5 about static factory method.
 * A3: Think about how will you improve this implementation in the future assessment.
 * What could be the drawbacks of this implementation?
 */
public class ResetManager {
    /**
     * A list of resettable instances
     */
    private List<Resettable> resettableList;
    /**
     * A singleton reset manager instance
     */
    private static ResetManager instance;

    /**
     * Get the singleton instance of reset manager.
     *
     * @return ResetManager singleton instance
     */
    public static ResetManager getInstance(){
        if(instance == null){
            instance = new ResetManager();
        }
        return instance;
    }

    /**
     * Constructor.
     */
    private ResetManager(){
        resettableList = new ArrayList<>();
    }

    /**
     * Reset the game by traversing through all the list.
     * By doing this way, it will avoid using `instanceof` all over the place.
     */
    public void run(){
        for (Resettable resettable : resettableList){
            resettable.resetInstance();
        }
    }

    /**
     * Add the Resettable instance to the list.
     *
     * @param reset resettable object
     */
    public void appendResetInstance(Resettable reset){
        resettableList.add(reset);
    }

    /**
     * Remove a Resettable instance from the list
     *
     * @param resettable resettable object
     */
    public void cleanUp(Resettable resettable){
        resettableList.remove(resettable);
    }
}
