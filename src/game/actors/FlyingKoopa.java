package game.actors;

import game.auxiliary.Status;

public class FlyingKoopa extends Koopa {

    /**
     * Constructor, add a FLY capability to FlyingKoopa.
     */
    public FlyingKoopa() {
        super("Flying Koopa", 'F', 150);
        this.addCapability(Status.FLY);
    }

}
