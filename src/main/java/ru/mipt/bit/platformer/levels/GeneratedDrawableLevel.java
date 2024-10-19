package ru.mipt.bit.platformer.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.mipt.bit.platformer.generators.ObjectGenerator;

import java.util.Collection;

public class GeneratedDrawableLevel extends EmptyDrawableLevel {

    public <T> GeneratedDrawableLevel(TiledMap level,
                                      Batch batch,
                                      OrthographicCamera camera,
                                      ObjectGenerator generator,
                                      int initialNumberOfObstacles,
                                      Collection<? super T> destination) {
        super(level, batch);
        generator.generate(initialNumberOfObstacles, destination);
    }
}
