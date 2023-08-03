package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.items.Wrench;

public class TradeAction extends Action {

    private int option;     //the item to be traded (0 = Power Star, 1 = Super Mushroom, 2 = Wrench)

    /**
     * Constructor, initializes the option.
     *
     * @param option the selected option to represent different items
     */
    public TradeAction(int option){
        this.option = option;
    }

    /**
     * Perform trading where the Player uses coin to purchase an item and the item is added into Player's inventory.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "You don't have enough coins!";

        Player player = (Player) actor;  //downcast actor to Player - only Player can execute TradeAction
        int coinBalance = player.getWallet().getBalance();

        //select which item to sell to Player
        switch (option) {
            case 0:     //Buy Power Star ($600)
                if (coinBalance >= 600) {
                    player.getWallet().useCoin(600);
                    player.addItemToInventory(new PowerStar());
                    result = "Mario obtained Power Star";
                }
                break;

            case 1:     //Buy Super Mushroom ($400)
                if (coinBalance >= 400) {
                    player.getWallet().useCoin(400);
                    player.addItemToInventory(new SuperMushroom());
                    result = "Mario obtained Super Mushroom";
                }
                break;

            case 2:     //Buy Wrench ($200)
                if (coinBalance >= 200) {
                    player.getWallet().useCoin(200);
                    player.addItemToInventory(new Wrench());
                    result = "Mario obtained Wrench";
                }
                break;
        }
        return result;
    }

    /**
     * Returns a descriptive string.
     *
     * @param actor The actor performing the action.
     * @return a String to put in the menu, eg. "Mario buys Power Star ($600)"
     */
    @Override
    public String menuDescription(Actor actor) {
        String description = null;

        //3 different menu descriptions to buy 3 different items
        if (option == 0){
            description = "Mario buys Power Star ($600)";
        }
        else if (option == 1){
            description = "Mario buys Super Mushroom ($400)";
        }
        else if (option == 2){
            description = "Mario buys Wrench ($200)";
        }
        return description;
    }

    /**
     * Returns the key used in the menu to trigger this Action.
     *
     * @return keys that represent different items to trade.
     */
    @Override
    public String hotkey() {
        String key = null;

        //3 different keys to buy 3 different items
        if (option == 0){
            key = "x";
        }
        else if (option == 1){
            key = "y";
        }
        else if (option == 2){
            key = "z";
        }
        return key;
    }

}
