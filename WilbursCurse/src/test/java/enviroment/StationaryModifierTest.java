package enviroment;

import characters.Wilbur;
import environment.BearTrap;
import environment.MagicCorn;
import environment.MagicTruffle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Timer;

import static org.junit.Assert.*;

public class StationaryModifierTest {
    // StationaryModifier objects
    private BearTrap beartrap;
    private MagicCorn magiccorn;
    private MagicTruffle magictruffle;
    private Wilbur wilbur;

    @BeforeEach
    public void setUp() {
        wilbur = new Wilbur(0,0);
        beartrap = new BearTrap();
        magiccorn = new MagicCorn();
        magictruffle = new MagicTruffle();
    }

    @Test
    public void TestMagicCorn() { // Testing increment
        int expectedFinalPoints = 10;
        wilbur.setPoints(0);
        wilbur.pickupMagicCorn();
        assertEquals(expectedFinalPoints, wilbur.getPoints());
    }
    @Test
    public void TestBearTrapPositive() { // Testing decrement
        int expectedFinalPoints = 10;
        boolean wilburState = false;
        wilbur.setPoints(20);
        wilbur.stepOnBearTrap();
        assertEquals(expectedFinalPoints, wilbur.getPoints());
        assertEquals(wilbur.isDead(), wilburState); // Asserts that BearTrap kills Wilbur
    }
    @Test
    public void TestBearTrapNegative() { // Testing negative decrement
        int expectedFinalPoints = -10;
        boolean wilburIsDead = true;
        wilbur.setPoints(0);
        wilbur.stepOnBearTrap();
        assertEquals(expectedFinalPoints, wilbur.getPoints());
        assertEquals(wilbur.isDead(), wilburIsDead); // Asserts that BearTrap kills Wilbur
    }

    @Test
    public void TestMagicTruffle() { // Testing special increment
        int expectedFinalPoints = 20;
        boolean wilburInvincibilityState = true;
        wilbur.setPoints(0);
        wilbur.pickupMagicTruffle(new Timer());
        assertEquals(expectedFinalPoints, wilbur.getPoints());
        assertEquals(wilbur.isInvincible(), wilburInvincibilityState); // Asserts that MagicTruffle makes Wilbur invincible
    }
}
