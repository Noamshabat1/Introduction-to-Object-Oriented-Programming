package pepse.util;

import danogl.collisions.Layer;

import java.awt.*;

/**
 * The Constants class contains all the constants used in the game.
 *
 * @author Noam shabat, Samuel Hayat
 */
public class Constants {
    //Sky:
    /**
     * The color of the sky at noon
     */
    public static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    // Night:
    /**
     * The opacity of the night overlay at midnight
     */
    public static final float MIDNIGHT_OPACITY = 0.5f;
    /**
     * The opacity of the night overlay at noon
     */
    public static final float AT_NOON_OPACITY = 0f;

    // Sun:
    /**
     * The position of the sun at noon
     */
    public static final float MIDDAY_SUN_POSITION = (float) Math.PI / 2;
    /**
     * The position of the sun at midnight
     */
    public static final float MIDNIGHT_SUN_POSITION = (float) (2.5 * Math.PI);

    // Sun Halo:
    /**
     * The multiplier for the dimensions of the sun halo
     */
    public static final float SUN_RADIUS = 100;
    /**
     * The radius of the sun's orbit
     */
    public static final float SUN_ORBIT_RADIUS = 125;
    /**
     * The A value of the sun's orbit
     */
    public static final float SUN_ORBIT_A = 5;
    /**
     * The B value of the sun's orbit
     */
    public static final float SUN_ORBIT_B = 3;

    /**
     *
     */
    public static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);

    // Terrain:
    /**
     * The color of the ground
     */
    public static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    /**
     * The game seed used to generate the terrain
     */
    public static final int GAME_SEED = 20;

    // Block:
    /**
     * The size of a block in the game
     */
    public static final int BLOCK_SIZE = 30;

    // Avatar:
    /**
     * The energy factor of the avatar
     */
    public static final int AVATAR_ENERGY_FACTOR = 10;
    /**
     * The maximum energy of the avatar
     */
    public static final int AVATAR_MAX_ENERGY = 100;
    /**
     * The minimum energy of the avatar
     */
    public static final int AVATAR_MIN_ENERGY = 0;
    /**
     * The energy loss of the avatar when moving
     */
    public static final float AVATAR_MOVE_ENERGY_LOSS = 0.5f;

    /**
     * The X Position of the avatar
     */
    public static final int AVATAR_X_POSITION = 760;
    /**
     * The Y Position of the avatar
     */
    public static final int AVATAR_Y_POSITION = 300;

    // Flora:
    // none for now.

    // Leaf:
    /**
     * The size of the leaf
     */
    public static final int LEAFS_GRID_SIZE = 6;
    /**
     * The fade length of the leaf
     */
    public static final float LEAF_FADE_LENGTH = 5;
    /**
     * The x velocity of the leaf
     */
    public static final float LEAF_X_VELOCITY = 30;
    /**
     * The y velocity of the leaf
     */
    public static final float LEAF_Y_VELOCITY = 110;
    /**
     * The angle transition starting value of the leaf
     */
    public static final float LEAF_ANGLE_TRANSITION_STARTING_VALUE = -2 * (float) Math.PI;
    /**
     * The angle transition ending value of the leaf
     */
    public static final float LEAF_ANGLE_TRANSITION_ENDING_VALUE = 2 * (float) Math.PI;
    /**
     * The size transition starting value of the leaf
     */
    public static final float LEAF_SIZE_TRANSITION_STARTING_VALUE = 1.0f;
    /**
     * The size transition ending value of the leaf
     */
    public static final float LEAF_SIZE_TRANSITION_ENDING_VALUE = 0.95f;
    /**
     * The cycle length of the leaf transitions
     */
    public static final float LEAF_TRANSITIONS_CYCLE_LENGTH = 0.4f;
    /**
     * The number of leaves to spawn
     */
    public static final int LEAF_SPAWN_RATE = 2;
    /**
     * the color of the leaf
     */
    public static final Color LEAF_COLOR = new Color(50, 180, 30);
    /**
     * The mass of the leaf
     */
    public static final float LEAF_MASS = 1.0f;

    /**
     * The minimum delay in seconds for the leaf to fall
     */
    public static final int MIN_DELAY_SECONDS_ANI = 11;
    /**
     * The maximum delay in seconds for the leaf to fall
     */
    public static final int MAX_DELAY_SECONDS_ANI = 21;

    /**
     * The minimum delay in seconds for the leaf to fall
     */
    public static final int MIN_DELAY_SECONDS = 2;
    /**
     * The maximum delay in seconds for the leaf to fall
     */
    public static final int MAX_DELAY_SECONDS = 10;

    /**
     * The rotation angle of the leaf on jump.
     */
    public static final int ROTATION_ANGLE = 90;

    // Log:
    /**
     * The color of the log
     */
    public static final Color LOG_COLOR = new Color(100, 48, 20);

    // Fruit:
    /**
     * The initial color of the fruit
     */
    public static final Color FRUIT_INIT_COLOR = new Color(255, 90, 0);
    /**
     * The color of the fruit when first jump.
     */
    public static final Color FRUIT_COLOR_OPTION1 = new Color(173, 255, 47);
    /**
     * The color of the fruit when Second jump.
     */
    public static final Color FRUIT_COLOR_OPTION2 = new Color(40, 105, 230);
    /**
     * The color of the fruit when Third jump.
     */
    public static final Color FRUIT_COLOR_OPTION3 = new Color(100, 10, 255);
    /**
     * The color of the fruit when Fourth jump.
     */
    public static final Color FRUIT_COLOR_OPTION4 = new Color(180, 200, 100);
    /**
     * The color of the fruit when Fifth jump.
     */
    public static final Color FRUIT_COLOR_OPTION5 = new Color(150, 10, 150);
    /**
     * The radius of the fruit
     */
    public static final int FRUIT_SPAWN_RATE = 10;

    /**
     * The delay of the fruit
     */
    public static final int FRUIT_DELAY = 30000;

    // UI:
    /**
     * The x position of the energy display
     */
    public static final int ENERGY_X_POSITION = 50;
    /**
     * The y position of the energy display
     */
    public static final int ENERGY_Y_POSITION = 50;


    //GameManager:
    /**
     * The length of the day cycle
     */
    public static final float DAY_CYCLE_LENGTH = 30;
    /**
     * The energy gained per fruit
     */
    public static final Float CALORIE_PER_FRUIT = 20f;

    /**
     * The layers of the game objects
     */
    public enum ObjectLayer {
        /**
         * The avatar layer and some description
         */
        AVATAR(Layer.DEFAULT, "Avatar's like the player in the game"),
        /**
         * The night layer and some description
         */
        NIGHT_SKY(Layer.FOREGROUND, "The night sky background"),
        /**
         * The sky layer and some description
         */
        SKY(Layer.BACKGROUND, "The sky background"),
        /**
         * The sun layer and some description
         */
        SUN(Layer.BACKGROUND, "The sun in the background"),
        /**
         * The sun halo layer and some description
         */
        SUN_HALO(Layer.BACKGROUND, "Halo effect around the sun"),
        /**
         * The terrain layer and some description
         */
        TERRAIN(Layer.STATIC_OBJECTS, "Non-collidable terrain"),
        /**
         * The tree layer and some description
         */
        TREE(Layer.FOREGROUND, "Trees that appear in the foreground"),
        /**
         * The trunk of the tree layer and some description
         */
        LOG(Layer.STATIC_OBJECTS, "Logs that appear in the foreground"),
        /**
         * The leaf layer and some description
         */
        LEAF(Layer.BACKGROUND, "Leaves that appear just above logs"),
        /**
         * The fruit layer and some description
         */
        FRUIT(Layer.STATIC_OBJECTS, "The fruit that can be eaten by the player");


        private final int layer;
        private final String description;

        /**
         * Constructor for the ObjectLayer enum.
         *
         * @param layer       The layer of the game object
         * @param description The description of the layer
         */
        ObjectLayer(int layer, String description) {
            this.layer = layer;
            this.description = description;
        }

        /**
         * Getter for the layer of the game object
         *
         * @return The layer of the game object
         */
        public int getLayer() {
            return this.layer;
        }
    }
}
