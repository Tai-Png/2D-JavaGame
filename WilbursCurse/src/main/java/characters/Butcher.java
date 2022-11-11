package characters;

import gamemechanics.BoardSpace;
import gamemechanics.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

/**
 * This is the class for moving enemies - the Butchers.
 * This class will create butcher object and will
 * hold images and coordinates of the Butcher.
 * A tracking method is included in this class to achieve butcher's chasing feature.
 * Butchers will chase Wilbur(player) and if Wilbur gets caught up, Wilbur dies
 */
public class Butcher extends MovingCharacter {
    // Path location to butcher image and creating the ImageView for butcher
    private String defaultButcherImagePath = "butcher.png";
    public Image getImage = new Image(getClass().getResourceAsStream(defaultButcherImagePath));
    public ImageView butcherImage= new ImageView(getImage);

    // The array that holds the probability of a butchers random move for each butcher
    private static double[] butcherMoveProb;

    /**
     * Constructor to initiate a butcher using coordinates
     * @param x the value of x coordinate
     * @param y the value of y coordinate
     */
    public Butcher(int x, int y)
    {
        // Sets a default look for the butcher
        characterView = new ImageView(new Image(getClass().getResourceAsStream(defaultButcherImagePath)));
        butcherImage.setFitWidth(BoardSpace.block_space);
        butcherImage.setFitHeight(BoardSpace.block_space);
        characterView = butcherImage;
        // Sets the position of the butcher
        position = new Coordinate(x,y);
    }

    /**
     * This function is used to move butcher in random direction
     * @param space the space array that hold all the coordinates
     */
    public void moveRandom(int space[][]){
        Random rand = new Random();
        int direction = rand.nextInt(2);
        if(direction==0){
            move("","","up","down",space);
        }
        if(direction==1){
            move("","","left","right",space);
        }
    }

    /**
     * Gets new probability ratios for the Butchers to move randomly on the board
     * Generates an array of probabilities to store and use later butcherMoveProb
     *
     * @param size the number of butchers to generate probabilities for
     */
    public static void generateNewProbabilities(int size)
    {
        // Create a new butcher probability array and initial probability boundary
        butcherMoveProb = new double[size];
        double probability = 0.25;

        // Loop through all the butchers and set an increasing "move randomly" boundary
        for(int i=0; i< butcherMoveProb.length; i++){
            butcherMoveProb[i] = probability;
            probability += 0.05;
        }
    }

    /**
     * Returns the probability of a random move of the Butcher at
     * the specified index in the butcherMoveProb array
     *
     * @param index the index corresponding to the probability of a random move in the butcherMoveProb
     *              of a Butcher on the board
     * @return the probability of a random move for the Butcher at this index
     */
    public static double getButcherProbability(int index)
    {
        // Return the butcher's probability at the index
        return butcherMoveProb[index];
    }

    /**
     * This function is a chasing system for the moving enemies to track the player
     * a probability aspect was implemented so that the butchers would random move for the probability of time,
     * and the rest would move towards Wilbur.
     * The chasing path is decided based on slope of the two characters line, if |slope| > 1, priority to move in vertical direction.
     * if |slope| < 1, priority to move in horizontal direction.
     * This is the graph of all the positions that could happen
     *     -----------B-----------
     *     | \    B2  |    B3  / |
     *     |   \      |      /   |
     *     |     \    |    /     |
     *     |   B1  \  |  /    B4 |
     *     B----------W----------B
     *     |        / |  \       |
     *     |      /   |    \     |
     *     |  B8/     | B6   \ B5|
     *     |  /   B7  |       \  |
     *     -----------B-----------
     * @param wilbur the character that needs to be tracking
     * @param space the space array that hold all the coordinates
     * @param randomMoveProbability this is the probability that a butcher will move randomly.
     */
    public void tracking(Wilbur wilbur, int space[][], double randomMoveProbability) {
        int dx = wilbur.position.getxCoordinate() - position.getxCoordinate();
        int dy = wilbur.position.getyCoordinate() - position.getyCoordinate();

        Random rand = new Random();
        double moveProbability = rand.nextDouble();
        if(moveProbability<=randomMoveProbability){
            moveRandom(space);

        }
        else {
            // For positions on vertical axis
            if (dx == 0) {
                // butcher is down to wilbur
                if (dy < 0) {
                    move("up", "right", "left", "down", space);
                }
                // butcher is up to wilbur
                else if (dy > 0) {
                    move("down", "right", "left", "up", space);
                }
            } else {
                // when slope is greater than 1, give priority to vertical move
                // when slope is less than 1, give priority to horizontal move
                double slope = Math.abs(dy) / Math.abs(dx);

                // if butcher is left-up to wilbur
                if (dx > 0 && dy > 0) {
                    // butcher's position in "B2"
                    if (slope >= 1) {
                        move("down", "right", "up", "left", space);

                    }
                    // butcher's position in "B1"
                    else{
                        move("right", "down", "up", "left", space);

                    }
                }

                // if butcher is left-down to wilbur
                else if (dx > 0 && dy < 0) {
                    // butcher's position in "B7"
                    if (slope >= 1) {
                        move("up", "right", "down", "left", space);
                    }
                    // butcher's position in "B8"
                    else{
                        move("right", "up", "down", "left", space);
                    }
                }

                // if butcher is right-down to wilbur
                else if (dx < 0 && dy < 0) {
                    // butcher's position in "B6"
                    if (slope >= 1) {
                        move("up", "left", "down", "right", space);
                    }
                    // butcher's position in "B5"
                    else{
                        move("left", "up", "down", "right", space);
                    }
                }

                // if butcher is right-up to wilbur
                else if (dx < 0 && dy > 0) {
                    // butcher's position in "B3"
                    if (slope >= 1) {
                        move("down", "left", "up", "right", space);
                    }
                    // butcher's position in "B4"
                    else {
                        move("left", "down", "up", "right", space);
                    }
                }

                // For positions on horizontal axis
                else{
                    if (dx > 0) {
                        move("right", "up", "down", "left", space);
                    } else{
                        move("left", "up", "down", "right", space);
                    }
                }
            }
        }
        //If butcher is moving towards the same position of Wilbur and Wilbur is not invincible, Wilbur get killed
        if(position.isCoordSame(position, wilbur.position))
        {
            if (!wilbur.isInvincible())
                wilbur.kill();
        }

    }

    /**
     * This function is used for move in one direction and will check whether this direction is movable
     * If this direction is ok to move, will perform the move, if not, will return false and not perform the move
     * the return value can indicate whether this direction is movable
     * @param direction the direction chosen from: "up", "down", "right", "left", or "n/a" if no need for the parameter
     * @param space the spaces array that holds collision and tracking information for the elements on the board
     * @return true is this direction is movable, false if not
     */
    public boolean move(String direction, int space[][]){

        // Set initial case (can't move in a direction)
        boolean successful = false;

        // Try the direction passed in to see if the butcher can move in this direction
        switch (direction){
            case "up":
                if(position.checkCoordUp(space) && space[position.getxCoordinate()][position.getyCoordinate()-1]!= BoardSpace.BUTCHER){
                    // If we can move up - move up and set successful to true
                    moveUp();
                    successful = true;
                }
                break;

            case "down":
                if(position.checkCoordDown(space) && space[position.getxCoordinate()][position.getyCoordinate()+1]!= BoardSpace.BUTCHER){
                    // If we can move down - move down and set successful to true
                    moveDown();
                    successful = true;
                }
                break;

            case"right":
                if(position.checkCoordRight(space) && space[position.getxCoordinate()+1][position.getyCoordinate()]!= BoardSpace.BUTCHER){
                    // Flip the butcher image
                    getImage().setScaleX(-1);
                    // If we can move right - move right and set successful to true
                    moveRight();
                    successful = true;
                }
                break;

            case "left":
                if(position.checkCoordLeft(space) && space[position.getxCoordinate()-1][position.getyCoordinate()]!= BoardSpace.BUTCHER){
                    // Flip the butcher image
                    getImage().setScaleX(1);
                    // If we can move left - move left and set successful to true
                    moveLeft();
                    successful = true;
                }
                break;
        }
        // Return the result of our move (whether successful or not)
        return successful;
    }


    /**
     * This function is used for move in directions 1 and 2 are required.
     * directions 3 and 4 will be randomly selected of directions 1 and 2 are not movable
     * @param direction1 the first preferable direction
     * @param direction2 the second preferable direction
     * @param direction3 the third preferable direction. Directions 3 and 4 will be randomly selected of directions 1 and 2 are not movable
     * @param direction4 the fourth preferable direction. directions 3 and 4 will be randomly selected of directions 1 and 2 are not movable
     * @param space the location on the board
     */
    public void move(String direction1, String direction2, String direction3, String direction4, int space[][]) {

        // The number of directions to choose from randomly
        int movementBound = 2;

        // If we can't move in direction1 then try the next direction
        if (!move(direction1, space)) {
            // If we can't move in direction2 then try the next direction
            if (!move(direction2, space)) {
                // Generate a random number (0 or 1)
                Random rand = new Random();
                int direction = rand.nextInt(movementBound);
                // If we got a zero try to move in direction3 first, otherwise try direction4 first
                if (direction == 0) {
                    // If we can't move in direction3 then try the 4th direction
                    if (!move(direction3, space)) {
                        move(direction4, space);
                    }
                } else {
                    // If we can't move in direction4 then try the 3rd direction
                    if (!move(direction4, space)) {
                        move(direction3, space);
                    }
                }
            }
        }
    }
}