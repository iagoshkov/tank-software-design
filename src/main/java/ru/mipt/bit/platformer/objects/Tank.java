package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movementCommand.TankAction;
import ru.mipt.bit.platformer.objects.state.HealthyState;
import ru.mipt.bit.platformer.objects.state.TankState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank extends MovableObject {
    private static String bulletImage = "images/bullet.png";
    private boolean manuallyControlled = false;
    private float initialMovementSpeed, shootingProgress = 1f;
    private int health, maxHealth;
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
    public boolean isManuallyControlled() {
        return manuallyControlled;
    }
    public void setManuallyControlled(boolean manuallyControlled) {
        this.manuallyControlled = manuallyControlled;
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

    public Bullet update(TankAction action, Collection<OnScreenObject> obstacles, Collection<Tank> tanks, float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, currentState.getMovementSpeed());
        shootingProgress = continueProgress(shootingProgress, deltaTime, 1);

        if (movementProgress == 1f) {
            coordinates.set(destinationCoordinates);
            if (action == TankAction.WAIT) return null;

            if (action == TankAction.SHOOT) {
                float shootingInterval = currentState.getMinShootInterval();
                if (shootingInterval == 0 || shootingProgress < shootingInterval) return null;

                shootingProgress = 0;
                Bullet bullet = new Bullet(bulletImage, new GridPoint2(coordinates).add(getBulletMovementDirection()),
                        battlefieldDimensions, getBulletMovementDirection());
                return bullet;
            }

            GridPoint2 coordinatesAfterMove = new GridPoint2(coordinates).add(action.getMovement());
            if (collides(obstacles, coordinatesAfterMove) == null && collides(tanks, coordinatesAfterMove) == null &&
                    !outOfBattlefield(coordinatesAfterMove)) {
                destinationCoordinates.set(coordinatesAfterMove);
                movementProgress = 0;
            }
            setRotation(action.getMovement());
        }
        return null;
    }

    public Tank(String path, GridPoint2 coordinates, float initialMovementSpeed, GridPoint2 battlefieldDimensions) {
        this(path, coordinates, initialMovementSpeed, battlefieldDimensions, 5);
    }
    public Tank(String path, GridPoint2 coordinates, float initialMovementSpeed, GridPoint2 battlefieldDimensions, int maxHealth) {
        super(path, coordinates, battlefieldDimensions);
        setProperties(coordinates, initialMovementSpeed, battlefieldDimensions, maxHealth);
    }
    public Tank(GridPoint2 coordinates, float initialMovementSpeed, GridPoint2 battlefieldDimensions) {
        this(coordinates, initialMovementSpeed, battlefieldDimensions, 5);
    }
    public Tank(GridPoint2 coordinates, float initialMovementSpeed, GridPoint2 battlefieldDimensions, int maxHealth) {
        super(coordinates, battlefieldDimensions);
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
