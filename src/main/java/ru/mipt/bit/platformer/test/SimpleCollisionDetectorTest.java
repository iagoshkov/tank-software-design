package ru.mipt.bit.platformer.test;

import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.collision.SimpleCollisionDetector;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Tank;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SimpleCollisionDetectorTest {
    
    private SimpleCollisionDetector collisionDetector;
    private TiledMapTileLayer mockLayer;
    
    @Before
    public void setUp() {
        mockLayer = mock(TiledMapTileLayer.class);
        when(mockLayer.getWidth()).thenReturn(10);
        when(mockLayer.getHeight()).thenReturn(8);
        collisionDetector = new SimpleCollisionDetector(mockLayer);
    }
    
    @Test
    public void testBoundsCollision() {
        assertTrue(collisionDetector.isPositionBlocked(new GridPoint2(-1, 5)));
        assertTrue(collisionDetector.isPositionBlocked(new GridPoint2(10, 5)));
    }
    
    @Test
    public void testObstacleCollision() {
        GameObject mockObstacle = mock(GameObject.class);
        when(mockObstacle.getCoordinates()).thenReturn(new GridPoint2(3, 3));
        
        collisionDetector.addObstacle(mockObstacle);
        
        assertTrue(collisionDetector.isPositionBlocked(new GridPoint2(3, 3)));
        assertFalse(collisionDetector.isPositionBlocked(new GridPoint2(3, 4)));
    }
    
    @Test
    public void testMovingTankCollision() {
        Tank mockTank = mock(Tank.class);
        when(mockTank.getCoordinates()).thenReturn(new GridPoint2(2, 2));
        
        collisionDetector.addMovingTank(mockTank);
        
        assertTrue(collisionDetector.isPositionBlocked(new GridPoint2(2, 2)));
    }

    public void testMovingTankOccupiesTwoCells() {
        Tank mockTank = mock(Tank.class);
        when(mockTank.getCoordinates()).thenReturn(new GridPoint2(2, 2));
        when(mockTank.isMoving()).thenReturn(true);
        when(mockTank.getDestinationCoordinates()).thenReturn(new GridPoint2(3, 2));
        
        collisionDetector.addMovingTank(mockTank);
        
        // Танк должен занимать обе клетки - текущую и следующую
        assertTrue(collisionDetector.isPositionBlocked(new GridPoint2(2, 2)));
        assertTrue(collisionDetector.isPositionBlocked(new GridPoint2(3, 2)));
        assertFalse(collisionDetector.isPositionBlocked(new GridPoint2(2, 3))); // Свободная клетка
    }

    @Test
    public void testStationaryTankOccupiesOneCell() {
        Tank mockTank = mock(Tank.class);
        when(mockTank.getCoordinates()).thenReturn(new GridPoint2(2, 2));
        when(mockTank.isMoving()).thenReturn(false);
        
        collisionDetector.addMovingTank(mockTank);
        
        // Неподвижный танк занимает только свою клетку
        assertTrue(collisionDetector.isPositionBlocked(new GridPoint2(2, 2)));
        assertFalse(collisionDetector.isPositionBlocked(new GridPoint2(3, 2))); // Свободная клетка
    }
}