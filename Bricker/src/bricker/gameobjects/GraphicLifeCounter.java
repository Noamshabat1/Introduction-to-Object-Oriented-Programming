package bricker.gameobjects;

import bricker.utils.GameConstants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * Manages a graphical representation of the player's lives in the game.
 * This class extends GameObject and is responsible for displaying life icons or similar graphics to
 * represent the player's remaining lives.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class GraphicLifeCounter extends GameObject {
    private final Vector2 widgetTopLeftCorner; // Top left corner of the life counter widget
    private final Vector2 widgetDimensions; // Dimensions of each heart icon
    private final Renderable widgetRenderable; // Graphical representation of each heart
    private final GameObjectCollection gameObjects; // Collection of game objects
    private final Counter livesCounter; // Counter representing the number of lives
    private int numOfLives; // Current number of lives
    private final Heart[] hearts; // Array to store heart objects


    /**
     * Constructs a new GraphicLifeCounter object.
     *
     * @param widgetTopLeftCorner   The top left corner of the widget in the game window.
     * @param widgetDimensions      The dimensions of each heart in the life counter.
     * @param livesCounter          The counter object representing the number of lives.
     * @param widgetRenderable      The graphical representation for each heart.
     * @param gameObjectsCollection Collection of all game objects for adding/removing hearts.
     * @param numOfLives            Initial number of lives.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions,
                              Counter livesCounter, Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection, int numOfLives) {
        super(widgetTopLeftCorner, Vector2.ZERO, null);

        this.widgetTopLeftCorner = widgetTopLeftCorner;
        this.widgetDimensions = widgetDimensions;
        this.widgetRenderable = widgetRenderable;
        this.gameObjects = gameObjectsCollection;
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.hearts = new Heart[GameConstants.MAX_NUMBER_OF_LIFE];
        this.initiateHearts();
    }

    /**
     * Initializes heart objects according to the initial number of lives.
     */
    private void initiateHearts() {
        for (int i = 0; i < this.numOfLives; i++) {
            this.createHeart(i);
        }
    }

    /**
     * Updates the life counter every frame. Adjusts the number of heart objects
     * to match the current number of lives.
     *
     * @param deltaTime Time since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Adjust the number of displayed hearts to life counter
        int currentLives = this.livesCounter.value();

        // Update the number of lives to match the counter
        if (numOfLives < currentLives) {
            this.createHeart(numOfLives);
            numOfLives++;
        } else if (currentLives < numOfLives) {
            numOfLives--;
            this.gameObjects.removeGameObject(hearts[numOfLives], Layer.UI);
            hearts[numOfLives] = null;
        }
    }

    /**
     * Creates a heart object at a specified index.
     *
     * @param heartId The index at which the heart object should be created.
     */
    private void createHeart(int heartId) {
        Vector2 heartPosition = new Vector2(
                this.widgetTopLeftCorner.x() + heartId *
                        (this.widgetDimensions.x() + GameConstants.HEART_WIDGET_PADDING),
                this.widgetTopLeftCorner.y());

        Heart newHeart = new Heart(heartPosition, this.widgetDimensions, this.widgetRenderable, true,
                this.livesCounter, gameObjects);
        this.hearts[heartId] = newHeart;
        this.gameObjects.addGameObject(newHeart, Layer.UI);
    }
}
