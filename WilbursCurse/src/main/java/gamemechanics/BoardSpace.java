package gamemechanics;

import javafx.scene.image.ImageView;

/**
 * This class is responsible for setting up the skeleton and framework of the
 * actual board. The class handles the imaging and coordinates of the object
 * to be drawn on the board.
 */
public class BoardSpace {

    // ImageView of the item to draw on the board
    protected ImageView imageView;
    // Position of object
    protected Coordinate position;

    // Static constant values that determine what type of entity is at which position on the board
    public static final int BARRIER = 1;
    public static final int MAGIC_CORN = 2;
    public static final int BUTCHER = 4;
    public static final int WALL = 3;
    public static final int MAGIC_TRUFFLE = 5;
    public static final int WILBUR = 6;
    public static final int BEAR_TRAP = 7;
    public static final int EXIT = 8;

    // Constant block space sizes
    public static final int block_space = 40;
    public static final int block_space_small = 30;


    // Getters and setters

    /**
     * This method returns the image of the object that will
     * be drawn onto the board.
     *
     * @return returns the image of the object
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * This method sets the image of the object that will be
     * drawn onto the board.
     *
     * @param imageView the image of the object
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * This method returns the coordinates of the object to
     * be drawn onto the board.
     *
     * @return returns the position of the object
     */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * This method sets the position of the object to be
     * drawn onto the board.
     *
     * @param position is the position of the object
     */
    public void setPosition(Coordinate position) {
        this.position = position;
    }

    /**
     * This method returns the x coordinate of the object to be
     * drawn onto the board.
     *
     * @return returns the x coordinate
     */
    public int getxCoordinate()
    {
        return position.getxCoordinate();
    }

    /**
     * This method returns the y coordinate of the object to be
     * drawn onto the board.
     *
     * @return returns the y coordinate
     */
    public int getyCoordinate()
    {
        return position.getyCoordinate();
    }

    /**
     * This method sets the x coordinate of the object to be
     * drawn onto the board.
     *
     * @param x is the x coordinate
     */
    public void setxCoordinate(int x) { setxCoordinate(x); }

    /**
     * This method sets the y coordinate of the object to be
     * drawn onto the board.
     *
     * @param y is the y coordinate
     */
    public void setyCoordinate(int y) { setyCoordinate(y); }
}
