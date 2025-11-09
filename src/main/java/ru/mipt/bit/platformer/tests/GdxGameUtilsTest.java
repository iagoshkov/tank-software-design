package ru.mipt.bit.platformer.tests;

import ru.mipt.bit.platformer.util.GdxGameUtils;

public class GdxGameUtilsTest {
    
    public void testContinueProgress() {
        float progress = GdxGameUtils.continueProgress(0.5f, 0.1f, 1.0f);
        assert progress == 0.6f;
    }

    public void testContinueProgressClampMax() {
        float progress = GdxGameUtils.continueProgress(1.0f, 0.1f, 1.0f);
        assert progress == 1.0f;
    }

    public void testContinueProgressClampMin() {
        float progress = GdxGameUtils.continueProgress(-0.5f, 0.1f, 1.0f);
        assert progress == 0.0f;
    }
}