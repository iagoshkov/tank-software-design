package ru.mipt.bit.platformer.test;

import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.controller.InputController;
import ru.mipt.bit.platformer.model.Tank;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class InputControllerTest {
    
    private InputController inputController;
    private Tank mockTank;
    
    @Before
    public void setUp() {
        mockTank = mock(Tank.class);
        when(mockTank.getCoordinates()).thenReturn(new GridPoint2(2, 2));
        inputController = new InputController(mockTank);
    }
    
    @Test
    public void testHandleInputWithSingleObstacle() {
        when(mockTank.move(any(), any())).thenReturn(true);
        
        boolean result = inputController.handleInput(new GridPoint2(5, 5));
        
       
        assertFalse(result); 
    }
    
    @Test
    public void testHandleInputWithMultipleObstacles() {
        when(mockTank.move(any(), any())).thenReturn(true);
        
        boolean result = inputController.handleInput(
            new GridPoint2(1, 1), 
            new GridPoint2(3, 3)
        );
        

        assertFalse(result);
    }
    
    @Test
    public void testControllerConstruction() {
        assertNotNull(inputController);
    }
}