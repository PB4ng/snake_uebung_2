package de.hsaalen;

import org.junit.Test;
import static org.junit.Assert.*;

public class IntPairTest
{

    @Test
    public void testClone()
    {
        IntPair original = new IntPair(5, 10);

        IntPair clone = original.clone();

        assertEquals(original.x, clone.x);
        assertEquals(original.y, clone.y);

        clone.x = 20;
        clone.y = 30;

        assertNotEquals(original.x, clone.x);
        assertNotEquals(original.y, clone.y);
    }

    @Test
    public void testToString()
    {
        IntPair pair1 = new IntPair(5, 10);
        assertEquals("(5/10)", pair1.toString());

        IntPair pair2 = new IntPair(-3, -7);
        assertEquals("(-3/-7)", pair2.toString());

        IntPair pair3 = new IntPair(0, 0);
        assertEquals("(0/0)", pair3.toString());

        IntPair pair4 = new IntPair(-8, 15);
        assertEquals("(-8/15)", pair4.toString());
    }
}
