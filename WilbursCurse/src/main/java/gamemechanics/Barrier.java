package gamemechanics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * This class represents a Barrier on the board, an object that characters cannot move
 * through and items cannot spawn on. Acts as a bound for the board.
 * e.g. First level is hay. Will hold images and coordinates for the
 * spaces that block the edges of the board.
 */
public class Barrier extends BoardSpace{

    // Images of default barrier type (hay)
    private String defaultBarrierImage = "hay.png";

    // The image that holds the default barrier image from resources
    public Image getImage = new Image(getClass().getResourceAsStream(defaultBarrierImage));

    // The ImageView that holds the default image
    public ImageView hayImage= new ImageView(getImage);

    // Image of farm (Board will have 1 farm house on first level)
    private String farmImage = "farm_house.png";

    // The image that holds the default farm image from resources
    public Image get_Image = new Image(getClass().getResourceAsStream(farmImage));

    // The ImageView that holds the default farm image
    public ImageView barnImage = new ImageView(get_Image);

    // Value to hold for switching the image on a barrier to farm image
    // Could be extended to change types for different levels
    public static int FARM_HOUSE = 1;

    /**
     * Constructor to set position of barrier and default image
     * @param x the x coordinate of a new barrier
     * @param y the y coordinate of a new barrier
     */
    public Barrier(int x, int y) {
        // Set position from coordinates passed in
        position = new Coordinate(x,y);

        // Set the size of the hay boardspace
        hayImage.setFitHeight(BoardSpace.block_space);
        hayImage.setFitWidth(BoardSpace.block_space);
        // Set the imageview to be hay
        imageView = hayImage;
    }

    /**
     * Method to switch the type of barrier to farm house (more types could be added)
     * @param type the type of image to switch to e.g. FARM_HOUSE in this class
     */
    public void setType(int type)
    {
        // If the type passed in is farmhouse set the imageview to be a
        // properly resized barn image
        if (type == FARM_HOUSE) {
            barnImage.setFitHeight(BoardSpace.block_space);
            barnImage.setFitWidth(BoardSpace.block_space);
            imageView = barnImage;
        }
    }
}
