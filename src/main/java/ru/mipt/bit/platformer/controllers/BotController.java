package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.ai.CommandSource;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

public class BotController {
    private final CommandSource commandSource;
    private final List<Tank> bots;

    public BotController(CommandSource commandSource, List<Tank> bots) {
        this.commandSource = commandSource;
        this.bots = bots;
    }

    public void process(float deltaTime) {
        for (var command : commandSource.getCommands()) {
            command.execute();
        }
        for (var bot : bots) {
            bot.makeProgress(deltaTime);
        }
    }
}
