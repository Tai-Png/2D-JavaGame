package gamemechanics;

import characters.Butcher;
import characters.Wilbur;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Coordinate class and related methods (comparing and checking coordinates against spaces matrix)
 *
 */
public class CoordinatesTest {

    int[][] spaces;

    /**
     * Sets up the global variables for the CoordinateTest
     */
    @BeforeEach
    public void init()
    {
        spaces = new int[5][5];
    }

    /**
     * Tests whether a barrier placed above wilbur will be detected by the checkCoordUp method
     */
    @Test
    public void checkCoordinateAboveForBarrier()
    {
        Wilbur wilbur = new Wilbur(0,1);
        spaces[0][0] = BoardSpace.BARRIER;
        boolean barrier = wilbur.getCoordinate().checkCoordUp(spaces);

        Assertions.assertFalse(barrier);
    }

    /**
     * Tests whether a barrier placed below wilbur will be detected by the checkCoordDown method
     */
    @Test
    public void checkCoordinateBelowForBarrier()
    {
        Wilbur wilbur = new Wilbur(0,0);
        spaces[0][1] = BoardSpace.BARRIER;
        boolean barrier = wilbur.getCoordinate().checkCoordDown(spaces);

        Assertions.assertFalse(barrier);
    }

    /**
     * Tests whether a barrier placed to the left of wilbur will be detected by the checkCoordLeft method
     */
    @Test
    public void checkCoordinateLeftForBarrier()
    {
        Wilbur wilbur = new Wilbur(1,0);
        spaces[0][0] = BoardSpace.BARRIER;
        boolean barrier = wilbur.getCoordinate().checkCoordLeft(spaces);

        Assertions.assertFalse(barrier);
    }

    /**
     * Tests whether a barrier placed to the right of wilbur will be detected by the checkCoordRight method
     */
    @Test
    public void checkCoordinateRightForBarrier()
    {
        Wilbur wilbur = new Wilbur(0,0);
        spaces[1][0] = BoardSpace.BARRIER;
        boolean barrier = wilbur.getCoordinate().checkCoordRight(spaces);

        Assertions.assertFalse(barrier);
    }

    /**
     * Tests whether checkCoordRight method will show that there is not a barrier to the right of Wilbur
     */
    @Test
    public void checkCoordinateRightForBarrierClear()
    {
        Wilbur wilbur = new Wilbur(0,0);
        boolean barrier = wilbur.getCoordinate().checkCoordRight(spaces);

        Assertions.assertTrue(barrier);
    }

    /**
     * Tests whether checkCoordLeft method will show that there is not a barrier to the left of Wilbur
     */
    @Test
    public void checkCoordinateLeftForBarrierClear()
    {
        Wilbur wilbur = new Wilbur(1,0);
        boolean barrier = wilbur.getCoordinate().checkCoordLeft(spaces);

        Assertions.assertTrue(barrier);
    }

    /**
     * Tests whether checkCoordUp method will show that there is not a barrier above Wilbur
     */
    @Test
    public void checkCoordinateUpForBarrierClear()
    {
        Wilbur wilbur = new Wilbur(0,1);
        boolean barrier = wilbur.getCoordinate().checkCoordUp(spaces);

        Assertions.assertTrue(barrier);
    }

    /**
     * Tests whether checkCoordDown method will show that there is not a barrier below Wilbur
     */
    @Test
    public void checkCoordinateDownForBarrierClear()
    {
        Wilbur wilbur = new Wilbur(0,0);
        boolean barrier = wilbur.getCoordinate().checkCoordDown(spaces);

        Assertions.assertTrue(barrier);
    }

    /**
     *  Tests whether two coordinates can be detected as the same position
     */
    @Test
    public void checkIfSameCoordinate()
    {
        Wilbur wilbur = new Wilbur(0,0);
        Butcher butcher = new Butcher(0,1);

        wilbur.moveDown();

        boolean sameCoordinate = Coordinate.isCoordSame(wilbur.getCoordinate(), butcher.getCoordinate());

        Assertions.assertTrue(sameCoordinate);
    }

    /**
     *  Tests whether two coordinates can be detected as the different positions
     */
    @Test
    public void checkIfDifferentCoordinate()
    {
        Wilbur wilbur = new Wilbur(0,0);
        Butcher butcher = new Butcher(0,1);

        boolean sameCoordinate = Coordinate.isCoordSame(wilbur.getCoordinate(), butcher.getCoordinate());

        Assertions.assertFalse(sameCoordinate);
    }


}
