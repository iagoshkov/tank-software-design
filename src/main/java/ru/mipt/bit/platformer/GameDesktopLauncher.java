package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import java.io.FileNotFoundException;
import java.util.Map;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private TREE tree1;
    private TREE tree2;
    //private TREE tree3;
    private TREES trees;
    private LEVEL level1;
    private TileMovement tileMovement;
    private HERO hero;


    @Override
    public void create() {
        System.out.println("CREATION");
        batch = new SpriteBatch();
        level1 = new LEVEL("level.tmx", batch);

        tileMovement = new TileMovement(level1.GroundLayer(), Interpolation.smooth);

        hero = new HERO(new GridPoint2(2,2), 90f);

        try {
            trees = new TREES(false, 10);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (TREE tree : trees.treeslist) {
            tree.CreateTreeGraphics("images/greenTree.png");
            moveRectangleAtTileCenter(level1.GroundLayer(), tree.TreeRectangle(), tree.TreeCoordinates());
        }
        System.out.println("END CREATION");
    }
    @Override
    public void render() {
        level1.ClearScreen();

        hero.Move(trees.treeslist.get(0), trees);
        tileMovement.moveRectangleBetweenTileCenters(hero.TankRectangle(), hero.TankCoordinates(),
                hero.TankDestinationCoordinates(), hero.TankMovementProgress());
        level1.Render();
        batch.begin();
        hero.Render(batch);
        for (TREE tree : trees.treeslist)
            tree.Render(batch);
        batch.end();

    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }
    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        hero.dispose();
        //tree1.dispose();
        //tree2.dispose();
        level1.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}