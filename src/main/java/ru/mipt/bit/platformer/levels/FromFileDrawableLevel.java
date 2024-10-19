package ru.mipt.bit.platformer.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.*;
import ru.mipt.bit.platformer.util.CharToDrawableConverter;
import ru.mipt.bit.platformer.util.FileParser;
import ru.mipt.bit.platformer.util.Mover;
import ru.mipt.bit.platformer.util.TileMovement;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class FromFileDrawableLevel extends EmptyDrawableLevel {

    public FromFileDrawableLevel(TiledMap level,
                                 Batch batch,
                                 FileParser fileParser,
                                 String fileName,
                                 Collection<? super GameObjectAbt> destination) {
        super(level, batch);
        Map<GridPoint2, Character> objectCoordinates;
        try {
            objectCoordinates = fileParser.parseCoordinatesFromFile(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (GridPoint2 coordinate : objectCoordinates.keySet()) {
            Character character = objectCoordinates.get(coordinate);
            if (character == Tank.getDrawableCharacterStatic()) {
                destination.add(new Tank
                        (
                                new Texture("images/tank_blue.png"),
                                coordinate.set(coordinate.x, height - 1 - coordinate.y),
                                0.4f,
                                1f,
                                0,
                                new TileMovement(getGroundLayer(), Interpolation.smooth)
                        ));
            } else if (character == Tree.getDrawableCharacterStatic()) {
                destination.add(new Tree
                        (
                                new Texture("images/greenTree.png"),
                                coordinate.set(coordinate.x, height - 1 - coordinate.y),
                                groundLayer
                        ));
            }
        }
    }
}
