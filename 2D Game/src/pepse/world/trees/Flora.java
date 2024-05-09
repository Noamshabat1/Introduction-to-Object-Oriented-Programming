package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.util.Vector2;
import pepse.util.Constants;
import pepse.util.Round;
import pepse.world.Actionable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Handles the creation of trees within the game, implementing tree generation logic.
 */
public class Flora {


    // Constants:
    private static final int TREE_SPAWN_RATE = 10;
    private static final int TREE_HEIGHT_RATE = 6;
    private static final int TREE_MIN_HEIGHT = 5;
    private final Vector2 windowDimensions;
    private final Function<Float, Float> getHeight;
    private final Random random;
    private final BiConsumer<GameObject, Integer> addGameObj;
    private final BiConsumer<GameObject, Integer> removeGameObj;
    private final Consumer<Actionable> addToListener;

    /**
     * Constructor for the Flora class.
     *
     * @param windowDimensions  The dimensions of the window
     * @param getHeight         Function to get the height of the terrain at a given x coordinate
     * @param addGameObjFunc    Function to add a game object to the game
     * @param removeGameObjFunc Function to remove a game object from the game
     * @param addToListenerFunc Function to add an actionnable to the listener
     */
    public Flora(Vector2 windowDimensions, Function<Float, Float> getHeight,
                 BiConsumer<GameObject, Integer> addGameObjFunc,
                 BiConsumer<GameObject, Integer> removeGameObjFunc,
                 Consumer<Actionable> addToListenerFunc) {
        this.windowDimensions = windowDimensions;
        this.random = new Random();
        this.getHeight = getHeight;
        this.addGameObj = addGameObjFunc;
        this.removeGameObj = removeGameObjFunc;
        this.addToListener = addToListenerFunc;
    }


    /**
     * Creates trees within the given range of x coordinates.
     *
     * @param minX The minimum x coordinate
     * @param maxX The maximum x coordinate
     * @return A list of the created game objects
     */
    public ArrayList<ArrayList<GameObject>> createInRange(int minX, int maxX) {

        ArrayList<ArrayList<GameObject>> createdObjects = new ArrayList<>();
        ArrayList<GameObject> logArrayList = new ArrayList<>();
        createdObjects.add(logArrayList);
        ArrayList<GameObject> leavesArrayList = new ArrayList<>();
        createdObjects.add(leavesArrayList);
        ArrayList<GameObject> fruitArrayList = new ArrayList<>();
        createdObjects.add(fruitArrayList);


        minX = Round.round(minX, Constants.BLOCK_SIZE);
        maxX = Round.round(maxX, Constants.BLOCK_SIZE);

        for (int x = minX; x < maxX; x += Constants.BLOCK_SIZE) {
            if (Math.abs(x - (windowDimensions.x() / 2)) < 100) {
                continue;
            }

            if (random.nextInt(TREE_SPAWN_RATE) == 1) {
//                System.out.println("Creating tree");
                // Tree's height varies between 5 and 10.
                int treeHeight = random.nextInt(TREE_HEIGHT_RATE) + TREE_MIN_HEIGHT;
                createTree(createdObjects, x, treeHeight);
                x += Constants.BLOCK_SIZE; // To ensure trees do not spawn too close to each other.
            }
        }
        return createdObjects;
    }

    /**
     * Creates a tree at the given x coordinate.
     *
     * @param createdObjects List to add created objects to
     * @param xCoordinate    X coordinate of the tree
     * @param treeHeight     Height of the tree
     */
    private void createTree(ArrayList<ArrayList<GameObject>> createdObjects,
                            int xCoordinate, int treeHeight) {
        int baseY = (int) Math.floor(getHeight.apply((float) xCoordinate) / Constants.BLOCK_SIZE);
        this.createLog(createdObjects, xCoordinate, baseY, treeHeight);
        this.createLeaves(createdObjects, xCoordinate, baseY, treeHeight);
        this.createFruit(createdObjects, xCoordinate, baseY, treeHeight);
    }


    /**
     * Creates all the logs of the current tree and adds them to the given list
     *
     * @param createdObjects List to add created logs to
     * @param xLocation      X location of the tree to create the logs of
     * @param height         Ground height at the tree to create the logs of
     * @param treeHeight     Height of the tree to create
     */
    private void createLog(ArrayList<ArrayList<GameObject>> createdObjects, float xLocation, int height,
                           int treeHeight) {

        for (float i = height; i < height + treeHeight; i++) {

            float yLocation = windowDimensions.y() - (i * Constants.BLOCK_SIZE);
            Vector2 baseVector = new Vector2(xLocation, yLocation);

            Log log = new Log(baseVector);
            log.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
            addToListener.accept(log);
//            logArrayList.add(log);
            createdObjects.get(0).add(log);
        }

//        createdObjects.add(logArrayList);
    }

    /**
     * Creates all the leaves of the current tree and adds them to the given list
     *
     * @param createdObjects List to add created leaves to
     * @param xLocation      X location of the tree to create the leaves of
     * @param height         Ground height at the tree to create the leaves of
     * @param treeHeight     Height of the tree to create
     */
    private void createLeaves(ArrayList<ArrayList<GameObject>> createdObjects, float xLocation, float height,
                              int treeHeight) {
//        ArrayList<GameObject> leavesArrayList = new ArrayList<>();
        int startX = (int) xLocation - (Constants.BLOCK_SIZE * (Constants.LEAFS_GRID_SIZE / 2));
        int endX = (int) xLocation + Constants.BLOCK_SIZE +
                (Constants.BLOCK_SIZE * (Constants.LEAFS_GRID_SIZE / 2));

        int startY = (int) (windowDimensions.y() - (height + treeHeight) * Constants.BLOCK_SIZE +
                (Constants.BLOCK_SIZE * (Constants.LEAFS_GRID_SIZE / 2)));

        int endY = (int) (windowDimensions.y() - (height + treeHeight) * Constants.BLOCK_SIZE -
                (Constants.BLOCK_SIZE * (Constants.LEAFS_GRID_SIZE / 2)));

        for (int i = startX; i < endX; i += Constants.BLOCK_SIZE) {
            for (int j = endY; j < startY; j += Constants.BLOCK_SIZE) {
                if (this.random.nextInt(Constants.LEAF_SPAWN_RATE) == 0) {
                    Leaf leaf = new Leaf(new Vector2(i, j));
                    addToListener.accept(leaf);
//                    leavesArrayList.add(leaf);
                    createdObjects.get(1).add(leaf);
                }
            }
        }
//        createdObjects.add(leavesArrayList);
    }

    /**
     * Creates all the fruits of the current tree and adds them to the given list
     *
     * @param createdObjects List to add created fruits to
     * @param xLocation      X location of the tree to create the fruits of
     * @param height         Ground height at the tree to create the fruits of
     * @param treeHeight     Height of the tree to create
     */
    private void createFruit(ArrayList<ArrayList<GameObject>> createdObjects, float xLocation, float height,
                             int treeHeight) {
//        ArrayList<GameObject> fruitArrayList = new ArrayList<>();
        int startX = (int) xLocation - (Constants.BLOCK_SIZE * (Constants.LEAFS_GRID_SIZE / 2));
        int endX = (int) xLocation + Constants.BLOCK_SIZE +
                (Constants.BLOCK_SIZE * (Constants.LEAFS_GRID_SIZE / 2));

        int startY = (int) (windowDimensions.y() - (height + treeHeight) * Constants.BLOCK_SIZE +
                (Constants.BLOCK_SIZE * (Constants.LEAFS_GRID_SIZE / 2)));

        int endY = (int) (windowDimensions.y() - (height + treeHeight) * Constants.BLOCK_SIZE -
                (Constants.BLOCK_SIZE * (Constants.LEAFS_GRID_SIZE / 2)));


        for (int i = startX; i < endX; i += Constants.BLOCK_SIZE / 2) {
            for (int j = endY; j < startY; j += Constants.BLOCK_SIZE * 2) {
                if (this.random.nextInt(Constants.FRUIT_SPAWN_RATE) == 0) {
                    TreeFruit fruit = new TreeFruit(new Vector2(i, j), addGameObj, removeGameObj);
                    addToListener.accept(fruit);
//                    fruitArrayList.add(fruit);
                    createdObjects.get(2).add(fruit);
                }
            }
        }
//        createdObjects.add(fruitArrayList);
    }
}


