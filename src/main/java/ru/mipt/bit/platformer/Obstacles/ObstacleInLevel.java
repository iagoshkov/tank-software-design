package ru.mipt.bit.platformer.Obstacles;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Entities.TextureObstacle;

import java.util.HashSet;

public class ObstacleInLevel implements IObstacleInLevel {
    private final HashSet<TextureObstacle> obstacleEntities;
    private final HashSet<GridPoint2> obstaclePositions;

    public ObstacleInLevel(){
        this.obstacleEntities = new HashSet<>();
        this.obstaclePositions = new HashSet<>();
    }

    public void addObstacle(TextureObstacle entity) {
        obstaclePositions.add(entity.position);
        obstacleEntities.add(entity);
    }

    public void removeObstacle(TextureObstacle entity) {
        obstaclePositions.remove(entity.position);
        obstacleEntities.remove(entity);
    }

    public HashSet<GridPoint2> getObstaclePositions(){
        return this.obstaclePositions;
    }

    public HashSet<TextureObstacle> getObstacleEntities(){
        return this.obstacleEntities;
    }
}
