package ru.mipt.bit.platformer;

import java.util.List;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.objects.Tree;


import ru.mipt.bit.platformer.objects.Tank;

public class CollisionManager {
    private List<Tree> trees;
    //private List<Tank> tanks;

    public CollisionManager(List<Tree> trees) {
        this.trees = trees;
    }

    public boolean canMoveTank(Tank tank, int dx, int dy) {
        // Получаем текущие координаты танка на сетке
        GridPoint2 currentTankCoordinates = tank.getTankCoordinates();
        
        // Вычисляем, на какую клетку танк собирается переместиться
        GridPoint2 destinationCoordinates = new GridPoint2(
            currentTankCoordinates.x + dx,
            currentTankCoordinates.y + dy
        );

        // Проверяем, совпадает ли целевая клетка с клеткой какого-либо дерева
        for (Tree tree : trees) {
            if (tree.getCoordinates().equals(destinationCoordinates)) {
                return false;
            }
        }
        
        return true; // Путь свободен
    }
}



