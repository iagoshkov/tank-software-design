package ru.mipt.bit.platformer.Builder;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Obstacles.IObstacleInLevel;
import ru.mipt.bit.platformer.Obstacles.ObstacleInLevel;
import ru.mipt.bit.platformer.Entities.TextureObstacle;

public class ObstacleLevelBuilder implements IObstacleLevelBuilder {
    private IObstacleInLevel obstacle;

    public ObstacleLevelBuilder(){
        this.obstacle = new ObstacleInLevel();
    }

    @Override
    public IObstacleInLevel createBush() {
        IObstacleInLevel result = this.obstacle;
        this.clear();
        return result;
    }

    @Override
    public IObstacleLevelBuilder addBush(GridPoint2 bushPosition) {
        TextureObstacle textureObstacle = new TextureObstacle("images/greenTree.png", bushPosition);
        this.obstacle.addObstacle(textureObstacle);
        return this;
    }

    @Override
    public void clear() {
        this.obstacle = new ObstacleInLevel();
    }
}
