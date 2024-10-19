package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.*;

import static java.lang.Math.min;

public class TreeGenerator implements ObjectGenerator<Tree> {
    final private CoordinatesGenerator coordinatesGenerator;
    final private IntegerGenerator integerGenerator;
    final private List<String> textures;
    final private TiledMapTileLayer groundLayer;

    public TreeGenerator(CoordinatesGenerator coordinatesGenerator, IntegerGenerator integerGenerator, List<String> textures, TiledMapTileLayer groundLayer) {
        this.coordinatesGenerator = coordinatesGenerator;
        this.integerGenerator = integerGenerator;
        this.textures = textures;
        this.groundLayer = groundLayer;
    }

    public TreeGenerator(CoordinatesGenerator coordinatesGenerator, List<String> textures, TiledMapTileLayer groundLayer) {
        this(coordinatesGenerator, coordinatesGenerator.getIntegerGenerator(), textures, groundLayer);
    }

    @Override
    public Collection<? super Tree> generate(int n, Collection<? super Tree> destination) {
        final int size = destination.size();
        while (destination.size() < min(n + size, coordinatesGenerator.getHeight() * coordinatesGenerator.getWidth())) {
            destination.add(new Tree(
                    new Texture(textures.get(integerGenerator.generate(0, textures.size() - 1))),
                    coordinatesGenerator.generate(),
                    groundLayer
            ));
        }
        return destination;
    }

    @Override
    public Tree generate() {
        return new Tree(new Texture(
                textures.get(integerGenerator.generate(0, textures.size() - 1))),
                coordinatesGenerator.generate(),
                groundLayer
        );
    }
}
