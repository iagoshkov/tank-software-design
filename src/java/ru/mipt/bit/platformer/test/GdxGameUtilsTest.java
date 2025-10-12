package ru.tests;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import static org.junit.Assert.*;

public class GdxGameUtilsTest {

    @Test
    public void testContinueProgress() {
        float progress = 0.5f;
        float deltaTime = 0.1f;
        float speed = 0.4f;
        
        float newProgress = GdxGameUtils.continueProgress(progress, deltaTime, speed);
        
        assertEquals(0.75f, newProgress, 0.01f);
    }

    @Test
    public void testContinueProgressClamping() {
        // Test that progress doesn't exceed 1.0
        float progress = GdxGameUtils.continueProgress(0.9f, 0.2f, 0.1f);
        assertEquals(1.0f, progress, 0.01f);
        
        // Test that progress doesn't go below 0.0
        progress = GdxGameUtils.continueProgress(-0.1f, 0.1f, 0.1f);
        assertEquals(0.0f, progress, 0.01f);
    }

    @Test
    public void testGridPointOperations() {
        GridPoint2 point = new GridPoint2(1, 1);
        
        assertEquals(new GridPoint2(2, 1), GdxGameUtils.incrementedX(point));
        assertEquals(new GridPoint2(0, 1), GdxGameUtils.decrementedX(point));
        assertEquals(new GridPoint2(1, 2), GdxGameUtils.incrementedY(point));
        assertEquals(new GridPoint2(1, 0), GdxGameUtils.decrementedY(point));
    }
}