package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.generators.*;
import ru.mipt.bit.platformer.levels.DrawableLevel;
import ru.mipt.bit.platformer.levels.EmptyDrawableLevel;
import ru.mipt.bit.platformer.objects.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class RandomGeneratedGameLoader implements GameLoader {

    private Batch batch;
    private DrawableLevel level;

    private Collection<Drawable> drawables = new HashSet<>();
    private Collection<Movable>   movables = new HashSet<>();

    public RandomGeneratedGameLoader() {
        batch = new SpriteBatch();

        level = new EmptyDrawableLevel(new TmxMapLoader().load("level.tmx"), batch);

        Mover mover = new Mover(new TileMovement(level.getGroundLayer(), Interpolation.smooth));

        SimpleIntegerGenerator simpleIntegerGenerator = new SimpleIntegerGenerator();
        final CoordinatesGenerator coordinatesGenerator = new CoordinatesGenerator
                (
                        simpleIntegerGenerator,
                        level.getHeight(),
                        level.getWidth()
                );

        final Tank tankMy = new Tank
                (
                        new Texture("images/tank_blue.png"),
                        coordinatesGenerator.generate(),
                        0.4f,
                        1f,
                        0,
                        mover.getTileMovement()
                );

        final TankAI tankAI = new TankAI
                (
                        new Texture("images/tank_blue.png"),
                        coordinatesGenerator.generate(),
                        0.4f,
                        1f,
                        0,
                        mover.getTileMovement(),
                        simpleIntegerGenerator
                );

        final ObjectGenerator<TankAI> tankAIGenerator = new TankAIGenerator(
                List.of("images/tank_blue.png"),
                coordinatesGenerator,
                List.of(0.4f),
                1f,
                List.of(0),
                mover.getTileMovement(),
                simpleIntegerGenerator
        );

        final ObjectGenerator<Tree> treeGenerator = new TreeGenerator(
                coordinatesGenerator,
                List.of("images/greenTree.png"),
                level.getGroundLayer()
        );

        drawables.add(tankMy);
        drawables.add(tankAI);
         movables.add(tankMy);
         movables.add(tankAI);

        Collection<TankAI> tmp = (Collection<TankAI>) tankAIGenerator.generate(3, drawables);
        movables.addAll(tmp);

        drawables = (Collection<Drawable>) treeGenerator.generate(20, drawables);

        final FileSaver fileSaver = new TxtSaver(level, drawables);
        fileSaver.saveToFile("src/main/res/level.txt");
    }

    @Override
    public Batch getBatch() {
        return batch;
    }

    @Override
    public DrawableLevel getLevel() {
        return level;
    }

    @Override
    public Collection<Drawable> getDrawables() {
        return drawables;
    }

    @Override
    public Collection<Movable> getMovables() {
        return movables;
    }
}
