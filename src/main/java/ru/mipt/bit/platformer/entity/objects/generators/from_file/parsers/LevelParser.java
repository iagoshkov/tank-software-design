package ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers;

import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.Obstacle;

import java.util.List;

public interface LevelParser {
    void parse(List<String> lines);
    List<Tank> getTanks();
    List<Obstacle> getObstacles();
    Integer getHeight();
    Integer getWidth();
}
