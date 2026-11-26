package ru.mipt.bit.platformer.graphics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthBarLayoutTest {

    @Test
    void placesBarAboveObjectAndScalesHealth() {
        HealthBarLayout bar = new HealthBarLayout(10, 20, 80, 40, 60, 80);

        assertEquals(10f, bar.x);
        assertEquals(66f, bar.y);
        assertEquals(80f, bar.width);
        assertEquals(8f, bar.height);
        assertEquals(60f, bar.filledWidth);
    }

    @Test
    void keepsFillWithinBar() {
        assertEquals(0f, layout(-10, 100).filledWidth);
        assertEquals(80f, layout(120, 100).filledWidth);
        assertEquals(0f, layout(10, 0).filledWidth);
    }

    private HealthBarLayout layout(int health, int maxHealth) {
        return new HealthBarLayout(0, 0, 80, 40, health, maxHealth);
    }
}
