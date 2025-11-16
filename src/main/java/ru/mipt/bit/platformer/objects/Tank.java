package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.drawers.Updatable;
import ru.mipt.bit.platformer.util.Direction;

import java.util.Random;

public class Tank extends GameObject implements Updatable {
    private final float movementSpeed;
    private final TextureRegion graphics;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1.0f;
    private Level level;
    private boolean isPlayerControlled;
    private GridPoint2 previousCoordinates;
    private final int bulletDamage = 25;

    public Tank(PlayerConfig config, Level level, boolean isPlayerControlled) {
        super(config.getInitialPosition(), generateRandomHealth());
        this.level = level;
        this.movementSpeed = config.getMovementSpeed();
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.graphics = new TextureRegion(new Texture(config.getTexturePath()));
        this.bounds = ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle(graphics);
        this.isPlayerControlled = isPlayerControlled;
        this.previousCoordinates = new GridPoint2(coordinates);
        
        level.placeObject(this);
        level.reservePosition(coordinates, this);
    }

    private static int generateRandomHealth() {
        Random random = new Random();
        return random.nextInt(21) + 80;
    }

    public void move(Direction direction) {
        if (isMoving()) return;
        
        GridPoint2 nextPosition = direction.getNextPosition(coordinates);
        
        if (!level.isPositionValid(nextPosition)) return;
        if (level.isPositionOccupied(nextPosition)) return;
        if (level.isPositionBlockedByTree(nextPosition)) return;
        
        previousCoordinates.set(coordinates);
        level.reservePosition(nextPosition, this);
        
        destinationCoordinates.set(nextPosition);
        movementProgress = 0f;
        rotation = direction.getRotation();
    }

    public void shoot() {
        GridPoint2 bulletPosition = getBulletStartPosition();
        if (level.isPositionValid(bulletPosition) && !level.isPositionBlockedByTree(bulletPosition)) {
            new Bullet(bulletPosition, Direction.fromRotation(rotation), level, bulletDamage, 0f);
        }
    }

    private GridPoint2 getBulletStartPosition() {
        return Direction.fromRotation(rotation).getNextPosition(coordinates);
    }

    public void takeDamage(int damage) {
        int newHealth = getHealth() - damage;
        setHealth(newHealth);
        
        if (!isAlive()) {
            level.removeObject(this);
            level.freePosition(coordinates);
            if (!coordinates.equals(previousCoordinates)) {
                level.freePosition(previousCoordinates);
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        if (isMoving()) {
            movementProgress = ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
            
            level.getTileMovement().moveRectangleBetweenTileCenters(
                bounds, coordinates, destinationCoordinates, movementProgress
            );
            
            if (movementProgress >= 1f) {
                level.freePosition(previousCoordinates);
                coordinates.set(destinationCoordinates);
                movementProgress = 1f;
                level.placeObject(this);
            }
        }
    }

    public boolean isMoving() {
        return movementProgress < 1f;
    }

    public boolean isPlayerControlled() {
        return isPlayerControlled;
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    @Override
    public void draw(Batch batch) {
        if (isAlive()) {
            ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled(batch, graphics, bounds, rotation);
        }
    }

    @Override
    public void dispose() {
        level.freePosition(coordinates);
        if (!coordinates.equals(previousCoordinates)) {
            level.freePosition(previousCoordinates);
        }
        graphics.getTexture().dispose();
    }
}