package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LevelGeneratorTest {

    @Test
    public void testGenerateRandomLevel() {
        LevelGenerator generator = new LevelGenerator(10, 8);
        LevelGenerator.LevelData data = generator.generateRandomLevel(5);

        assertNotNull(data.getTankPosition());
        assertNotNull(data.getTreePositions());
        assertEquals(5, data.getTreePositions().size());

        // Проверяем, что танк не находится на позиции дерева
        GridPoint2 tankPos = data.getTankPosition();
        for (GridPoint2 treePos : data.getTreePositions()) {
            assertNotEquals(tankPos, treePos);
        }

        // Проверяем, что все позиции в пределах поля
        assertTrue(tankPos.x >= 0 && tankPos.x < 10);
        assertTrue(tankPos.y >= 0 && tankPos.y < 8);

        for (GridPoint2 treePos : data.getTreePositions()) {
            assertTrue(treePos.x >= 0 && treePos.x < 10);
            assertTrue(treePos.y >= 0 && treePos.y < 8);
        }
    }

    @Test
    public void testLoadLevelFromFile() throws IOException {
        // Создаем временный файл уровня
        File tempFile = File.createTempFile("level", ".txt");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("___T__T___\n");
            writer.write("__TT__TTTT\n");
            writer.write("_________T\n");
            writer.write("TTTT__T__T\n");
            writer.write("_____X____\n");
            writer.write("__________\n");
        }

        LevelGenerator generator = new LevelGenerator(10, 6);
        LevelGenerator.LevelData data = generator.loadLevelFromFile(tempFile.getAbsolutePath());

        // Проверяем позицию танка (X в строке 4, позиции 5)
        assertEquals(new GridPoint2(5, 1), data.getTankPosition()); // Y инвертирован

        // Проверяем позиции деревьев
        List<GridPoint2> trees = data.getTreePositions();
        assertEquals(9, trees.size()); // Подсчитано вручную

        // Проверяем конкретные позиции
        assertTrue(trees.contains(new GridPoint2(3, 5))); // T в первой строке
        assertTrue(trees.contains(new GridPoint2(6, 5))); // T в первой строке
        // И т.д.
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadLevelFromFileInvalidSize() throws IOException {
        File tempFile = File.createTempFile("level", ".txt");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("T___\n"); // 4 символа вместо 10
            writer.write("____\n");
        }

        LevelGenerator generator = new LevelGenerator(10, 6);
        generator.loadLevelFromFile(tempFile.getAbsolutePath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadLevelFromFileNoTank() throws IOException {
        File tempFile = File.createTempFile("level", ".txt");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("__________\n");
            writer.write("__________\n");
        }

        LevelGenerator generator = new LevelGenerator(10, 2);
        generator.loadLevelFromFile(tempFile.getAbsolutePath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadLevelFromFileInvalidSymbol() throws IOException {
        File tempFile = File.createTempFile("level", ".txt");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("___Z______\n"); // Z - недопустимый символ
            writer.write("_____X____\n");
        }

        LevelGenerator generator = new LevelGenerator(10, 2);
        generator.loadLevelFromFile(tempFile.getAbsolutePath());
    }
}