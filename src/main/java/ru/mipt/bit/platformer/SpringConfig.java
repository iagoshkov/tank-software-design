package ru.mipt.bit.platformer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.controllers.AiInputController;
import ru.mipt.bit.platformer.controllers.PlayerInputController;

import java.util.Random;

@Configuration
public class SpringConfig {

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public PlayerInputController playerInputController() {
        return new PlayerInputController();
    }

    @Bean
    public AiInputController aiInputController() {
        return new AiInputController(random());
    }
}