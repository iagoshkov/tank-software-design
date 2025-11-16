package ru.mipt.bit.platformer.test;

import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.controller.AITankController;
import ru.mipt.bit.platformer.model.Tank;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AITankControllerTest {
    
    private AITankController aiController;
    private Tank mockTank;
    
    
    @Before
    public void setUp() {
        mockTank = mock(Tank.class);
        when(mockTank.isMoving()).thenReturn(false);
        aiController = new AITankController(mockTank);
    }
    
    @Test
    public void testControllerCreation() {
        assertNotNull(aiController);
        assertNotNull(aiController.getCurrentDirection());
    }
    
    @Test
    public void testDirectionChangeOverTime() {
        Direction initialDirection = aiController.getCurrentDirection();
        
        // Симулируем прошедшее время больше интервала смены направления
        aiController.update(1.5f);
        
        // Направление должно измениться
        assertNotEquals(initialDirection, aiController.getCurrentDirection());
    }
    
    @Test
    public void testTankMovementWhenNotMoving() {
        when(mockTank.isMoving()).thenReturn(false);
        
        aiController.update(0.5f);
        
        verify(mockTank, atLeastOnce()).move(any());
    }
    
    @Test
    public void testNoMovementWhenTankIsMoving() {
        when(mockTank.isMoving()).thenReturn(true);
        
        aiController.update(0.5f);
        
        verify(mockTank, never()).move(any());
    }
}