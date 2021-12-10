package ru.mipt.bit.platformer.control.commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.gameObjects.Bullet;
import ru.mipt.bit.platformer.objects.Direction;
import ru.mipt.bit.platformer.driver.LogicLevel;
import ru.mipt.bit.platformer.objects.gameObjects.Tank;

import java.util.Date;

public class ShootCommand implements Command{

    private final Tank tank;
    private final LogicLevel logicLevel;

    public ShootCommand(Tank tank, LogicLevel logicLevel){
        this.tank = tank;
        this.logicLevel = logicLevel;
    }

    @Override
    public void execute(){
        tank.shoot();
        if (tank.canShoot()) {

            GridPoint2 bulletCoords = getNextCoords();
            Bullet bullet = new Bullet(bulletCoords, tank.getRotation(), tank.getCollisionChecker(), tank);

            if (bullet.noCollisions()) {
                logicLevel.addBullet(bullet);
            }
        }
        tank.setLastTimeShooting(new Date().getTime());
    }

    private GridPoint2 getNextCoords() {
        GridPoint2 destCoords;

        GridPoint2 coords = tank.getCoordinates();
        float rotation = tank.getRotation();

        if (rotation == Direction.UP.rotation)
            destCoords = coords.add(Direction.UP.vector);
        else if (rotation == Direction.RIGHT.rotation)
            destCoords = coords.add(Direction.RIGHT.vector);
        else if (rotation == Direction.DOWN.rotation)
            destCoords = coords.add(Direction.DOWN.vector);
        else
            destCoords = coords.add(Direction.LEFT.vector);

        return destCoords;
    }
}
