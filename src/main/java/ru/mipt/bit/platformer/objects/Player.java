package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.drawers.Updatable;
import ru.mipt.bit.platformer.util.Direction;

public class Player extends GameObject implements Updatable {
    private final float movementSpeed;
    private final TextureRegion graphics;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1.0f;
    private Level level;

    public Player(PlayerConfig config, Level level) {
        super(config.getInitialPosition());
        this.level = level;
        this.movementSpeed = config.getMovementSpeed();
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.graphics = new TextureRegion(new Texture(config.getTexturePath()));
        this.bounds = ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle(graphics);
        
        level.placeObject(this);
    }

    public void move(Direction direction) {
        if (isMoving()) return;
        
        GridPoint2 nextPosition = direction.getNextPosition(coordinates);
        
        // Проверяем границы уровня
        if (!level.isPositionValid(nextPosition)) {
            return;
        }
        
        // Проверяем, что позиция не занята танком
        if (level.isPositionOccupied(nextPosition)) {
            return;
        }
        
        // Проверяем, что на позиции нет дерева
        if (level.isPositionBlockedByTree(nextPosition)) {
            return;
        }
        
        destinationCoordinates.set(nextPosition);
        movementProgress = 0f;
        rotation = direction.getRotation();
    }

    @Override
    public void update(float deltaTime) {
        if (isMoving()) {
            movementProgress = ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
            
            level.getTileMovement().moveRectangleBetweenTileCenters(
                bounds, coordinates, destinationCoordinates, movementProgress
            );
            
            if (movementProgress >= 1f) {
                coordinates.set(destinationCoordinates);
                movementProgress = 1f;
                level.placeObject(this);
            }
        }
    }

    public boolean isMoving() {
        return movementProgress < 1f;
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    @Override
    public void draw(Batch batch) {
        ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, bounds, rotation);
    }

    @Override
    public void dispose() {
        graphics.getTexture().dispose();
    }
}