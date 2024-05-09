package bricker.brick_strategies;

import bricker.utils.GameConstants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import bricker.gameobjects.ExtraPaddle;
import danogl.util.Vector2;

/**
 * ExtraPaddleStrategy is a collision strategy class that extends BasicCollisionStrategy.
 * It is designed to add a paddle to the game when certain collision conditions are met.
 * This strategy is activated upon a collision event, and it checks if the conditions for adding an
 * extra paddle are met (e.g., checking the lives of the extra paddles). If the conditions are satisfied,
 * it creates and adds an extra paddle to the game, enhancing the player's ability to interact with the game.
 * Usage:
 * - Instantiate this class with the necessary game components.
 * - Attach this strategy to a GameObject.
 * When a collision occurs, the strategy will check the conditions and add an extra paddle if appropriate.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class ExtraPaddleStrategy extends BasicCollisionStrategy {
    private final GameObjectCollection gameObjects; // sets the game object.
    private final Vector2 windowDimensions; // sets the game window dimensions.
    private final Counter extraPaddlesLives; // sets the counter of extra life to the extra paddle.
    private final Renderable extraPaddleRenderable; // set the appearance of the extra paddle.
    private final UserInputListener inputListener; // set the input from the user.
    private final String ID = "ExtraPaddleStrategy"; // The idea of the event
    private final boolean originalBehavior; // If this instance is a clone or an original



    /**
     * Constructor for the ExtraPaddleStrategy class. Initializes the strategy with all necessary
     * components and settings required to add an extra paddle in the game.
     *
     * @param gameObjects           The collection of all game objects. Used to add the extra paddle
     *                              to the game.
     * @param extraPaddleRenderable The renderable object to represent the extra paddle.
     * @param inputListener         The listener for user inputs, essential for controlling the extra
     *                             paddle.
     * @param windowDimensions      The dimensions of the game window, used for positioning the extra
     *                             paddle.
     * @param extraPaddlesLives     A counter representing the lives or instances of extra paddles in
     *                             the game.
     * @param bricksCounter    The counter of bricks in the game
     * @param originalBehavior  A boolean variable that indicates us if we are a clone or a natural
     *                          instance
     */
    public ExtraPaddleStrategy(GameObjectCollection gameObjects, Renderable extraPaddleRenderable,
                               UserInputListener inputListener, Vector2 windowDimensions,
                               Counter extraPaddlesLives, Counter bricksCounter,
                               boolean originalBehavior) {
        // Activating super constructor
        super(gameObjects, bricksCounter);

        //Admin objects
        this.gameObjects = gameObjects;

        // Creation data
        this.extraPaddleRenderable = extraPaddleRenderable;
        this.inputListener = inputListener;

        // Mandatory data
        this.windowDimensions = windowDimensions;

        // Counters
        this.extraPaddlesLives = extraPaddlesLives;

        // practical data
        this.originalBehavior = originalBehavior ;
    }

    /**
     * Returns a unique tag identifying this specific strategy.
     * This tag can be used for debugging or identifying the strategy during the game.
     *
     * @return A string representing the tag of this collision strategy.
     */
    @Override
    public String getTag() {
        return ID;
    }

    /**
     * Handles the collision event and potentially adds an extra paddle to the game.
     * This method is invoked when a collision occurs with an object implementing this strategy.
     * It checks certain conditions (e.g., the life of the extra paddle) and, if met, adds a new extra paddle.
     *
     * @param thisObj       The GameObject that this strategy is attached to.
     * @param otherObj      The GameObject that has collided with thisObj.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        // Activating the super-onCollision
        // Notice that all the delete object implementation is here
        if (originalBehavior) {
            super.onCollision(thisObj, otherObj);
        }

        if (extraPaddlesLives.value() >= GameConstants.MAX_EXTRA_PADDLE_INSTANCES) {
            return;
        }
        ExtraPaddle extraPaddle = new ExtraPaddle(Vector2.ZERO, GameConstants.PADDLE_SIZE_VECTOR,
                extraPaddleRenderable, inputListener, windowDimensions,
                GameConstants.BORDER_WIDTH, gameObjects, extraPaddlesLives);

        //Add the paddle to the game
        this.gameObjects.addGameObject(extraPaddle, Layer.DEFAULT);

        // Reposition it on the board
        Vector2 extraPaddlePosition = new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2);
        extraPaddle.setCenter(extraPaddlePosition);

        // update the number of instances on the board
        extraPaddlesLives.increment();
    }
}
