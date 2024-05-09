package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.world.Actionable;
import pepse.world.Block;

/**
 * A class that represents a single log game object
 */
public class Log extends Block implements Actionable {
    private static final String TAG = "log";
    /**
     * Constructor for the Log class.
     *
     * @param topLeftCorner The top left corner of the log.
     */
    public Log(Vector2 topLeftCorner) {

        super(topLeftCorner,
                new RectangleRenderable(ColorSupplier.approximateColor(Constants.LOG_COLOR)));

        this.setTag(TAG);
    }

    /**
     * Called when the GameObject is clicked.
     */
    @Override
    public void onJump() {
        this.renderer().setRenderable(new
                RectangleRenderable(ColorSupplier.approximateColor(Constants.LOG_COLOR)));
    }
}