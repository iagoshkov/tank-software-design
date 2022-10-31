package ru.mipt.bit.platformer.AI;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.util.objects.Player;
import ru.mipt.bit.platformer.util.objects.Tree;

import java.util.ArrayList;
import java.util.List;

public class EnvSet {


    public GameState envSet (List<Tree> trees, List<Player> tanks, int screenSide1, int screenSide2){
        GameState.GameStateBuilder gameStateBuilder = new GameState.GameStateBuilder();
        return gameStateBuilder
                .obstacles(treeList(trees))
                .bots(botList(tanks))
                .player(createPlayer(tanks.get(0)))
                .levelWidth(screenSide1)
                .levelHeight(screenSide2)
                .build();
    }

    List<Obstacle> treeList(List<Tree> trees){
        List<Obstacle> obstacles = new ArrayList<>();
        for (Tree tree : trees) {
            obstacles.add(new Obstacle(tree.getCoordinates().x, tree.getCoordinates().y));
        }
        return obstacles;
    }

    List<Bot> botList(List<Player> tanks){
        List<Bot> bots = new ArrayList<>();
        for (int i = 1; i < tanks.size(); i++) {
            bots.add(createBot(tanks.get(i)));
        }
        return bots;
    }

    public Orientation stateOrientation(GridPoint2 coordinates, GridPoint2 destinationCoordinates) {
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

    Player createPlayer(Player tank){
        return new Player.PlayerBuilder()
                .source(tank)
                .x(tank.getCoordinates().x)
                .y(tank.getCoordinates().y)
                .destX(tank.getDestination().x)
                .destY(tank.getDestination().y)
                .orientation(stateOrientation(tank.getCoordinates(), tank.getDestination()))
                .build();
    }

    public Bot createBot(Player tank) {
        return new Bot.BotBuilder()
                .source(tank)
                .x(tank.getCoordinates().x)
                .y(tank.getCoordinates().y)
                .destX(tank.getDestination().x)
                .destY(tank.getDestination().y)
                .orientation(stateOrientation(tank.getCoordinates(), tank.getDestination()))
                .build();
    }

}
