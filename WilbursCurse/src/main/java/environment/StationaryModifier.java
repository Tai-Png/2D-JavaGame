package environment;
import gamemechanics.Coordinate;
import javafx.scene.image.ImageView;



/**
 * This class serves as a parent class for any non-moving object to be placed on the board
 * BearTrap, MagicCorn and MagicTruffle will extend StationaryModifier
 * Holds all the static values that will alter points such as how much to increment the score when a Corn is picked up
 * Holds coordinate attribute of objects on the board
 * Holds visibility attribute that dictates if an object is painted on the board
 * Holds upperbounds for X and Y coordinates for when the random function is used
 */
public class StationaryModifier {

    // Points to add/remove when this item is walked over
    protected int pointChange;
    // Position of the item on the board
    protected Coordinate position;
    // Whether this item is visible
    protected boolean visible = true;
    // The image of the item to show on the board
    protected ImageView itemImage;

    // Constant values to change points by
    public static final int MAGIC_CORN_POINTS = 10;
    public static final int MAGIC_TRUFFLE_POINTS = 20;
    public static final int BEAR_TRAP_POINTS = 10;


    /**
     * @return the coordinate of an object
    */
    public Coordinate getCoordinate()
    {
        return position;
    }

    /**
     * Gets the x Coordinate of this item
     * @return the x Coordinate of this item
     */
    public int getxCoordinate()
    {
        return position.getxCoordinate();
    }

    /**
     * Gets the y Coordinate of this item
     * @return the y Coordinate of this item
     */
    public int getyCoordinate()
    {
        return position.getyCoordinate();
    }


    /**
     * Gets the objects image file
     * @return image file
     */
    public ImageView getImage()
    {
        return itemImage;
    }

    /**
     * Setter for an object's visibility on the board
     * @param state true or false
     */
    public void setVisible(boolean state) {
        this.visible = state;
    }
}
