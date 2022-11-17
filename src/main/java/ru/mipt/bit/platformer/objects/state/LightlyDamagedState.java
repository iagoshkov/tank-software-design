package ru.mipt.bit.platformer.objects.state;

public class LightlyDamagedState implements TankState{
    private final float movementSpeed;
    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public float getMinShootInterval() {
        return 0.5f;
    }

    public LightlyDamagedState(float initialMovementSpeed) {
        this.movementSpeed = initialMovementSpeed * 2;
    }
}
