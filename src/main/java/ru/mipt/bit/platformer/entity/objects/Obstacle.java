package ru.mipt.bit.platformer.entity.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;


public class Obstacle implements GameObject {
    protected GridPoint2 coordinates;

    public Obstacle(GridPoint2 point) {
        coordinates = point;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean isBusyCoordinate(GridPoint2 point) {
        return coordinates.equals(point);
    }

    public void updateState(float deltaTime) {}
}
