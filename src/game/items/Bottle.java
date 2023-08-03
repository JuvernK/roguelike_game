package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DrinkAction;

import java.util.ArrayList;

public class Bottle extends MagicItem {

    private ArrayList<Water> lstWater = new ArrayList<>();      //store water that is added to the Bottle
    private DrinkAction drinkAction = new DrinkAction(this);    //allow actor to drink the water from the Bottle

    /***
     * Constructor.
     *
     */
    public Bottle() {
        super("Bottle", 'b', false);
    }

    /**
     * Inform the current Bottle in inventory of the passage of time. Add or remove a DrinkAction depending on the content
     * of the Bottle.
     *
     * @param currentLocation The location of the actor carrying this Bottle.
     * @param actor The actor carrying this Bottle.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        if (!lstWater.isEmpty()) {  //if the list of water is not empty
            if (!this.getAllowableActions().contains(drinkAction))  //if it does not already possess a DrinkAction
                this.addAction(drinkAction);
        }
        else {
            this.removeAction(drinkAction);
        }
    }

    /**
     * Add new Water into the list of water.
     *
     * @param water the Water to be added
     */
    public void addToBottle(Water water) {     //add the water to the end of the list
        lstWater.add(water);
    }

    /**
     * Remove the last water that is added to the bottle, since Bottle follows Last In First Out operation.
     */
    public void removeFromBottle(){
        lstWater.remove(lstWater.size()-1);
    }

    /**
     * Retrieve the water to be consumed, and remove it from the water list.
     *
     * @return the water to be consumed
     */
    public Water popFromBottle() {      //
        Water targetWater = lstWater.get(lstWater.size()-1);
        removeFromBottle();
        return targetWater;
    }

    /**
     * Assign a string representation for Bottle, shows the list of water stored in the Bottle.
     *
     * @return the string representation, e.g. "Bottle[Healing Water, Power Water]"
     */
    @Override
    public String toString() {
        return super.toString() + lstWater;
    }

}

