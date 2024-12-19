package ru.mipt.bit.platformer.entity.tree;

import com.badlogic.gdx.math.GridPoint2;


public class Tree {

    private GridPoint2 coordinates;

    public Tree(GridPoint2 point) {
            this.coordinates = point;
        }

        public GridPoint2 getCoordinates() {
            return coordinates;
        }
    }