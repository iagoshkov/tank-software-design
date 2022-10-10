package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class LevelGenerator {
    private final ArrayList<GridPoint2> obstaclesCoordinates = new ArrayList<>();
    private GridPoint2 playerCoordinates;

    public ArrayList<GridPoint2> getObstaclesCoordinates() {
        return obstaclesCoordinates;
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public LevelGenerator(String filePath) throws FileNotFoundException {
        createFromFile(filePath);
    }

    public LevelGenerator(int[] dimensions, int obstaclesNumber) {
        for (int i = 0; i < obstaclesNumber; i++) {
            obstaclesCoordinates.add(getNewCoordinate(dimensions, obstaclesCoordinates));
        }
        playerCoordinates = getNewCoordinate(dimensions, obstaclesCoordinates);
    }

    private GridPoint2 getNewCoordinate(int[] dimensions, ArrayList<GridPoint2> compareTo) {
        Random rd = new Random();

        GridPoint2 newCoordinates = new GridPoint2(rd.nextInt(dimensions[0]), rd.nextInt(dimensions[1]));
        while (collides(compareTo, newCoordinates))
            newCoordinates.set(rd.nextInt(dimensions[0]), rd.nextInt(dimensions[1]));
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


    private void createFromFile(String filePath) throws FileNotFoundException {
        ArrayList<String> levelLayout = new ArrayList<>();
        readFile(levelLayout, filePath);

        int[] dimensions = new int[]{levelLayout.get(0).length(), levelLayout.size()};

        for (int i = 0; i < dimensions[0]; ++i) {
            for (int j = 0; j < dimensions[1]; ++j) {
                switch (levelLayout.get(j).charAt(i)) {
                    case ('T'):
                        obstaclesCoordinates.add(new GridPoint2(i, levelLayout.size() - j - 1));
                        break;
                    case ('X'):
                        playerCoordinates = new GridPoint2(i, levelLayout.size() - j - 1);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static void readFile(ArrayList<String> levelLayout, String filePath) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                levelLayout.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
