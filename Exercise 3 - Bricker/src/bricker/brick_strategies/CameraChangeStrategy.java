package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.utils.GameConstants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;

/**
 * CameraChangeStrategy extends BasicCollisionStrategy and focuses on altering the camera's focus to
 * the ball upon collision.
 * This strategy adds a dynamic visual effect to the game by shifting the camera's view, thereby
 * enhancing the player's visual experience and increasing the game's immersion.
 * <p>
 * The strategy, when triggered by a collision event, activates a camera that follows the ball,
 * creating a more engaging and visually stimulating gameplay environment. This change in perspective
 * can add a new layer of challenge and excitement to the game.
 * <p>
 * Usage:
 * - Instantiate this class with the necessary game manager, game objects, the ball, and window controller.
 * - Attach this strategy to a GameObject.
 * - On collision, the camera focus shifts to the ball, enhancing the game's visual dynamics.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class CameraChangeStrategy extends BasicCollisionStrategy {
    private final WindowController windowController; // An object that is used to manage the game's
    // window
    private final BrickerGameManager gameManager; // The Big boss himself
    private final Ball ball; // The ball object so we can follow it
    private static final String ID = "CameraChangeStrategy"; // The id of the event
    private static final String MOCK_BALL = "MockBall"; // A constant not a magic number
    private final boolean originalBehavior;

    /**
     * Constructs a CameraChangeStrategy instance.
     * Initializes the strategy with the game manager, game objects, the ball, and the window
     * controller for managing camera behavior.
     *
     * @param gameManager      The main game manager, responsible for managing game states and
     *                         interactions.
     * @param gameObjects      Collection of all game objects, used for accessing and modifying game
     *                         elements.
     * @param ball             The Ball object, which will become the focus of the camera in this
     *                         strategy.
     * @param windowController Controller for managing the game window, essential for handling camera
     *                        focus and behavior.
     */

    /**
     * Constructs a CameraChangeStrategy instance.
     * Initializes the strategy with the game manager, game objects, the ball, and the window
     * controller for managing camera behavior.
     * @param gameManager The main game manager, responsible for managing game states and
     *                    interactions.
     * @param gameObjects      Collection of all game objects, used for accessing and modifying game
     *                         elements.
     * @param ball             The Ball object, which will become the focus of the camera in this
     *                         strategy.
     * @param windowController Controller for managing the game window, essential for handling camera
     *                         focus and behavior.
     * @param bricksCounter    The counter of bricks in the game
     * @param originalBehavior  A boolean variable that indicates us if we are a clone or a natural
     *                          instance
     */
    public CameraChangeStrategy(BrickerGameManager gameManager, GameObjectCollection gameObjects,
                                Ball ball, WindowController windowController, Counter bricksCounter,
                                boolean originalBehavior) {
        super(gameObjects, bricksCounter);
        this.gameManager = gameManager;
        this.windowController = windowController;
        this.ball = ball;
        this.originalBehavior = originalBehavior ;
    }

    /**
     * Returns a unique tag identifying this specific strategy.
     * The tag is useful for debugging or identifying the strategy during gameplay.
     *
     * @return A string representing the unique tag of this collision strategy.
     */
    @Override
    public String getTag() {
        return ID;
    }

    /**
     * Handles the collision event and manages the camera's focus and behavior.
     * This method is invoked when a collision occurs with an object using this strategy.
     * It activates a camera that focuses on the ball, providing a dynamic and immersive visual
     * experience.
     *
     * @param thisObj          The GameObject associated with this collision strategy.
     * @param otherObj         The GameObject that has collided with thisObj.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (originalBehavior) {
            super.onCollision(thisObj, otherObj);
        }

        // Activate the camera only if it's not active and the other object is not a MockBall
        if (!gameManager.isCameraActive && !otherObj.getTag().equals(MOCK_BALL)) {

            Camera camera = new Camera(ball, Vector2.ZERO,
                    windowController.getWindowDimensions().mult(GameConstants.CAMERA_CHANGE_MODIFIER),
                    windowController.getWindowDimensions());
            gameManager.activateCamera(camera);
        }
    }
}