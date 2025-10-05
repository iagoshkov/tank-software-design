package ru.mipt.bit.platformer.test;

import org.junit.Before;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Tree;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class TreeTest {
    
    private Tree tree;
    private TiledMapTileLayer mockLayer;
    private Texture mockTexture;
    
    @Before
    public void setUp() {
        mockLayer = mock(TiledMapTileLayer.class);
        when(mockLayer.getTileWidth()).thenReturn(128);
        when(mockLayer.getTileHeight()).thenReturn(128);
        
        mockTexture = mock(Texture.class);
        tree = new Tree(mockTexture, mockLayer, new GridPoint2(3, 4));
    }
    
    @Test
    public void testInitialPosition() {
        assertEquals(new GridPoint2(3, 4), tree.getCoordinates());
    }
    
    @Test
    public void testTreeIsStatic() {
        // Дерево не должно менять позицию при обновлении
        GridPoint2 initialPosition = tree.getCoordinates();
        tree.update(1.0f); // Прошла 1 секунда
        
        assertEquals(initialPosition, tree.getCoordinates());
    }
    
    @Test
    public void testRectangleCreation() {
        assertNotNull(tree.getRectangle());
    }
}