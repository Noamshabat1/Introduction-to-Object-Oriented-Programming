package bricker.brick_strategies;

import bricker.utils.GameConstants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.Heart;

/**
 * Strategy for granting an extra life upon collision.
 * <p>
 * This class extends BasicCollisionStrategy and is responsible for
 * creating a Heart object, which increases the player's life count,
 * when a collision occurs.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class ExtraLifeStrategy extends BasicCollisionStrategy {
    private final GameObjectCollection gameObjects; // set the game object.
    private final Renderable renderable; // set the appearance of the heart object.
    private final Vector2 objectSize = GameConstants.HEART_SIZE_VECTOR; // size of heart.
    private final Counter lifeCounter; // set the user game-life counter.
    private final String ID = "ExtraLifeStrategy"; // The idea of the event
    private final boolean originalBehavior; // A boolean variable that indicates us if we are a clone
    // or a natural instance

    /**
     * Constructs an ExtraLifeStrategy instance with the necessary components to create and manage
     * heart objects.
     *
     * @param gameObjects The game object of the game.
     * @param renderable  The visual representation for the heart object.
     * @param lifeCounter A counter tracking the player's lives.
     */

    /**
     * Constructs an ExtraLifeStrategy instance with the necessary components to create and manage
     * heart objects.
     * @param gameObjects   The game object of the game.
     * @param renderable    The visual representation for the heart object.
     * @param lifeCounter   Create new scratch file from selection
     * @param bricksCounter    The counter of bricks in the game
     * @param originalBehavior  A boolean variable that indicates us if we are a clone or a natural
     *                          instance
     */
    public ExtraLifeStrategy(GameObjectCollection gameObjects, Renderable renderable,
                             Counter lifeCounter, Counter bricksCounter, boolean originalBehavior) {
        super(gameObjects, bricksCounter);
        this.gameObjects = gameObjects;
        this.renderable = renderable;
        this.lifeCounter = lifeCounter;
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
     * Handles the collision event and creates a heart object to grant an extra life.
     * This method is invoked when a collision occurs with an object implementing this strategy.
     * It creates a heart object that, when collected, increases the player's life count.
     *
     * @param thisObj       The GameObject that this strategy is attached to.
     * @param otherObj      The GameObject that has collided with thisObj.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {

        if (originalBehavior) {super.onCollision(thisObj, otherObj);}
        GameObject heart = new Heart(thisObj.getTopLeftCorner(),
                this.objectSize,
                this.renderable,
                true,
                lifeCounter,
                gameObjects);

        heart.setVelocity(GameConstants.EXTRA_HEART_INITIAL_VELOCITY);
        heart.setCenter(thisObj.getCenter());
        gameObjects.addGameObject(heart);
    }

}
