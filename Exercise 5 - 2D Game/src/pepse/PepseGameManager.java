package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.WindowController;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.util.Constants;
import pepse.world.Avatar;
import pepse.world.Sky;
import pepse.world.UI.NumericDisplay;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The main game manager for the Pepse game.
 */
public class PepseGameManager extends GameManager {

    private WindowController windowController;
    private Vector2 windowDimensions;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private Flora flora;
    private Avatar avatar;
    private NumericDisplay numericEnergyDisplay;

    /**
     * The main method for the game.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    /**
     * Initializes the game.
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.imageReader = imageReader;

        this.createBackground();
        this.createAvatar();
        this.createTerrain();

    }

    /**
     * Creates the background of the game.
     */
    private void createBackground() {
        GameObject sky = Sky.create(this.windowDimensions);
        gameObjects().addGameObject(sky, Constants.ObjectLayer.SKY.getLayer());

        GameObject night = Night.create(this.windowDimensions, Constants.DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(night, Constants.ObjectLayer.NIGHT_SKY.getLayer());

        GameObject sun = Sun.create(this.windowDimensions, Constants.DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Constants.ObjectLayer.SUN.getLayer());

        GameObject halo = SunHalo.create(sun);
        gameObjects().addGameObject(halo, Constants.ObjectLayer.SUN_HALO.getLayer());
    }

    /**
     * Creates the avatar of the game.
     */



    private void createAvatar() {
        this.avatar = new Avatar(Vector2.of(Constants.AVATAR_X_POSITION, Constants.AVATAR_Y_POSITION),
                inputListener, imageReader);
        setCamera(new Camera(this.avatar, Vector2.ZERO, windowDimensions, windowDimensions));
        gameObjects().addGameObject(this.avatar, Constants.ObjectLayer.AVATAR.getLayer());

        this.numericEnergyDisplay = new NumericDisplay(Vector2.of(Constants.ENERGY_X_POSITION,
                Constants.ENERGY_Y_POSITION),
                Vector2.of(Constants.ENERGY_X_POSITION, Constants.ENERGY_Y_POSITION),
                avatar::getEnergy, gameObjects()::addGameObject);
    }

    /**
     * Creates the terrain of the game.
     */
    private void createTerrain() {
        Terrain ground = new Terrain(this.windowDimensions, Constants.GAME_SEED);
        List<GameObject> blockArray = ground.createInRange(0,
                (int) windowController.getWindowDimensions().x());

        for (GameObject block : blockArray) {
            block.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
            gameObjects().addGameObject(block, Constants.ObjectLayer.TERRAIN.getLayer());
        }

        this.flora = new Flora(windowDimensions, ground::groundHeightAt, gameObjects()::addGameObject,
                gameObjects()::removeGameObject, avatar::addToObserver);
        ArrayList<ArrayList<GameObject>> treesArray = flora.createInRange(0, (int) windowDimensions.x());

        for (GameObject log : treesArray.get(0)) {
            gameObjects().addGameObject(log, Constants.ObjectLayer.LOG.getLayer());
        }
        for (GameObject leaf : treesArray.get(1)) {
            gameObjects().addGameObject(leaf, Constants.ObjectLayer.LEAF.getLayer());
        }
        for (GameObject fruit : treesArray.get(2)) {
            gameObjects().addGameObject(fruit, Constants.ObjectLayer.FRUIT.getLayer());
        }
    }


    /**
     * Runs the game.
     */
    @Override
    public void run() {
        super.run();
    }

    /**
     * Updates the game.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        isKeyWPressed();
        updateGraphicEnergy();

    }

    /**
     * Checks if the key W is pressed.
     */
    private void isKeyWPressed() {
        if (this.inputListener.isKeyPressed(KeyEvent.VK_W)) {
            if (this.windowController.openYesNoDialog("Thank you for playing!, Do you want to play again?")) {
                this.windowController.resetGame();
            } else {
                this.windowController.closeWindow();
            }
        }
    }

    /**
     * Updates the graphic energy.
     */
    private void updateGraphicEnergy() {
        numericEnergyDisplay.updateEnergy();
    }
}

//TODO: in here we will add updates regarding the game state and problems.
/**
 * 1. Plant the trees on the floor (7.3.2) -> Done
 * 2. Implement the character energy + visual effect -> Done
 * 3.5 The avatar has an observer -> Done
 * 3. We may change the name of flora class to flora (7.2) -> Done
 * 4. (7.3.2) collision with flora -> Done
 * 5. The observer works -> Done
 * 6. The fruits are eatable -> Done
 */