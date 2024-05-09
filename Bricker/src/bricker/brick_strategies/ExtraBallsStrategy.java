package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * ExtraBallsStrategy is a specialized collision strategy that extends BasicCollisionStrategy.
 * This class is specifically designed for adding extra balls into the game upon certain collision events,
 * thereby increasing the challenge and dynamic nature of the gameplay.
 * The strategy, when triggered by a collision, leads to the creation of additional balls, adding complexity
 * and excitement to the game. It is a part of the game's strategy to diversify the player's experience.
 * Usage:
 * - Instantiate this class with the necessary game components.
 * - Attach this strategy to a GameObject.
 * - On collision, the strategy will be triggered to add extra balls into the game.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class ExtraBallsStrategy extends BasicCollisionStrategy {

    private final BrickerGameManager gameManager; // set the game manager.
    private final String ID = "ExtraBallsStrategy"; // The idea of the event
    private final boolean originalBehavior; //A boolean variable that indicates us if we are a clone
    // or a natural instance


    /**
     * Constructor for ExtraBallsStrategy. Sets up the strategy with all required components for
     * adding extra balls.
     *
     * @param gameObjects The collection of game objects, used to manage and add new balls within the
     *                   game.
     * @param gameManager The game manager, responsible for managing game states and interactions,
     *                    including the creation of extra balls.
     */

    /**
     * Constructor for ExtraBallsStrategy. Sets up the strategy with all required components for
     * adding extra balls.
     * @param gameObjects   The collection of game objects, used to manage and add new balls within the
     *                      game.
     * @param gameManager       The game manager, responsible for managing game states and interactions,
     *                          including the creation of extra balls.
     * @param bricksCounter     The counter of bricks in the game
     * @param originalBehavior  A boolean variable that indicates us if we are a clone or a natural
     *                          instance
     */
    public ExtraBallsStrategy(GameObjectCollection gameObjects, BrickerGameManager gameManager,
                              Counter bricksCounter, boolean originalBehavior) {
        super(gameObjects, bricksCounter);
        this.gameManager = gameManager;
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
     * Handles the collision event and adds extra balls to the game.
     * This method is invoked when a collision occurs with an object implementing this strategy.
     * It triggers the creation of additional balls in the game.
     *
     * @param thisObj       The GameObject that this strategy is attached to.
     * @param otherObj      The GameObject that has collided with thisObj.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (originalBehavior) {
            super.onCollision(thisObj, otherObj);
        }
        gameManager.createMockBalls(thisObj);
    }
}
