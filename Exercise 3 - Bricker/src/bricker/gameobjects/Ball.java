package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a ball in the game, responsible for handling
 * ball-specific behaviors such as collisions.
 * This class extends GameObject and is a central element in gameplay,
 * interacting with other game objects.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class Ball extends GameObject {
    private static Counter collisionCounter; // Counter for tracking the number of collisions
    private final Sound collisionSound; // Sound to play on collision

    /**
     * Constructs a new Ball object.
     *
     * @param topLeftCorner  The top-left corner of the ball in the game window.
     * @param dimensions     The dimensions (width and height) of the ball.
     * @param renderable     The graphical representation of the ball.
     * @param collisionSound The sound to play on collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        collisionCounter = new Counter(0);
        setTag("Ball");
    }

    /**
     * Retrieves the count of collisions that have occurred across all Ball instances.
     *
     * @return The total number of collisions.
     */
    public int getCollisionCounter() {
        return collisionCounter.value();
    }

    /**
     * Called when this ball collides with another game object. Updates the ball's
     * velocity based on the collision, plays a collision sound, and increments the collision counter.
     *
     * @param other     The other game object involved in the collision.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        collisionSound.play();
        collisionCounter.increment();
    }
}
