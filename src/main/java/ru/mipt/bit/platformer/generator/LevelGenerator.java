package ru.mipt.bit.platformer.generator;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;

public interface LevelGenerator {
        public ArrayList<GridPoint2> getObstaclesCoordinates();
        public ArrayList<GridPoint2> getPlayersCoordinates();
}
