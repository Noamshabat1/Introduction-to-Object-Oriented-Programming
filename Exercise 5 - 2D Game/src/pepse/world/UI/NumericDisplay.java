package pepse.world.UI;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * A class that represents a numeric display game object
 */
public class NumericDisplay{
    private final TextRenderable textRenderable ;
    private final Supplier<Float> getEnergy ;

    /**
     * Constructor for the NumericDisplay class.
     *
     * @param topLeftCorner The top left corner of the numeric display.
     * @param dimensions    The dimensions of the numeric display.
     * @param getEnergyFunc Function to get the energy value
     * @param adder         Function to add a game object to the game
     */
    public NumericDisplay(Vector2 topLeftCorner, Vector2 dimensions, Supplier<Float> getEnergyFunc,
                            BiConsumer<GameObject, Integer> adder) {
        this.getEnergy = getEnergyFunc ;
        textRenderable = new TextRenderable("Energy : "+ getEnergy.get() + "%");
        textRenderable.setColor(Color.WHITE);

        GameObject gameObject = new GameObject(topLeftCorner, dimensions, textRenderable);
        gameObject.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        adder.accept(gameObject, Layer.UI);

    }

    /**
     * Updates the energy value of the numeric display.
     */
    public void updateEnergy(){
        textRenderable.setString("Energy : "+ getEnergy.get() + "%");
    }
}

