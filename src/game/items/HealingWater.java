package game.items;

import edu.monash.fit2099.engine.actors.Actor;

public class HealingWater extends MagicItem implements Water{

    /**
     * Constructor.
     */
    public HealingWater() {
        super("Healing Water", '~', false);
    }

    /**
     * Heal the actor by 50 hit points.
     *
     * @param actor the Actor that drinks the water
     */
    @Override
    public void waterEffect(Actor actor) {
        actor.heal(50);
    }

}
