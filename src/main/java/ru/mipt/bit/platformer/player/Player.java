package ru.mipt.bit.platformer.player;

import com.badlogic.gdx.Gdx;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private static Rectangle rectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private static GridPoint2 coordinates = new GridPoint2(1,0);
    private static float movementProgress = 1f;
    private static float rotation = 0f;




    // get time passed since the last render
    public float getDeltaTime() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        return deltaTime;
    }

    public Player(){
        this.rectangle = createBoundingRectangle(GraphicsPlayer.getPlayer());
    }

    public static Rectangle getRectangle() {
        return rectangle;
    }

    public static GridPoint2 getCoordinates() {
        return coordinates;
    }

    public static float getMovementProgress() {
        return movementProgress;
    }

    public static float getRotation() {
        return rotation;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
