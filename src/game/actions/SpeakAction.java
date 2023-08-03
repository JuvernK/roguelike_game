package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.auxiliary.Status;
import java.util.Random;

public class SpeakAction extends Action {
    private Random random = new Random();
    private Actor target;    //actor to speak to
    private final static String SENTENCE_1 = "You might need a wrench to smash Koopa's hard shells.";
    private final static String SENTENCE_2 = "You better get back to finding the Power Stars.";
    private final static String SENTENCE_3 = "The Princess is depending on you! You are our only hope.";
    private final static String SENTENCE_4 = "Being imprisoned in these walls can drive a fungus crazy :(";

    /**
     * Constructor.
     *
     * @param target the Actor to speak to
     */
    public SpeakAction(Actor target) {
        this.target = target;
    }

    /**
     * Checks the actor's capability and provides suitable sentences for Toad to speak with the actor.
     *
     * @param actor The actor performing the action
     * @param map The map the actor is on
     * @return the sentences that Toad spoken to actor in the menu
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";
        int choice;
        boolean hasWrench = actor.hasCapability(Status.CRUSH);   //the actor possesses a Wrench
        boolean hasStar = actor.hasCapability(Status.IMMUNITY);  //the actor has the effect of Power Star

        if (hasWrench && hasStar) //choose sentence 3 or 4
            choice = 3 + random.nextInt(2);             // 3 + (0 or 1)

        else if (hasWrench)     //choose sentence 2 or 3 or 4
            choice = 2 + random.nextInt(3);             // 2 + (0 or 1 or 2)

        else if (hasStar) {     //choose sentence 1 or 3 or 4
            do {
                choice = 1 + random.nextInt(4);         // 1 + (0 or 1 or 2 or 3)
            } while (choice == 2);  //loop until choice != 2
        }
        else  //choose any sentence
            choice = 1 + random.nextInt(4);

        //choose the sentence to speak based on the choice
        switch (choice) {
            case 1:
                result = SENTENCE_1;
                break;
            case 2:
                result = SENTENCE_2;
                break;
            case 3:
                result = SENTENCE_3;
                break;
            case 4:
                result = SENTENCE_4;
                break;
        }
        return result;   //return the sentence spoken to show in the menu
    }

    /**
     * Returns a descriptive string.
     *
     * @param actor the actor performing the action
     * @return a String to put in the menu, e.g. "Toad speaks to Player at North"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " speaks to " + target;
    }

    /**
     * Returns the key used in the menu to trigger this Action.
     *
     * @return "w", the hotkey for player to speak with Toad
     */
    @Override
    public String hotkey(){
        return "w";
    }

}
