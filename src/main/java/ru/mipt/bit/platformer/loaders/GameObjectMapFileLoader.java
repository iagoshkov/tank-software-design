package ru.mipt.bit.platformer.loaders;

import com.badlogic.gdx.math.GridPoint2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameObjectMapFileLoader implements GameObjectMapLoader {
    private static final char PLAYER_SYMBOL = 'P';
    private static final char BOT_SYMBOL = 'B';
    private static final char TREE_SYMBOL = 'T';

    private final int height;
    private final int width;
    private final String fileName;

    private GridPoint2 playerPosition;
    private List<GridPoint2> botPositions;
    private List<GridPoint2> treePositions;

    public GameObjectMapFileLoader(int height, int width, String fileName) {
        this.height = height;
        this.width = width;
        this.fileName = fileName;
    }

    private void clear() {
        playerPosition = null;
        botPositions = new ArrayList<>();
        treePositions = new ArrayList<>();
    }

    @Override
    public void loadMap() throws MapLoadingException {
        clear();

        List<String> lines;
        try (InputStream inputStream = getClass().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new MapLoadingException("error reading map file", e);
        }

        if (height != lines.size()) {
            throw new MapLoadingException("incorrect map height");
        }

        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);

            if (width != line.length()) {
                throw new MapLoadingException("incorrect map width");
            }

            final int y = lines.size() - i - 1;

            for (int x = 0; x < line.length(); ++x) {
                if (line.charAt(x) == PLAYER_SYMBOL) {
                    if (playerPosition != null) {
                        throw new MapLoadingException("multiple positions for player");
                    }
                    playerPosition = new GridPoint2(x, y);
                }
                if (line.charAt(x) == BOT_SYMBOL) {
                    botPositions.add(new GridPoint2(x, y));
                }
                if (line.charAt(x) == TREE_SYMBOL) {
                    treePositions.add(new GridPoint2(x, y));
                }
            }
        }
    }

    @Override
    public GridPoint2 getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public List<GridPoint2> getBotPositions() {
        return botPositions;
    }

    @Override
    public List<GridPoint2> getTreePositions() {
        return treePositions;
    }
}
