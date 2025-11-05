package ru.mipt.bit.platformer.collision;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;

public class SimpleCollisionDetector implements CollisionDetector {
    private final List<GameObject> obstacles = new ArrayList<>();
    private final List<Tank> movingTanks = new ArrayList<>();
    private final LevelBoundsChecker boundsChecker;
    
    public SimpleCollisionDetector(TiledMapTileLayer groundLayer) {
        this.boundsChecker = new LevelBoundsChecker(groundLayer);
    }
    
    @Override
    public boolean isPositionBlocked(GridPoint2 position) {
        // Проверяем границы уровня
        if (!boundsChecker.isWithinBounds(position)) {
            return true;
        }
        
        // Проверяем статические препятствия
        boolean staticObstacle = obstacles.stream()
            .anyMatch(obstacle -> obstacle.getCoordinates().equals(position));
        if (staticObstacle) {
            return true;
        }
        
        // Проверяем движущиеся танки
        for (Tank tank : movingTanks) {
            GridPoint2 currentPos = tank.getCoordinates();
            
            // Если танк движется - занимает 2 клетки
            if (tank.isMoving()) {
                // Текущая позиция танка
                if (currentPos.equals(position)) {
                    return true;
                }
                
                // Следующая позиция танка согласно неправлению 
                GridPoint2 nextPos = getNextPosition(tank);
                if (nextPos != null && nextPos.equals(position)) {
                    return true;
                }
            } else {
                // Если танк не движется, занимает только свою клетку
                if (currentPos.equals(position)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public boolean wouldCollide(GridPoint2 from, GridPoint2 to, GameObject movingObject) {
        return isPositionBlocked(to);
    }
    
    //метод для определения следующей позиции танка
    private GridPoint2 getNextPosition(Tank tank) {
        if (tank instanceof Tank) {
            return ((Tank) tank).getDestinationCoordinates();
        }
        return null;
    }
    
    @Override
    public void addObstacle(GameObject obstacle) {
        obstacles.add(obstacle);
    }
    
    @Override
    public void removeObstacle(GameObject obstacle) {
        obstacles.remove(obstacle);
    }
    
    public void addMovingTank(Tank tank) {
        movingTanks.add(tank);
    }
    
    public void removeMovingTank(Tank tank) {
        movingTanks.remove(tank);
    }
    
    public List<GameObject> getObstacles() {
        return new ArrayList<>(obstacles);
    }
    
    public List<Tank> getMovingTanks() {
        return new ArrayList<>(movingTanks);
    }
}