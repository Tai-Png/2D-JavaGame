package characters;

import environment.ItemType;
import environment.StationaryModifier;
import gamemechanics.BoardSpace;
import gamemechanics.Coordinate;
import gamemechanics.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;

import java.util.Timer;
import java.util.TimerTask;

import static gamemechanics.Direction.*;

/**
 * This is the class for the Main Player called Wilbur. This class creates a Wilbur
 * object with 12 possible characterView attributes, a position using coordinates,
 * and a direction he is facing. Wilbur can accumulate and lose points. He can also
 * die or become invincible.
 */

public class Wilbur extends MovingCharacter {
    // Direction Wilbur is facing
    Direction direction;

    // Sets initial points for the character
    protected int points = 0;

    // Whether Wilbur is invincible (MagicTruffle effect)
    protected boolean isInvincible = false;

    // ImagePaths for Wilbur
    private String[] wilburDown = new String[]{"WilburDown1.png", "WilburDown2.png", "WilburDown3.png"};
    private String[] wilburUp = new String[]{"WilburUp1.png", "WilburUp2.png", "WilburUp3.png"};
    private String[] wilburLeft = new String[]{"WilburLeft1.png", "WilburLeft2.png", "WilburLeft3.png"};
    private String[] wilburRight = new String[]{"WilburRight1.png", "WilburRight2.png", "WilburRight3.png"};

    // This is for Wilbur's movement
    public ImageView[] down = new ImageView[wilburDown.length];
    public ImageView[] up = new ImageView[wilburUp.length];
    public ImageView[] left = new ImageView[wilburLeft.length];
    public ImageView[] right = new ImageView[wilburRight.length];

    private TimerTask endInvincible = null;


    /**
     * This is the constructor for this Wilbur player object. His
     * coordinates are set along with his image and direction.
     *
     * @param x is the x coordinate of Wilbur object
     * @param y is the y coordinate of Wilbur object
     */

    // Constructor for setting Wilbur's coordinates and default image
    public Wilbur(int x, int y) {
        // Sets Wilbur's coordinates
        position = new Coordinate(x, y);

        initPlayerImages();
        characterView = down[1];
        this.direction = Down;
    }

    // Getters and Setters


    /**
     * This method sets Wilbur's points
     *
     * @param points is the points of Wilbur object
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * This method returns Wilbur's points
     *
     * @return return the points of Wilbur
     */
    public int getPoints() {
        return points;
    }

    /**
     * This method sets whether Wilbur is invincible
     * or not.
     *
     * @param state is the state of Wilbur object's invincibility
     */
    public void setInvincible(boolean state) {
        isInvincible = state;
    }

    /**
     * This method runs the moving character superclass for kill
     *
     */
    public void kill() {
        if (!isInvincible()) {
            super.kill();
        }
    }

    /**
     * This method returns a boolean variable determining if
     * Wilbur is invincible.
     *
     * @return return boolean variable for Wilbur object's invincibility
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * This method sets Wilbur's direction.
     *
     * @param direction is the direction of Wilbur object
     */

    // Direction changes of Wilbur
    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    /**
     * Changes Wilbur's score depending on the type of modifier passed into the function
     * @param modifier the type of item that should be applied to Wilbur's points
     */
    public void modifyScore(ItemType modifier) {
       switch(modifier)
       {
           case BearTrap:
               points-= StationaryModifier.BEAR_TRAP_POINTS;
               if (points < 0)
                   kill();
               break;
           case MagicCorn:
               points+=StationaryModifier.MAGIC_CORN_POINTS;
               break;
           case MagicTruffle:
               points+=StationaryModifier.MAGIC_TRUFFLE_POINTS;
               break;
       }
    }


    /**
     * This method sets the length of time that Wilbur can be invincible for.
     *
     * @param timer is the timer for Wilbur object's invincibility
     * @param milliseconds is the duration of Wilbur object's invincibility
     */
    public void setInvincibilityLength(Timer timer, long milliseconds) {
        // Set Wilbur to invincible and change his image to reflect this
        this.setInvincible(true);
        Wilbur w = this;

        if (endInvincible != null)
            endInvincible.cancel();

        // Set a timer that will run at the end of the seconds to make Wilbur change back to normal
        endInvincible = new TimerTask() {
            @Override
            public void run() {
                w.setInvincible(false);
            }
        };

        // Schedule the effects
        timer.schedule(endInvincible, milliseconds);

    }

    /**
     * This method simply returns Wilbur's new image.
     *
     * @param name is the name of the file for the image of Wilbur object
     * @return Wilbur's Image
     */
    private Image getImage(String name) {
        return (new Image(getClass().getResourceAsStream(name)));
    }


    /**
     * This method stores each image of Wilbur within the array holders
     * for the images.
     */
    public void initPlayerImages() {
        final int wilburDirectionImageLength = 3;
        for (int i = 0; i < wilburDirectionImageLength; i++) {
            down[i] = new ImageView(getImage(wilburDown[i]));
            down[i].setFitHeight(BoardSpace.block_space);
            down[i].setFitWidth(BoardSpace.block_space);

            up[i] = new ImageView(getImage(wilburUp[i]));
            up[i].setFitHeight(BoardSpace.block_space);
            up[i].setFitWidth(BoardSpace.block_space);

            left[i] = new ImageView(getImage(wilburLeft[i]));
            left[i].setFitHeight(BoardSpace.block_space);
            left[i].setFitWidth(BoardSpace.block_space);

            right[i] = new ImageView(getImage(wilburRight[i]));
            right[i].setFitHeight(BoardSpace.block_space);
            right[i].setFitWidth(BoardSpace.block_space);
        }
    }

    /**
     * This method determines which image to store in Wilbur's characterView
     * attribute based on the direction he is facing and the steps taken. Furthermore,
     * there is a check to see if he is invincible. If this is the case then his color
     * contrast and saturation changes. If he is not invincible these changes do not
     * occur.
     */
    public void draw() {

        switch (direction) {
            case Up:
                if (WilburFoot == 1) {
                    characterView = up[0];
                } else if (WilburFoot == 2) {
                    characterView = up[1];
                } else if (WilburFoot == 3) {
                    characterView = up[2];
                }
                break;
            case Down:
                if (WilburFoot == 1) {
                    characterView = down[0];
                } else if (WilburFoot == 2) {
                    characterView = down[1];
                } else if (WilburFoot == 3) {
                    characterView = down[2];
                }
                break;
            case Left:
                if (WilburFoot == 1) {
                    characterView = left[0];
                } else if (WilburFoot == 2) {
                    characterView = left[1];
                } else if (WilburFoot == 3) {
                    characterView = left[2];
                }
                break;
            case Right:
                if (WilburFoot == 1) {
                    characterView = right[0];
                } else if (WilburFoot == 2) {
                    characterView = right[1];
                } else if (WilburFoot == 3) {
                    characterView = right[2];
                }
                break;
        }
        if (isInvincible)
        {
            ColorAdjust colourAdjust = new ColorAdjust();
            colourAdjust.setContrast(0.8);
            colourAdjust.setSaturation(0.8);
            characterView.setEffect(colourAdjust);
        }
        else
        {
            characterView.setEffect(null);
        }
    }

    /**
     * Runs the behavior for Wilbur when picking up a magic truffle
     * Wilbur becomes invincible and the score is incremented by the truffle amount
     *
     * @param gameTime The timer to run the invincibility off of
     */
    public void pickupMagicTruffle(Timer gameTime)
    {
        modifyScore(ItemType.MagicTruffle);
        setInvincibilityLength(gameTime, 5000);
    }

    /**
     * Runs the behavior when picking up a piece of magic corn
     * Incrememnts the score
     */
    public void pickupMagicCorn()
    {
        modifyScore(ItemType.MagicCorn);
    }

    /**
     * Runs the behavior for the attack of the butcher, kills wilbur if not invincible
     * @return whether or not Wilbur is dead
     */
    public boolean attackedByButcher()
    {
        if (!isInvincible()) {
            kill();
            return true;
        }

        return false;
    }

    /**
     *  Runs the behavior for Wilbur stepping on the BearTrap, Damages Wilbur if he is not Invincible (kills if points go negative)
     * @return whether or not Wilbur is dead
     */
    public boolean stepOnBearTrap()
    {
        if (!isInvincible()) {
            modifyScore(ItemType.BearTrap);
            return points < 0;
        }

        return false;
    }
}
