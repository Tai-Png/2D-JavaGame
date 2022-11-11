package gamemechanics;

import characters.Wilbur;
import environment.StationaryModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Timer;

public class InteractionTest {

    int[][] spaces;
    Wilbur wilbur;

    /**
     * Sets up the global variables for gamemechanics.InteractionTest
     */
    @BeforeEach
    public void init()
    {
        spaces = new int[5][5];
        wilbur = new Wilbur(0,0);
    }

    /**
     * Simulates an interaction with Magic Corn, ensures that the correct interaction is returned and that the amount of points
     * corn gives is returned
     */
    @Test
    public void interactWithCorn()
    {
        spaces[0][1] = BoardSpace.MAGIC_CORN;

        wilbur.moveDown();

        wilbur.pickupMagicCorn();

        Assertions.assertEquals(checkInteractionUtility(), BoardSpace.MAGIC_CORN);
        Assertions.assertEquals(wilbur.getPoints(), StationaryModifier.MAGIC_CORN_POINTS);
    }

    /**
     * Simulates an interaction with Magic Truffle, ensures that the correct interaction is returned and that the amount of points
     * Magic Truffle gives is returned
     */
    @Test
    public void interactWithMagicTruffle()
    {
        spaces[0][1] = BoardSpace.MAGIC_TRUFFLE;

        wilbur.moveDown();

        wilbur.pickupMagicTruffle(new Timer());

        Assertions.assertEquals(checkInteractionUtility(), BoardSpace.MAGIC_TRUFFLE);
        Assertions.assertEquals(wilbur.getPoints(), StationaryModifier.MAGIC_TRUFFLE_POINTS);
    }

    /**
     * Simulates an interaction with BearTrap, ensures that the correct interaction is returned points become negative
     * and that Wilbur dies
     */
    @Test
    public void interactWithBearTrapDie()
    {
        spaces[0][1] = BoardSpace.BEAR_TRAP;

        wilbur.moveDown();

        wilbur.stepOnBearTrap();

        Assertions.assertEquals(checkInteractionUtility(), BoardSpace.BEAR_TRAP);
        Assertions.assertEquals(-10, wilbur.getPoints());
        Assertions.assertTrue(wilbur.isDead());

    }

    /**
     * Simulates an interaction with BearTrap, ensures that the correct interaction is returned points go to zero and
     * Wilbur stays alive
     */
    @Test
    public void interactWithBearTrapLive()
    {
        spaces[0][1] = BoardSpace.BEAR_TRAP;

        wilbur.moveDown();

        wilbur.setPoints(StationaryModifier.BEAR_TRAP_POINTS);

        wilbur.stepOnBearTrap();

        Assertions.assertEquals(checkInteractionUtility(), BoardSpace.BEAR_TRAP);
        Assertions.assertEquals(wilbur.getPoints(), 0);
        Assertions.assertFalse(wilbur.isDead());

    }

    /**
     * Simulates an interaction with BearTrap while invincible,
     * Ensures that the correct interaction is returned and that the points stay the same
     */
    @Test
    public void invincibleInteractWithBearTrap()
    {
        int points = 20;
        spaces[0][1] = BoardSpace.BEAR_TRAP;

        wilbur.setPoints(points);

        wilbur.moveDown();

        wilbur.setInvincible(true);

        wilbur.attackedByButcher();

        Assertions.assertEquals(checkInteractionUtility(), BoardSpace.BEAR_TRAP);
        Assertions.assertFalse(wilbur.isDead());
        Assertions.assertEquals(points, wilbur.getPoints());
    }

    /**
     * Simulates an interaction with a Butcher, ensures that the correct interaction is returned and that Wilbur dies
     */
    @Test
    public void interactWithButcher()
    {
        spaces[0][1] = BoardSpace.BUTCHER;

        wilbur.moveDown();

        wilbur.attackedByButcher();

        Assertions.assertEquals(checkInteractionUtility(), BoardSpace.BUTCHER);
        Assertions.assertTrue(wilbur.isDead());
    }

    /**
     * Simulates an interaction with a Butcher, ensures that the correct interaction is returned and that Wilbur
     * does not die
     */
    @Test
    public void invincibleInteractWithButcher()
    {
        spaces[0][1] = BoardSpace.BUTCHER;

        wilbur.moveDown();

        wilbur.setInvincible(true);

        wilbur.attackedByButcher();

        Assertions.assertEquals(checkInteractionUtility(), BoardSpace.BUTCHER);
        Assertions.assertFalse(wilbur.isDead());

    }

    /**
     * Ensure that our detection utility can detect the exit interaction
     */
    @Test
    public void interactWithExit()
    {
        spaces[0][1] = BoardSpace.EXIT;

        wilbur.moveDown();

        Assertions.assertEquals(checkInteractionUtility(), BoardSpace.EXIT);
    }

    /**
     * Utility for repeated code to get the type of interaction from the spaces array and wilbur's coordinates
     */
    public int checkInteractionUtility()
    {
        return wilbur.getCoordinate().checkIfInteraction(spaces);
    }
}

