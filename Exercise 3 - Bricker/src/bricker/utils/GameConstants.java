package bricker.utils;

import danogl.util.Vector2;

/**
 * Contains constant values used throughout the game.
 * This class defines a set of public static final fields representing various constants used in the
 * game. These include dimensions, speeds, initial values for game objects like balls, paddles, and UI
 * elements, as well as file paths for assets and output messages. These constants ensure consistency
 * and ease of modifications across the game's code.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class GameConstants {
    // Background Layer
    // Border

    /**
     * The default constructor of the class, it doesn't do anything useful to us, in fact it doesn't do
     * anything at all and should be ashamed of that because he lives here and doesn't pay rent
     */
    GameConstants(){}

    /**
     * The dimension of the main window of the game
     */
    public static final Vector2 WINDOW_DIMENSION_VECTOR = new Vector2(700, 500) ;

    /**
     * Pretty obvious
     */
    public static final String BRICKER = "Bricker" ;

    /**
     * Width of the game border
     */
    public static final float BORDER_WIDTH = 20f;

    // UI Layer

    // Heart
    /**
     * Initial number of lives for the player
     */
    public static final int INITIAL_LIVES_COUNT = 3;

    /**
     * Initial velocity of the hearts that are created and dropping from the extra heart behavior
     */
    public static final Vector2 EXTRA_HEART_INITIAL_VELOCITY = new Vector2(0, 75);

    /**
     * The Maximum amount of life a game can have
     */
    public static final int MAX_NUMBER_OF_LIFE = 4;

    /**
     * A vector that contains the size of the object Heart
     */
    public static final Vector2 HEART_SIZE_VECTOR = new Vector2(20, 20);

    /**
     * Padding between heart icons
     */
    public static final int HEART_WIDGET_PADDING = 2;

    // default layer

    // Ball
    /**
     * A vector that contains the size of the object Ball
     */
    public static final Vector2 BALL_SIZE_VECTOR = new Vector2(28, 28);

    /**
     * The speed of the ball
     */
    public static final float BALL_SPEED = 280;

    // Mock Ball
    /**
     * The size relation between a ball and mock ball
     */
    public static final double MOCK_BALL_DIFF_CONST = 0.75;

    // Paddle
    /**
     * A vector that contains the size of the paddle
     */
    public static final Vector2 PADDLE_SIZE_VECTOR = new Vector2(110, 15);

    /**
     * The speed of the paddle's movement
     */
    public static final float PADDLE_MOVEMENT_SPEED = 550;

    /**
     * The length of the invisible padding of the paddle
     */
    public static final int PADDLE_PADDING = 4;

    // Extra Paddle
    /**
     * The max number of collision the extra paddle can take before disappearing
     */
    public static final int MAX_EXTRA_PADDLE_COLLISIONS = 4;


    // Static Layer

    // Bricks
    /**
     * The height of each brick
     */
    public static final int BRICK_HEIGHT = 15;

    // Asset file paths
    /**
     * The path of the background image
     */
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";

    /**
     * The path of the ball image
     */
    public static final String BALL_IMAGE_PATH = "assets/ball.png";

    /**
     * The path of the ball sound
     */
    public static final String BALL_SOUND_PATH = "assets/blop_cut_silenced.wav";

    /**
     * The path of the paddle image
     */
    public static final String PADDLE_IMAGE_PATH = "assets/paddle.png";

    /**
     * The path of the brick image
     */
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";

    /**
     * The path of the heart image
     */
    public static final String HEART_IMAGE_PATH = "assets/heart.png";

    // Extra images for power-ups and effects
    /**
     * The path of the mock ball image
     */
    public static final String MOCK_BALL_IMAGE_PATH = "assets/mockBall.png";

    // Output messages
    /**
     * Winning status message that can be displayed at the end of the game
     */
    public static final String WIN_STATUS = "You won! ";

    /**
     * Losing a status message that can be displayed at the end of the game
     */
    public static final String LOSE_STATUS = "You lost! ";

    // Factory use's
    /**
     * The initial value of all counters which is usually 0
     */
    public static final int COUNTER_INITIAL_VALUE = 0;

    /**
     * A key value used to define the default behavior in the function chooseStrategy of the
     * strategy factory.
     * (0) Means that we can roll all the different behavior
     */
    public static final int DEFAULT_BEHAVIOUR = 0;

    /**
     * A key value used to define the default behavior in the function chooseStrategy of the
     * strategy factory.
     * (1) Means that we can roll all the different special behavior
     */
    public static final int DEFAULT_BEHAVIOUR_1 = 1;

    /**
     * A key value used to define the default behavior in the function chooseStrategy of the
     * strategy factory.
     * (2) Means that we can roll all the special different behavior except the double power effect
     */
    public static final int DEFAULT_BEHAVIOUR_2 = 2;

    /**
     * A constant used to help avoiding the roll of basic strategies
     */
    public static final int DOUBLE_BEHAVIOR_JUMP = 5;

    // Global constants
    /**
     * A modifier so much used that we made it constant
     */
    public static final float HALF_FACTOR_MODIFIER = 0.5f;

    /**
     * The max number of double power that can be evoked
     */
    public static final int MAX_DOUBLE_POWER = 3;

    /**
     * A modifier for the camera change
     */
    public static final float CAMERA_CHANGE_MODIFIER = 1.2f;

    /**
     * The maximum number of extra paddles
     */
    public static final int MAX_EXTRA_PADDLE_INSTANCES = 1;
}
