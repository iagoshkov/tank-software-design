package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.generators.IntegerGenerator;
import ru.mipt.bit.platformer.keys.Direction;
import ru.mipt.bit.platformer.util.TileMovement;

public class TankAI extends Tank implements AI, Movable, Drawable {
    private final IntegerGenerator integerGenerator;
    private static final Character drawableCharacter = 'A';

    public TankAI(Texture texture,
                  GridPoint2 coordinates,
                  float movementSpeed,
                  float movementProgress,
                  float rotation,
                  TileMovement tileMovement,
                  IntegerGenerator integerGenerator) {
        super(texture, coordinates, movementSpeed, movementProgress, rotation, tileMovement);
        this.integerGenerator = integerGenerator;
    }

    @Override
    public Character getDrawableCharacter() {
        return drawableCharacter;
    }

    public static Character getDrawableCharacterStatic() {
        return drawableCharacter;
    }

    @Override
    public Direction generateDirection() {
        return Direction.values()[integerGenerator.generate(0, 3)];
    }
}
