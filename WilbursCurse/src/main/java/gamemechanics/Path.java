package gamemechanics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Class for making Paths on the board that the characters are allowed to walk
 * on. Will have a variety of looks depending on the randomization
 */
public class Path extends BoardSpace{

    // Default image path for Barrier (Hay)
    private String defaultPathImage = "path.png";
    private String pathsArray[] = {"grasspath.png", "grasspath2.png",
            "grasspath3.png", "grasspath4.png", "grasspath5.png", "path1.png", "path2.png"};
    public Image getImage = new Image(getClass().getResourceAsStream(defaultPathImage));
    public ImageView pathImage= new ImageView(getImage);
    private Random random = new Random();

    /**
     * Constructor to set position of barrier and default image
     * @param x the x coordinate of a new path
     * @param y the y coordinate of a new path
     */
    public Path(int x, int y) {
        // Set the position to the coordinate arguments
        position = new Coordinate(x,y);
        // Resize the image to the correct size
        pathImage.setFitHeight(BoardSpace.block_space);
        pathImage.setFitWidth(BoardSpace.block_space);
        // Set the imageview to the new picture
        imageView = pathImage;
    }

    /**
     * Method to set the path to a random path appearance (image);
     */
    public void setRandomPathImage()
    {
        // Get a new random image and set it as the current imageview
        imageView = new ImageView(new Image(getClass().getResourceAsStream(pathsArray[random.nextInt(pathsArray.length)])));
        imageView.setFitHeight(BoardSpace.block_space);
        imageView.setFitWidth(BoardSpace.block_space);
    }

}
