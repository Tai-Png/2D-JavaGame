package characters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;

import java.util.Timer;

public class WilburTest {
    // Wilbur object for Testing
    private Wilbur wilbur;

    // Butcher object for Testing
    private Butcher butcher;

    // Timer for "ticks"
    private Timer gameTime = new Timer();

    // Here we set up the characters before the testing.

    @BeforeEach
    public void setUp() {
        wilbur = new Wilbur(3, 3);
        butcher = new Butcher(3, 3);
    }

    // This method tests if Wilbur can move to the right on the board, by
    // comparing his actual coordinate after moving with the expected
    // coordinate after moving.

    @Test
    public void TestMovementRight() {
        int expectedxcoord = wilbur.getCoordinate().getxCoordinate() + 1;
        wilbur.moveRight();
        int actualxcoord = wilbur.getCoordinate().getxCoordinate();
        assertEquals(expectedxcoord, actualxcoord);
    }

    // This method tests if Wilbur can move to the left on the board, by
    // comparing his actual coordinate after moving with the expected
    // coordinate after moving.

    @Test
    public void TestMovementLeft() {
        int expectedxcoord = wilbur.getCoordinate().getxCoordinate() - 1;
        wilbur.moveLeft();
        int actualxcoord = wilbur.getCoordinate().getxCoordinate();
        assertEquals(expectedxcoord, actualxcoord);
    }

    // This method tests if Wilbur can move down on the board, by
    // comparing his actual coordinate after moving with the expected
    // coordinate after moving.

    @Test
    public void TestMovementDown() {
        int expectedycoord = wilbur.getCoordinate().getyCoordinate() + 1;
        wilbur.moveDown();
        int actualycoord = wilbur.getCoordinate().getyCoordinate();
        assertEquals(expectedycoord, actualycoord);
    }

    // This method tests if Wilbur can move up on the board, by
    // comparing his actual coordinate after moving with the expected
    // coordinate after moving.

    @Test
    public void TestMovementUp() {
        int expectedycoord = wilbur.getCoordinate().getyCoordinate() - 1;
        wilbur.moveUp();
        int actualycoord = wilbur.getCoordinate().getyCoordinate();
        assertEquals(expectedycoord, actualycoord);
    }

    // This method is a conceptual test for overall movement. It is used to
    // see if Wilbur can move full in a circle showing that all the
    // movement attributes can work together in unison.

    @Test
    public void TestMovementInCircle() {
        int expectedycoord = wilbur.getCoordinate().getyCoordinate();
        int expectedxcoord = wilbur.getCoordinate().getxCoordinate();
        wilbur.moveDown();
        wilbur.moveRight();
        wilbur.moveUp();
        wilbur.moveLeft();
        int actualycoord = wilbur.getCoordinate().getyCoordinate();
        int actualxcoord = wilbur.getCoordinate().getxCoordinate();
        assertEquals(expectedycoord, actualycoord);
        assertEquals(expectedxcoord, actualxcoord);
    }

    // This method tests to see if the kill method does work in killing Wilbur. This is done
    // by having the coordinates of Wilbur and the butcher match, followed by calling the kill
    // method and testing if this changed the value of isDead.

    @Test
    public void TestKill() {
        if (wilbur.getCoordinate().getyCoordinate() == butcher.getCoordinate().getyCoordinate() &&
                wilbur.getCoordinate().getxCoordinate() == butcher.getCoordinate().getxCoordinate()) {
            wilbur.kill();
        }

        assertTrue(wilbur.isDead());
    }

    // This method tests the dying aspect of Wilbur's invincibility.
    // Wilbur is set to be invincible and then the kill method is called in
    // attempt to kill him. The result should be that the boolean value of
    // isDead should not change while invincible.

    @Test
    public void TestInvincibility() {
        wilbur.setInvincible(true);
        wilbur.kill();
        assertFalse(wilbur.isDead());
    }

    // This Method is meant to test if the invincibility of Wilbur does last 5 seconds.
    // This is not the most efficient test since there is a delay in calculation of the times etc.
    // Overall, the way this works is we try to have the main thread sleep while Wilbur is still
    // invincible and we record this time.

    @Test
    public void TestInvincibilityLength() {
        long initialtime = System.currentTimeMillis() / 1000;
        wilbur.setInvincible(true);
        wilbur.setInvincibilityLength(gameTime, 5000);
        while(wilbur.isInvincible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) { break; };
        }
        long finaltime = System.currentTimeMillis() / 1000;
        long actualtime = finaltime - initialtime;
        assertTrue(actualtime >= 5);
    }

}