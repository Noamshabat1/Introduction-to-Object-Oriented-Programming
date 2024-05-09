package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * A class that represents the avatar game object
 */
public class Avatar extends GameObject {
    // Constants:
    private static final String TAG = "avatar";
    private static final float VELOCITY_X = 300;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final int AVATAR_HEIGHT_FACTOR = 50;
    private static final double TIME_BETWEEN_CLIPS = 0.2;

    private final UserInputListener inputListener;
    private final JumpObserver jumpObserver;
    private float energy ;

    private Renderable idleAnimation;
    private Renderable jumpAnimation;
    private Renderable runAnimation;

    private static final Color AVATAR_COLOR = Color.DARK_GRAY;


    /**
     * Constructor for the Avatar class.
     *
     * @param pos          The position of the avatar.
     * @param inputListener The input listener for the avatar.
     * @param imageReader  The image reader for the avatar.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(AVATAR_HEIGHT_FACTOR), new OvalRenderable(AVATAR_COLOR));

        this.inputListener = inputListener;
        initAnimations(imageReader);

        this.energy = Constants.AVATAR_MAX_ENERGY ;

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);

        this.jumpObserver = new JumpObserver();
        this.setTag(TAG);
    }

    /**
     * Initializes the avatar animations
     *
     * @param imageReader Image reader
     */
    private void initAnimations(ImageReader imageReader) {
        this.initIdleAnimation(imageReader);
        this.initJumpAnimation(imageReader);
        this.initRunAnimation(imageReader);
    }

    /**
     * Initializes the idle animation
     *
     * @param imageReader Image reader
     */
    private void initIdleAnimation(ImageReader imageReader) {
        Renderable[] idleAnimationRenderable = {
                imageReader.readImage("assets/idle_0.png", true),
                imageReader.readImage("assets/idle_1.png", true),
                imageReader.readImage("assets/idle_2.png", true),
                imageReader.readImage("assets/idle_3.png", true)
        };

        this.idleAnimation = new AnimationRenderable(idleAnimationRenderable, 0.2);
    }

    /**
     * Initializes the jump animation
     *
     * @param imageReader Image reader
     */
    private void initJumpAnimation(ImageReader imageReader) {
        Renderable[] jumpAnimationRenderable = {
                imageReader.readImage("assets/jump_0.png", true),
                imageReader.readImage("assets/jump_1.png", true),
                imageReader.readImage("assets/jump_2.png", true),
                imageReader.readImage("assets/jump_3.png", true)
        };

        this.jumpAnimation = new AnimationRenderable(jumpAnimationRenderable, 0.2);
    }

    /**
     * Initializes the run animation
     *
     * @param imageReader Image reader
     */
    private void initRunAnimation(ImageReader imageReader) {
        Renderable[] runAnimationRenderable = {
                imageReader.readImage("assets/run_0.png", true),
                imageReader.readImage("assets/run_1.png", true),
                imageReader.readImage("assets/run_2.png", true),
                imageReader.readImage("assets/run_3.png", true),
                imageReader.readImage("assets/run_4.png", true),
                imageReader.readImage("assets/run_5.png", true)
        };

        this.runAnimation = new AnimationRenderable(runAnimationRenderable, TIME_BETWEEN_CLIPS);
    }


    /**
     * Updates the avatar's state.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        this.handleMovementInput();
        this.updateAnimations();

    }

    /**
     * Handles the avatar's movement input
     */
    private void handleMovementInput() {
        float xVel = 0;

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            xVel -= VELOCITY_X;

            if (energy - Constants.AVATAR_MOVE_ENERGY_LOSS < Constants.AVATAR_MIN_ENERGY) {
                energy = Constants.AVATAR_MIN_ENERGY;

            } else { // if he is on the ground and have energy, move
                energy -= Constants.AVATAR_MOVE_ENERGY_LOSS;
                xVel -= VELOCITY_X;
            }

        }
        else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += VELOCITY_X;
            if (energy - Constants.AVATAR_MOVE_ENERGY_LOSS < Constants.AVATAR_MIN_ENERGY) {
                energy = Constants.AVATAR_MIN_ENERGY;
            }
            else { // if he is on the ground and have energy, move
                energy -= Constants.AVATAR_MOVE_ENERGY_LOSS;
                xVel += VELOCITY_X;
            }

        }

        transform().setVelocityX(xVel);

        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 &&
                energy >= Constants.AVATAR_ENERGY_FACTOR) {

            transform().setVelocityY(VELOCITY_Y);
            energy -= Constants.AVATAR_ENERGY_FACTOR;
            jumpObserver.jumped();

        } else if (xVel == 0 && getVelocity().y() == 0 ) {
            if(energy + 1 > Constants.AVATAR_MAX_ENERGY)
                energy = Constants.AVATAR_MAX_ENERGY ;
            else{
                energy += 1 ;
            }

        }

    }

    /**
     * Handles the avatar animation
     */
    private void updateAnimations() {
        if (this.getVelocity().x() != 0) {
            this.renderer().setRenderable(this.runAnimation);
            this.renderer().setIsFlippedHorizontally(this.getVelocity().x() < 0);
        }

        if (this.getVelocity().y() != 0) {
            this.renderer().setRenderable(this.jumpAnimation);
            this.renderer().setIsFlippedHorizontally(this.getVelocity().x() < 0);
        }

        if (this.getVelocity().x() == 0 && this.getVelocity().y() == 0) {
            this.renderer().setRenderable(this.idleAnimation);
        }
    }


    /**
     * Returns the energy of the avatar
     *
     * @return The energy of the avatar
     */
    public Float getEnergy() {
        return energy ;
    }

    /**
     * Adds energy to the avatar
     *
     * @param energyToAdd The energy to add
     */
    public void addEnergy(float energyToAdd){
        if(energy + energyToAdd > Constants.AVATAR_MAX_ENERGY){
            energy = 100;
        }
        else{
            energy += energyToAdd ;
        }
    }

    /**
     * Adds an object to the jump observer
     *
     * @param object The object to add
     */
    public void addToObserver(Actionable object){
        jumpObserver.addToObserver(object);
    }
}

