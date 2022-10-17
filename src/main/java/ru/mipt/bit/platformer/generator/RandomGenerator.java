package ru.mipt.bit.platformer.generator;

import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class RandomGenerator implements LevelGenerator {
    private final ArrayList<GridPoint2> obstaclesCoordinates = new ArrayList<>();
    private final ArrayList<GridPoint2> playersCoordinates = new ArrayList<>();

    public ArrayList<GridPoint2> getObstaclesCoordinates() {
        return obstaclesCoordinates;
    }

    public ArrayList<GridPoint2> getPlayersCoordinates() {
        return playersCoordinates;
    }

    public RandomGenerator(int dim_x, int dim_y, int obstaclesNumber, int playersNumber) {
        for (int i = 0; i < obstaclesNumber; i++) {
            obstaclesCoordinates.add(getNewCoordinate(dim_x, dim_y, obstaclesCoordinates, playersCoordinates));
        }
        for (int i = 0; i < playersNumber; i++) {
            playersCoordinates.add(getNewCoordinate(dim_x, dim_y, obstaclesCoordinates, playersCoordinates));
        }
    }

    private GridPoint2 getNewCoordinate(int dim_x, int dim_y, ArrayList<GridPoint2> existingObstacles, ArrayList<GridPoint2> existingPlayers) {
        Random rd = new Random();

        GridPoint2 newCoordinates = new GridPoint2(rd.nextInt(dim_x), rd.nextInt(dim_y));
        while (collides(existingObstacles, newCoordinates) || collides(existingPlayers, newCoordinates))
            newCoordinates.set(rd.nextInt(dim_x), rd.nextInt(dim_y));
        return newCoordinates;
    }

    private boolean collides (ArrayList<GridPoint2> coordinates, GridPoint2 newCoordinates) {
        for (var coord : coordinates) {
            if (Objects.equals(coord, newCoordinates)) {
                return true;
            }
        }
        return false;
    }

}
