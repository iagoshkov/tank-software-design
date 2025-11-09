package ru.mipt.bit.platformer.tests;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.configs.PlayerConfig;
import ru.mipt.bit.platformer.configs.TreeConfig;

public class ConfigsTest {
    
    public void testPlayerConfigCreation() {
        GridPoint2 position = new GridPoint2(1, 1);
        PlayerConfig config = new PlayerConfig(position);
        assert config.getInitialPosition().equals(position);
        assert config.getMovementSpeed() == PlayerConfig.DEFAULT_MOVEMENT_SPEED;
        assert config.getTexturePath().equals(PlayerConfig.DEFAULT_TEXTURE);
        System.out.println("✓ testPlayerConfigCreation passed");
    }

    public void testTreeConfigCreation() {
        GridPoint2 position = new GridPoint2(2, 2);
        TreeConfig config = new TreeConfig(position);
        assert config.getInitialPosition().equals(position);
        assert config.getMovementSpeed() == 0f;
        assert config.getTexturePath().equals(TreeConfig.DEFAULT_TEXTURE);
        System.out.println("✓ testTreeConfigCreation passed");
    }

    public static void main(String[] args) {
        ConfigsTest test = new ConfigsTest();
        test.testPlayerConfigCreation();
        test.testTreeConfigCreation();
        System.out.println("✅ All ConfigsTest passed!");
    }
}