package ru.mipt.bit.platformer.entity.objects.generators;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.Obstacle;
import ru.mipt.bit.platformer.entity.objects.Tank;

import java.util.List;

public interface LevelGenerator {
    void generate();
    Level getLevel();
    Tank getPlayerTank();
    List<Tank> getAITanks();
    List<Obstacle> getObstacles();
}
