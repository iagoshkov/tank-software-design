package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.levels.DrawableLevel;
import ru.mipt.bit.platformer.objects.Drawable;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.TankAI;
import ru.mipt.bit.platformer.objects.Tree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TxtSaver implements FileSaver {

    private List<Character> field;
    private final DrawableLevel level;
    private final Collection<? extends Drawable> drawables;

    public TxtSaver(DrawableLevel level, Collection<? extends Drawable> drawables) {
        this.level = level;
        this.drawables = drawables;
    }

    @Override
    public void saveToFile(String fileName) {
        initField(level);
        drawObjects(level, drawables);
        try {
            saveFieldToFile(level, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawObjects(DrawableLevel level, Collection<? extends Drawable> drawables) {
        for (Drawable drawable : drawables) {
            GridPoint2 coordinates = drawable.getCoordinates();
            int index = coordinates.x + level.getWidth() * coordinates.y;
            if (drawable instanceof TankAI tankAI) {
                field.set(index, CharToDrawableConverter.getCharFromDrawable(tankAI));
            } else if (drawable instanceof Tree tree) {
                field.set(index, CharToDrawableConverter.getCharFromDrawable(tree));
            } else if (drawable instanceof Tank tank) {
                field.set(index, CharToDrawableConverter.getCharFromDrawable(tank));
            }
        }
    }

    private void initField(DrawableLevel level) {
        field = Stream.generate(() -> '_')
                .limit((long) level.getHeight() * level.getWidth())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void saveFieldToFile(DrawableLevel level, String fileName) throws IOException {
        File file = new File(fileName);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int y = level.getHeight() - 1; y > -1; y--) {
                String row = field.subList(y * level.getWidth(), (y + 1) * level.getWidth())
                                  .stream()
                                  .map(String::valueOf)
                                  .collect(Collectors.joining());
                writer.write(row);
                if (y > 0) {
                    writer.newLine();
                }
            }
        }
    }
}
