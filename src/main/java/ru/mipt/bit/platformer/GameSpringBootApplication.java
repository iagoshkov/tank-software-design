package ru.mipt.bit.platformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ru.mipt.bit.platformer.controllers.AiInputController;
import ru.mipt.bit.platformer.controllers.PlayerInputController;

@SpringBootApplication
public class GameSpringBootApplication {
    
    private static ConfigurableApplicationContext context;
    
    public static void initialize() {
        if (context == null) {
            context = SpringApplication.run(GameSpringBootApplication.class);
        }
    }
    
    public static <T> T getBean(Class<T> beanClass) {
        if (context == null) {
            initialize();
        }
        return context.getBean(beanClass);
    }
    
    public static GameDesktopLauncher getGameLauncher() {
        if (context == null) {
            initialize();
        }
        PlayerInputController playerController = context.getBean(PlayerInputController.class);
        AiInputController aiController = context.getBean(AiInputController.class);
        return new GameDesktopLauncher(playerController, aiController);
    }

    
    public static void close() {
        if (context != null) {
            context.close();
            context = null;
        }
    }
}