package ru.mipt.bit.platformer.objects.state;

public class HealthyState implements TankState{
    private final float movementSpeed;
    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public float getMinShootInterval() {
        return 0.5f;
    }

    public HealthyState(float initialMovementSpeed) {
        this.movementSpeed = initialMovementSpeed;
    }
}
