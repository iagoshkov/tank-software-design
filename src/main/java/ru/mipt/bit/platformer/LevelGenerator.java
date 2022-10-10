package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.OnScreenObject;

import java.io.*;
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

        GridPoint2 new_coords = new GridPoint2(rd.nextInt(dimensions[0]), rd.nextInt(dimensions[1]));
        while (collides(compareTo, new_coords))
            new_coords = new GridPoint2(rd.nextInt(dimensions[0]), rd.nextInt(dimensions[1]));
        return new_coords;
    }

    private boolean collides (ArrayList<GridPoint2> coordinates, GridPoint2 new_coordinates) {
        boolean collides = false;
        for (var coord : coordinates) {
            if (Objects.equals(coord, new_coordinates)) {
                collides = true;
                break;
            }
        }
        return collides;
    }


    private void createFromFile(String filePath) throws FileNotFoundException {
        ArrayList<String> levelLayout = new ArrayList<>();
        readFile(levelLayout, filePath);

        int[] dimensions = new int[]{levelLayout.get(0).length(), levelLayout.size()};

        for (int i = 0; i < dimensions[0]; ++i) {
            for (int j = 0; j < dimensions[1]; ++j) {
                switch (levelLayout.get(j).charAt(i)) {
                    case ('T'):
                        obstaclesCoordinates.add(new GridPoint2(i, j));
                        break;
                    case ('X'):
                        playerCoordinates = new GridPoint2(i, j);
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
