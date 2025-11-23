package ru.mipt.bit.platformer.tests;

import ru.mipt.bit.platformer.util.TileMovement;

public class TileMovementTest {
    
    public void testTileMovementCreation() {
        try {
            new TileMovement(null, null);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
}