package bricker.gameobjects;

import bricker.utils.GameConstants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents an additional paddle with potentially different
 * behaviors or properties from the main paddle.
 * This class extends Paddle and can be used for special gameplay mechanics.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class ExtraPaddle extends Paddle {
    private final Counter extraPaddle; // Counter for the number of extra paddles.
    private final Counter collisionCounter; // Counter for tracking collisions
    private final GameObjectCollection gameObjects; // An object that can add or delete objects from
    // the game

    /**
     * The Constructs for a new ExtraPaddle object.
     *
     * @param topLeftCorner     The top left corner of the paddle in the game window.
     * @param dimensions        The dimensions of the paddle.
     * @param renderable        The graphical representation of the paddle.
     * @param inputListener     Listener for user input.
     * @param windowDimensions  The dimensions of the game window.
     * @param minDistFromEdge   The minimum distance from the edge of the game window.
     * @param extraPaddlesLives The life of the Extra paddle that is being added.
     * @param gameObjects       The game object of the game.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, Vector2 windowDimensions, float minDistFromEdge,
                       GameObjectCollection gameObjects, Counter extraPaddlesLives) {

        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistFromEdge);
        this.collisionCounter = new Counter(GameConstants.COUNTER_INITIAL_VALUE);
        this.gameObjects = gameObjects;
        extraPaddle = extraPaddlesLives;
        setTag("ExtraPaddle");
    }


    /**
     * Updates the paddle's position and behavior each frame.
     * Can be overridden to implement custom behavior for the Extra Paddle.
     *
     * @param deltaTime Time's elapsed since the last update call.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.collisionCounter.value() >= GameConstants.MAX_EXTRA_PADDLE_COLLISIONS) {
            this.extraPaddle.decrement();
            this.gameObjects.removeGameObject(this);
        }
    }

    /**
     * onCollisionEnter represents the behavior when an object ether ball or mackBall encounter a
     * collision with extra paddle it will have the right action.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("Ball") || other.getTag().equals("MockBall")) {
            collisionCounter.increment();
        }
    }
}
