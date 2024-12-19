package ru.mipt.bit.platformer.entity.tank;

import com.badlogic.gdx.math.GridPoint2;

    public enum Direction {
        RIGHT(0f, new GridPoint2(1, 0)),
        LEFT(-180f, new GridPoint2(-1, 0)),
        UP(90f, new GridPoint2(0, 1)),
        DOWN(-90f, new GridPoint2(0, -1)),
        NULL(0, new GridPoint2(0, 0));

        private float directionRotation;
        private GridPoint2 directionPoint;

            Direction(float directionRotation, GridPoint2 directionPoint) {
                this.directionRotation = directionRotation;
                this.directionPoint = directionPoint;
            }

            public float getDirectionRotation() {
                return directionRotation;
            }

            public GridPoint2 getDirectionPoint() {
                return directionPoint;
            }
        }
