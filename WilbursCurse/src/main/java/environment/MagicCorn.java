package environment;

import gamemechanics.BoardSpace;
import gamemechanics.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents a MagicCorn object that will be placed on the board
 * This serves as the regular reward and will be collected by Wilbur coming into contact with it
 * Coming into contact with the Magic Corn will increment the total points by a set amount
 * All Magic Corn on the board is required to be collected to move on to the next level
 * Holds the image file for MagicCorn
 * Contains constructor for the Magic Corn
 */
public class MagicCorn extends StationaryModifier {

    // Sets the default image location for MagicCorn
    private String defaultCornImage = "corn.png";

    /**
     * The Image of the MagicCorn object
     */
    public Image getImage = new Image(getClass().getResourceAsStream(defaultCornImage));

    /**
     * The ImageView containing the MagicCorn image
     */
    public ImageView cornImage= new ImageView(getImage);

    /**
     * Default MagicCorn constructor for setting a random position
     */
    public MagicCorn() {
        // Corn will increment score
        pointChange = StationaryModifier.MAGIC_CORN_POINTS;


        setVisible(true); // Make Magic Corn visible
    }

    /**
     * Basic constructor to set coordinate and default image of Magic Corn
     * @param x coordinate to set Magic Corn to
     * @param y coordinate to set Magic Corn to
     */
    public MagicCorn(int x, int y)
    {
        position = new Coordinate(x,y);
        itemImage = new ImageView(new Image(getClass().getResourceAsStream(defaultCornImage)));
        cornImage.setFitWidth(BoardSpace.block_space_small);
        cornImage.setFitHeight(BoardSpace.block_space_small);
        itemImage = cornImage;
    }
}
