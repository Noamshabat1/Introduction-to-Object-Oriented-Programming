package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.world.Actionable;
import pepse.world.Block;

/**
 * Refactored Leaf class that represents a single leaf game object.
 */
public class Leaf extends Block implements Actionable {

    private static final String TAG = "leaf";
    private static final float FALLING_TRANSITION_TIME = 1f;
    private static final float TRANSITION_TIME_ROTATION = 0.5f;
    private final Vector2 initialPosition;
    private Transition<Float> fallingTransition;

    /**
     * Constructor for the Leaf class.
     *
     * @param topLeftCorner The top left corner of the leaf.
     */
    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, new RectangleRenderable(ColorSupplier.approximateColor(Constants.LEAF_COLOR)));
        this.setTag(TAG);
        this.physics().setMass(Constants.LEAF_MASS);
        this.initialPosition = topLeftCorner;
        initLeafLifecycle();
    }

    /**
     * Initializes the leaf's lifecycle by scheduling the leaf transitions and animations.
     */
    private void initLeafLifecycle() {
        scheduleRandomTask(this::initLeafTransitions, 0, 1); // Random start for leaf animation
        scheduleRandomTask(this::startLeafAnimation, 0, 3); // Random start for leaf falling
    }

    /**
     * Called when a collision occurs with this GameObject.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.removeComponent(fallingTransition);
    }

    /**
     * Updates the leaf's state.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.transform().getVelocity().y() == 0) {
            this.transform().setVelocity(Vector2.ZERO);
        }
    }

    /**
     * Initializes the leaf's transitions.
     */
    private void initLeafTransitions() {
        leafAngleTransition();
        leafSizeTransition();
    }

    /**
     * Schedules a transition for the leaf's angle.
     */
    private void leafAngleTransition() {
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle,
                Constants.LEAF_ANGLE_TRANSITION_STARTING_VALUE,
                Constants.LEAF_ANGLE_TRANSITION_ENDING_VALUE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                Constants.LEAF_TRANSITIONS_CYCLE_LENGTH,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    /**
     * Schedules a transition for the leaf's size.
     */
    private void leafSizeTransition() {
        new Transition<>(
                this,
                (factor) -> this.setDimensions(new Vector2(Constants.BLOCK_SIZE,
                        Constants.BLOCK_SIZE).mult(factor)),
                Constants.LEAF_SIZE_TRANSITION_STARTING_VALUE,
                Constants.LEAF_SIZE_TRANSITION_ENDING_VALUE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                Constants.LEAF_TRANSITIONS_CYCLE_LENGTH,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    /**
     * Starts the leaf's animation.
     */
    private void startLeafAnimation() {
        resetToInitialPosition();
        scheduleRandomTask(this::startFade, Constants.MIN_DELAY_SECONDS_ANI, Constants.MAX_DELAY_SECONDS_ANI);
        // and fade
    }

    /**
     * Resets the leaf to its initial position.
     */
    private void resetToInitialPosition() {
        this.setTopLeftCorner(initialPosition);
        this.renderer().fadeIn(Constants.LEAF_FADE_LENGTH);
        this.setVelocity(Vector2.ZERO);
    }

    /**
     * Starts the leaf's fade.
     */
    private void startFade() {
        setupFallingTransition();
        this.transform().setVelocityY(Constants.LEAF_Y_VELOCITY);
        this.renderer().fadeOut(Constants.LEAF_FADE_LENGTH,
                () -> scheduleRandomTask(this::startLeafAnimation, Constants.MIN_DELAY_SECONDS,
                        Constants.MAX_DELAY_SECONDS));
    }

    /**
     * Sets up the leaf's falling transition.
     */
    private void setupFallingTransition() {
        this.fallingTransition = new Transition<>(
                this,
                this.transform()::setVelocityX,
                -1 * Constants.LEAF_X_VELOCITY,
                Constants.LEAF_X_VELOCITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                FALLING_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    /**
     * Schedules a random task.
     *
     * @param task            The task to be scheduled.
     * @param minDelaySeconds The minimum delay in seconds.
     * @param maxDelaySeconds The maximum delay in seconds.
     */
    private void scheduleRandomTask(Runnable task, int minDelaySeconds, int maxDelaySeconds) {
        float delay = minDelaySeconds + (float) Math.random() * (maxDelaySeconds - minDelaySeconds);
        new ScheduledTask(this, delay, false, task);
    }


    /**
     * Called when the avatar jumps.
     */
    @Override
    public void onJump() {
        float newAngle = this.renderer().getRenderableAngle() + Constants.ROTATION_ANGLE;
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle, // The method to change the angle.
                this.renderer().getRenderableAngle(), // Starting angle.
                newAngle, // Ending angle (current angle + 90 degrees).
                Transition.LINEAR_INTERPOLATOR_FLOAT, // Interpolator.
                TRANSITION_TIME_ROTATION, // Duration of the transition in seconds.
                Transition.TransitionType.TRANSITION_ONCE, // Transition once without looping.
                null // No need to do anything on completion.
        );
    }
}
