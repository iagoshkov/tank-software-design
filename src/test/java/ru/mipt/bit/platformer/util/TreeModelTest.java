package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import static org.junit.Assert.*;

public class TreeModelTest {

    @Test
    public void testOccupiesPosition() {
        GridPoint2 pos = new GridPoint2(1, 1);
        TreeModel tree = new TreeModel(pos);
        assertTrue(tree.occupiesPosition(pos));
        assertFalse(tree.occupiesPosition(new GridPoint2(1, 2)));
    }

    @Test
    public void testUpdate() {
        TreeModel tree = new TreeModel(new GridPoint2(1, 1));
        tree.update(0.1f); // should do nothing
        assertEquals(new GridPoint2(1, 1), tree.getCoordinates());
    }
}