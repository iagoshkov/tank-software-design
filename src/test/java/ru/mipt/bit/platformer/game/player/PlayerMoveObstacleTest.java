package ru.mipt.bit.platformer.game.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.game.controls.MoveCommand;
import ru.mipt.bit.platformer.game.entities.Coordinates;
import ru.mipt.bit.platformer.game.entities.GameEntity;
import ru.mipt.bit.platformer.game.entities.Obstacle;
import ru.mipt.bit.platformer.game.entities.Tank;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMoveObstacleTest {

    static Player player;
    static PlayerMoveLogic moveLogic;

    @BeforeAll
    static void beforeAll() {
        GameEntity obstacle1 = new Obstacle(new Coordinates(1, 3));
        GameEntity obstacle2 = new Obstacle(new Coordinates(3, 3));

        player = new Tank(new Coordinates(1, 10));
        List<GameEntity> obstacles = List.of(obstacle1, obstacle2);

        moveLogic = new PlayerMoveLogic(player, obstacles);
    }

    @Test
    void moveVertical() {
        moveLogic.setPlayerCoordinates(new Coordinates(1, 2));
        assertFalse(moveLogic.makeMove(MoveCommand.UP));
        moveLogic.confirmMove();
        assertEquals(new Coordinates(1, 2), player.getCoordinates());
        assertEquals(90f, player.getRotation());
    }

    @Test
    void moveHorizontal() {
        moveLogic.setPlayerCoordinates(new Coordinates(0, 3));
        assertFalse(moveLogic.makeMove(MoveCommand.RIGHT));
        moveLogic.confirmMove();
        assertEquals(new Coordinates(0, 3), player.getCoordinates());
        assertEquals(0f, player.getRotation());
    }

    @Test
    void moveObstacleAround() {
        moveLogic.setPlayerCoordinates(new Coordinates(2, 3));
        assertTrue(moveLogic.makeMove(MoveCommand.DOWN));
        moveLogic.confirmMove();
        assertEquals(new Coordinates(2, 2), player.getCoordinates());
        assertEquals(-90f, player.getRotation());
    }
}