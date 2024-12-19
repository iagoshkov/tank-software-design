package ru.mipt.bit.platformer.entity.tank;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank {

    private static final float MOVEMENT_SPEED = 0.4f;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public Tank(GridPoint2 point) {
            this.destinationCoordinates = point;
            this.coordinates = new GridPoint2(this.destinationCoordinates);
            this.rotation = 0f;
        }
        public GridPoint2 getCoordinates() {
            return coordinates;
        }
        public GridPoint2 getDestinationCoordinates() {
            return destinationCoordinates;
        }
        public float getMovementProgress() {
            return movementProgress;
        }
        public float getRotation() {
            return rotation;
        }

        public void setCoordinates(GridPoint2 point) {
            coordinates.set(point);
        }

        public void updateState(float deltaTime) {
            movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
            if (isEqual(movementProgress, 1f)) {
                setCoordinates(destinationCoordinates);
            }
        }

        public void move(GridPoint2 point, float rotation) {
            destinationCoordinates.add(point.x, point.y);
            this.rotation = rotation;
            movementProgress = 0f;
        }
    }
