package ru.mipt.bit.platformer.Builder;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Obstacles.IObstacleInLevel;

public interface IObstacleLevelBuilder {
    IObstacleLevelBuilder addBush(GridPoint2 position);
    IObstacleInLevel createBush();
    void clear();
}
