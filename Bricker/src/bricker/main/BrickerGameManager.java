package bricker.main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;
import gameobjects.Ball;

public class BrickerGameManager extends GameManager {
    public static final float BORDER_WIDTH = 20.0f;

    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final Vector2 PADDLE_SIZE = new Vector2(100, 20);
    private static final int PADDLE_PADDING = 20;

    private static final String BRICK_IMAGE_PATH = "assets/brick.png";
    private static final int BRICKS_TOP_PADDING = 40;
    private static final int BRICKS_PER_ROW = 7;
    private static final int BRICK_HEIGHT = 15;
    private static final int BRICK_PADDING = 2;

    private final Vector2 windowDimensions;
    private WindowController windowController;
    private UserInputListener inputListener;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);

        this.windowDimensions = windowDimensions;
    }


    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // creating ball
        ImageRenderable ballImage = imageReader.readImage("assets/ball.png", true);
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage);
//        ball.setVelocity(new Vector2(0,100));
        ball.setVelocity(Vector2.DOWN.mult(200));
        Vector2 windowDimension =  windowController.getWindowDimensions();
        ball.setCenter(windowDimension.mult(0.5f));
        this.gameObjects().addGameObject(ball);

        // creating paddle
//        ImageRenderable paddleImage = imageReader.readImage("assets/paddle.png", true);
//        GameObject paddle = new GameObject(Vector2.ZERO,new Vector2(100, 15), paddleImage);
//        gameObjects().addGameObject(paddle);
//        paddle.setCenter(new Vector2(windowDimension.x()/2, windowDimension.y()-30));

        // creating paddles
        int [] paddleHeight = {(int) windowDimension.y()-30,30};

        ImageRenderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        for (int i = 0; i<paddleHeight.length; i++){
            GameObject paddle = new GameObject(Vector2.ZERO,new Vector2(100, 15), paddleImage);
            gameObjects().addGameObject(paddle);
            paddle.setCenter(new Vector2(windowDimension.x()/2,paddleHeight[i]));

        }
    }

    private void createBorders() {
        // Right Border
        this.gameObjects().addGameObject(
                new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()), null)
        );

        // Left Border
        this.gameObjects().addGameObject(
                new GameObject(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()), null)
        );

        // Top Border
        // noinspection SuspiciousNameCombination
        this.gameObjects().addGameObject(
                new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), BORDER_WIDTH), null)
        );
    }



    public static void main(String[] args) {

        BrickerGameManager gameManager = new BrickerGameManager("Bricker", new Vector2(700, 500));
        gameManager.run();
    }
}
