package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

public class Wall extends GameObjectAbt implements GameObject {

    public Wall(GridPoint2 coordinates) {
        super(coordinates, 0f);
    }

}
