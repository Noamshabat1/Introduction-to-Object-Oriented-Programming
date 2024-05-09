package bricker.gameobjects;

import bricker.utils.GameConstants;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;


/**
 * Represents the paddle in the game, handling its logic and movements.
 * This class extends GameObject and is responsible for the paddle's
 * behaviors, including reacting to user input, maintaining its position,
 * and preventing it from moving beyond the game window's boundaries.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class Paddle extends GameObject {
    private final UserInputListener inputListener; // Listener for user input events
    private final Vector2 windowDimensions; // The dimensions of the game window
    private final float minDistFromEdge; // Minimum distance from the edge of the game window

    /**
     * Constructs a new Paddle object.
     *
     * @param topLeftCorner    The top left corner of the paddle in the game window.
     * @param dimensions       The dimensions of the paddle.
     * @param renderable       The graphical representation of the paddle.
     * @param inputListener    Listener for user input.
     * @param windowDimensions The dimensions of the game window.
     * @param minDistFromEdge  The minimum distance from the edge of the game window.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions, float minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;
        setTag("Paddle");
    }

    /**
     * Updates the paddle's position based on user input and game logic.
     * Ensures the paddle does not move beyond the game window's boundaries.
     *
     * @param deltaTime Time's elapsed since the last update call.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDirection = Vector2.ZERO;
        // Left movement
        if (this.inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if ((this.getCenter().x() - super.getDimensions().x() / 2) - this.minDistFromEdge > 0) {
                movementDirection = movementDirection.add(Vector2.LEFT);
            }
        }
        // Right movement
        if (this.inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if ((this.getCenter().x() + super.getDimensions().x() / 2) +
                    this.minDistFromEdge < this.windowDimensions.x()) {
                movementDirection = movementDirection.add(Vector2.RIGHT);
            }
        }

        super.setVelocity(movementDirection.mult(GameConstants.PADDLE_MOVEMENT_SPEED));
    }
}