package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.ai.commands.Command;
import ru.mipt.bit.platformer.ai.commands.MoveCommand;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;

public class RandomAI implements CommandSource {
    private final List<Tank> bots;

    public RandomAI(List<Tank> bots) {
        this.bots = bots;
    }

    @Override
    public List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();
        for (var bot : bots) {
            commands.add(new MoveCommand(bot, Direction.random()));
        }
        return commands;
    }
}
