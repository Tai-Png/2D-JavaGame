package environment;

import gamemechanics.BoardSpace;
import gamemechanics.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



/**
 * This class serves as a Bonus Reward that increments the same amount of
 * points as MagicCorn but also grants Wilbur invincibility as a bonus
 * for a set amount of time when Wilbur comes into contact with it.
 * All Magic Truffles do not need to be collected to proceed to the next level
 * Holds the image file name
 * Contains constructor for the Magic Truffle
 */
public class MagicTruffle extends StationaryModifier {

    // Sets the number of seconds that invincibility will last for when picking up the magic truffle
    private final int invincibilityTime = 5;

    // Default image path for MagicTruffle
    private final String defaultMagicTruffleImage = "magic_truffle.png";

    /**
     * The Image of the MagicTruffle
     */
    public Image getImage = new Image(getClass().getResourceAsStream(defaultMagicTruffleImage));
    /**
     *  The ImageView containing the MagicTruffle image
      */
    public ImageView truffleImage= new ImageView(getImage);

    /**
     * Default constructor for Magic Truffle if no arguments are passed
     */
    public MagicTruffle() {
        pointChange = StationaryModifier.MAGIC_TRUFFLE_POINTS; // MagicTruffle will
        setVisible(true); // Make MagicTruffle visible

    }

    /**
     * Basic constructor to set coordinate and default image of Magic Truffle
     * @param x coordinate to set Magic Truffle to
     * @param y coordinate to set Magic Truffle to
     */
    public MagicTruffle(int x, int y) {
        position = new Coordinate(x,y);
        itemImage = new ImageView(new Image(getClass().getResourceAsStream(defaultMagicTruffleImage)));
        truffleImage.setFitHeight(BoardSpace.block_space_small);
        truffleImage.setFitWidth(BoardSpace.block_space_small);
        itemImage = truffleImage;
    }
}
