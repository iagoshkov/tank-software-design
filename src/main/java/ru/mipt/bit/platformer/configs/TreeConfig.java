package ru.mipt.bit.platformer.configs;

import com.badlogic.gdx.math.GridPoint2;

public class TreeConfig extends GameObjectConfig {
    public static final String DEFAULT_TEXTURE = "images/greenTree.png";
    
    public TreeConfig(GridPoint2 initialPosition) {
        super(DEFAULT_TEXTURE, initialPosition, 0f);
    }
    
    public TreeConfig(String texturePath, GridPoint2 initialPosition) {
        super(texturePath, initialPosition, 0f);
    }
}
