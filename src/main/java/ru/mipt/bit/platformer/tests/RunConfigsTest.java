package ru.mipt.bit.platformer.tests;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.configs.TreeConfig;

public class RunConfigsTest {
    public static void main(String[] args) {
        System.out.println("Running ConfigsTest...");
        
        // Test PlayerConfig
        GridPoint2 position = new GridPoint2(1, 1);
        PlayerConfig config = new PlayerConfig(position);
        assert config.getInitialPosition().equals(position);
        assert config.getMovementSpeed() == PlayerConfig.DEFAULT_MOVEMENT_SPEED;
        assert config.getTexturePath().equals(PlayerConfig.DEFAULT_TEXTURE);
        System.out.println("✓ PlayerConfig test passed");
        
        // Test TreeConfig
        GridPoint2 treePosition = new GridPoint2(2, 2);
        TreeConfig treeConfig = new TreeConfig(treePosition);
        assert treeConfig.getInitialPosition().equals(treePosition);
        assert treeConfig.getMovementSpeed() == 0f;
        assert treeConfig.getTexturePath().equals(TreeConfig.DEFAULT_TEXTURE);
        System.out.println("✓ TreeConfig test passed");
        
        System.out.println("✅ All ConfigsTest passed!");
    }
}