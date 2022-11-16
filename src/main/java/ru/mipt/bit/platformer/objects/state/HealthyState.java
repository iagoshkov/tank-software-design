package ru.mipt.bit.platformer.objects.state;

public class HealthyState implements TankState{
    private final float movementSpeed;
    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public boolean shootingAllowed() {
        return true;
    }

    public HealthyState(float initialMovementSpeed) {
        this.movementSpeed = initialMovementSpeed;
    }
}
