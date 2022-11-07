package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Objects;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet extends OnScreenObject{
    private GridPoint2 battlefieldDimensions;
    private GridPoint2 destinationCoordinates;
    private final GridPoint2 movementCoordinates;
    private float movementProgress = 1f;


    public float getMovementProgress() {
        return movementProgress;
    }
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    private OnScreenObject collides (ArrayList<? extends OnScreenObject> objects, GridPoint2 newCoordinates) {
        for (var object : objects) {
            if (Objects.equals(object.coordinates, newCoordinates) || (!this.equals(object) && object instanceof Tank && Objects.equals(((Tank) object).getDestinationCoordinates(), newCoordinates))) {
                return object;
            }
        }
        return null;
    }

    private boolean outOfBattlefield (GridPoint2 newCoordinates) {
        return newCoordinates.x < 0 || newCoordinates.y < 0 || newCoordinates.x >= battlefieldDimensions.x ||
                newCoordinates.y >= battlefieldDimensions.y;
    }

    public Bullet(String path, GridPoint2 coordinates, GridPoint2 battlefieldDimensions, GridPoint2 movementCoordinates) {
        super(path, coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.battlefieldDimensions = battlefieldDimensions;
        this.movementCoordinates = movementCoordinates;
    }

    public Bullet(GridPoint2 coordinates, GridPoint2 battlefieldDimensions, GridPoint2 movementCoordinates) {
        super(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.battlefieldDimensions = battlefieldDimensions;
        this.movementCoordinates = movementCoordinates;
    }

    public void update(ArrayList<OnScreenObject> obstacles, ArrayList<Tank> tanks,
                       float deltaTime, float movementSpeed) {
        this.movementProgress = continueProgress(this.movementProgress, deltaTime, movementSpeed);

        if (movementProgress == 1f) {
            this.coordinates.set(destinationCoordinates);

            GridPoint2 coordinatesAfterMove = new GridPoint2(this.coordinates).add(this.movementCoordinates);
            OnScreenObject collidesWith;

            if (collides(tanks, coordinatesAfterMove) != null && (collidesWith = collides(tanks, coordinatesAfterMove)) instanceof Tank) {
                alive = false;
                Tank collidedTank = (Tank) collidesWith;
                collidedTank.decreaseHealth();
                return;
            }

            if ((collides(obstacles, coordinatesAfterMove) != null) || outOfBattlefield(coordinatesAfterMove)) {
                alive = false;
                return;
            }

            this.destinationCoordinates.set(coordinatesAfterMove);
            this.movementProgress = 0f;
        }
    }
}
