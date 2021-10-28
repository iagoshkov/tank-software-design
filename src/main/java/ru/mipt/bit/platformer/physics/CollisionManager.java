package ru.mipt.bit.platformer.physics;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Collections;
import java.util.List;

public class CollisionManager {
    private List<Obstacle> obstacles = Collections.emptyList();
    private List<Tank> tanks = Collections.emptyList();
    private final int mapHeight;
    private final int mapWidth;

    public CollisionManager(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void setTanks(List<Tank> tanks) {
        this.tanks = tanks;
    }

    private boolean isLegalMapPosition(GridPoint2 point) {
        return point.x >= 0 && point.x < mapWidth && point.y >= 0 && point.y < mapHeight;
    }

    private boolean collidesWithObstacles(GridPoint2 dest) {
        for (var obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(dest)) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesWithOtherTanks(Tank tank, GridPoint2 dest) {
        for (var otherTank : tanks) {
            if (tank == otherTank) {
                continue;
            }
            if (otherTank.getCoordinates().equals(dest) || otherTank.getDestinationCoordinates().equals(dest)) {
                return true;
            }
        }
        return false;
    }

    public boolean canMove(Tank tank, GridPoint2 dest) {
        return isLegalMapPosition(dest) && !collidesWithObstacles(dest) && !collidesWithOtherTanks(tank, dest);
    }
}
