package ru.mipt.bit.platformer.generator;

import com.badlogic.gdx.math.GridPoint2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public interface LevelGenerator {
        public ArrayList<GridPoint2> getObstaclesCoordinates();
        public ArrayList<GridPoint2> getPlayersCoordinates();

        public static LevelGenerator getLevelGenerator(String levelFilePath, int dim_x, int dim_y) {
                LevelGenerator generator;
                File f = new File(levelFilePath);

                if (f.exists() && !f.isDirectory()) {
                        try {
                                generator = new FromFileGenerator(levelFilePath);
                        } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                        }
                } else {
                        Random rd = new Random();
                        generator = new RandomGenerator(dim_x, dim_y, rd.nextInt(10), rd.nextInt(3)+1);
                }
                return generator;
        }
}
