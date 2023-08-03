package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.auxiliary.Status;
import game.auxiliary.Wallet;
import game.items.Bottle;
import game.items.Hammer;
import game.reset.ResetAction;
import game.reset.Resettable;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable, ExtraLives {

	private final Menu menu = new Menu();
	private static Wallet wallet = new Wallet(); //a wallet system to manage the Coins of Player
	private static ResetAction autoResetAction = new ResetAction(); 	//an Action to reset the game automatically
	private static ResetAction manualResetAction = new ResetAction();	//an Action to reset the game manually
	private boolean autoReset = false;			//indicate whether to reset automatically
	private static int immuneTurns = 0;			//the no. of turns of invincibility effect left
	private static int currentTurn = 0;			//the current turn after game starts, increment by 1 after each turn
	private static int hearts = 3;				//no. of lives remaining, 3 hearts in total
	private Location initialLocation;			//reference to initial location when game starts
	private int punchDamage = 5;				//the intrinsic attack damage, starting damage is 5

	/**
	 * Constructor.
	 *
	 * @param name        the name of the Player
	 * @param displayChar character to represent the player in the display
	 * @param hitPoints   Player's starting hit points
	 * @param location 	  Player's starting location
	 */
	public Player(String name, char displayChar, int hitPoints, Location location) {
		super(name, displayChar, hitPoints);
		this.initialLocation = location;
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.addCapability(Status.TRADABLE);
		this.addCapability(Status.SPEAKABLE);
		this.addCapability(Status.RESET_GAME);
		this.registerInstance();   				//add instance to ResetManager

		this.addItemToInventory(new Bottle());	//add a Bottle to inventory
	}

	/**
	 * Retrieve the Wallet of the Player.
	 *
	 * @return the Wallet instance of Player
	 */
	public Wallet getWallet() {   //method to retrieve the Player's wallet
		return wallet;
	}

	/**
	 * Getter for the bottle added to Player's inventory during at the start of the game.
	 *
	 * @return the bottle in Player's inventory
	 */
	public Bottle getBottle() {
		Bottle bottle = (Bottle) this.getInventory().get(0); 	//bottle is the first item added - index 0
		return bottle;
	}

	/**
	 * Create and return an intrinsic weapon. The punch damage will increase when Player drinks the PowerWater or when
	 * his remaining hearts become lower.
	 *
	 * @return a freshly-instantiated IntrinsicWeapon
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(punchDamage, "punches");
	}

	/**
	 * Method to update the Player's punch damage whenever he drinks the PowerWater. The intrinsic damage increases by 15.
	 */
	private void updatePunchDamage() {
		if (this.hasCapability(Status.ATK_UP)){
			punchDamage += 15;
			this.removeCapability(Status.ATK_UP);
		}
	}

	/**
	 * Method to be called each time Player hp reaches 0, decreases the remaining lives, gives Player buffs and
	 * moves Player back to its initial location.
	 *
	 * @param location location of the Player
	 */
	public void die(Location location) {
		autoReset = true;	//to reset automatically in playTurn()
		hearts--;			//remaining lives decrease by 1

		this.extraPunch();	//add extra damage to punch

		if (hearts == 0) {
			location.map().removeActor(this);		//removed from map = game over
		}
		else {
			String result = "Mario dies, 1 heart is gone.... His punch now deals 10 extra damage!";
			this.heal(this.getMaxHp());           	//heal to maximum hp

			if (hearts == 1) {
				this.resetMaxHp(this.getMaxHp() + 100);  //increase max hp by 50 points
				this.addItemToInventory(new Hammer());
				result += " Hammer is obtained, 1 more heart left, maximum hp increases by 50, don't waste it!";
			}
			//if Player is not at its initial position, move Player back
			if (initialLocation != location.map().locationOf(this)) {
				location.map().moveActor(this, initialLocation);
			}
			System.out.println(result);
		}
	}

	/**
	 * Method to add extra damage to the current punch damage depending on the number of hearts left.
	 */
	private void extraPunch() {
		int damageMultiplier = 3 - hearts;			//multiplier based on Mario lives left
		int extraDamage = 10 * damageMultiplier;	//extra damage = 0 (3 hearts), 10 (2 hearts), 20 (1 heart)
		punchDamage += extraDamage;
	}

	/**
	 * Select and return an action to perform on the current turn.
	 *
	 * @param actions    collection of possible Actions for Player
	 * @param lastAction The Action Player took last turn
	 * @param map        the map containing the Player
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

		//when Player dies, 'reset' will be set to true, the game will be reset automatically
		if (autoReset) {
			autoReset = false;
			return autoResetAction;
		}

		//reset implementation
		if (this.hasCapability(Status.RESET_CAPABLE)) {  //determine if Player can be reset
			this.removeCapability(Status.TALL);
			this.removeCapability(Status.IMMUNITY);  	//remove status from SuperMushroom & PowerStar
			this.heal(this.getMaxHp());   				//heal to maximum hp
			this.removeCapability(Status.RESET_CAPABLE);
		}

		//remove Player's ability to reset manually after Player resets manually
		if (lastAction == manualResetAction) {
			this.removeCapability(Status.RESET_GAME);
		}

		//if Player hasn't reset the game manually, add a ResetAction to the actions list
		if (this.hasCapability(Status.RESET_GAME)) {
			actions.add(manualResetAction);
		}

		this.updatePunchDamage();							//update Player's punch damage if possible
		this.playerDescription(this, map, display);   //display Mario's description in console
		this.checkImmune();  						  		//check if Mario has immunity turns

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	/**
	 * Method to display descriptions of the Player in the console.
	 *
	 * @param player the current Player
	 * @param map the map the Player is on
	 * @param display the I/O object to which messages may be written
	 */
	private void playerDescription (Player player, GameMap map, Display display) {
		currentTurn++;	//increment the current turn by 1

		display.println("Mario" + this.printHp() + " at " + map.locationOf(player).getGround()+
				"(" + map.locationOf(player).x() + "," + map.locationOf(player).y() + ")");	 //display Mario's hp, location
		display.println("Hearts left: " + hearts + "♥");						//display the remaining hearts
		display.println("Punch damage: " + punchDamage + "👊");					//display the current punch damage
		display.println("Current turn: " + currentTurn);						//display how many turns have passed
		display.println("💰 Wallet: $" + player.getWallet().getBalance());  	//display Mario's wallet's balance
		display.println("🔰 Capabilities: " + player.capabilitiesList());  		//display Mario's current capabilities
		display.println("👜 Inventory: " + player.getInventory());				//display the items in Mario's inventory
		display.println("🔧 Weapon: " + player.getWeapon());					//display Mario's current weapon

		if (this.hasCapability(Status.IMMUNITY))   //if Mario consumed PowerStar
			display.println("MARIO IS INVINCIBLE! Remaining Turns: " + immuneTurns);
	}

	/**
	 * Reset the immuneTurns for PowerStar effect back to 10.
	 */
	public void resetImmuneTurns() {	//reset the turns for immune effect to 10 turns
		immuneTurns = 10;
	}

	/**
	 * Check and remove the PowerStar effect after 10 turns.
	 */
	public void checkImmune() {  	//check if Player still has immunity
		immuneTurns--;
		if (immuneTurns <= 0) {    	//remove immunity status if no more turns
			this.removeCapability(Status.IMMUNITY);
		}
	}

	/**
	 * Allow Player to be able to reset.
	 */
	@Override
	public void resetInstance() {
		this.addCapability(Status.RESET_CAPABLE);
	}

	/**
	 * Retrieve the display character of Player.
	 *
	 * @return the display character
	 */
	@Override
	public char getDisplayChar(){
		return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()): super.getDisplayChar();
	}

}
