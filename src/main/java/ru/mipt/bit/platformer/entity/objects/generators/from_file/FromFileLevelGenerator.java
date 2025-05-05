package ru.mipt.bit.platformer.entity.objects.generators.from_file;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.Obstacle;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.generators.LevelGenerator;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.LevelParser;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.readers.LevelReader;

import java.util.*;

public class FromFileLevelGenerator implements LevelGenerator {

    private final LevelReader fileReader;
    private final LevelParser fileParser;
    private List<Obstacle> obstacles;
    private List<Tank> tanks;
    private Level level;

    public FromFileLevelGenerator(LevelReader fileReader, LevelParser fileParser) {
        this.fileReader = fileReader;
        this.fileParser = fileParser;
    }
    @Override
    public void generate() {
        List<String> lines = fileReader.read();
        fileParser.parse(lines);

        level = new Level(fileParser.getHeight(), fileParser.getWidth());

        tanks = fileParser.getTanks();
        tanks.forEach(tank -> level.addGameObject(tank));

        obstacles = fileParser.getObstacles();
        obstacles.forEach(obstacle -> level.addGameObject(obstacle));
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
}
