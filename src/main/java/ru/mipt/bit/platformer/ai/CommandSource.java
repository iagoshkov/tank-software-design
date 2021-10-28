package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.ai.commands.Command;

import java.util.List;

public interface CommandSource {
    List<Command> getCommands();
}
