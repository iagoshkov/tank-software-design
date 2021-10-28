package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.ai.AI;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

public class BotController {
    private final AI ai;
    private final List<Tank> bots;

    public BotController(AI ai, List<Tank> bots) {
        this.ai = ai;
        this.bots = bots;
    }

    public void process(float deltaTime) {
        for (var command : ai.getCommands()) {
            command.execute();
        }
        for (var bot : bots) {
            bot.makeProgress(deltaTime);
        }
    }
}
