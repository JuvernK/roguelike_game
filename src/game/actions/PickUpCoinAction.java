package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.items.Coin;

public class PickUpCoinAction extends Action {

    private final Coin coin;

    /**
     * Constructor, initializes the coin to be picked up.
     *
     * @param coin the Coin to be picked up
     */
    public PickUpCoinAction (Coin coin) {this.coin = coin;}

    /**
     * Perform the Action, remove the Coin from the Actor's location and add the Coin value to Player's wallet.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.locationOf(actor).removeItem(coin);
        ((Player) actor).getWallet().addCoin(coin.getCoinValue());  //add coin into Player's wallet
        return actor + " picked up the " + coin;
    }

    /**
     * Returns a descriptive string to display in menu.
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " picks up the " + coin + "($" + coin.getCoinValue() + ")";
    }
}
