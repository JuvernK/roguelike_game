package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DestroyAction;
import game.auxiliary.Status;
import game.auxiliary.Utils;
import game.jump.JumpAction;
import game.jump.Jumpable;
import game.reset.Resettable;

/**
 * Abstract base class for all Tree stages
 * @see Ground
 * @see Jumpable
 * @see Resettable
 */
public abstract class Tree extends Ground implements Jumpable, Resettable{

    /**
     * Age of the Tree
     */
    private int age;

    /**
     * Constructor, add Tree to list of jumpable objects and register instance to Reset Manager.
     *
     */
    public Tree(char displayChar) {
        super(displayChar);
        this.addToJumpableList();
        this.registerInstance();    //add instance to ResetManager
    }

    /**
     * Tree can experience the joy of time, reset the Tree whenever reset is possible.
     *
     * @param location The location of the Tree
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        age++;

        if (this.hasCapability(Status.RESET_CAPABLE)){    //determine if Tree can be reset
            location.setGround(new Dirt());               //reset implementation
            this.addCapability(Status.HAS_RESET);         //to check if instance has been reset
            this.removeCapability(Status.RESET_CAPABLE);
        }
    }

    /**
     * Getter for the age of the tree
     *
     * @return age of the tree
     */
    public int getAge(){
        return age;
    }

    /**
     * Allow only actors that can fly to walk into the Tree's location.
     *
     * @param actor the Actor to check
     * @return true if the actor can fly, false otherwise
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Status.FLY);     //only FlyingKoopa can enter normally
    }

    /**
     * Return a list of actions that can be done towards Tree.
     *
     * @param actor the Actor acting
     * @param location the current Location of Tree
     * @param direction the direction of the Tree from the actor
     * @return list of actions that can be performed to Tree
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        if (location.containsAnActor())
            return actions;

        if (actor.hasCapability(Status.IMMUNITY))
            actions.add(new DestroyAction(location, direction));
        else
            actions.add(new JumpAction(location, direction));

        return actions;
    }

    /**
     * Allow Tree to be able to reset with 50% chance.
     *
     * @see Utils#probability(double)
     */
    @Override
    public void resetInstance() {
        if (Utils.probability(0.5)){
            this.addCapability(Status.RESET_CAPABLE);
        }
    }

}
