package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movementCommand.TankAction;

import java.util.ArrayList;
import java.util.Objects;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends OnScreenObject {
    private static String bulletImage = "images/bullet.png";
    public void decreaseHealth() {
        if (health == 0) {
            this.alive = false;
            return;
        }
        this.health--;
    }

    private GridPoint2 getBulletMovementDirection() {
        if (rotation == 0f) {
            return new GridPoint2(1, 0);
        } else if (rotation == -180f) {
            return new GridPoint2(-1, 0);
        } else if (rotation == 90f) {
            return new GridPoint2(0, 1);
        } else if (rotation == -90f) {
            return new GridPoint2(0, -1);
        }
        return new GridPoint2();
    }

    private int health = 3;
    private GridPoint2 battlefieldDimensions;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public boolean isManuallyControlled() {
        return manuallyControlled;
    }

    public void setManuallyControlled(boolean manuallyControlled) {
        this.manuallyControlled = manuallyControlled;
    }

    private boolean manuallyControlled = false;

    public float getMovementProgress() {
        return movementProgress;
    }
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }
    private void setRotation(GridPoint2 movement) {
        if (movement.x > 0) {
            this.rotation = 0;
        } else if (movement.x < 0) {
            this.rotation = -180f;
        } else if (movement.y > 0) {
            this.rotation = 90f;
        } else if (movement.y < 0) {
            this.rotation = -90f;
        }
    }

    private boolean collides (ArrayList<? extends OnScreenObject> allCoordinates, GridPoint2 newCoordinates) {
        for (var coord : allCoordinates) {
            if (Objects.equals(coord.coordinates, newCoordinates) || (!this.equals(coord) && coord instanceof Tank && Objects.equals(((Tank) coord).destinationCoordinates, newCoordinates))) {
                return true;
            }
        }
        return false;
    }

    private boolean outOfBattlefield (GridPoint2 newCoordinates) {
        return newCoordinates.x < 0 || newCoordinates.y < 0 || newCoordinates.x >= battlefieldDimensions.x ||
                newCoordinates.y >= battlefieldDimensions.y;
    }

    public Tank(String path, GridPoint2 coordinates, GridPoint2 battlefieldDimensions) {
        super(path, coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.battlefieldDimensions = battlefieldDimensions;
    }

    public Tank(GridPoint2 coordinates, GridPoint2 battlefieldDimensions) {
        super(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.battlefieldDimensions = battlefieldDimensions;
    }

    public Bullet update(TankAction action, ArrayList<OnScreenObject> obstacles, ArrayList<Tank> tanks,
                       float deltaTime, float movementSpeed) {
        this.movementProgress = continueProgress(this.movementProgress, deltaTime, movementSpeed);

        if (movementProgress == 1f) {
            this.coordinates.set(destinationCoordinates);
            if (action == TankAction.WAIT) return null;

            if (action == TankAction.SHOOT) {
                System.out.println("BANG");
                Bullet bullet = new Bullet(bulletImage, new GridPoint2(this.coordinates).add(getBulletMovementDirection()),
                        battlefieldDimensions, getBulletMovementDirection());
                return bullet;
            }

            GridPoint2 coordinatesAfterMove = new GridPoint2(this.coordinates).add(action.getMovement());
            if (!collides(obstacles, coordinatesAfterMove) && !collides(tanks, coordinatesAfterMove) && !outOfBattlefield(coordinatesAfterMove)) {
                this.destinationCoordinates.set(coordinatesAfterMove);
                this.movementProgress = 0f;
            }
            this.setRotation(action.getMovement());
        }
        return null;
    }
}
