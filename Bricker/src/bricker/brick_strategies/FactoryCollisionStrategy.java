//package bricker.brick_strategies;
//
//
//import bricker.gameobjects.Ball;
//import bricker.main.BrickerGameManager;
//import bricker.utils.GameConstants;
//import danogl.collisions.GameObjectCollection;
//import danogl.gui.UserInputListener;
//import danogl.gui.WindowController;
//import danogl.gui.rendering.Renderable;
//import danogl.util.Counter;
//import danogl.util.Vector2;
//
//import java.util.Random;
//
///**
// * FactoryCollisionStrategy is a factory class responsible for creating and providing different
// * collision strategies for the game Bricker. It encapsulates the logic for selecting a specific
// * collision strategy based on random selection or specific game behaviors.
// * This factory pattern allows for easy extension and modification of collision strategies in the game.
// * It holds references to various game components like Ball, GameObjectCollection, and others, which
// * are used to initialize different collision strategies with the required context and dependencies.
// * Usage:
// * - Create an instance of this class with all required game components.
// * - Call chooseStrategy with a behavior code to get a specific collision strategy.
// *
// * @author Noam Shabat, Samuel Hayat
// * @version Java 11
// */
//public class FactoryCollisionStrategy {
//    private static final int PROBABILITY_BOUND = 10; // the 1/10 probability.
//    private final Random random; // random number for the factory choice.
//    private final Ball ball; // set a ball object.
//    private final BrickerGameManager bigManager; // set a manager object.
//    private final GameObjectCollection gameObjects; // set a game object.
//    private final UserInputListener inputListener; // sets the user input.
//    private final WindowController windowController; // set the window Controller.
//    private final Renderable extraPaddleRenderable; // set the appearance of the extra paddle.
//    private final Vector2 windowDimensions; // set the dimensions of the window.
//    private final Counter extraPaddleLives; // sets the lives of the extra paddle.
//    private final Renderable heartRenderable; // set the appearance of the heart object.
//    private final Counter userLivesLeft; // counts the users life.
////    private final static Counter numberOfDoubleEventOccurred =
////            new Counter(GameConstants.COUNTER_INITIAL_VALUE);
//    private final Counter bricksCounter;
//
//
//
//    /**
//     * Constructor for FactoryCollisionStrategy. Initializes the factory with all necessary game
//     * components and settings.
//     * This constructor sets up the factory with references to various game elements and
//     * configurations that are essential for creating different collision strategies.
//     *
//     * @param bigManager            The main game manager, responsible for managing game states and
//     *                              interactions.
//     * @param ball                  The Ball object used in the game, involved in many collision
//     *                              strategies.
//     * @param gameObjects           Collection of all game objects. Used to access and modify game
//     *                              elements within
//     *                              strategies.
//     * @param heartRenderable       The renderable object to be used in life-related collision
//     *                              strategies.
//     * @param userInputListener     Listener for user inputs, used in strategies requiring player
//     *                              interaction.
//     * @param windowDimensions      The dimensions of the game window, necessary for positioning and
//     *                              scaling objects.
//     * @param extraPaddlesLives     The counter for extra paddle lives, used in paddle-related
//     *                              strategies.
//     * @param windowController      The controller for the game window, used for strategies that involve
//     *                              window manipulation.
//     * @param extraPaddleRenderable The renderable object for extra paddles, used in paddle-related
//     *                              strategies.
//     * @param userLivesLeft         The counter for the user's remaining lives, used in life-affecting
//     *                              strategies.
//     * @param bricksCounter    The counter of bricks in the game
//     */
//    public FactoryCollisionStrategy(BrickerGameManager bigManager, Ball ball,
//                                    GameObjectCollection gameObjects,
//                                    Renderable heartRenderable, UserInputListener userInputListener,
//                                    Vector2 windowDimensions, Counter extraPaddlesLives,
//                                    WindowController windowController, Renderable extraPaddleRenderable,
//                                    Counter userLivesLeft, Counter bricksCounter) {
//
//        this.bigManager = bigManager;
//        this.ball = ball;
//        this.gameObjects = gameObjects;
//        this.heartRenderable = heartRenderable;
//        this.inputListener = userInputListener;
//        this.windowDimensions = windowDimensions;
//        this.extraPaddleLives = extraPaddlesLives;
//        this.windowController = windowController;
//        this.extraPaddleRenderable = extraPaddleRenderable;
//        this.userLivesLeft = userLivesLeft;
//        this.bricksCounter = bricksCounter;
//        this.random = new Random();
//    }
//
//    /**
//     * Chooses and returns a specific collision strategy based on the provided behavior code.
//     * The method utilizes a random number generation mechanism to select a strategy when the behavior
//     * code is 0. For other behavior codes, it determines the strategy based on predefined rules.
//     * The strategy is chosen from a set that includes BasicCollisionStrategy, ExtraBallsStrategy,
//     * CameraChangeStrategy, ExtraPaddleStrategy, and others.
//     *
//     * @param behaviour An integer representing the behavior code for Double-Power behavior. The code
//     *                  determines how
//     *                  the strategy is chosen:
//     *                  0 - Random selection.
//     *                  1 - Selection biased towards certain strategies.
//     *                  Other - Specific predefined selection excluding Double Power Strategy.
//     * @return CollisionStrategy An instance of a CollisionStrategy, selected based on the given
//     * behavior code.
//     * @throws IllegalStateException if an unexpected value is encountered in randomNumber.
//     */
//    public CollisionStrategy chooseStrategy(int behaviour) {
//        int randomNumber;
//        boolean originalBehavior ;
//
//        if (behaviour == GameConstants.DEFAULT_BEHAVIOUR) {  // Generates a number between 0 and 9.
//            randomNumber = random.nextInt(PROBABILITY_BOUND);
//            originalBehavior = true ;
//
//        } else if (behaviour == GameConstants.DEFAULT_BEHAVIOUR_1) { // Generates a number between 5
//            // and 9.
//            randomNumber = random.nextInt(5) + GameConstants.DOUBLE_BEHAVIOR_JUMP;
//            originalBehavior = false ;
//
//        } else { // Generates a number between 5 and 8 - without Double Power Strategy.
//            randomNumber = random.nextInt(4) + GameConstants.DOUBLE_BEHAVIOR_JUMP;
//            originalBehavior = false ;
//        }
//
//        if (randomNumber < 5) { // 0, 1, 2, 3, 4 (50% chance)
//            return new BasicCollisionStrategy(this.gameObjects, this.bricksCounter);
//        } else {  // the rest have (50% chance)
//            switch (randomNumber) {
//                case 5:
//                    return new CameraChangeStrategy(bigManager, gameObjects, ball, windowController,
//                            bricksCounter, originalBehavior);
//                case 6:
//                    return new ExtraPaddleStrategy(gameObjects, extraPaddleRenderable, inputListener,
//                            windowDimensions, extraPaddleLives, bricksCounter, originalBehavior);
//                case 7:
//                    return new ExtraBallsStrategy(gameObjects, bigManager, bricksCounter,
//                            originalBehavior);
//                case 8:
//                    return new ExtraLifeStrategy(gameObjects, heartRenderable, userLivesLeft,
//                            bricksCounter, originalBehavior);
//                case 9:
//                    return new DoublePowersStrategy(gameObjects, bigManager, bricksCounter,
//                            originalBehavior);
//                default:
//                    // This case is never happening, but it's good practice
//                    throw new IllegalStateException("Unexpected value: " + randomNumber);
//            }
//        }
//    }
//}

package bricker.brick_strategies;


import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import bricker.utils.GameConstants;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

/**
 * FactoryCollisionStrategy is a factory class responsible for creating and providing different
 * collision strategies for the game Bricker. It encapsulates the logic for selecting a specific
 * collision strategy based on random selection or specific game behaviors.
 * This factory pattern allows for easy extension and modification of collision strategies in the game.
 * It holds references to various game components like Ball, GameObjectCollection, and others, which
 * are used to initialize different collision strategies with the required context and dependencies.
 * Usage:
 * - Create an instance of this class with all required game components.
 * - Call chooseStrategy with a behavior code to get a specific collision strategy.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class FactoryCollisionStrategy {
    private static final int PROBABILITY_BOUND = 10; // the 1/10 probability.
    private final Random random; // random number for the factory choice.
    private final Ball ball; // set a ball object.
    private final BrickerGameManager bigManager; // set a manager object.
    private final GameObjectCollection gameObjects; // set a game object.
    private final UserInputListener inputListener; // sets the user input.
    private final WindowController windowController; // set the window Controller.
    private final Renderable extraPaddleRenderable; // set the appearance of the extra paddle.
    private final Vector2 windowDimensions; // set the dimensions of the window.
    private final Counter extraPaddleLives; // sets the lives of the extra paddle.
    private final Renderable heartRenderable; // set the appearance of the heart object.
    private final Counter userLivesLeft; // counts the users life.
    //    private final static Counter numberOfDoubleEventOccurred =
//            new Counter(GameConstants.COUNTER_INITIAL_VALUE);
    private final Counter bricksCounter;



    /**
     * Constructor for FactoryCollisionStrategy. Initializes the factory with all necessary game
     * components and settings.
     * This constructor sets up the factory with references to various game elements and
     * configurations that are essential for creating different collision strategies.
     *
     * @param bigManager            The main game manager, responsible for managing game states and
     *                              interactions.
     * @param ball                  The Ball object used in the game, involved in many collision
     *                              strategies.
     * @param gameObjects           Collection of all game objects. Used to access and modify game
     *                              elements within
     *                              strategies.
     * @param heartRenderable       The renderable object to be used in life-related collision
     *                              strategies.
     * @param userInputListener     Listener for user inputs, used in strategies requiring player
     *                              interaction.
     * @param windowDimensions      The dimensions of the game window, necessary for positioning and
     *                              scaling objects.
     * @param extraPaddlesLives     The counter for extra paddle lives, used in paddle-related
     *                              strategies.
     * @param windowController      The controller for the game window, used for strategies that involve
     *                              window manipulation.
     * @param extraPaddleRenderable The renderable object for extra paddles, used in paddle-related
     *                              strategies.
     * @param userLivesLeft         The counter for the user's remaining lives, used in life-affecting
     *                              strategies.
     * @param bricksCounter    The counter of bricks in the game
     */
    public FactoryCollisionStrategy(BrickerGameManager bigManager, Ball ball,
                                    GameObjectCollection gameObjects,
                                    Renderable heartRenderable, UserInputListener userInputListener,
                                    Vector2 windowDimensions, Counter extraPaddlesLives,
                                    WindowController windowController, Renderable extraPaddleRenderable,
                                    Counter userLivesLeft, Counter bricksCounter) {

        this.bigManager = bigManager;
        this.ball = ball;
        this.gameObjects = gameObjects;
        this.heartRenderable = heartRenderable;
        this.inputListener = userInputListener;
        this.windowDimensions = windowDimensions;
        this.extraPaddleLives = extraPaddlesLives;
        this.windowController = windowController;
        this.extraPaddleRenderable = extraPaddleRenderable;
        this.userLivesLeft = userLivesLeft;
        this.bricksCounter = bricksCounter;
        this.random = new Random();
    }

    /**
     * Chooses and returns a specific collision strategy based on the provided behavior code.
     * The method utilizes a random number generation mechanism to select a strategy when the behavior
     * code is 0. For other behavior codes, it determines the strategy based on predefined rules.
     * The strategy is chosen from a set that includes BasicCollisionStrategy, ExtraBallsStrategy,
     * CameraChangeStrategy, ExtraPaddleStrategy, and others.
     *
     * @param behaviour An integer representing the behavior code for Double-Power behavior. The code
     *                  determines how
     *                  the strategy is chosen:
     *                  0 - Random selection.
     *                  1 - Selection biased towards certain strategies.
     *                  Other - Specific predefined selection excluding Double Power Strategy.
     * @return CollisionStrategy An instance of a CollisionStrategy, selected based on the given
     * behavior code.
     * @throws IllegalStateException if an unexpected value is encountered in randomNumber.
     */
    public CollisionStrategy chooseStrategy(int behaviour) {
        int randomNumber;
        boolean originalBehavior ;

        if (behaviour == GameConstants.DEFAULT_BEHAVIOUR) {  // Generates a number between 0 and 9.
            randomNumber = random.nextInt(PROBABILITY_BOUND);
            originalBehavior = true ;

        } else if (behaviour == GameConstants.DEFAULT_BEHAVIOUR_1) { // Generates a number between 5
            // and 9.
            randomNumber = random.nextInt(5) + GameConstants.DOUBLE_BEHAVIOR_JUMP;
            originalBehavior = false ;

        } else { // Generates a number between 5 and 8 - without Double Power Strategy.
            randomNumber = random.nextInt(4) + GameConstants.DOUBLE_BEHAVIOR_JUMP;
            originalBehavior = false ;
        }

        if (randomNumber < 5) { // 0, 1, 2, 3, 4 (50% chance)
            return new BasicCollisionStrategy(this.gameObjects, this.bricksCounter);
        } else {  // the rest have (50% chance)
            switch (randomNumber) {
                case 5:
                    return new CameraChangeStrategy(bigManager, gameObjects, ball, windowController,
                            bricksCounter, originalBehavior);
                case 6:
                    return new ExtraPaddleStrategy(gameObjects, extraPaddleRenderable, inputListener,
                            windowDimensions, extraPaddleLives, bricksCounter, originalBehavior);
                case 7:
                    return new ExtraBallsStrategy(gameObjects, bigManager, bricksCounter,
                            originalBehavior);
                case 8:
                    return new ExtraLifeStrategy(gameObjects, heartRenderable, userLivesLeft,
                            bricksCounter, originalBehavior);
                case 9:
                    return new DoublePowersStrategy(gameObjects, bigManager, bricksCounter,
                            originalBehavior);
                default:
                    // This case is never happening, but it's good practice
                    throw new IllegalStateException("Unexpected value: " + randomNumber);
            }
        }
    }
}
