package characters;

import gamemechanics.BoardSpace;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class ButcherTest extends TestCase {
    int[][] spaces = new int[20][20];
    BoardSpace boardSpace = new BoardSpace();
    @BeforeEach
    public void setup(){
        for(int i=0; i<20; i++){
            for(int j=0; j<20; j++){
                spaces[i][j] = 0;
            }
        }
    }
    /*
    This is the graph of all the positions that could happen
    Will test for all the B1~B8 positions and all the positions on the horizontal and vertical lines
    -----------B-----------
    | \    B2  |    B3  / |
    |   \      |      /   |
    |     \    |    /     |
    |   B1  \  |  /    B4 |
    B----------W----------B
    |        / |  \       |
    |       /  |    \     |
    |   B8 /   | B6  \  B5|
    |    /  B7 |      \   |
    -----------B-----------
     */

    // Tests for move in horizontal or vertical direction
    // When butcher and wilbur are in horizontal or vertical lines
    @Test
    public void testTrackingR() {
        //When butcher is left to Wilbur, butcher should move right
        Butcher butcher = new Butcher(0,0);
        Wilbur wilbur = new Wilbur(5,0);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),1);
        Assertions.assertEquals(butcher.position.getyCoordinate(),0);
    }
    @Test
    public void testTrackingL() {
        //When butcher is right to Wilbur, butcher should move left
        Butcher butcher = new Butcher(5,0);
        Wilbur wilbur = new Wilbur(0,0);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),4);
        Assertions.assertEquals(butcher.position.getyCoordinate(),0);
    }
    @Test
    public void testTrackingUp() {
        //When butcher is down to Wilbur, butcher should move up
        Butcher butcher = new Butcher(0,5);
        Wilbur wilbur = new Wilbur(0,0);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),0);
        Assertions.assertEquals(butcher.position.getyCoordinate(),4);
    }
    @Test
    public void testTrackingDown() {
        //When butcher is up to Wilbur, butcher should move down
        Butcher butcher = new Butcher(0,0);
        Wilbur wilbur = new Wilbur(0,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),0);
        Assertions.assertEquals(butcher.position.getyCoordinate(),1);
    }

    // Tests when Wilbur and Butcher are not in vertical or horizontal position
    // That is the position of B1 ~ B8 in the graph above
    @Test
    public void testTrackingUL1() {
        //When butcher is B1 (upper left, |slope|<1)
        //priority is to move right
        Butcher butcher = new Butcher(1,4);
        Wilbur wilbur = new Wilbur(5,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),2);
        Assertions.assertEquals(butcher.position.getyCoordinate(),4);
    }
    @Test
    public void testTrackingUL2() {
        //When butcher is B2 (upper left, |slope|>1)
        //priority is to move down
        Butcher butcher = new Butcher(4,1);
        Wilbur wilbur = new Wilbur(5,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),4);
        Assertions.assertEquals(butcher.position.getyCoordinate(),2);
    }
    @Test
    public void testTrackingUR1() {
        //When butcher is B3 (upper right, |slope|>1)
        //priority is to move down
        Butcher butcher = new Butcher(6,1);
        Wilbur wilbur = new Wilbur(5,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),6);
        Assertions.assertEquals(butcher.position.getyCoordinate(),2);
    }
    @Test
    public void testTrackingUR2() {
        //When butcher is B4 (upper right, |slope|<1)
        //priority is to move left
        Butcher butcher = new Butcher(9,4);
        Wilbur wilbur = new Wilbur(5,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),8);
        Assertions.assertEquals(butcher.position.getyCoordinate(),4);
    }
    @Test
    public void testTrackingLR1() {
        //When butcher is B5 (lower right, |slope|<1)
        //priority is to move left
        Butcher butcher = new Butcher(9,6);
        Wilbur wilbur = new Wilbur(5,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),8);
        Assertions.assertEquals(butcher.position.getyCoordinate(),6);
    }
    @Test
    public void testTrackingLR2() {
        //When butcher is B6 (lower right, |slope|>1)
        //priority is to move up
        Butcher butcher = new Butcher(7,9);
        Wilbur wilbur = new Wilbur(5,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),7);
        Assertions.assertEquals(butcher.position.getyCoordinate(),8);
    }
    @Test
    public void testTrackingLL1() {
        //When butcher is B7 (lower right, |slope|>1)
        //priority is to move up
        Butcher butcher = new Butcher(4,9);
        Wilbur wilbur = new Wilbur(5,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),4);
        Assertions.assertEquals(butcher.position.getyCoordinate(),8);
    }
    @Test
    public void testTrackingLL2() {
        //When butcher is B8 (lower right, |slope|<1)
        //priority is to move right
        Butcher butcher = new Butcher(3,6);
        Wilbur wilbur = new Wilbur(5,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertEquals(butcher.position.getxCoordinate(),4);
        Assertions.assertEquals(butcher.position.getyCoordinate(),6);
    }

    //This test is used for testing when the butcher moves to Wilbur, Wilbur will be killed when they are in the same
    //coordinate.
    @Test
    public void testKillWilbur(){
        Butcher butcher = new Butcher(5,5);
        Wilbur wilbur = new Wilbur(5,5);
        butcher.tracking(wilbur,spaces,0);
        Assertions.assertTrue(wilbur.isDead);
    }

    //Testing for butcher's move when there is a barrier
    //Barriers including WALL, BARRIERs (hay bales), EXIT, and BUTCHERs
    @Test
    public void testMoveWall() {
        Butcher butcher = new Butcher(5,5);
        spaces[6][5] = boardSpace.WALL;
        Assertions.assertFalse(butcher.move("right",spaces));
    }
    @Test
    public void testMoveBarrier() {
        Butcher butcher = new Butcher(5,5);
        spaces[5][6] = boardSpace.BARRIER;
        Assertions.assertFalse(butcher.move("down",spaces));
    }
    @Test
    public void testMoveExit() {
        Butcher butcher = new Butcher(5,5);
        spaces[6][5] = boardSpace.EXIT;
        Assertions.assertTrue(butcher.move("right",spaces));
    }
    @Test
    public void testMoveButcher() {
        Butcher butcher = new Butcher(5,5);
        spaces[5][4] = boardSpace.BUTCHER;
        Assertions.assertFalse(butcher.move("up",spaces));
    }
}