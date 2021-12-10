package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.LogicLevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RandomGenerator implements LevelGenerator {

    private final LogicLevel logicLevel;
    private int numberOfTanks;
    private int numberOfTrees;

    public RandomGenerator(){
        logicLevel = new LogicLevel();

        getFieldSizeFromFile();
        generateRandomObjects();
    }

    private void getFieldSizeFromFile(){
        try {
            File file = new File("src/main/resources/startingSettings/fieldSize.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            String[] sizes;

            if (line != null) {
                sizes = line.split(" ");
                logicLevel.setWidth(Integer.parseInt(sizes[0]));
                logicLevel.setHeight(Integer.parseInt(sizes[1]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNumbersFromFile(){
        try {
            File file = new File("src/main/resources/startingSettings/random.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            String[] sizes;

            if (line != null) {
                sizes = line.split(" ");
                numberOfTanks = Integer.parseInt(sizes[0]);
                numberOfTrees = Integer.parseInt(sizes[1]);
            }

            if (numberOfTanks > logicLevel.getWidth() * logicLevel.getHeight() - 2){
                numberOfTanks = logicLevel.getWidth() * logicLevel.getHeight() - 2;
            }

            if (numberOfTrees + numberOfTanks > logicLevel.getWidth() * logicLevel.getHeight()){
                numberOfTrees = logicLevel.getWidth() * logicLevel.getHeight() - numberOfTanks;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GridPoint2 generateCoordinateOnField(){
        return new GridPoint2((int) (Math.random() * logicLevel.getWidth()), (int) (Math.random() * logicLevel.getHeight()));
    }

    private void generateRandomObjects() {
        getNumbersFromFile();

        List<GridPoint2> tankCoordinates = new ArrayList<>();
        List<GridPoint2> treeCoordinates = new ArrayList<>();

        tankCoordinates.add(generateCoordinateOnField());

        GridPoint2 tmpCoordinate;
        for (int i = 0; i < numberOfTanks; i++){
            tmpCoordinate = generateCoordinateOnField();
            while (tankCoordinates.contains(tmpCoordinate)){
                tmpCoordinate = generateCoordinateOnField();
            }
            tankCoordinates.add(tmpCoordinate);
        }

        for (int i = 0; i < numberOfTrees; i++){
            tmpCoordinate = generateCoordinateOnField();
            while (tankCoordinates.contains(tmpCoordinate) || treeCoordinates.contains(tmpCoordinate)){
                tmpCoordinate = generateCoordinateOnField();
            }
            treeCoordinates.add(tmpCoordinate);
        }

        logicLevel.createObjects(tankCoordinates, treeCoordinates);
    }

    @Override
    public LogicLevel getLevel() {
        return logicLevel;
    }
}
