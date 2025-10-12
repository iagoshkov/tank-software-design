package ru.mipt.bit.platformer.config;


// Вынесены константы для соблюдения OCP

public final class GameConfig {
    public static final float MOVEMENT_SPEED = 0.4f;
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 1024;
    public static final int TILE_WIDTH = 128;
    public static final int TILE_HEIGHT = 128;
    
    private GameConfig() {
        // Для невозможности создания экземпляров
    }
}