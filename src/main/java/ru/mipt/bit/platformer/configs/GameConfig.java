package ru.mipt.bit.platformer.configs;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.controllers.AiInputController;
import ru.mipt.bit.platformer.controllers.PlayerInputController;

import java.util.Random;

@Configuration
@ComponentScan("ru.mipt.bit.platformer")
public class GameConfig {

    @Bean
    public Lwjgl3ApplicationConfiguration applicationConfiguration() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Tank Game");
        config.setWindowedMode(1280, 1024);
        config.setResizable(false);
        return config;
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public PlayerInputController playerInputController() {
        return new PlayerInputController();
    }

    @Bean
    public AiInputController aiInputController(Random random) {
        return new AiInputController(random);
    }
}