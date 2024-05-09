package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;

/**
 * Class that creates and adds a sun GameObject to the specified game layer.
 * This GameObject simulates the sun by orbiting around the game's sky.
 *
 * @author Noam shabat, Samuel Hayat
 */
public class Sun {
    private static final String TAG = "sun";

    /**
     * Creates and adds a sun GameObject to the specified game layer.
     * This GameObject simulates the sun by orbiting around the game's sky.
     *
     * @param windowDimensions The dimensions of the game window, used to size the sun.
     * @param cycleLength      The duration of the full day-night cycle in the game.
     * @return The created sun GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        float screenMidX = (windowDimensions.x() / 2);
        float screenMidY = (windowDimensions.y() / 2);

        float sunX = screenMidX - (Constants.SUN_RADIUS / 2);
        float sunY = screenMidY - (Constants.SUN_RADIUS / 2);

        OvalRenderable renderer = new OvalRenderable(Color.YELLOW);

        GameObject sun = new GameObject(new Vector2(sunX, sunY),
                Vector2.ONES.mult(Constants.SUN_RADIUS), renderer);

        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(TAG);


        new Transition<>(
                sun,
                (degree) -> sun.setCenter(new Vector2(
                        (float) Math.cos(-degree) * Constants.SUN_ORBIT_RADIUS *
                                Constants.SUN_ORBIT_A + screenMidX,
                        (float) Math.sin(-degree) * Constants.SUN_ORBIT_RADIUS *
                                Constants.SUN_ORBIT_B + screenMidY
                )),
                Constants.MIDDAY_SUN_POSITION,
                Constants.MIDNIGHT_SUN_POSITION,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        return sun;
    }
}
