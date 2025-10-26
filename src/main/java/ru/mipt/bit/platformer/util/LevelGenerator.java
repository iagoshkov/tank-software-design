package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGenerator {
    private final int width;
    private final int height;
    private final Random random;

    public LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.random = new Random();
    }

    /**
     * Генерирует случайный уровень с деревьями и случайной позицией танка.
     * @param treeCount количество деревьев
     * @return объект LevelData с позициями танка и деревьев
     */
    public LevelData generateRandomLevel(int treeCount) {
        List<GridPoint2> treePositions = new ArrayList<>();
        GridPoint2 tankPosition;

        // Генерируем деревья
        for (int i = 0; i < treeCount; i++) {
            GridPoint2 pos;
            do {
                pos = new GridPoint2(random.nextInt(width), random.nextInt(height));
            } while (treePositions.contains(pos)); // Избегаем дубликатов
            treePositions.add(pos);
        }

        // Генерируем позицию танка, не занятую деревьями
        do {
            tankPosition = new GridPoint2(random.nextInt(width), random.nextInt(height));
        } while (treePositions.contains(tankPosition));

        return new LevelData(tankPosition, treePositions);
    }

    /**
     * Загружает уровень из файла.
     * @param filePath путь к файлу
     * @return объект LevelData с позициями танка и деревьев
     * @throws IOException если файл не найден или ошибка чтения
     * @throws IllegalArgumentException если формат файла некорректный
     */
    public LevelData loadLevelFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Файл уровня пустой");
        }

        int fileHeight = lines.size();
        int fileWidth = lines.get(0).length();

        if (fileWidth != width || fileHeight != height) {
            throw new IllegalArgumentException("Размер уровня в файле (" + fileWidth + "x" + fileHeight +
                    ") не соответствует ожидаемому (" + width + "x" + height + ")");
        }

        // Проверяем, что все строки одинаковой длины
        for (String line : lines) {
            if (line.length() != fileWidth) {
                throw new IllegalArgumentException("Строки уровня разной длины");
            }
        }

        List<GridPoint2> treePositions = new ArrayList<>();
        GridPoint2 tankPosition = null;

        for (int y = 0; y < fileHeight; y++) {
            String line = lines.get(y);
            for (int x = 0; x < fileWidth; x++) {
                char symbol = line.charAt(x);
                GridPoint2 pos = new GridPoint2(x, height - 1 - y); // Y координата инвертирована (верх - низ)

                switch (symbol) {
                    case 'T':
                        treePositions.add(pos);
                        break;
                    case 'X':
                        if (tankPosition != null) {
                            throw new IllegalArgumentException("Найдено более одной позиции танка (X)");
                        }
                        tankPosition = pos;
                        break;
                    case '_':
                        // Пустая клетка - ничего не делаем
                        break;
                    default:
                        throw new IllegalArgumentException("Некорректный символ '" + symbol + "' в позиции (" + x + "," + y + ")");
                }
            }
        }

        if (tankPosition == null) {
            throw new IllegalArgumentException("Позиция танка (X) не найдена в файле уровня");
        }

        return new LevelData(tankPosition, treePositions);
    }

    /**
     * Класс для хранения данных уровня: позиция танка и позиции деревьев.
     */
    public static class LevelData {
        private final GridPoint2 tankPosition;
        private final List<GridPoint2> treePositions;

        public LevelData(GridPoint2 tankPosition, List<GridPoint2> treePositions) {
            this.tankPosition = tankPosition;
            this.treePositions = new ArrayList<>(treePositions);
        }

        public GridPoint2 getTankPosition() {
            return tankPosition;
        }

        public List<GridPoint2> getTreePositions() {
            return new ArrayList<>(treePositions);
        }
    }
}