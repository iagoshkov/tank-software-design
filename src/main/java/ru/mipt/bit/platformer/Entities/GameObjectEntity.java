package ru.mipt.bit.platformer.Entities;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class GameObjectEntity {
    public GridPoint2 position;
    public float rotation;
    public GameObjectEntity(GridPoint2 position, float rotation){
        this.position = position;
        this.rotation = rotation;
    }
    public GameObjectEntity(GridPoint2 position){
        this.position = position;
        this.rotation = 0f;
    }
}
