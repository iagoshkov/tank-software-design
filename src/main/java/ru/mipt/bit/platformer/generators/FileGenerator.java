package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.LogicLevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileGenerator implements LevelGenerator {

    private final LogicLevel logicLevel;

    public FileGenerator(){
        logicLevel = new LogicLevel();

        getFieldSizeFromFile();
        generateLevelFromFile();
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

    public void generateLevelFromFile(){
        try {
            File file = new File("src/main/resources/startingSettings/level.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            List<String> lines = new ArrayList<>();
            String oneLine = reader.readLine();

            while (oneLine != null) {
                lines.add(0, oneLine);
                oneLine = reader.readLine();
            }

            List<GridPoint2> tankCoordinates = new ArrayList<>(FileParser.calculateTankCoordinates(lines));
            List<GridPoint2> treeCoordinates = new ArrayList<>(FileParser.calculateTreeCoordinates(lines));

            logicLevel.createObjects(tankCoordinates, treeCoordinates);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LogicLevel getLevel() {
        return logicLevel;
    }
}
