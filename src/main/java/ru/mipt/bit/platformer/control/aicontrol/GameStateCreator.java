package ru.mipt.bit.platformer.control.aicontrol;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.objects.gameObjects.Tank;
import ru.mipt.bit.platformer.objects.gameObjects.Tree;

import java.util.ArrayList;
import java.util.List;

public class GameStateCreator {

    public GameState createGameState(List<Tree> trees, List<Tank> tanks, int levelWidth, int levelHeight){
        GameState.GameStateBuilder gameStateBuilder = new GameState.GameStateBuilder();
        return gameStateBuilder
                .obstacles(obstaclesFromTrees(trees))
                .bots(botsFromTanks(tanks))
                .player(createPlayer(tanks.get(0)))
                .levelWidth(levelWidth)
                .levelHeight(levelHeight)
                .build();
    }

    List<Obstacle> obstaclesFromTrees(List<Tree> trees){
        List<Obstacle> obstacles = new ArrayList<>();
        for (Tree tree : trees) {
            obstacles.add(new Obstacle(tree.getCoordinates().x, tree.getCoordinates().y));
        }
        return obstacles;
    }

    List<Bot> botsFromTanks(List<Tank> tanks){
        List<Bot> bots = new ArrayList<>();
        for (int i = 1; i < tanks.size(); i++) {
            bots.add(createBot(tanks.get(i)));
        }
        return bots;
    }

    public Bot createBot(Tank tank) {
        return new Bot.BotBuilder()
                .source(tank)
                .x(tank.getCoordinates().x)
                .y(tank.getCoordinates().y)
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .orientation(createOrientation(tank.getCoordinates(), tank.getDestinationCoordinates()))
                .build();
    }

    public Orientation createOrientation(GridPoint2 coordinates, GridPoint2 destinationCoordinates) {
        int deltaX = destinationCoordinates.x - coordinates.x;
        int deltaY = destinationCoordinates.y - coordinates.y;
        if (deltaX > 0) {
            return Orientation.E;
        } else if (deltaX < 0) {
            return Orientation.W;
        } else if (deltaY > 0) {
            return Orientation.N;
        } else {
            return Orientation.S;
        }
    }

    Player createPlayer(Tank tank){
        return new Player.PlayerBuilder()
                .source(tank)
                .x(tank.getCoordinates().x)
                .y(tank.getCoordinates().y)
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .orientation(createOrientation(tank.getCoordinates(), tank.getDestinationCoordinates()))
                .build();
    }

}
