package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;


/**
 * Utility class for creating a sky object that spans the entire game window.
 * The sky is rendered in the camera's coordinate space, meaning it remains static
 * as the camera moves, simulating a distant background.
 */
public class Sky {
    private static final String TAG = "sky";

    /**
     * Create the sun and return it
     * @param windowDimensions the dimension of the main window
     * @return a newly created instance of the sun
     */
    public static GameObject create(Vector2 windowDimensions) {
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Constants.BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(TAG);
        return sky;
    }

    /**
     * Gets the tag of the sky object
     *
     * @return The tag of the sky object
     */
    public static String getTag() {
        return TAG;
    }
}