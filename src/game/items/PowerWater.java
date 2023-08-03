package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import game.auxiliary.Status;

public class PowerWater extends MagicItem implements Water{

    /**
     * Constructor.
     */
    public PowerWater() {
        super("Power Water", '~', false);
    }

    /**
     * Add a status to the actor, which will allow the actor to utilize this status in its class and increase
     * its intrinsic attack damage by 15.
     *
     * @param actor the Actor that drinks the water
     */
    @Override
    public void waterEffect(Actor actor) {
        actor.addCapability(Status.ATK_UP);
    }

}
