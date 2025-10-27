package ru.mipt.bit.platformer.configs;

import com.badlogic.gdx.math.GridPoint2;

public class PlayerConfig extends GameObjectConfig {
    public static final float DEFAULT_MOVEMENT_SPEED = 0.4f;
    public static final String DEFAULT_TEXTURE = "src/main/resources/images/tank_blue.png";
    
    public PlayerConfig(GridPoint2 initialPosition) {
        super(DEFAULT_TEXTURE, initialPosition, DEFAULT_MOVEMENT_SPEED);
    }
    
    public PlayerConfig(String texturePath, GridPoint2 initialPosition, float movementSpeed) {
        super(texturePath, initialPosition, movementSpeed);
    }
}
