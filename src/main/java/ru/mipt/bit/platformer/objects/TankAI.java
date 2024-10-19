package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.generators.IntegerGenerator;
import ru.mipt.bit.platformer.keys.Direction;
import ru.mipt.bit.platformer.levels.DrawableLevel;
import ru.mipt.bit.platformer.levels.Level;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Collection;

public class TankAI extends Tank implements AI{
    private IntegerGenerator integerGenerator;
    private Level level;
    private Collection<? extends GameObject> obstacles;

    public TankAI(Texture texture,
                  GridPoint2 coordinates,
                  float movementSpeed,
                  float movementProgress,
                  float rotation,
                  TileMovement tileMovement,
                  Character drawableCharacter,
                  IntegerGenerator integerGenerator,
                  Level level,
                  Collection<? extends GameObject> obstacles) {
        super(texture, coordinates, movementSpeed, movementProgress, rotation, tileMovement, drawableCharacter);
        this.integerGenerator = integerGenerator;
        this.level = level;
        this.obstacles = obstacles;
    }

    @Override
    public void changeMovementState(float deltaTime) {
        Direction direction = generateDirection();
        if (canMoveToDirection(direction, obstacles, level)) {
            move(direction);
        }
        super.changeMovementState(deltaTime);
    }

    @Override
    public Direction generateDirection() {
        return Direction.values()[integerGenerator.generate(0, 3)];
    }
}
