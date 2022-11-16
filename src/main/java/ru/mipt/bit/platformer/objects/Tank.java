package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movementCommand.TankAction;
import ru.mipt.bit.platformer.objects.state.HealthyState;
import ru.mipt.bit.platformer.objects.state.TankState;

import java.util.ArrayList;
import java.util.Objects;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends OnScreenObject {
    private static String bulletImage = "images/bullet.png";
    private float initialMovementSpeed;
    TankState currentState;
    public void wasShot() {
        if (health == 0) {
            this.alive = false;
            return;
        }
        this.health--;
        this.currentState = TankState.dispatch(health, maxHealth, initialMovementSpeed);
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

    private int health, maxHealth;
    private GridPoint2 battlefieldDimensions, destinationCoordinates;
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    private boolean manuallyControlled = false;
    public boolean isManuallyControlled() {
        return manuallyControlled;
    }
    public void setManuallyControlled(boolean manuallyControlled) {
        this.manuallyControlled = manuallyControlled;
    }

    private float movementProgress = 1f;
    public float getMovementProgress() {
        return movementProgress;
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
        return newCoordinates.x < 0 || newCoordinates.y < 0 || newCoordinates.x >= battlefieldDimensions.x
                || newCoordinates.y >= battlefieldDimensions.y;
    }

    public Bullet update(TankAction action, ArrayList<OnScreenObject> obstacles, ArrayList<Tank> tanks, float deltaTime) {
        this.movementProgress = continueProgress(this.movementProgress, deltaTime, currentState.getMovementSpeed());

        if (movementProgress == 1f) {
            this.coordinates.set(destinationCoordinates);
            if (action == TankAction.WAIT) return null;

            if (action == TankAction.SHOOT) {
                if (!currentState.shootingAllowed()) return null;
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

    public Tank(String path, GridPoint2 coordinates, float initialMovementSpeed, GridPoint2 battlefieldDimensions) {
        this(path, coordinates, initialMovementSpeed, battlefieldDimensions, 5);
    }
    public Tank(String path, GridPoint2 coordinates, float initialMovementSpeed, GridPoint2 battlefieldDimensions, int maxHealth) {
        super(path, coordinates);
        setProperties(coordinates, initialMovementSpeed, battlefieldDimensions, maxHealth);
    }
    public Tank(GridPoint2 coordinates, float initialMovementSpeed, GridPoint2 battlefieldDimensions) {
        this(coordinates, initialMovementSpeed, battlefieldDimensions, 5);
    }
    public Tank(GridPoint2 coordinates, float initialMovementSpeed, GridPoint2 battlefieldDimensions, int maxHealth) {
        super(coordinates);
        setProperties(coordinates, initialMovementSpeed, battlefieldDimensions, maxHealth);
    }

    private void setProperties(GridPoint2 coordinates, float initialMovementSpeed, GridPoint2 battlefieldDimensions, int maxHealth) {
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.battlefieldDimensions = battlefieldDimensions;

        this.initialMovementSpeed = initialMovementSpeed;
        this.currentState = new HealthyState(initialMovementSpeed);

        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }
}
