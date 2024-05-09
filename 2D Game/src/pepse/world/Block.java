package pepse.world;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;


/**
 * A class that represents a single block game object
 */
public class Block extends GameObject {
    private static final String TAG = "block";
    private static final int SIZE = Constants.BLOCK_SIZE;

    /**
     * Constructor for the Block class.
     *
     * @param topLeftCorner The top left corner of the block.
     * @param renderable    The renderable of the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag(TAG);
    }
}