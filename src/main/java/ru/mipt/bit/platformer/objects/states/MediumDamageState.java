package ru.mipt.bit.platformer.objects.states;

import ru.mipt.bit.platformer.objects.gameObjects.Tank;

import java.util.Date;

public class MediumDamageState implements State {
    private final Tank tank;

    public MediumDamageState(Tank tank) {
        this.tank = tank;
        this.tank.setMovementSpeed(tank.getMovementSpeed() * 2f);
    }

    @Override
    public boolean canShoot() {
        long time = new Date().getTime();
        long delta = time - tank.getLastTimeShooting();
        if (delta < 500)
            return false;
        tank.setLastTimeShooting(time);
        return true;
    }
}
