package ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.plaint_text;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.Obstacle;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.LevelParser;

import java.util.ArrayList;
import java.util.List;

public class PlainTextLevelParser implements LevelParser {

    private final List<Tank> tanks = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();
    private Integer height;
    private Integer width;

    @Override
    public void parse(List<String> lines) {
        width = lines.size();
        height = lines.get(0).length();

        tanks.clear();
        obstacles.clear();
        int xCoordinate, yCoordinate = lines.size();
        for (String line : lines) {
            xCoordinate = 0;
            for (char c : line.toCharArray()) {
                xCoordinate++;
                switch (c) {
                    case '_':
                        continue;
                    case 'T':
                        obstacles.add(new Obstacle(new GridPoint2(xCoordinate, yCoordinate)));
                        break;
                    case 'X':
                        tanks.add(new Tank(new GridPoint2(xCoordinate, yCoordinate), 100));
                        break;
                }
            }
            yCoordinate--;
        }
    }

    @Override
    public List<Tank> getTanks() {
        return tanks;
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public Integer getWidth() {
        return width;
    }
}
