package ru.mipt.bit.platformer.Entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Render.Visualisation;

public class TextureObstacle {
    public GameObjectEntity obstacleEntity;
    public Visualisation obstacleVisualisation;
    public GridPoint2 position;
    public TextureObstacle(String texturePath, GridPoint2 position) {
        obstacleVisualisation = new Visualisation(texturePath);
        this.position = position;
        obstacleEntity = new GameObjectEntity(this.position);
    }
}
