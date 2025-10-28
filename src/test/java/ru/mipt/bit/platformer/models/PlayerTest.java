package ru.mipt.bit.platformer.models;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.TileMovement;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private TileMovement movement;

    private static final int TILE_SIZE = 128;

    @BeforeEach
    void setUp() {
        TiledMapTileLayer layer = new TiledMapTileLayer(10, 10, TILE_SIZE, TILE_SIZE);
        movement = new TileMovement(layer, Interpolation.linear);

        player = new Player(movement, new GridPoint2(1, 1));
    }

    @Test
    void playerStartsAtCorrectTileCenter() {
        Rectangle rect = player.getRectangle();
        float centerX = rect.x + TILE_SIZE / 2f;
        float centerY = rect.y + TILE_SIZE / 2f;

        assertEquals(1.5f * TILE_SIZE, centerX, 1e-3);
        assertEquals(1.5f * TILE_SIZE, centerY, 1e-3);
    }

    @Test
    void playerMovesToNewTileAfterUpdate() {
        player.startMovement(new GridPoint2(2, 1), 0f);
        player.update(0.5f);

        Rectangle rect = player.getRectangle();
        float centerX = rect.x + TILE_SIZE / 2f;
        float centerY = rect.y + TILE_SIZE / 2f;

        assertTrue(centerX > 1.5f * TILE_SIZE);
        assertEquals(1.5f * TILE_SIZE, centerY, 1e-3);
    }
}
