package ru.mipt.bit.platformer.models.graphics;

import static com.badlogic.gdx.math.MathUtils.clamp;

public interface Movable {
    float MAX_PROGRESS = 1f;
    float MIN_PROGRESS = 0f;

    void move(Direction direction, float time);

    default float continueProgress(float previousProgress, float deltaTime, float speed) {
        return clamp(previousProgress + deltaTime / speed, 0f, 1f);
    }
}
