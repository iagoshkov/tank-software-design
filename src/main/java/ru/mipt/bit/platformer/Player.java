package ru.mipt.bit.platformer;

import static com.badlogic.gdx.math.MathUtils.isEqual;

import ru.mipt.bit.platformer.objects.Tank;

public class Player {
    private Tank tank;
    private CollisionManager collisionManager;


    public Player(Tank tank, CollisionManager collisionManager) {
        this.tank = tank;
        this.collisionManager = collisionManager;
    }

    public void move() {
        for (Directions dir : Directions.values()) {
            if (dir.isPressed() && isEqual(tank.getTankMovementProggress(), 1f)) {
                // проверка столкновений
                if (collisionManager.canMoveTank(tank, dir.dx , dir.dy)) {
                    tank.move(dir.dx, dir.dy);
                    tank.setTankMovementProggress(0f);
                }
                tank.setRotation(dir.rotation);
            }
        }
    }
}
