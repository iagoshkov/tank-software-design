package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Collection;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet extends MovableObject {
    public void update(Collection<OnScreenObject> obstacles, Collection<Tank> tanks, float deltaTime, float movementSpeed) {
        movementProgress = continueProgress(this.movementProgress, deltaTime, movementSpeed);

        if (movementProgress == 1f) {
            coordinates.set(destinationCoordinates);

            GridPoint2 coordinatesAfterMove = new GridPoint2(this.coordinates).add(this.movementCoordinates);
            OnScreenObject collidesWith;

            destinationCoordinates.set(coordinatesAfterMove);
            movementProgress = 0f;

            if (collides(tanks, coordinates) != null && (collidesWith = collides(tanks, coordinates)) instanceof Tank) {
                alive = false;
                Tank collidedTank = (Tank) collidesWith;
                collidedTank.wasShot();
            } else if ((collides(obstacles, coordinates) != null) || outOfBattlefield(coordinates)) {
                alive = false;
            }
        }
    }

    public Bullet(String path, GridPoint2 coordinates, GridPoint2 battlefieldDimensions, GridPoint2 movementCoordinates) {
        super(path, coordinates, battlefieldDimensions);
        setProperties(coordinates, movementCoordinates);
    }

    public Bullet(GridPoint2 coordinates, GridPoint2 battlefieldDimensions, GridPoint2 movementCoordinates) {
        super(coordinates, battlefieldDimensions);
        setProperties(coordinates, movementCoordinates);
    }

    private void setProperties(GridPoint2 coordinates, GridPoint2 movementCoordinates) {
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.movementCoordinates = movementCoordinates;
    }
}
