package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;

/**
 * Class that creates and adds a night overlay GameObject to the specified game layer.
 * This overlay simulates night by adjusting its opacity based on the game's day-night cycle.
 *
 * @author Noam shabat, Samuel Hayat
 */
public class Night {
    private static final String NIGHT_OBJECT_TAG = "night";

    /**
     * Creates and adds a night overlay GameObject to the specified game layer.
     * This overlay simulates night by adjusting its opacity based on the game's day-night cycle.
     *
     * @param windowDimensions The dimensions of the game window, used to size the night overlay.
     * @param cycleDuration    The duration of the full day-night cycle in the game.
     * @return The created night overlay GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleDuration) {
        RectangleRenderable nightOverlayRenderable = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, nightOverlayRenderable);

        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_OBJECT_TAG);

        new Transition<>(
                night,
                night.renderer()::setOpaqueness,
                Constants.AT_NOON_OPACITY,
                Constants.MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleDuration / 2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        return night;
    }
}
