package ru.mipt.bit.platformer.collision;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.GameObject;

import java.util.List;

// Абстракция для обнаружения коллизий

public interface CollisionDetector {
    boolean isPositionBlocked(GridPoint2 position);
    boolean wouldCollide(GridPoint2 from, GridPoint2 to, GameObject movingObject);
    void addObstacle(GameObject obstacle);
    void removeObstacle(GameObject obstacle);
}