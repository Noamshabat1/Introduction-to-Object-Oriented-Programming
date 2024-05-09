package bricker.gameobjects;

import bricker.utils.GameConstants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.collisions.GameObjectCollection;

/**
 * Represents a heart object in the game, typically used for increasing player lives or similar
 * power-ups. This class extends GameObject and handles interactions like collisions with the paddle.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class Heart extends GameObject {
    private final Counter lifeCounter; // The life counter to modify
    private boolean beenUsed; // Flag to check if the heart has been used
    private final boolean isPositive; // Determines if the heart adds or subtracts a life
    private final GameObjectCollection gameObjects; // Collection of game objects for managing removal

    /**
     * Constructor for creating a heart object in the game.
     *
     * @param topLeftCorner The top left corner of the heart.
     * @param dimensions    The dimensions of the heart.
     * @param renderable    The graphical representation of the heart.
     * @param isPositive    Determines whether the heart adds or subtracts a life.
     * @param lifeCounter   The life counter to be modified on collision.
     * @param gameObjects   The collection of game objects for managing removal.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 boolean isPositive, Counter lifeCounter, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.isPositive = isPositive;
        this.lifeCounter = lifeCounter;
        this.beenUsed = false;
        this.gameObjects = gameObjects;
        setTag("Heart");
    }

    /**
     * Determines whether the heart should collide with another game object.
     *
     * @param otherObj The other game object.
     * @return True if the other object is a paddle, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject otherObj) {
        return (super.shouldCollideWith(otherObj) && otherObj.getTag().equals("Paddle"));
    }

    /**
     * Handles the collision event with a paddle. Modifies the life counter based on
     * whether the heart is positive or negative and marks the heart as used.
     *
     * @param other     The game object this heart collided with.
     * @param collision Details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        if (this.lifeCounter == null || this.beenUsed) {
            return;
        }
        if (isPositive && (lifeCounter.value() < GameConstants.MAX_NUMBER_OF_LIFE)) {
            this.lifeCounter.increment();
        }
        this.beenUsed = true;
        this.gameObjects.removeGameObject(this); // Remove the heart obj from the game
    }
}
