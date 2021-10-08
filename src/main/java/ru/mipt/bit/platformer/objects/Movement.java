package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

public class Movement {
    public GridPoint2 directionVector;
    public float rotation;

    public Movement(){
        directionVector = new GridPoint2(0, 0);
        rotation = 0f;
    }

    public Movement(GridPoint2 vector, float rotation){
        directionVector = vector;
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getDirectionVector() {
        return directionVector;
    }

    public void setToZero(){
        directionVector.x = 0;
        directionVector.y = 0;
        rotation = 0f;
    }

    public boolean isNull(){
        return (directionVector.x == 0 && directionVector.y == 0);
    }
}
