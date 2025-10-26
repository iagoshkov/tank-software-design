package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class LevelData {
    private final List<GridPoint2> treePositions;
    private final GridPoint2 playerStartPosition;
    private final int width;
    private final int height;
    
    public LevelData(List<GridPoint2> treePositions, GridPoint2 playerStartPosition, int width, int height) {
        this.treePositions = treePositions;
        this.playerStartPosition = playerStartPosition;
        this.width = width;
        this.height = height;
    }
    
    public List<GridPoint2> getTreePositions() {
        return treePositions;
    }
    
    public GridPoint2 getPlayerStartPosition() {
        return playerStartPosition;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}