package ru.mipt.bit.platformer.test;

import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class TankTest {
    
    private Tank tank;
    private TiledMapTileLayer mockLayer;
    private Texture mockTexture;
    
    @Before
    public void setUp() {
        mockLayer = mock(TiledMapTileLayer.class);
        when(mockLayer.getTileWidth()).thenReturn(128);
        when(mockLayer.getTileHeight()).thenReturn(128);
        
        mockTexture = mock(Texture.class);
        tank = new Tank(mockTexture, mockLayer, new GridPoint2(1, 1));
    }
    
    @Test
    public void testInitialState() {
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertFalse(tank.isMoving());
        assertEquals(1f, tank.getMovementProgress(), 0.001f);
    }
    
    @Test
    public void testMoveWithoutObstacle() {
        boolean moved = tank.move(Direction.RIGHT, new GridPoint2(5, 5)); // препятствие далеко
        
        assertTrue(moved);
        assertTrue(tank.isMoving());
        assertEquals(0f, tank.getMovementProgress(), 0.001f);
    }
    
    @Test
    public void testMoveWithObstacle() {
        boolean moved = tank.move(Direction.RIGHT, new GridPoint2(2, 1)); // препятствие на пути
        
        assertFalse(moved);
        assertFalse(tank.isMoving());
    }
    
    @Test
    public void testCannotMoveWhileMoving() {
        // Начинаем движение
        tank.move(Direction.RIGHT, new GridPoint2(5, 5));
        
        // Пытаемся двигаться снова
        boolean movedAgain = tank.move(Direction.UP, new GridPoint2(5, 5));
        
        assertFalse(movedAgain); // Должно вернуть false, так как уже движется
    }
    
    @Test
    public void testUpdateMovement() {
        tank.move(Direction.RIGHT, new GridPoint2(5, 5));
        
        // Симулируем небольшое время
        tank.updateMovement(0.1f, 0.4f);
        
        assertTrue(tank.getMovementProgress() > 0f);
        assertTrue(tank.getMovementProgress() < 1f);
        assertTrue(tank.isMoving());
    }
}