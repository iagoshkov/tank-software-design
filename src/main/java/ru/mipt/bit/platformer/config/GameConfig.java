package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.collision.SimpleCollisionDetector;
import ru.mipt.bit.platformer.controller.InputController;
import ru.mipt.bit.platformer.controller.ShootCommand;
import ru.mipt.bit.platformer.controller.ToggleHealthDisplayCommand;
import ru.mipt.bit.platformer.level.LevelManager;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.observer.GameLevelObservable;
import ru.mipt.bit.platformer.render.GameRenderer;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

@Configuration
public class GameConfig {

    @Bean
    public TiledMap tiledMap() {
        return new TmxMapLoader().load("level.tmx");
    }

    @Bean
    public Texture treeTexture() {
        return new Texture("images/greenTree.png");
    }

    @Bean
    public Texture tankTexture() {
        return new Texture("images/tank_blue.png");
    }

    @Bean
    public Texture bulletTexture() {
        return new Texture("images/bullet.png");
    }

    @Bean
    public TileMovement tileMovement(TiledMap tiledMap) {
        return new TileMovement(getSingleLayer(tiledMap), com.badlogic.gdx.math.Interpolation.smooth);
    }

    @Bean
    public CollisionDetector collisionDetector(TiledMap tiledMap) {
        return new SimpleCollisionDetector(getSingleLayer(tiledMap));
    }

    @Bean
    public GameLevelObservable gameLevelObservable() {
        return new GameLevelObservable();
    }

    @Bean
    public LevelManager levelManager(TiledMap tiledMap, Texture treeTexture, Texture tankTexture, 
                                   Texture bulletTexture, CollisionDetector collisionDetector,
                                   GameLevelObservable gameLevelObservable) {
        return new LevelManager(getSingleLayer(tiledMap), treeTexture, tankTexture, 
                              bulletTexture, collisionDetector, gameLevelObservable);
    }

    @Bean
    public ToggleHealthDisplayCommand toggleHealthDisplayCommand(LevelManager levelManager) {
        return levelManager.getToggleHealthCommand();
    }
}