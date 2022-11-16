package ru.mipt.bit.platformer.objects.state;

public class LightlyDamagedState implements TankState{
    private final float movementSpeed;
    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public boolean shootingAllowed() {
        return true;
    }

    public LightlyDamagedState(float initialMovementSpeed) {
        this.movementSpeed = 0.5f * initialMovementSpeed;
    }
}
