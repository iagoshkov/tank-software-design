package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.levels.DrawableLevel;
import ru.mipt.bit.platformer.levels.Level;
import ru.mipt.bit.platformer.objects.Drawable;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.TankAI;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Collection;
import java.util.List;

import static java.lang.Math.min;

public class TankAIGenerator implements ObjectGenerator<TankAI> {
    final private List<String> textures;
    final private CoordinatesGenerator coordinatesGenerator;
    final private List<Float> movementSpeeds;
    final private float movementProgress;
    final private IntegerGenerator integerGenerator;
    final private List<Integer> rotations;
    final private TileMovement tileMovement;
    final private Character character;
    final private Level level;
    final private Collection<? extends GameObject> obstacles;


    public TankAIGenerator(List<String> textures,
                           CoordinatesGenerator coordinatesGenerator,
                           List<Float> movementSpeeds,
                           float movementProgress,
                           List<Integer> rotations,
                           TileMovement tileMovement,
                           Character character,
                           IntegerGenerator integerGenerator,
                           Level level,
                           Collection<? extends GameObject> obstacles) {

        this.textures = textures;
        this.coordinatesGenerator = coordinatesGenerator;
        this.movementSpeeds = movementSpeeds;
        this.movementProgress = movementProgress;
        this.integerGenerator = integerGenerator;
        this.rotations = rotations;
        this.tileMovement = tileMovement;
        this.character = character;
        this.level = level;
        this.obstacles = obstacles;
    }

    public TankAIGenerator(List<String> textures,
                           CoordinatesGenerator coordinatesGenerator,
                           List<Float> movementSpeeds,
                           float movementProgress,
                           List<Integer> rotations,
                           TileMovement tileMovement,
                           Character character,
                           Level level,
                           Collection<? extends GameObject> obstacles) {

        this(
                textures,
                coordinatesGenerator,
                movementSpeeds,
                movementProgress,
                rotations,
                tileMovement,
                character,
                coordinatesGenerator.getIntegerGenerator(),
                level,
                obstacles);
    }

    @Override
    public Collection<? super TankAI> generate(int n, Collection<? super TankAI> destination) {
        final int size = destination.size();
        while (destination.size() < min(n + size, coordinatesGenerator.getHeight() * coordinatesGenerator.getWidth())) {
            destination.add(new TankAI
                    (
                            new Texture(textures.get(generateIndex())),
                            coordinatesGenerator.generate(),
                            movementSpeeds.get(generateIndex()),
                            1f,
                            rotations.get(generateIndex()),
                            tileMovement,
                            character,
                            integerGenerator,
                            level,
                            obstacles
                    ));
        }
        return destination;
    }

    @Override
    public TankAI generate() {
        return new TankAI
                (
                        new Texture(textures.get(generateIndex())),
                        coordinatesGenerator.generate(),
                        movementSpeeds.get(generateIndex()),
                        1f,
                        rotations.get(generateIndex()),
                        tileMovement,
                        character,
                        integerGenerator,
                        level,
                        obstacles
                );
    }

    int generateIndex(){
        return integerGenerator.generate(0, textures.size() - 1);
    };
}
