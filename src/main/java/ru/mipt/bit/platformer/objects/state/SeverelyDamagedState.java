package ru.mipt.bit.platformer.objects.state;

public class SeverelyDamagedState implements TankState{
    private final float movementSpeed;
    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public float getMinShootInterval() {
        return 0;
    }

    public SeverelyDamagedState(float initialMovementSpeed) {
        this.movementSpeed = initialMovementSpeed * 3;
    }
}
