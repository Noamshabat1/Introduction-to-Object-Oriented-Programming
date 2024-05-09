package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.utils.GameConstants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * DoublePowersStrategy is a collision strategy class extending BasicCollisionStrategy.
 * It's designed to amplify the impact of game elements upon collision by initiating two different
 * collision strategies simultaneously. This strategy effectively "doubles" the gameplay effect,
 * adding an extra layer of challenge and unpredictability. Upon a collision event, this strategy
 * triggers two random collision strategies, thereby doubling the game's response to that collision.
 * This can lead to various gameplay changes, such as additional balls, changes in camera perspective,
 * etc., enhancing the gameplay experience.
 * Usage:
 * - Instantiate this class with the game manager.
 * - Attach this strategy to a GameObject.
 * - When a collision occurs, the strategy triggers two other strategies, creating a compounded effect.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class DoublePowersStrategy extends BasicCollisionStrategy {
    private final BrickerGameManager bigManager; // sets the big manager.
    private static final Counter doublePowerOccurred =
            new Counter(GameConstants.COUNTER_INITIAL_VALUE);  // sets the counter of double events
    // that happened.
    private final String ID = "DoublePowersStrategy"; // The idea of the event
    private final boolean originalBehavior; // A boolean variable that indicates us if we are a clone
    // or a natural instance

    /**
     * Constructor for the DoublePowersStrategy class. Initializes the strategy with the game manager and
     * the administration GameObjectCollection.
     *
     * @param admin                       The GameObjectCollection used for administrative purposes
     *                                      in the game.
     * @param gameManager                 The main game manager, responsible for managing game states
     *                                      and interactions.
     *                                    It is essential for creating and applying different
     *                                      collision strategies.
     */

    /**
     * Constructor for the DoublePowersStrategy class. Initializes the strategy with the game manager and
     * the administration GameObjectCollection.
     * @param admin                       The GameObjectCollection used for administrative purposes
     *                                      in the game.
     * @param gameManager                 The main game manager, responsible for managing game states
     *                                      and interactions. It is essential for creating and
     *                                      applying different collision strategies.
     * @param bricksCounter    The counter of bricks in the game
     * @param originalBehavior  A boolean variable that indicates us if we are a clone or a natural
     *                          instance
     */
    public DoublePowersStrategy(GameObjectCollection admin, BrickerGameManager gameManager,
                                Counter bricksCounter, boolean originalBehavior) {
        super(admin, bricksCounter);
        bigManager = gameManager;
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
     * Handles the collision event by triggering two separate collision strategies, thereby
     * "doubling" the effect.
     * This method is invoked when a collision occurs with an object implementing this strategy.
     * It selects and applies two random collision strategies, enhancing the gameplay's dynamic nature.
     *
     * @param thisObj          The GameObject that this strategy is attached to.
     * @param otherObj         The GameObject that has collided with thisObj.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {

        if (originalBehavior) {
            super.onCollision(thisObj, otherObj);
        }

        doublePowerOccurred.increment();
        FactoryCollisionStrategy behaviorFactory = this.bigManager.createStrategyFactory();
        CollisionStrategy strategy1 = createRandomStrategy(behaviorFactory);
        CollisionStrategy strategy2 = createRandomStrategy(behaviorFactory);

        strategy1.onCollision(thisObj, otherObj);
        strategy2.onCollision(thisObj, otherObj);

        if (originalBehavior) {
            doublePowerOccurred.reset();
        }
    }

    /**
     * Creates and returns a random collision strategy based on the game's current state.
     * This method aids the onCollision method by providing one of the two required strategies.
     *
     * @param behaviorFactory The FactoryCollisionStrategy instance used to generate random strategies.
     * @return CollisionStrategy A randomly chosen collision strategy.
     */
    private CollisionStrategy createRandomStrategy(FactoryCollisionStrategy behaviorFactory) {
        if (doublePowerOccurred.value() >= GameConstants.MAX_DOUBLE_POWER) {
            return behaviorFactory.chooseStrategy(GameConstants.DEFAULT_BEHAVIOUR_2);
        } else {
            return behaviorFactory.chooseStrategy(GameConstants.DEFAULT_BEHAVIOUR_1);
        }
    }
}
