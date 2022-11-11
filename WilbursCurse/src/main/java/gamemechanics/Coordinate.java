package gamemechanics;

/**
 * Class Coordinate is used for holding the x and y coordinates values on the Board Space 2D array.
 * Will do the move through changing x or y coordinate value
 * Will compare 2 coordinate values
 * will check whether the next intended moving coordinate is blocked
 */
public class Coordinate {

    // X value of the coordinate
    private int xCoordinate;
    // Y value of the coordinate
    private int yCoordinate;

    /**
     * Default constructor to set coordinates to x and y value
     * @param x value of the x coordinate
     * @param y value of the y coordinate
     */
    public Coordinate(int x, int y)
    {
        xCoordinate = x;
        yCoordinate = y;
    }

    // Getters and setters

    /**
     * Getter for x coordinate value
     * @return value of the x coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Getter for y coordinate value
     * @return value of the y coordinate
     */
    public int getyCoordinate()
    {
        return yCoordinate;
    }

    /**
     * Setter for x coordinate
     * @param x value of the x coordinate
     */
    public void setxCoordinate(int x)
    {
        xCoordinate = x;
    }

    /**
     * Setter for y coordinate
     * @param y value of the y coordinate
     */
    public void setyCoordinate(int y)
    {
        yCoordinate = y;
    }

    /**
     * Moves a coordinate y value one up
     */
    public void up()
    {
        --yCoordinate;
    }

    /**
     * Moves a coordinate y value one down
      */
    public void down()
    {
        ++yCoordinate;
    }

    /**
     * Moves a coordinate x value one to the left
     */
    public void left() { --xCoordinate; }

    /**
     * Moves a coordinate x value one to the right
     */
    public void right() { ++xCoordinate; }

    /**
     * Checks if two sets of coordinates are the same
     * @param a the first coordinate
     * @param b the second coordinate
     * @return true of the 2 coordinates are same, false otherwise
     */
    public static boolean isCoordSame(Coordinate a, Coordinate b){
        if(a.getxCoordinate() == b.getxCoordinate() && a.getyCoordinate()== b.getyCoordinate()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Checks the space directly up from the coordinate to see if it is blocked (true if clear)
     * @param spaces the board coordinate system
     * @return true if the coordinate up is good to go, false of the coordinate up is blocked
     */
    public boolean checkCoordUp(int spaces[][]){
        if( this.getyCoordinate() <= 0 ||
                spaces[xCoordinate][yCoordinate-1]== BoardSpace.BARRIER ||
                spaces[xCoordinate][yCoordinate-1]== BoardSpace.WALL){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Checks the space directly down from the coordinate to see if it is blocked (true if clear)
     * @param spaces the board coordinate system
     * @return true if the coordinate down is good to go, false of the coordinate down is blocked
     */
    public boolean checkCoordDown(int spaces[][]){
        if(spaces[xCoordinate][yCoordinate+1]== BoardSpace.BARRIER ||
                spaces[xCoordinate][yCoordinate+1]== BoardSpace.WALL){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Checks the space directly right from the coordinate to see if it is blocked (true if clear)
     * @param spaces the board coordinate system
     * @return true if the coordinate right is good to go, false of the coordinate right is blocked
     */
    public boolean checkCoordRight(int spaces[][]){
        if(spaces[xCoordinate+1][yCoordinate]== BoardSpace.BARRIER ||
                spaces[xCoordinate+1][yCoordinate]== BoardSpace.WALL){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Checks the space directly left from the coordinate to see if it is blocked (true if clear)
     * @param spaces the board coordinate system
     * @return true if the coordinate left is good to go, false of the coordinate left is blocked
     */
    public boolean checkCoordLeft(int spaces[][]){
        if(xCoordinate <= 0 ||
                spaces[xCoordinate-1][yCoordinate]== BoardSpace.BARRIER ||
                spaces[xCoordinate-1][yCoordinate]== BoardSpace.WALL){
            return false;
        }
        else{
            return true;
        }
    }

    //

    /**
     * Returns the type of interaction the main character is having on the board
     * @param spaces the board coordinate system
     * @return BARRIER = 1; CORN = 2; BUTCHER = 3; WALL = 4; MAGIC_TRUFFLE = 5;
     *          WILBUR = 6; BEAR_TRAP = 7; EXIT = 8;
     */
    public int checkIfInteraction(int spaces[][])
    {
        return spaces[xCoordinate][yCoordinate];
    }


    @Override
    public String toString() {
        return "Coordinate{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }

}