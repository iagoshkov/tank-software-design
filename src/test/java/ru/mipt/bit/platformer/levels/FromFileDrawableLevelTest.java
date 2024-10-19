package ru.mipt.bit.platformer.levels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Drawable;
import ru.mipt.bit.platformer.objects.GameObjectAbt;
import ru.mipt.bit.platformer.objects.Movable;
import ru.mipt.bit.platformer.util.Mover;
import ru.mipt.bit.platformer.util.TxtParser;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class FromFileDrawableLevelTest {
//    @Test
//    void testFromFileDrawableLevel() {
//        Batch batch = new SpriteBatch();
//
//        Collection<GameObjectAbt> objects = new HashSet<>();
//        Collection<Drawable>    drawables = new HashSet<>();
//        Collection<Movable>      movables = new HashSet<>();
//
//        Mover mover = null;
//        DrawableLevel level = new FromFileDrawableLevel
//                (
//                        new TmxMapLoader().load("level.tmx"),
//                        batch,
//                        new TxtParser(),
//                        mover,
//                        "src/main/res/level.txt",
//                        objects
//                );
//
//        for (GameObjectAbt object : objects) {
//            if (object instanceof Movable) {
//                movables.add((Movable) object);
//            }
//            if (object instanceof Drawable) {
//                drawables.add((Drawable) object);
//            }
//        }
//        System.out.println(movables);
//    }

}