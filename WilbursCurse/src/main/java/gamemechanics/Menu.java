package gamemechanics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Holds the background image objects for the main menu
 *
 */
public class Menu extends BoardSpace {
    // The default menu background path
    private String defultImage = "Menu.png";
    // The default menu background Image
    public Image getImage = new Image(getClass().getResourceAsStream(defultImage));
    // The default menu background ImageView
    public ImageView menuImage= new ImageView(getImage);

}