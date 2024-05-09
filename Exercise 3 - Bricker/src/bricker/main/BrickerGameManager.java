package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.FactoryCollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.*;
import bricker.utils.GameConstants;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Manages the overall game logic for a brick-breaking game.
 * This class is responsible for initializing and managing game components,
 * including game objects like balls, bricks, and paddles, and handling their interactions.
 * It sets up the game environment, manages collisions, and keeps track of the game state.
 * Key responsibilities include:
 * - Setting up the game scene with necessary objects.
 * - Managing collisions and their effects.
 * - Handling user input and game progression.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11.
 */

public class BrickerGameManager extends GameManager {

    private Ball ball; // using composition to hold the ball object.

    // brick
    private final int numberOfRows; // Number of rows of bricks.
    private final int numberOfCols; // Number of cols of bricks.
    private final Brick[] brickArray; // Array of all the bricks.
    private Counter bricksCounter; // the number of bricks in the game.

    // lives

    private final Vector2 windowDimensionsVector; // The dimension of the window
    private WindowController windowController; // The Object that manages the window
    private UserInputListener inputListener; // An object that receives input from the user
    private Counter heartNumber; // The number of player's left "lives"


    private FactoryCollisionStrategy factoryStrategy; // The factory that creates the different behaviors
    private ImageReader imageReader; // An object that renders images into playable objects
    private SoundReader soundReader; // An object that renders sounds into playable objects
    private final Counter extraPaddleLives = new Counter(0); // The number of collisions left to the
    // paddle before it disappear
    /**
     * A boolean variable that tells if the camera is centered into an object
     * True if the camera is centered in an object
     * False if the camera centers the windows
     */
    public boolean isCameraActive;
    private int hitState; // The number of hits before the camera disappears


    /**
     * Default constructor!
     * Constructs a new {@code BrickerGameManager} with the specified window title and dimensions.
     * This constructor initializes the game with the default number of rows and columns for the brick's
     * layout.
     * (default row = 7, column = 8)
     *
     * @param windowTitle            The title of the game window.
     * @param windowDimensionsVector The dimensions of the game window in pixels.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensionsVector) {
        super(windowTitle, windowDimensionsVector);
        this.windowDimensionsVector = windowDimensionsVector;
        this.numberOfRows = 7;
        this.numberOfCols = 8;
        brickArray = new Brick[this.numberOfCols * this.numberOfCols];
    }

    /**
     * Non-default Constructor!
     * Constructs a new {@code BrickerGameManager} with custom settings for the game window, rows,
     * and columns.
     *
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window in pixels.
     * @param numberOfRows     The number of bricks per row
     * @param numberOfCols     The number of bricks per column
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numberOfRows,
                              int numberOfCols) {
        super(windowTitle, windowDimensions);
        this.windowDimensionsVector = windowDimensions;
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;
        brickArray = new Brick[this.numberOfCols * this.numberOfCols];
    }

    /**
     * Initializes game components and sets up the game environment.
     * This includes creating the ball, paddle, bricks, and setting up collision logic and game borders.
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.heartNumber = new Counter(GameConstants.INITIAL_LIVES_COUNT);
        this.bricksCounter = new Counter(0);
        this.isCameraActive = false;

        // Creating game Background
        this.createGameBackground(imageReader);
        this.createHeartCounters(imageReader);

        // Creating game borders
        this.createGameBorders();

        // Creating regularBall
        this.createGameBall(imageReader, soundReader);

        // Creating paddle
        this.createGamePaddle(imageReader);

        // Creating Strategy Factory
        this.factoryStrategy = this.createStrategyFactory();

        // Creating bricks
        this.createGameBricks(imageReader);
    }

    /**
     * Creates everything related to the lives.
     * This method initializes and adds the graphical and numerical life counters to the game objects.
     *
     * @param imageReader An instance of ImageReader to read the heart image.
     */
    private void createHeartCounters(ImageReader imageReader) {
        Renderable image = imageReader.readImage(GameConstants.HEART_IMAGE_PATH, true);
        // using composition to hold the graphic counter-object.
        GraphicLifeCounter graphicCounter = new GraphicLifeCounter(
                new Vector2(GameConstants.BORDER_WIDTH + 35,
                        this.windowDimensionsVector.y() - GameConstants.BORDER_WIDTH * 2 - 20),
                GameConstants.HEART_SIZE_VECTOR,
                this.heartNumber,
                image,
                this.gameObjects(),
                this.heartNumber.value());

        this.gameObjects().addGameObject(graphicCounter, Layer.UI);

        // using composition to hold the numeric counter-object.
        NumericLifeCounter numericCounter = new NumericLifeCounter(
                this.heartNumber,
                new Vector2(GameConstants.BORDER_WIDTH + 5,
                        this.windowDimensionsVector.y() - GameConstants.BORDER_WIDTH * 2 - 20),
                GameConstants.HEART_SIZE_VECTOR,
                this.gameObjects());

        this.gameObjects().addGameObject(numericCounter, Layer.UI);
    }

    /**
     * Creates and arranges the bricks for the game. This method calculates the size and position of each
     * brick based on the game's window dimensions and the predefined number of rows and columns. It then
     * initializes each brick and adds it to the game objects.
     *
     * @param imageReader An instance of ImageReader for reading the brick image.
     */
    private void createGameBricks(ImageReader imageReader) {

        ImageRenderable brickImage = imageReader.readImage(GameConstants.BRICK_IMAGE_PATH, false);

        float brickLength = (windowDimensionsVector.x() - GameConstants.BORDER_WIDTH * 2) / numberOfCols;
        Vector2 brickSizeVector = new Vector2(brickLength, (float) GameConstants.BRICK_HEIGHT);

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfCols; j++) {

                Vector2 brickPosition = new Vector2(
                        GameConstants.BORDER_WIDTH + (j * brickLength),
                        GameConstants.BORDER_WIDTH + (i * 20));

                CollisionStrategy strategy = factoryStrategy.chooseStrategy(GameConstants.DEFAULT_BEHAVIOUR);

                brickArray[i] = new Brick(brickPosition, brickSizeVector, brickImage, strategy);
                bricksCounter.increment();
                this.gameObjects().addGameObject(brickArray[i], Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Initializes and returns a new FactoryCollisionStrategy object.
     * This method sets up the necessary components for collision strategies used in the game,
     * including images and sounds for various game elements like the paddle, heart, and mock ball. It
     * creates and configures a FactoryCollisionStrategy instance with these elements, along with
     * other game state information.
     *
     * @return An instance of FactoryCollisionStrategy, fully initialized with the necessary game elements
     * and state information. This instance can then be used to generate and manage collision
     * strategies throughout the game.
     */
    public FactoryCollisionStrategy createStrategyFactory() {
        Renderable PaddleImage = imageReader.readImage(GameConstants.PADDLE_IMAGE_PATH, true);
        Renderable heartImage = imageReader.readImage(GameConstants.HEART_IMAGE_PATH, true);

        factoryStrategy = new FactoryCollisionStrategy(
                this, this.ball, this.gameObjects(), heartImage, this.inputListener,
                this.windowDimensionsVector, this.extraPaddleLives, this.windowController,
                PaddleImage, this.heartNumber, this.bricksCounter);

        return factoryStrategy;
    }

    /**
     * Creates the paddle for the game. This method initializes the paddle with its image, size, and
     * input listener, and then adds it to the game objects.
     *
     * @param imageReader An instance of ImageReader for reading the paddle image.
     */
    private void createGamePaddle(ImageReader imageReader) {
        Vector2 windowDimension = windowController.getWindowDimensions();

        ImageRenderable paddleImage = imageReader.readImage(GameConstants.PADDLE_IMAGE_PATH, true);

        // using composition to hold the paddle object.
        Paddle paddle = new Paddle(Vector2.ZERO, GameConstants.PADDLE_SIZE_VECTOR, paddleImage,
                inputListener, windowDimension, GameConstants.BORDER_WIDTH);

        paddle.setCenter(new Vector2(windowDimension.x() / 2, windowDimension.y() - 30));
        gameObjects().addGameObject(paddle, Layer.DEFAULT);
    }

    /**
     * Creates the ball for the game. This method initializes the ball with its image and sound, sets its
     * initial velocity and position, and then adds it to the game objects.
     *
     * @param imageReader An instance of ImageReader for reading the ball image.
     * @param soundReader An instance of SoundReader for reading the collision sound.
     */
    private void createGameBall(ImageReader imageReader, SoundReader soundReader) {
        ImageRenderable ballImage = imageReader.readImage(GameConstants.BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(GameConstants.BALL_SOUND_PATH);
        ball = new Ball(Vector2.ZERO, GameConstants.BALL_SIZE_VECTOR, ballImage, collisionSound);

        setRandomBallVelocity(ball);

        Vector2 windowDimension = windowController.getWindowDimensions();
        ball.setCenter(windowDimension.mult(GameConstants.HALF_FACTOR_MODIFIER));
        this.gameObjects().addGameObject(ball, Layer.DEFAULT);
    }

    /**
     * Creates the borders for the game. This method initializes the top, left, and right borders of the
     * game area to prevent the ball from going out of bounds.
     */
    private void createGameBorders() {

        GameObject leftBorder =
                new GameObject(Vector2.ZERO,
                        new Vector2(GameConstants.BORDER_WIDTH, windowDimensionsVector.y()), null);

        GameObject rightBorder =
                new GameObject(new Vector2(windowDimensionsVector.x() - GameConstants.BORDER_WIDTH, 0),
                        new Vector2(GameConstants.BORDER_WIDTH, windowDimensionsVector.y()), null);

        GameObject topBorder =
                new GameObject(new Vector2(GameConstants.BORDER_WIDTH, 0),
                        new Vector2(windowDimensionsVector.x() - 2 * GameConstants.BORDER_WIDTH,
                                GameConstants.BORDER_WIDTH),
                        null);

        this.gameObjects().addGameObject(leftBorder, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(rightBorder, Layer.STATIC_OBJECTS);
        this.gameObjects().addGameObject(topBorder, Layer.STATIC_OBJECTS);

    }

    /**
     * Creates the background for the game. This method initializes the background with an image and
     * sets it to cover the entire game window.
     *
     * @param imageReader An instance of ImageReader for reading the background image.
     */
    private void createGameBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage(GameConstants.BACKGROUND_IMAGE_PATH, false);

        GameObject background = new GameObject(Vector2.ZERO, this.windowDimensionsVector, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }


    // ************************** Logic functions **************************** //


    /**
     * Updates the game state. This method is called once per frame and is responsible for updating
     * the game's logic based on the time that has passed since the last frame.
     *
     * @param deltaTime The time elapsed since the last update call, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        isKeyWPressed();
        checkLivesStatus(ball);
        updateCamera();
    }

    /**
     * this function is do to check if the camera special affect is met.
     * and in the case it is, deactivate the camera
     */
    private void updateCamera() {
        if (isCameraActive && (ball.getCollisionCounter() >= this.hitState + 5)) {
            setCamera(null);
            isCameraActive = false;
        }
    }

    /**
     * Checks for player input to quit the game. If the specific quit key is pressed,
     * the game ends.
     */
    private void isKeyWPressed() {
        if (this.inputListener.isKeyPressed(KeyEvent.VK_W)) {
            bricksCounter.reset();
            checkForGameEnd();
        }
    }

    /**
     * Checks for game end conditions such as winning or losing and takes appropriate actions.
     */
    private void checkForGameEnd() {
        String userMessage = "";
        if (bricksCounter.value() <= 0) { // we win
            userMessage = GameConstants.WIN_STATUS;
        } else if (this.heartNumber.value() <= 0) {  // we lose
            userMessage = GameConstants.LOSE_STATUS;
        }

        if (userMessage.isEmpty()) { // the game is continuing
            return;
        }

        if (this.windowController.openYesNoDialog(userMessage + "Play again?")) {
            // answer to the player
            this.windowController.resetGame();
        } else {
            this.windowController.closeWindow();
        }
    }

    /**
     * Checks the status of player lives and handles the restart of the turn if needed.
     *
     * @param ball The ball object representing the player's ball.
     */
    private void checkLivesStatus(Ball ball) {
        if (this.isObjectIsOutsideBorders(ball)) {
            this.heartNumber.decrement();
            this.restartTurn(ball);
        }
    }

    /**
     * Checks if a game object is outside the game borders.
     *
     * @param object The game object to check.
     * @return True if the object is outside the borders, false otherwise.
     */
    private boolean isObjectIsOutsideBorders(GameObject object) {
        return (object.getCenter().y() + (int) (object.getDimensions().y() / 2))
                > this.windowDimensionsVector.y();
    }

    /**
     * Restarts the turn by setting the ball's position and velocity to default values.
     *
     * @param ball The ball object to restart the turn for.
     */
    private void restartTurn(Ball ball) {
        ball.setCenter(this.windowDimensionsVector.mult(GameConstants.HALF_FACTOR_MODIFIER));
        setRandomBallVelocity(ball);
    }

    /**
     * Sets a random velocity for the ball at the start of the game or after a restart.
     *
     * @param gameObject The ball object whose velocity is to be set.
     */
    private void setRandomBallVelocity(GameObject gameObject) {
        Random rand = new Random();

        float ballVelX = GameConstants.BALL_SPEED;
        float ballVelY = GameConstants.BALL_SPEED;

        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        gameObject.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * Creates two mock balls in the game.
     *
     * @param thisObj the MockBall that is being created.
     */
    public void createMockBalls(GameObject thisObj) {
        createOneMockBall(thisObj);
        createOneMockBall(thisObj);
    }

    /**
     * This function is responsible to creat one MockBall to be entered to the game.
     *
     * @param thisObj the MockBall that is being created.
     */
    private void createOneMockBall(GameObject thisObj) {
        //Initialize Mock Ball parameters
        ImageRenderable ballImage = this.imageReader.readImage(GameConstants.MOCK_BALL_IMAGE_PATH, true);
        Sound ballSound = soundReader.readSound(GameConstants.BALL_SOUND_PATH);
        Vector2 extraBallDimension =
                new Vector2((float) (GameConstants.BALL_SIZE_VECTOR.x() * GameConstants.MOCK_BALL_DIFF_CONST),
                        (float) (GameConstants.BALL_SIZE_VECTOR.y() * GameConstants.MOCK_BALL_DIFF_CONST));

        //Create it
        MockBall mockBall = new MockBall(Vector2.ZERO, extraBallDimension, ballImage, ballSound);

        // Put it on board
        setRandomMockBallVelocity(mockBall);
        mockBall.setCenter(windowController.getWindowDimensions().mult(GameConstants.HALF_FACTOR_MODIFIER));

        this.gameObjects().addGameObject(mockBall, Layer.DEFAULT);
        mockBall.setCenter(thisObj.getCenter());
    }

    /**
     * this function sets a random Mock Ball Velocity on receiving a Mock Ball to be added to the game.
     *
     * @param mockBall the MockBall that we are setting his Velocity.
     */
    private void setRandomMockBallVelocity(MockBall mockBall) {
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;

        float velocityX = (float) Math.cos(angle) * GameConstants.BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * GameConstants.BALL_SPEED;

        mockBall.setVelocity(new Vector2(velocityX, velocityY));
    }

    /**
     * Activates the camera within the game. This method sets the current view to the camera provided,
     * marks the camera as active, and updates the hit state based on the ball's current collision counter.
     * Usage of this method is essential for scenarios where the game's view needs to be focused or
     * altered through a different camera perspective, often used for special effects or focusing on
     * specific game elements.
     *
     * @param camera The Camera object to be activated. It is expected that this object is not null and
     *               properly configured before being passed to this method.
     */
    public void activateCamera(Camera camera) {
        setCamera(camera);
        isCameraActive = true;
        this.hitState = ball.getCollisionCounter();
    }

    // ****************************** MAIN ********************************* //

    /**
     * The entry point of the program. This method initializes the game manager and starts the game.
     * It supports command-line arguments to customize the number of rows and bricks per row.
     *
     * @param args Command-line arguments. The first argument is the number of rows, and the second is
     *             the number of bricks per row. If not provided, default values are used.
     */
    public static void main(String[] args) {
        BrickerGameManager gameManager;
        if (args.length == 2) {
            int numberOfRows = Integer.parseInt(args[0]);
            int numberOfBricksPerRow = Integer.parseInt(args[1]);
            gameManager = new BrickerGameManager(GameConstants.BRICKER,
                    GameConstants.WINDOW_DIMENSION_VECTOR,
                    numberOfRows, numberOfBricksPerRow);
        } else {
            gameManager = new BrickerGameManager(GameConstants.BRICKER,
                    GameConstants.WINDOW_DIMENSION_VECTOR);
        }
        gameManager.run();
    }
}


