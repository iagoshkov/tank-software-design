package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Collection;
import java.util.Objects;

public class MovableObject extends OnScreenObject {
    protected GridPoint2 battlefieldDimensions;
    protected GridPoint2 destinationCoordinates;
    protected GridPoint2 movementCoordinates;
    protected float movementProgress = 1f;

    public MovableObject(String path, GridPoint2 coordinates, GridPoint2 battlefieldDimensions) {
        super(path, coordinates);
        this.battlefieldDimensions = battlefieldDimensions;
    }
    public MovableObject(GridPoint2 coordinates, GridPoint2 battlefieldDimensions) {
        super(coordinates);
        this.battlefieldDimensions = battlefieldDimensions;
    }

    public float getMovementProgress() {
        return movementProgress;
    }
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    protected OnScreenObject collides(Collection<? extends OnScreenObject> objects, GridPoint2 newCoordinates) {
        for (var object : objects) {
            if (Objects.equals(object.coordinates, newCoordinates) || (!this.equals(object) && object instanceof Tank && Objects.equals(((Tank) object).getDestinationCoordinates(), newCoordinates))) {
                return object;
            }
        }
        return null;
    }

    protected boolean outOfBattlefield (GridPoint2 newCoordinates) {
        return newCoordinates.x < 0 || newCoordinates.y < 0 || newCoordinates.x >= battlefieldDimensions.x ||
                newCoordinates.y >= battlefieldDimensions.y;
    }
}
