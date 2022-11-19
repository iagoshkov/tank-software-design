package ru.mipt.bit.platformer.movementCommand;

import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AiMovementCommand {
    private final AI strategy;
    private ArrayList<OnScreenObject> obstaclesInGame;
    private ArrayList<Tank> playersInGame;
    private ArrayList<Obstacle> obstaclesConverted;
    private org.awesome.ai.state.movable.Player playerConverted;
    private final int levelWidth;
    private final int levelHeight;
    private List<Bot> botsConverted;
    public HashMap<Tank, TankAction> getTankActions(Collection<OnScreenObject> obstacles, Collection<Tank> tanks) {
        HashMap<Tank, TankAction> ret = new HashMap<>();
        this.obstaclesInGame = new ArrayList<>(obstacles);
        this.playersInGame = new ArrayList<>(tanks);

        convertObstacles();
        convertBots();
        convertPlayer();

        for (var recommendation : strategy.recommend(getGameStateBuilder().build()))
            ret.put((Tank) recommendation.getActor().getSource(), getActionType(recommendation));

        return ret;
    }

    private TankAction getActionType(Recommendation recommendation) {
        TankAction action;
        if (recommendation.getAction() == Action.MoveNorth){
            action = TankAction.MOVE_UP;
        } else if (recommendation.getAction() == Action.MoveSouth){
            action = TankAction.MOVE_DOWN;
        } else if (recommendation.getAction() == Action.MoveWest){
            action = TankAction.MOVE_LEFT;
        } else if (recommendation.getAction() == Action.MoveEast){
            action = TankAction.MOVE_RIGHT;
        } else if (recommendation.getAction() == Action.Shoot){
            action = TankAction.SHOOT;
        } else {
            action = TankAction.WAIT;
        }
        return action;
    }

    private GameState.GameStateBuilder getGameStateBuilder() {
        GameState.GameStateBuilder stateBuilder = new GameState.GameStateBuilder();
        stateBuilder.player(playerConverted);
        stateBuilder.bots(botsConverted);
        stateBuilder.obstacles(obstaclesConverted);
        stateBuilder.levelWidth(levelWidth);
        stateBuilder.levelHeight(levelHeight);
        return stateBuilder;
    }

    public AiMovementCommand(int levelWidth, int levelHeight, AI strategy) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.strategy = strategy;
    }

    private void convertPlayer() {
        for (var player : playersInGame.stream().filter(Tank::isManuallyControlled).collect(Collectors.toList())) {
            playerConverted = createPlayerBuilder(player).build();
            return;
        }
    }

    private void convertBots() {
        botsConverted = new ArrayList<>();
        for (var bot : playersInGame.stream().filter(actor -> !actor.isManuallyControlled()).collect(Collectors.toList()))
            botsConverted.add(createBotBuilder(bot).build());
    }

    private void convertObstacles() {
        obstaclesConverted = new ArrayList<>();
        for (var o : obstaclesInGame) {
            obstaclesConverted.add(new Obstacle(o.getCoordinates().x, o.getCoordinates().y));
        }
    }

    private Bot.BotBuilder createBotBuilder(Tank bot) {
        var botBuilder = new Bot.BotBuilder();
        botBuilder.x(bot.getCoordinates().x);
        botBuilder.y(bot.getCoordinates().y);
        botBuilder.destX(bot.getDestinationCoordinates().x);
        botBuilder.destY(bot.getDestinationCoordinates().y);
        botBuilder.source(bot);

        Orientation orientation = Orientation.S;
        float botRotation = bot.getRotation();
        if (botRotation == 0) {
            orientation = Orientation.E;
        }
        else if (botRotation == -180) {
            orientation = Orientation.W;
        }
        else if (botRotation == 90) {
            orientation = Orientation.N;
        }
        botBuilder.orientation(orientation);
        return botBuilder;
    }

    private org.awesome.ai.state.movable.Player.PlayerBuilder createPlayerBuilder(Tank tank) {
        var playerBuilder = new org.awesome.ai.state.movable.Player.PlayerBuilder();
        playerBuilder.x(tank.getCoordinates().x);
        playerBuilder.y(tank.getCoordinates().y);
        playerBuilder.destX(tank.getDestinationCoordinates().x);
        playerBuilder.destY(tank.getDestinationCoordinates().y);
        playerBuilder.source(tank);

        Orientation orientation = Orientation.S;
        float botRotation = tank.getRotation();
        if (botRotation == 0) {
            orientation = Orientation.E;
        }
        else if (botRotation == -180) {
            orientation = Orientation.W;
        }
        else if (botRotation == 90) {
            orientation = Orientation.N;
        }
        playerBuilder.orientation(orientation);
        return playerBuilder;
    }
}
