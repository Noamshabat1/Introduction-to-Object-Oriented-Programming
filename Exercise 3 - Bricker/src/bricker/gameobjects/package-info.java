/**
 * The 'bricker.gameobjects' package is an assemblage of classes that form the core interactive components
 * of a brick-breaking game. These classes, extending from GameObject, each contributing unique properties
 * and behaviors.
 * The package is designed to ensure each class plays a distinct role in the game's mechanics by using
 * polymorphism concept's:
 * - Ball: The central element of the game, this class governs the ball's physics, including its movement
 * and reaction to collisions.
 * - Brick: Symbolizes the bricks that to be broken, endowed with various collision strategies that
 * trigger different game effects.
 * - Paddle: The player's avatar, this class handles the logic and movement of the paddle, allowing player
 * control and interaction.
 * - Heart: A thematic power-up, this object can influence the player's lives, adding a strategic element
 * to the game.
 * - NumericLifeCounter and GraphicLifeCounter: These classes visually represent and manage the player's
 * remaining lives, adding a tangible element to the player's progress and survival.
 * - MockBall and ExtraPaddle: Crafted for specific scenarios, these classes offer extended gameplay
 * features and serve as tools for testing and enhancing the game's diversity.
 * It's important but the notice that MockBall is inherent from GameObject and that ExtraPaddle is
 * inherent from the Paddle itself.
 *
 * @author Noam Shabat, Samuel Hayat
 * @version Java 11
 */
package bricker.gameobjects;
