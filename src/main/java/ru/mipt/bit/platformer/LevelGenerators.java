// package ru.mipt.bit.platformer;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.files.FileHandle;
// import com.badlogic.gdx.math.GridPoint2;
// import ru.mipt.bit.platformer.configs.PlayerConfig;
// import ru.mipt.bit.platformer.configs.TreeConfig;
// import ru.mipt.bit.platformer.objects.Player;
// import ru.mipt.bit.platformer.objects.Tree;

// import java.util.ArrayList;
// import java.util.List;

// // Интерфейс генератора уровня
// interface LevelGenerator {
//     Level generate();
//     Player getPlayer();
// }

// // Случайный генератор
// class RandomLevelGenerator implements LevelGenerator {
//     private final int width, height;
//     private Player player;

//     public RandomLevelGenerator(int width, int height) {
//         this.width = width;
//         this.height = height;
//     }

//     public Level generate() {
//         Level level = new Level();
//         GridPoint2 playerPos = new GridPoint2(1, 1);
//         PlayerConfig config = new PlayerConfig(playerPos);
//         player = new Player(config, level);
        
//         // Добавляем несколько случайных деревьев
//         new Tree(new TreeConfig(new GridPoint2(1, 3)), level);
//         new Tree(new TreeConfig(new GridPoint2(3, 2)), level);
//         new Tree(new TreeConfig(new GridPoint2(4, 4)), level);
        
//         return level;
//     }

//     public Player getPlayer() { return player; }
// }

