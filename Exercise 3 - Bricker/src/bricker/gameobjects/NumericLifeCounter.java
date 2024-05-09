package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;


/**
 * Manages the numeric representation of the player's lives in the game.
 * This class extends GameObject and is responsible for displaying and
 * updating the number of lives the player has remaining.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class NumericLifeCounter extends GameObject {

    private final Color COLOR_ONE_LIFE = Color.red; // Color when one life is remaining
    private final Color COLOR_TWO_LIVES = Color.yellow; // Color when two lives are remaining
    private final Color COLOR_DEFAULT = Color.green; // Default color for more than two lives
    private final Counter livesCounter; // Counter object representing the number of lives
    private final TextRenderable textObject; // Text object for displaying the number of lives

    /**
     * Constructs a new NumericLifeCounter object to display the number of lives numerically.
     *
     * @param livesCounter         The counter-object representing the number of lives.
     * @param topLeftCorner        The top left corner of the numeric display in the game window.
     * @param dimensions           The dimensions of the numeric display.
     * @param gameObjectCollection Collection of all game objects for adding this counter.
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, Vector2.ZERO, null);

        this.livesCounter = livesCounter;

        this.textObject = new TextRenderable(this.livesCounter.value() + "");
        this.textObject.setColor(COLOR_DEFAULT);

        gameObjectCollection.addGameObject(new GameObject(topLeftCorner, dimensions, this.textObject),
                Layer.UI);
    }

    /**
     * Updates the numeric display of lives each frame. Adjusts the number and color
     * based on the current number of lives.
     *
     * @param deltaTime Time since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int currentLives = livesCounter.value();

        this.textObject.setString(currentLives + "");
        updateColor();
    }

    /**
     * Updates the color of the numeric life counter based on the current number of lives.
     */
    private void updateColor() {
        int currentLives = livesCounter.value();
        if (currentLives == 1) {
            this.textObject.setColor(COLOR_ONE_LIFE);
        } else if (currentLives == 2) {
            this.textObject.setColor(COLOR_TWO_LIVES);
        } else {
            this.textObject.setColor(COLOR_DEFAULT);
        }
    }
}