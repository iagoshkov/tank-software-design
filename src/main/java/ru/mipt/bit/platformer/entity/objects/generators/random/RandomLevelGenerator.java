package ru.mipt.bit.platformer.entity.objects.generators.random;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.Obstacle;
import ru.mipt.bit.platformer.entity.objects.generators.LevelGenerator;

import java.util.*;

public class RandomLevelGenerator implements LevelGenerator {

    private final int height;
    private final int width;
    private final Set<GridPoint2> points = new HashSet<>();
    private List<Obstacle> obstacles;
    private List<Tank> tanks;
    private Level level;

    public RandomLevelGenerator(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public void generate() {
        points.clear();
        Random random = new Random();

        level = new Level(height, width);
        generateObstacles(5, random);
        generateTanks(3, random);
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public Tank getPlayerTank() {
        return tanks.get(0);
    }

    @Override
    public List<Tank> getAITanks() {
        return tanks.subList(1, tanks.size());
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    private GridPoint2 getRandomPoint(Random random) {
        return new GridPoint2(random.nextInt(width - 1), random.nextInt(height - 1));
    }

    private boolean checkExistPoint(GridPoint2 point) {
        return points.contains(point);
    }

    private GridPoint2 getPoint(Random random) {
        GridPoint2 point = getRandomPoint(random);
        while (checkExistPoint(point)) {
            point = getRandomPoint(random);
        }
        points.add(point);
        return point;
    }

    public void generateObstacles(int countObjects, Random random) {
        obstacles = new ArrayList<>();

        for (int i = 0; i < countObjects; i++) {
            GridPoint2 point = getPoint(random);
            Obstacle object = new Obstacle(point);
            obstacles.add(object);
            level.addGameObject(object);
        }
    }

    public void generateTanks(int countObjects, Random random) {
        tanks = new ArrayList<>();

        for (int i = 0; i < countObjects; i++) {
            GridPoint2 point = getPoint(random);
            Tank tank = new Tank(point, 100);
            tanks.add(tank);
            level.addGameObject(tank);
        }
    }
}
