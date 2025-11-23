package ru.mipt.bit.platformer.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {
    private static AnnotationConfigApplicationContext context;

    public static void initialize() {
        context = new AnnotationConfigApplicationContext(GameConfig.class);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static void close() {
        if (context != null) {
            context.close();
        }
    }
}