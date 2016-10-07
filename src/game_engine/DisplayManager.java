package game_engine;

import input.InputHandler;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dmr5bq on 9/7/16.
 */



/**
 * Changes made on 9/9
 * * * Attempted to implement states, allowing each integer 'state' to hold some different set of DisplayObjects
 * * * Interacts with the StateManager to check current state values
 * */
public class DisplayManager {

    public static final int MAX_STATES = StateManager.MAX_STATES;

    private ArrayList[] states;
    protected ArrayList<DisplayObject> currentDisplayObjects;
    protected Game parent;

    private static final String DM_INIT_WARNING = "DisplayManager:\n\tWarning: this Display Manager is not linked to a game! Please use link().";

    public DisplayManager ( Game game ) {
        this.currentDisplayObjects = new ArrayList<>();

        this.states = new ArrayList[MAX_STATES];
        this.states[0] = this.currentDisplayObjects; // make the initialized set to the default state's imgs

        this.parent = game;
    }

    public DisplayManager () {
        this.currentDisplayObjects = new ArrayList<>();
        this.states = new ArrayList[MAX_STATES];
        System.out.println(DM_INIT_WARNING);
    }

    public void addChild(DisplayObject child) { //adds a child to the current state
        int stateNumber = 0;

        if (this.parent.stateManager != null) {
            StateManager sm_ptr = this.parent.stateManager;
            stateNumber = sm_ptr.getState();
        }

        this.states[stateNumber].add(child);
    }

    public void removeChild(DisplayObject child) {
        this.currentDisplayObjects.remove(child);
    }

    public void clearChildren() {
        this.currentDisplayObjects.clear();
    }

    public void link(Game game) {
        this.parent = game;
    }

    public ArrayList<DisplayObject> getChildren() {
        return this.currentDisplayObjects;
    }

    public void draw (Graphics g) {
        for (DisplayObject child : this.currentDisplayObjects ) {
            child.draw(g);
        }
    }

    public void update (InputHandler input) {
        int stateNumber = 0;

        if (this.parent.stateManager != null) {
            StateManager sm_ptr = this.parent.stateManager;
            stateNumber = sm_ptr.getState();
        }

        // Change current set of display objects to the current state's images
        this.currentDisplayObjects = this.states[stateNumber];

        for (DisplayObject child : this.currentDisplayObjects )
            child.update(input);
    }
}
