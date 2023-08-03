package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import game.auxiliary.Status;

public class SuperMushroom extends MagicItem {

    /**
     * Constructor, the status is initialized.
     */
    public SuperMushroom(){
        super("Super Mushroom", '^', true);
        super.setStatus(Status.TALL);
    }

    /**
     * Provide a buff, increase the actor's maximum hp by 50.
     *
     * @param actor The actor that consumed the MagicItem.
     */
    public void buff(Actor actor){
        actor.increaseMaxHp(50);
    }

}
