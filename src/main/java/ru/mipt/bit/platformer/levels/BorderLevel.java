package ru.mipt.bit.platformer.levels;

public class BorderLevel implements Level {
    private final int width;
    private final int height;

    public BorderLevel(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
