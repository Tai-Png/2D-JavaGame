package environment;

import gamemechanics.BoardSpace;
import gamemechanics.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents a BearTrap on the game board.
 * This is the stationary punishment
 * Decrements points by set amount if Wilbur comes into contact with Bear Trap
 * Will be removed from board if Wilbur comes into contact with it
 * If points are less than 0, Wilbur will die
 * Holds the image file name
 * Contains constructor for the Bear Trap
 */
public class BearTrap extends StationaryModifier {
    // Sets the default image location for BearTrap

    private String defaultBearTrapImage = "trap.png";

    /**
     * The Image of the BearTrap object
     */
    public Image getImage = new Image(getClass().getResourceAsStream(defaultBearTrapImage));

    /**
     * The ImageView containing the BearTrap image
     */
    public ImageView trapImage= new ImageView(getImage);

    /**
     * Basic constructor for creating a BearTrap at a random coordinate
     */
    public BearTrap() {

        pointChange = StationaryModifier.BEAR_TRAP_POINTS;
        setVisible(true); // Make BearTrap visible
    }

    /**
     * Basic constructor to set coordinate and default image of Bear Trap
     * @param x coordinate to set Magic Corn to
     * @param y coordinate to set Magic Corn to
     */
    public BearTrap(int x, int y) {
        position = new Coordinate(x,y);
        itemImage = new ImageView(new Image(getClass().getResourceAsStream(defaultBearTrapImage)));
        trapImage.setFitWidth(BoardSpace.block_space_small);
        trapImage.setFitHeight(BoardSpace.block_space_small);
        itemImage = trapImage;
    }
}
