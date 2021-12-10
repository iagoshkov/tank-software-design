package ru.mipt.bit.platformer.driver;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.observation.Event;
import ru.mipt.bit.platformer.driver.observation.Observable;
import ru.mipt.bit.platformer.driver.observation.Observer;
import ru.mipt.bit.platformer.graphics.LevelRenderer;
import ru.mipt.bit.platformer.objects.gameObjects.Bullet;
import ru.mipt.bit.platformer.objects.gameObjects.GameObject;
import ru.mipt.bit.platformer.objects.gameObjects.Tank;
import ru.mipt.bit.platformer.objects.gameObjects.Tree;

import java.util.ArrayList;
import java.util.List;

public class LogicLevel implements Observable {

    public LevelRenderer levelRenderer;

    private final List<Tank> tanks;
    private final List<Tree> trees;
    private final List<Bullet> bullets;

    private int height;
    private int width;

    private final CollisionChecker collisionChecker;

    public LogicLevel(){
        tanks = new ArrayList<>();
        trees = new ArrayList<>();
        bullets = new ArrayList<>();
        height = 0;
        width = 0;
        collisionChecker = new CollisionChecker();
    }

    public void setHeight(int height) {
        this.height = height;
        collisionChecker.setHeight(height);
    }

    public void setWidth(int width) {
        this.width = width;
        collisionChecker.setWidth(width);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        notifyObservers(Event.AddBullet, bullet);
    }

    public void createObjects(List<GridPoint2> tankCoordinates, List<GridPoint2> treeCoordinates){
        createTanks(tankCoordinates);
        createTrees(treeCoordinates);

        levelRenderer = new LevelRenderer(tanks, trees);
    }

    public void createTanks(List<GridPoint2> tankCoordinates) {
        for (GridPoint2 coordinate : tankCoordinates) {
            Tank tank = new Tank(coordinate, collisionChecker);
            tanks.add(tank);
            collisionChecker.addTank(tank);
        }
    }

    public void createTrees(List<GridPoint2> treeCoordinates) {
        for (GridPoint2 coordinate : treeCoordinates) {
            Tree tree = new Tree(coordinate);
            trees.add(tree);
            collisionChecker.addTree(tree);
        }
    }

    public void moveTanks() {
        for (Tank tank : tanks) {
            tank.moveCommand();
        }
    }

    public void moveBullets() {
        for (Bullet bullet : bullets) {
            bullet.move();
        }
    }

    public void checkObjects() {
        checkTanks();
        checkBullets();
    }

    public void checkTanks() {
        ArrayList <Tank> tanksCopy = new ArrayList<>(tanks);
        for (Tank tank : tanksCopy) {
            if (!tank.isAlive()) {
                notifyObservers(Event.RemoveTank, tank);
                tanks.remove(tank);
            }
        }
    }

    public void checkBullets() {
        ArrayList<Bullet> bulletsCopy = new ArrayList<>(bullets);
        for (Bullet bullet : bulletsCopy) {
            if (!bullet.isExistent()) {
                notifyObservers(Event.RemoveBullet, bullet);
                bullets.remove(bullet);
            }
        }
    }

    @Override
    public void addObserver(Observer o) {

    }

    @Override
    public void removeObserver(Observer o) {

    }

    @Override
    public void notifyObservers(Event event, GameObject object) {
        int id = 0;
        if (object instanceof Tank) {
            id = tanks.indexOf((Tank) object);
        }
        if (object instanceof Tree) {
            id = trees.indexOf((Tree) object);
        }
        if (object instanceof Bullet) {
            id = bullets.indexOf((Bullet) object);
        }

        levelRenderer.update(event, object, id);
        collisionChecker.update(event, object, id);
    }
}
