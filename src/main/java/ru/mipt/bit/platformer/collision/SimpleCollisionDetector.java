package ru.mipt.bit.platformer.collision;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.GameObject;

import java.util.ArrayList;
import java.util.List;


public class SimpleCollisionDetector implements CollisionDetector {
    private final List<GameObject> obstacles = new ArrayList<>();
    
    @Override
    public boolean isPositionBlocked(GridPoint2 position) {
        return obstacles.stream()
            .anyMatch(obstacle -> obstacle.getCoordinates().equals(position));
    }
    
    @Override
    public boolean wouldCollide(GridPoint2 from, GridPoint2 to, GameObject movingObject) {
        return isPositionBlocked(to);
    }
    
    @Override
    public void addObstacle(GameObject obstacle) {
        obstacles.add(obstacle);
    }
    
    @Override
    public void removeObstacle(GameObject obstacle) {
        obstacles.remove(obstacle);
    }
}