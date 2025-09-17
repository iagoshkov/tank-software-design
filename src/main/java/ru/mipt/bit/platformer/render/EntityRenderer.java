package ru.mipt.bit.platformer.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Player;
import ru.mipt.bit.platformer.model.World;
import ru.mipt.bit.platformer.util.TileMovement;

public class EntityRenderer {

    private final PlayerRenderer playerRenderer;
    private final ObstacleRenderer obstacleRenderer;
    private final TileMovement tileMovement;

    public EntityRenderer(TileMovement tileMovement,
                          PlayerRenderer playerRenderer,
                          ObstacleRenderer obstacleRenderer) {
        this.tileMovement = tileMovement;
        this.playerRenderer = playerRenderer;
        this.obstacleRenderer = obstacleRenderer;
    }

    public void render(World world, Batch batch) {
        batch.begin();
        Player player = world.getPlayer();
        playerRenderer.render(player, batch, tileMovement);

        for (GridPoint2 obstaclePos : world.getObstacles()) {
            obstacleRenderer.render(obstaclePos, batch, world.getTileGrid());
        }

        batch.end();
    }
}
