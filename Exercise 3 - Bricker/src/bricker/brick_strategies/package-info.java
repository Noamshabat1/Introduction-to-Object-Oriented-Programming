/**
 * The 'bricker.brick_strategies' package contains a suite of collision strategy classes made for a
 * brick's game.
 * These strategies are unique in defining the diverse and dynamic effects triggered by the ball's
 * collision with bricks.
 * Each strategy class is created to implement a distinct behavior, catering to the game's interactive
 * and challenging nature.
 * These include:
 * - BasicCollisionStrategy: The basic collision behavior, primarily involving object removal on impact.
 * - ExtraPaddleStrategy: Introduces an additional paddle to the game, offering enhanced player control.
 * - ExtraLifeStrategy: Rewards players with extra lives, contributing to game longevity.
 * - ExtraBallsStrategy: Spawns additional balls in the game, escalating the level of challenge.
 * - DoublePowersStrategy: Amplifies game effects by executing two collision strategies simultaneously.
 * - CameraChangeStrategy: Alters the camera focus on the ball instead of on the normal way.
 * - FactoryCollisionStrategy: A factory pattern class responsible for generating these collision
 * strategies, enabling a modular and extensible approach to strategy creation.
 * Through these strategies, the gameplay is imbued with a spectrum of interactions ranging from basic
 * to complex, each adding a unique twist to the traditional brick-breaking game dynamics.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
package bricker.brick_strategies;
