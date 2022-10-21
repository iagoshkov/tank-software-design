package ru.mipt.bit.platformer.Obstacles;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Entities.TextureObstacle;

import java.util.HashSet;

public interface IObstacleInLevel {
    void addObstacle(TextureObstacle obstacle);
    void removeObstacle(TextureObstacle obstacle);

    HashSet<GridPoint2> getObstaclePositions();
    HashSet<TextureObstacle> getObstacleEntities();
}
