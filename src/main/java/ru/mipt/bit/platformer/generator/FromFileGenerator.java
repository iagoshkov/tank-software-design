package ru.mipt.bit.platformer.generator;

import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class FromFileGenerator implements LevelGenerator {
    private final ArrayList<GridPoint2> obstaclesCoordinates = new ArrayList<>();
    private final ArrayList<GridPoint2> playersCoordinates  = new ArrayList<>();

    public ArrayList<GridPoint2> getObstaclesCoordinates() {
        return obstaclesCoordinates;
    }

    public ArrayList<GridPoint2> getPlayersCoordinates() {
        return playersCoordinates;
    }

    public FromFileGenerator(String filePath) throws FileNotFoundException {
        createFromFile(filePath);
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
                        playersCoordinates.add(new GridPoint2(i, levelLayout.size() - j - 1));
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
