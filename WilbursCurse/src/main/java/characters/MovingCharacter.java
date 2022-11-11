package characters;

import gamemechanics.Coordinate;
import javafx.scene.image.ImageView;

/**
 * The MovingCharacters class is the super class for Wilbur and Butcher classes.
 * This class will hold attributes of the position coordinate, isDead boolean value, characterView image,
 * and WilburFoot. It also holds their getters and setters.
 * Can move the character in 4 directions
 * Can kill a character
 *
 */
public class MovingCharacter{
    // Holds the position (Coordinates) of the character on the grid
    protected Coordinate position;
    // Determines whether the character should be able to interact and continue in the game
    protected boolean isDead = false;
    // Determines the look of the character on the board (how they are drawn)
    public ImageView characterView;
    // used when determine which image should use to draw when character moves

    public int WilburFoot = 1;



    // Getters and setters


    /**
     * This is the getter for Coordinate of a MovingCharacter
     * @return the coordinate of the MovingCharacter
     */
    public Coordinate getCoordinate()
    {
        return position;
    }

    /**
     * Gets the x Coordinate of the Character
     * @return the x Coordinate of the Character
     */
    public int getxCoordinate(){return getCoordinate().getxCoordinate();}

    /**
     * Gets the y Coordinate of the Character
     * @return the y Coordinate of the Character
     */
    public int getyCoordinate(){return getCoordinate().getyCoordinate();}


    /**
     * The getter of characterView
     * @return characterView
     */
    public ImageView getImage()
    {
        return characterView;
    }

    /**
     * The getter for position coordinate
     * @return the coordinate of position of a moving character
     */
    public Coordinate getPosition() {
        return position;
    }


    /**
     * Query if the character is dead
     * @return the boolean value of isDead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * This function sets isDead to true, showing that the character has died
     */
    public void kill(){isDead = true;}

    // Basic functions for moving in the four directions

    /**
     * This function is used for moving a character to the left coordinate
     */
    public void moveLeft() {
        position.left();
    }

    /**
     * This function is used for moving a character to the right coordinate
     */
    public void moveRight() {
        position.right();
    }

    /**
     * This function is used for moving a character to the up coordinate
     */
    public void moveUp() {
        position.up();
    }

    /**
     * This function is used for moving a character to the down coordinate
     */
    public void moveDown() { position.down(); }
}
