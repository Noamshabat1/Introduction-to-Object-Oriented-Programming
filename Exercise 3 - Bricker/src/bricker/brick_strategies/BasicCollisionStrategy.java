package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;



/**
 * BasicCollisionStrategy implements the CollisionStrategy interface to provide a basic response to
 * collisions in the game.
 * This strategy is fundamental and typically involves the removal of the colliding object,
 * thereby affecting the game's state.
 * It is a modular strategy that can be applied in various scenarios where simple collision handling is
 * required, such as deleting bricks upon collision.
 * This strategy forms the basis for more complex collision behaviors in the game.
 * Usage:
 * - Instantiate this class with a reference to the GameObjectCollection.
 * - Attach this strategy to a GameObject.
 * - Upon collision, the attached GameObject will be removed, and the relevant game counters will be
 * updated.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
public class BasicCollisionStrategy implements bricker.brick_strategies.CollisionStrategy{

    // The GameObjectCollection reference that allows to remove an object from the game
    private final GameObjectCollection admin;
    private final Counter bricksCounter ;
    private final String ID = "BasicCollisionStrategy"; // The idea of the event

    /**
     * Constructs a BasicCollisionStrategy instance.
     * Initializes the strategy with a reference to the GameObjectCollection
     * which manages the game objects. This collection is used to remove bricks from the game.
     * @param admin The GameObjectCollection instance managing the game objects and overall game logic.
     * @param bricksCounter The counter of bricks
     */
    public BasicCollisionStrategy(GameObjectCollection admin, Counter bricksCounter) {
        this.admin = admin;
        this.bricksCounter = bricksCounter ;
    }

    /**
     * Returns a unique tag identifying this specific strategy.
     * The tag is an ID of this object and is used to identify an instance of this class
     *
     * @return A string representing the unique tag of this collision strategy.
     */
    public String getTag(){
        return ID;
    }



    /**
     * Handles the collision event according to the basic collision strategy.
     * This method is invoked when an object implementing this strategy collides with another object.
     *
     * @param thisObj The GameObject associated with this collision strategy.
     * @param otherObj The GameObject that has collided with thisObj.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj){
        if(this.admin.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){
            bricksCounter.decrement();
        }
    }
}




