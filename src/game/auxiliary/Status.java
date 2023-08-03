package game.auxiliary;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
    HOSTILE_TO_ENEMY,   //considered hostile towards enemy (e.g., to be attacked by enemy)
    TALL,               //current instance has "grown" (after consumed SuperMushroom)
    IMMUNITY,           //current instance is immune/invincible (after consumed PowerStar)
    TRADABLE,           //actor can trade with Toad
    SPEAKABLE,          //actor can speak with Toad
    RESET_GAME,         //actor can reset the game
    RESET_CAPABLE,      //this instance can be reset when game is reset
    HAS_RESET,          //current instance has been reset before
    FERTILE,            //ground that can grow Trees
    SHELL,              //has a shell (Koopa)
    DORMANT,            //dormant state, cannot do anything (for Koopa)
    CRUSH,              //can crush Koopa's shell (has Wrench)
    FLY,                //can fly (for Flying Koopa)
    EGG,                //to check for yoshi egg
    NOSHI,              //to indicate egg breaking
    END_GAME,           //can interact with Princess Peach to end the game
    ATK_UP             //increase the base attack/intrinsic attack damage by 15
}
