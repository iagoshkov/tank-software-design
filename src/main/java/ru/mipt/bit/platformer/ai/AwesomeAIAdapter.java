package ru.mipt.bit.platformer.ai;

import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Player;
import org.awesome.ai.state.immovable.Obstacle;

import ru.mipt.bit.platformer.ai.commands.Command;
import ru.mipt.bit.platformer.ai.commands.MoveCommand;
import ru.mipt.bit.platformer.ai.commands.NoopCommand;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;
import java.util.stream.Collectors;

public class AwesomeAIAdapter implements CommandSource {
    private final AI ai;
    private final GameState gameState;

    public AwesomeAIAdapter(AI ai, Tank player, List<Tank> bots, List<ru.mipt.bit.platformer.model.Obstacle> obstacles,
                            int levelWidth, int levelHeight) {
        this.ai = ai;
        this.gameState = buildGameState(player, bots, obstacles, levelWidth, levelHeight);
    }

    private static Player tankToPlayer(Tank tank) {
        return Player.builder()
                .source(tank)
                .x(tank.getCoordinates().x)
                .y(tank.getCoordinates().y)
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .build();
    }

    private static Bot tankToBot(Tank tank) {
        return Bot.builder()
                .source(tank)
                .x(tank.getCoordinates().x)
                .y(tank.getCoordinates().y)
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .build();
    }

    private static Obstacle obstacleToObstacle(ru.mipt.bit.platformer.model.Obstacle obstacle) {
        return new Obstacle(obstacle.getCoordinates().x, obstacle.getCoordinates().y);
    }

    private static GameState buildGameState(Tank player, List<Tank> bots,
                                            List<ru.mipt.bit.platformer.model.Obstacle> obstacles,
                                            int levelWidth, int levelHeight) {
        var _player = tankToPlayer(player);
        var _bots = bots.stream()
                .map(AwesomeAIAdapter::tankToBot)
                .collect(Collectors.toList());
        var _obstacles = obstacles.stream()
                .map(AwesomeAIAdapter::obstacleToObstacle)
                .collect(Collectors.toList());
        return GameState.builder()
                .obstacles(_obstacles)
                .bots(_bots)
                .player(_player)
                .levelWidth(levelWidth)
                .levelHeight(levelHeight)
                .build();
    }

    private static Direction directionFromAction(Action action) {
        if (action == Action.MoveEast) {
            return Direction.RIGHT;
        }
        if (action == Action.MoveNorth) {
            return Direction.UP;
        }
        if (action == Action.MoveWest) {
            return Direction.LEFT;
        }
        if (action == Action.MoveSouth) {
            return Direction.DOWN;
        }
        return null;
    }

    private static Command commandFromRecommendation(Recommendation recommendation) {
        Tank tank = (Tank) recommendation.getActor().getSource();
        Direction direction = directionFromAction(recommendation.getAction());
        if (direction == null) {
            return new NoopCommand();
        }
        return new MoveCommand(tank, direction);
    }

    @Override
    public List<Command> getCommands() {
        return ai.recommend(gameState).stream()
                .map(AwesomeAIAdapter::commandFromRecommendation)
                .collect(Collectors.toList());
    }
}
