package ru.mipt.bit.platformer.driver;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.observation.Event;
import ru.mipt.bit.platformer.driver.observation.Observer;
import ru.mipt.bit.platformer.objects.gameObjects.Bullet;
import ru.mipt.bit.platformer.objects.gameObjects.GameObject;
import ru.mipt.bit.platformer.objects.gameObjects.Tank;
import ru.mipt.bit.platformer.objects.gameObjects.Tree;

import java.util.ArrayList;
import java.util.List;

public class CollisionChecker implements Observer {

    private final List<Tank> tanks;
    private final List<Tree> trees;
    private final List<Bullet> bullets;

    private int height;
    private int width;

    public CollisionChecker() {
        tanks = new ArrayList<>();
        trees = new ArrayList<>();
        bullets = new ArrayList<>();
        height = 0;
        width = 0;
    }

    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    public void addTree(Tree tree) {
        trees.add(tree);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean noCollisionsForTank(GridPoint2 newCoordinates, Tank tankToMove) {
        return  noCollisionTankWithTank(newCoordinates, tankToMove) &&
                noCollisionTreeWithTank(newCoordinates, tankToMove) &&
                noCollisionWithWall(newCoordinates);
    }

    private boolean noCollisionWithWall(GridPoint2 newCoordinates) {
        return newCoordinates.x >= 0 && newCoordinates.x < width &&
                newCoordinates.y >= 0 && newCoordinates.y < height;
    }

    private boolean noCollisionTreeWithTank(GridPoint2 newCoordinates, Tank tankToMove) {
        for (Tree tree : trees) {
            if (!tankToMove.isMovementPossible(tree.getCoordinates(), newCoordinates))
                return false;
        }
        return true;
    }

    private boolean noCollisionTankWithTank(GridPoint2 newCoordinates, Tank tankToMove) {
        for (Tank tank : tanks) {
            if (tank.equals(tankToMove)) {
                continue;
            }
            if     (!tankToMove.isMovementPossible(tank.getCoordinates(), newCoordinates) ||
                    !tankToMove.isMovementPossible(tank.getDestinationCoordinates(), newCoordinates))

                return false;
        }
        return true;
    }


    public boolean noCollisionsForBullet(GridPoint2 newCoordinates, Bullet bullet) {
        if (!noCollisionWithWall(newCoordinates)) {
            bullet.setNotExistent();
            return false;
        }

        return  noCollisionBulletWithBullet(newCoordinates, bullet) &&
                noCollisionBulletWithTank(newCoordinates, bullet) &&
                noCollisionBulletWithTree(newCoordinates, bullet);
    }

    private boolean noCollisionBulletWithBullet(GridPoint2 newCoordinates, Bullet bulletToMove) {
        for (Bullet bullet : bullets) {
            if (bullet.equals(bulletToMove)) {
                continue;
            }
            if (!bulletToMove.isMovementPossible(bullet.getCoordinates(), newCoordinates) ||
                    !bulletToMove.isMovementPossible(bullet.getDestinationCoordinates(), newCoordinates)) {
                bullet.setNotExistent();
                bulletToMove.setNotExistent();
                return false;
            }
        }
        return true;
    }

    private boolean noCollisionBulletWithTank(GridPoint2 newCoordinates, Bullet bullet) {
        for (Tank tank : tanks) {
            if (!tank.equals(bullet.getTank()) && (!bullet.isMovementPossible(tank.getCoordinates(), newCoordinates) ||
                    !bullet.isMovementPossible(tank.getDestinationCoordinates(), newCoordinates)) ) {

                bullet.setNotExistent();

                tank.takeDamage(bullet);
                return false;
            }
        }
        return true;
    }

    private boolean noCollisionBulletWithTree(GridPoint2 newCoordinates, Bullet bullet) {

        for (Tree tree : trees) {
            if (!bullet.isMovementPossible(tree.getCoordinates(), newCoordinates)) {
                bullet.setNotExistent();
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(Event event, GameObject gameObject, int id) {
        switch(event) {
            case RemoveTank:
                tanks.remove((Tank) gameObject);
                break;
            case RemoveBullet:
                bullets.remove((Bullet) gameObject);
                break;
            case AddBullet:
                bullets.add((Bullet) gameObject);
                break;
        }
    }
}
