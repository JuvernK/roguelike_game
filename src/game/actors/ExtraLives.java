package game.actors;

import edu.monash.fit2099.engine.positions.Location;

public interface ExtraLives {

    /**
     * To be implemented by an Actor class when the actor has more than one life and need to perform some operations
     * each time it dies.
     *
     * @param location location the actor is on
     * @see Player#die(Location)
     */
    void die(Location location);

}
