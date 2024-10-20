package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.graphics.Texture;
import ru.mipt.bit.platformer.objects.TankAI;
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

    public TankAIGenerator(List<String> textures,
                           CoordinatesGenerator coordinatesGenerator,
                           List<Float> movementSpeeds,
                           float movementProgress,
                           List<Integer> rotations,
                           TileMovement tileMovement,
                           IntegerGenerator integerGenerator) {

        this.textures = textures;
        this.coordinatesGenerator = coordinatesGenerator;
        this.movementSpeeds = movementSpeeds;
        this.movementProgress = movementProgress;
        this.integerGenerator = integerGenerator;
        this.rotations = rotations;
        this.tileMovement = tileMovement;
    }

    public TankAIGenerator(List<String> textures,
                           CoordinatesGenerator coordinatesGenerator,
                           List<Float> movementSpeeds,
                           float movementProgress,
                           List<Integer> rotations,
                           TileMovement tileMovement) {

        this(
                textures,
                coordinatesGenerator,
                movementSpeeds,
                movementProgress,
                rotations,
                tileMovement,
                coordinatesGenerator.getIntegerGenerator());
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
                            movementProgress,
                            rotations.get(generateIndex()),
                            tileMovement,
                            integerGenerator
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
                        integerGenerator
                );
    }

    int generateIndex(){
        return integerGenerator.generate(0, textures.size() - 1);
    };
}
