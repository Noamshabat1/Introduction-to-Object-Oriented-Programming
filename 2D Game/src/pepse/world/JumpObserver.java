package pepse.world;

import java.util.ArrayList;

/**
 * A class that represents a jump observer object.
 */
public class JumpObserver {

    private final ArrayList<Actionable> observerList = new ArrayList<>();

    /**
     * Adds an object to the observer list
     *
     * @param object The object to be added to the observer list
     */
    public void addToObserver(Actionable object) {
        observerList.add(object);
    }

    /**
     * It's the function that is in charge to update the Objects that a jump has been executed by the
     * player and to act accordingly
     */
    public void jumped() {
        for (Actionable object : observerList) {
            object.onJump();
        }
    }
}
