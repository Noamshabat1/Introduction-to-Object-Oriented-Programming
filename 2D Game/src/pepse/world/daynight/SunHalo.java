package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import pepse.util.Constants;

import java.awt.*;

/**
 * Class that creates and adds a sun halo GameObject to the specified game layer.
 * This GameObject simulates the sun's halo by orbiting around the game's sky.
 *
 * @author Noam shabat, Samuel Hayat
 */
public class SunHalo {
    private static final String TAG = "sunHalo";
    private final static float HALO_DIMENSIONS_MULTIPLIER = 1.5f;

    /**
     * Creates and adds a sun halo GameObject to the specified game layer.
     * This GameObject simulates the sun halo by orbiting around the game's sky.
     *
     * @param sun The sun GameObject to orbit around.
     * @return The created sun halo GameObject.
     */
    public static GameObject create(GameObject sun) {
        OvalRenderable sunHaloRender = new OvalRenderable(Constants.SUN_HALO_COLOR);

        GameObject sunHalo = new GameObject(sun.getTopLeftCorner(),
                sun.getDimensions().mult(HALO_DIMENSIONS_MULTIPLIER), sunHaloRender);

        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(TAG);

        sunHalo.addComponent((deltaTime -> sunHalo.setCenter(sun.getCenter())));

        return sunHalo;
    }
}
