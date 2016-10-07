package game_engine;


/**
 * Created by dmr5bq on 9/9/16.
 */

 /* Changes made on 9/9
         * * *  Implemented 'state' values, which must lay between minState and maxState
         * * *  NEED TO implement checking that maxState <= MAX_STATES to avoid ArrayOutOfBounds
         * * *  Polled by the DisplayManager in order to check the state to change its image set out dependent on the value
         * */
public class StateManager {

    public static final int MAX_STATES = 10;

    private int state;
    private Game parent;
    private int minState;
    private int maxState;

    public StateManager(Game parent) {
        this.parent = parent;
        this.state = 0;
        this.minState = 0;
        this.maxState = 0;
    }

    public static int getMaxStates() {
        return MAX_STATES;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if (this.stateInRange(state))
            this.state = state;
    }

    public Game getParent() {
        return parent;
    }

    public void setParent(Game parent) {
        this.parent = parent;
    }

    public void registerNewState() {
        this.maxState++;
    }

    public void nextState() {
        if (this.state < this.maxState)
            this.state++;
        else if ( this.state == this.maxState )
            this.state = this.minState;
    }

    public void prevState() {
        if (this.state > this.minState)
            this.state--;
        else if (this.state == this.minState)
            this.state = maxState;
    }

    public boolean removeLastState() {
        if (this.maxState > this.minState) {
            this.maxState--;
            return true;
        }
        else return false;
    }

    public boolean removeFirstState() {
        if (this.minState < this.maxState) {
            this.minState++;
            return true;
        }
        else return false;
    }

    public int getMaxState() {
        return this.maxState;
    }

    public int getMinState() {
        return this.minState;
    }

    public boolean stateInRange(int state) {
        return state <= this.maxState && state >= this.minState;
    }
}
