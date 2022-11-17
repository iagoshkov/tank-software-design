package ru.mipt.bit.platformer.objects.state;

public interface TankState {
    float getMovementSpeed();
    float getMinShootInterval();

    static TankState dispatch(int health, int maxHealth, float initialMovementSpeed) {
        if (health >= 0.7f * maxHealth) {
            return new HealthyState(initialMovementSpeed);
        } else if (0.15f * maxHealth <= health) {
            return new LightlyDamagedState(initialMovementSpeed);
        } else {
            return new SeverelyDamagedState(initialMovementSpeed);
        }
    }
}
