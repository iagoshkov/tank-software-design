 package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.GameObject;


// Стратегия движения для добавления новых типов движения 

public interface MovementStrategy {
    boolean canMoveTo(GridPoint2 target);
    void moveTo(GridPoint2 target);
    void update(float deltaTime);
    boolean isMoving();
    GridPoint2 getCurrentDirection();
}