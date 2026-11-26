package ru.mipt.bit.platformer.control;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToggleHealthBarsCommandTest {

    @Test
    void executeTogglesHealthBars() {
        AtomicInteger calls = new AtomicInteger();
        ToggleHealthBarsCommand command = new ToggleHealthBarsCommand(calls::incrementAndGet);

        command.execute();
        assertEquals(1, calls.get());

        command.execute();
        assertEquals(2, calls.get());
    }
}
