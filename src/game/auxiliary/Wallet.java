package game.auxiliary;

/**
 * The player's wallet
 */
public class Wallet {

    private int balance = 0;  //the Coin balance

    /**
     * Return the coin balance.
     *
     * @return the number of coins in the wallet
     */
    public int getBalance(){
        return this.balance;
    }

    /**
     * Add more coins to the current coin balance.
     *
     * @param coins number of coins to be added
     */
    public void addCoin(int coins) {
        this.balance = getBalance() + coins;
    }

    /**
     * Deduct coins from the current coin balance.
     *
     * @param coins number of coins to be used
     */
    public void useCoin(int coins) {
        this.balance = getBalance() - coins;
    }

}

