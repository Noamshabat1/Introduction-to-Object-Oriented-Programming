package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * A variant of the Ball class with additional behaviors.
 * This class extends Ball and could be used for testing or
 * special gameplay scenarios.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class MockBall extends GameObject {

    private final Sound collisionSound; // the collision sound of the Mock Ball.

    /**
     * Construct a new MockBall instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the GameObject will not be rendered.
     * @param collisionSound The sound to play when a collision occurs.
     */
    public MockBall(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        setTag("MockBall");

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
    }
}
