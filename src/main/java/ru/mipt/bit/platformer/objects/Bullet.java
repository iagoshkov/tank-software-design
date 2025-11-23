package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.drawers.Updatable;
import ru.mipt.bit.platformer.util.Direction;

public class Bullet extends GameObject implements Updatable {
    private final Direction direction;
    private final Level level;
    private final int damage;
    private boolean active = true;
    private final ShapeRenderer shapeRenderer;
    
    private int cellsTraveled = 0;
    private static final int MAX_CELLS = 10;

    public Bullet(GridPoint2 startPosition, Direction direction, Level level, int damage, float speed) {
        super(new GridPoint2(startPosition), 1);
        this.direction = direction;
        this.level = level;
        this.damage = damage;
        
        this.shapeRenderer = new ShapeRenderer();
        this.bounds = new Rectangle();
        this.bounds.setSize(10, 10);
        
        level.placeObject(this);
        updateBounds();
    }

    @Override
    public void update(float deltaTime) {
        if (!active) return;
        
        GridPoint2 nextPosition = direction.getNextPosition(coordinates);
        
        if (checkCollisions(nextPosition)) {
            destroy();
            return;
        }
        
        if (!level.isPositionValid(nextPosition)) {
            destroy();
            return;
        }
        
        if (level.isPositionBlockedByTree(nextPosition)) {
            destroy();
            return;
        }
        
        coordinates.set(nextPosition);
        cellsTraveled++;
        updateBounds();
        
        if (cellsTraveled >= MAX_CELLS) {
            destroy();
            return;
        }
        
        checkCollisions(coordinates);
    }

    private boolean checkCollisions(GridPoint2 position) {
        for (GameObject obj : level.getGameObjects()) {
            if (obj != this && obj.isAlive()) {
                if (obj instanceof Tank) {
                    Tank tank = (Tank) obj;
                    if (tank.getCoordinates().equals(position)) {
                        tank.takeDamage(damage);
                        return true;
                    }
                } else if (obj instanceof Player) {
                    Player player = (Player) obj;
                    if (player.getCoordinates().equals(position)) {
                        player.takeDamage(damage);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void destroy() {
        if (active) {
            active = false;
            level.removeObject(this);
        }
    }

    public boolean isActive() {
        return active;
    }

    private void updateBounds() {
        level.getTileMovement().moveRectangleBetweenTileCenters(bounds, coordinates, coordinates, 1.0f);
    }

    @Override
    public void draw(Batch batch) {
        if (active) {
            batch.end();
            
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.circle(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, bounds.width / 2);
            
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, bounds.width / 4);
            
            shapeRenderer.end();
            batch.begin();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}