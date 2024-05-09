package bricker.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

/**
 * Interface for collision strategies.
 * Defines a common structure for different collision handling strategies in the game.
 * Each strategy class implements this interface to provide specific collision behaviors.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public interface CollisionStrategy {
    /**
     * A function that deals with collision that has to be implemented in all classes that
     * implements this behavior.
     *
     * @param thisObj          The object that gets collision
     * @param otherObj         The object collision
     */
    void onCollision(GameObject thisObj, GameObject otherObj);

    /**
     * A function that has to be implemented that returns the id of the strategy
     *
     * @return A string that is used as the id tag
     */
    String getTag();
}