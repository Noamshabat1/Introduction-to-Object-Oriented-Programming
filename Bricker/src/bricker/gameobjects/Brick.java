package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a brick in the game, which can have multiple collision
 * strategies and behaviors.
 * This class extends GameObject and is a key element in brick-breaking games,
 * handling collisions and other game logic.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy; // strategy for handling collisions

    /**
     * The Constructor for a single brick object with an unique strategy power.
     *
     * @param topLeftCorner     The top left corner of the brick.
     * @param dimensions        The dimensions of the brick.
     * @param renderable        The graphical representation of the brick.
     * @param collisionStrategy An array of strategies for handling collisions.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);

        this.collisionStrategy = collisionStrategy;
        this.setTag("Brick");
    }

    /**
     * Called when this brick collides with another game object.
     * Executes all collision strategies associated with this brick.
     *
     * @param other     The other game object involved in the collision.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }
}
