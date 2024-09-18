package ru.mipt.bit.platformer.game.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.game.controls.MoveCommand;
import ru.mipt.bit.platformer.game.objects.Coordinates;
import ru.mipt.bit.platformer.game.objects.GameEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerMoveLogicTest {

    static Player player;
    static PlayerMoveLogic moveLogic;

    @BeforeAll
    static void beforeAll() {
        GameEntity obstacle1 = new GameEntity(new Coordinates(1, 3));
        GameEntity obstacle2 = new GameEntity(new Coordinates(3, 3));

        player = new Player(new Coordinates(1, 1));
        List<GameEntity> obstacles = List.of(obstacle1, obstacle2);

        moveLogic = new PlayerMoveLogic(player, obstacles);
    }

    @Test
    void makeMovePositiveUp() {
        player.setCoordinates(new Coordinates(1, 1));
        assertTrue(moveLogic.makeMove(MoveCommand.UP));
        moveLogic.confirmMove();
        assertEquals(player.getCoordinates(), new Coordinates(1, 2));
    }
}