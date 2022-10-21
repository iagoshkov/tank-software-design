package ru.mipt.bit.platformer.Entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Entities.GameObjectEntity;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class TankEntity extends GameObjectEntity {
    public GridPoint2 destinationPosition;
    public float movementProgress;

    public TankEntity(GridPoint2 position, float rotation) {
        super(position, rotation);
        this.movementProgress = 0f;
        this.destinationPosition = this.position;
    }
}
