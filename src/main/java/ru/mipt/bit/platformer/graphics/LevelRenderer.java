package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ru.mipt.bit.platformer.driver.observation.Event;
import ru.mipt.bit.platformer.driver.observation.Observer;
import ru.mipt.bit.platformer.graphics.textures.BulletTexture;
import ru.mipt.bit.platformer.graphics.textures.TankTexture;
import ru.mipt.bit.platformer.graphics.textures.TreeTexture;
import ru.mipt.bit.platformer.objects.gameObjects.Bullet;
import ru.mipt.bit.platformer.objects.gameObjects.GameObject;
import ru.mipt.bit.platformer.objects.gameObjects.Tank;
import ru.mipt.bit.platformer.objects.gameObjects.Tree;

import java.util.ArrayList;
import java.util.List;

public class LevelRenderer implements Observer {

    private final Batch batch;

    private final LevelLayer levelLayer;

    private final List<TankTexture> tankTextures;
    private final List<TreeTexture> treeTextures;
    private final List<BulletTexture> bulletTextures;

    public LevelRenderer(List<Tank> tanks, List<Tree> trees){
        batch = new SpriteBatch();
        levelLayer = new LevelLayer(new TmxMapLoader().load("level.tmx"), batch);

        tankTextures = new ArrayList<>();
        for (int i = 0; i < tanks.size(); i++){
            tankTextures.add(new TankTexture(new Texture("images/tank_blue.png"),
                                             new Texture("images/health.png")));
        }

        treeTextures = new ArrayList<>();
        for (int i = 0; i < trees.size(); i++){
            treeTextures.add(new TreeTexture(new Texture("images/greenTree.png")));
        }

        bulletTextures = new ArrayList<>();

        levelLayer.placeObstacles(trees, treeTextures);
    }

    public void render(List<Tank> tanks, List<Bullet> bullets){
        for (int i = 0; i < tanks.size(); i++) {
            levelLayer.updateTanksPlacement(tanks.get(i), tankTextures.get(i));
        }
        for (int i = 0; i < bullets.size(); i++) {
            levelLayer.updateBulletsPlacement(bullets.get(i), bulletTextures.get(i));
        }
        levelLayer.render();

        Drawer.draw(batch, tanks, tankTextures, treeTextures, bullets, bulletTextures);

    }

    public void dispose(){
        for (TreeTexture treeTexture : treeTextures) {
            treeTexture.getTexture().dispose();
        }
        for (TankTexture tankTexture : tankTextures) {
            tankTexture.getBlueTank().dispose();
        }
        for (BulletTexture bulletTexture : bulletTextures) {
            bulletTexture.getBulletTexture().dispose();
        }
        levelLayer.dispose();
        batch.dispose();
    }

    @Override
    public void update(Event event, GameObject gameObject, int id) {
        switch(event) {
            case AddBullet:
                bulletTextures.add(new BulletTexture(new Texture("images/bullet_square.png")));
                break;
            case RemoveBullet:
                bulletTextures.remove(id);
                break;
            case RemoveTank:
                tankTextures.remove(id);
                break;
            case OnOffHealth:
                for (TankTexture tankTexture : tankTextures) {
                    tankTexture.changeHealthBar();
                }
                break;
        }
    }
}
