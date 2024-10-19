package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Collection;

import static java.lang.Math.min;

public class CoordinatesGenerator implements ObjectGenerator<GridPoint2> {

    private final IntegerGenerator integerGenerator;
    private final int height, width;

    public CoordinatesGenerator(IntegerGenerator integerGenerator, int height, int width) {
        this.integerGenerator = integerGenerator;
        this.height = height;
        this.width = width;
    }

    @Override
    public Collection<? super GridPoint2> generate(int n, Collection<? super GridPoint2> destination) {
        final int size = destination.size();
        while (destination.size() < min(n + size, width * height)) {
            destination.add(new GridPoint2(integerGenerator.generate(0, width - 1), integerGenerator.generate(0, height - 1)));
        }
        return destination;
    }

    @Override
    public GridPoint2 generate() {
        return new GridPoint2(integerGenerator.generate(0, width - 1), integerGenerator.generate(0, height - 1));
    }

    public IntegerGenerator getIntegerGenerator() {
        return integerGenerator;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
