package ru.mipt.bit.platformer.tests;

import ru.mipt.bit.platformer.controllers.AiInputController;
import ru.mipt.bit.platformer.controllers.PlayerInputController;
import ru.mipt.bit.platformer.util.Direction;

public class InputControllerTest {
    
    public void testPlayerInputController() {
        PlayerInputController controller = new PlayerInputController();
        Direction direction = controller.getInputDirection();
        assert direction != null;
    }

    public void testAiInputController() {
        AiInputController controller = new AiInputController();
        Direction direction = controller.getInputDirection();
        assert direction != null;
    }
}