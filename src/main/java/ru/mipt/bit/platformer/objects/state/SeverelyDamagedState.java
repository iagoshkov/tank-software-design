package ru.mipt.bit.platformer.objects.state;

public class SeverelyDamagedState implements TankState{
    private final float movementSpeed;
    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public boolean shootingAllowed() {
        return false;
    }

    public SeverelyDamagedState(float initialMovementSpeed) {
        this.movementSpeed = 0.33f * initialMovementSpeed;
    }
}
