package ru.mipt.bit.platformer.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovableTexturedItem extends TexturedItem {
    private float progress = 1f;
    GridPoint2 destination;
    private float intermediateBottomLeftX;
    private float intermediateBottomLeftY;


    public MovableTexturedItem(String src, GridPoint2 curCoordinate) {
        super(src, curCoordinate);
    }

    public void continueProgress(float deltaTime, float speed) {
        progress = clamp(progress + deltaTime / speed, 0f, 1f);
        if (progressCompleted()) {
            super.setCoordinates(destination);
            resetProgress();
        }
    }

    public boolean progressCompleted() {
        return isEqual(progress, 1f);
    }

    public void resetProgress() {
        progress = 0f;
    }

    public void moveRight() {
        destination.add(1, 0);
    }

    public void moveLeft() {
        destination.sub(1, 0);
    }

    public void moveUp() {
        destination.add(0, 1);
    }

    public void moveDown() {
        destination.sub(0, 1);
    }

    float getProgress() {
        return progress;
    }

    @Override
    public void draw(Batch batch) {
        draw(batch,
                region,
                new Rectangle(rectangle).setX(intermediateBottomLeftX).setY(intermediateBottomLeftY),
                rotation);
    }

    void setCoordinatesToDraw(float intermediateBottomLeftX, float intermediateBottomLeftY) {
        this.intermediateBottomLeftX = intermediateBottomLeftX;
        this.intermediateBottomLeftY = intermediateBottomLeftY;
    }
}
