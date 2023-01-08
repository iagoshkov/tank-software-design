package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

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
    private String level_file_name = "level.txt";
    public Scanner get_Scanner_for_txt(String level_file_name) throws FileNotFoundException {
        File global_path = new File(new File(new File(new File(System.getProperty("user.dir"), "src"), "main"), "resources"), level_file_name);
        System.out.println(global_path.getAbsolutePath());
        if (global_path.exists())
            System.out.println("File is present.");
        else
            System.out.println("File is not present"); // файл отсутствует
        Scanner scanner = new Scanner(global_path);
        return scanner;
    }
    @Override
    public void create() {
        System.out.println("CREATION");
        batch = new SpriteBatch();
        level1 = new LEVEL("level.tmx", batch);

        tileMovement = new TileMovement(level1.GroundLayer(), Interpolation.smooth);
        hero = new HERO();
        Integer hero_x = 0, hero_y = 0;
        boolean generate_from_file = true; // выбираем каким образом будем создавать уровень: из файла или случайно

        if (generate_from_file) {
            try {
                Scanner scanner1 = get_Scanner_for_txt(level_file_name);
                trees = new TREES(generate_from_file, scanner1);
                Scanner scanner2 = get_Scanner_for_txt(level_file_name);
                Integer[] hero_xy = hero.get_coordinates_from_file(scanner2);
                hero_x = hero_xy[0];
                hero_y = hero_xy[1];
                System.out.println(hero_xy);
                System.out.println(hero_xy[0]);
                System.out.println(hero_xy[1]);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            trees = new TREES(generate_from_file, 12);
        }

        hero.set_coordinates(new GridPoint2(hero_x, hero_y), 90f);
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