package pepse.world;

import danogl.GameObject;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.util.NoiseGenerator;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;


/**
 * Manages the creation and representation of game terrain, using Perlin noise
 * for natural landscape generation.
 */
public class Terrain {
    private static final String TAG = "ground";
    private final Vector2 windowDimensions;
    private final float groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;


    /**
     * Constructor for the Terrain class.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param seed             The seed for the Perlin noise generator.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = ((float) 1 / 2) * windowDimensions.y();
        this.windowDimensions = windowDimensions;
        int startPoint = (int) windowDimensions.y();
        Random random = new Random();
        this.noiseGenerator = new NoiseGenerator(random.nextFloat() + seed, startPoint);
    }


    /**
     * Creates a list of blocks within the given range.
     *
     * @param minX The minimum x value.
     * @param maxX The maximum x value.
     * @return The list of blocks created.
     */
    public List<GameObject> createInRange(int minX, int maxX) {
        List<GameObject> createdObjects = new ArrayList<>();

        for (float x = minX; x < maxX; x += Constants.BLOCK_SIZE) {
            float height = (float) Math.floor(this.groundHeightAt(x) / Constants.BLOCK_SIZE);
            for (int i = 0; i < height; i++) {
                createdObjects.add(this.createBlock(x, windowDimensions.y() - (i * Constants.BLOCK_SIZE)));
            }
        }
        return createdObjects;
    }

    /**
     * Gets the tag of the ground object
     * @param x The x coordinate.
     *
     * @return The tag of the ground object
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Constants.BLOCK_SIZE * 7); //TODO: why 7?
        return groundHeightAtX0 + noise;
    }

    /**
     * Creates a single block at the given coordinates
     *
     * @param x Block X
     * @param y Block Y
     * @return Created block
     */
    private Block createBlock(float x, float y) {
        RectangleRenderable renderableBlock = new RectangleRenderable(
                ColorSupplier.approximateColor(Constants.BASE_GROUND_COLOR));
        return new Block(new Vector2(x, y), renderableBlock);
    }
}



