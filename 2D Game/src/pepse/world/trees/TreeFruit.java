package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.world.Actionable;
import pepse.world.Avatar;
import pepse.world.Block;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

/**
 * A class that represents a single tree fruit game object
 */
public class TreeFruit extends Block implements Actionable {
    // Constants:
    private static final String TAG = "treeFruit";
    private static final String AVATAR_TAG = "avatar";

    private final BiConsumer<GameObject, Integer> addGameObj;
    private final BiConsumer<GameObject, Integer> removeGameObj;
    private int colorIndex = 0;
    private final List<Color> colors = List.of(
            ColorSupplier.approximateColor(Constants.FRUIT_INIT_COLOR),
            ColorSupplier.approximateColor(Constants.FRUIT_COLOR_OPTION1),
            ColorSupplier.approximateColor(Constants.FRUIT_COLOR_OPTION2),
            ColorSupplier.approximateColor(Constants.FRUIT_COLOR_OPTION3),
            ColorSupplier.approximateColor(Constants.FRUIT_COLOR_OPTION4),
            ColorSupplier.approximateColor(Constants.FRUIT_COLOR_OPTION5)
    );

    /**
     * Constructor for the TreeFruit class.
     *
     * @param topLeftCorner     The top left corner of the tree fruit.
     * @param addGameObjFunc    Function to add a game object to the game
     * @param removeGameObjFunc Function to remove a game object from the game
     */
    public TreeFruit(Vector2 topLeftCorner, BiConsumer<GameObject, Integer> addGameObjFunc,
                     BiConsumer<GameObject, Integer> removeGameObjFunc) {
        super(topLeftCorner, new OvalRenderable(ColorSupplier.approximateColor(Constants.FRUIT_INIT_COLOR)));
        this.addGameObj = addGameObjFunc;
        this.removeGameObj = removeGameObjFunc;
        this.setTag(TAG);
    }

    /**
     * Called when a collision occurs with this GameObject.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (Objects.equals(other.getTag(), AVATAR_TAG)) {
            Avatar avatar = (Avatar) other;
            avatar.addEnergy(Constants.CALORIE_PER_FRUIT);
        }
        this.disappear();
    }

    /**
     * Called when the GameObject is clicked.
     */
    private void disappear() {
        TreeFruit me = this;
        removeGameObj.accept(me, Constants.ObjectLayer.FRUIT.getLayer());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                addGameObj.accept(me, Constants.ObjectLayer.FRUIT.getLayer());
            }
        }, Constants.FRUIT_DELAY);

    }

    /**
     * Called when the GameObject is clicked.
     */
    @Override
    public void onJump() {
        colorIndex = (colorIndex + 1) % colors.size();
        // Set the renderer to the new color
        this.renderer().setRenderable(new OvalRenderable(colors.get(colorIndex)));
    }
}

