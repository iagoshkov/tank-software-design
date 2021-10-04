package ru.mipt.bit.platformer.objects;


import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree {
    private static Rectangle obstacleRectangle;
    private final GridPoint2 obstacleCoordinates = new GridPoint2(1, 3);

    public Tree() {
        this.obstacleRectangle = createBoundingRectangle(GraphicsObjects.getObstacle());
    }


    public GridPoint2 getObstacleCoordinates() {
        return obstacleCoordinates;
    }

    public static Rectangle getObstacleRectangle() {
        return obstacleRectangle;
    }


}
