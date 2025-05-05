package ru.mipt.bit.platformer.entity.objects.generators.from_file.readers.plaint_text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.readers.LevelReader;

public class PlaintTextLevelReader implements LevelReader {
    private final String filePath;

    public PlaintTextLevelReader(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public List<String> read() {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return lines;
    }
}
