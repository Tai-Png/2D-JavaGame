package gamemechanics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Simple test for the Path class
 */
public class BoundaryTest {

    /**
     * Tests that setRandomPathImage does set an image different from the original
     */
    @Test
    public void testPathImageSwitch()
    {
        Path path = new Path(0,0);

        ImageView im = path.getImageView();

        path.setRandomPathImage();

        ImageView im2 = path.getImageView();

        assertNotEquals(im.getImage(), im2.getImage());
    }
}
