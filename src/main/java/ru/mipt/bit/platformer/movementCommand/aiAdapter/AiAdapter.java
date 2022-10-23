package ru.mipt.bit.platformer.movementCommand.aiAdapter;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AiAdapter {
    private final AI strategy;
    private ArrayList<OnScreenObject> obstaclesInGame;
    private ArrayList<Player> playersInGame;
    private ArrayList<Obstacle> obstaclesConverted;
    private org.awesome.ai.state.movable.Player playerConverted;
    private final int levelWidth;
    private final int levelHeight;
    private List<Bot> botsConverted;
    public HashMap<Player, GridPoint2> getNextMovement (ArrayList<OnScreenObject> obstacles, ArrayList<Player> players) {
        HashMap<Player, GridPoint2> ret = new HashMap<>();
        this.obstaclesInGame = obstacles;
        this.playersInGame = players;

        convertObstacles();
        convertBots();
        convertPlayer();

        for (var recommendation : strategy.recommend(getGameStateBuilder().build()))
            ret.put((Player) recommendation.getActor().getSource(), getActionType(recommendation));

        return ret;
    }

    private GridPoint2 getActionType(Recommendation recommendation) {
        GridPoint2 movement;
        if (recommendation.getAction() == Action.MoveNorth){
            movement = new  GridPoint2(0, 1);
        } else if (recommendation.getAction() == Action.MoveSouth){
            movement = new GridPoint2(0, -1);
        } else if (recommendation.getAction() == Action.MoveWest){
            movement = new GridPoint2(-1, 0);
        } else if (recommendation.getAction() == Action.MoveEast){
            movement = new GridPoint2(1, 0);
        } else {
            movement = new GridPoint2(0, 0);
        }
        return movement;
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

    public AiAdapter(int levelWidth, int levelHeight, AI strategy) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.strategy = strategy;
    }

    private void convertPlayer() {
        for (var player : playersInGame.stream().filter(Player::isManuallyControlled).collect(Collectors.toList())) {
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

    private Bot.BotBuilder createBotBuilder(Player bot) {
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

    private org.awesome.ai.state.movable.Player.PlayerBuilder createPlayerBuilder(Player player) {
        var playerBuilder = new org.awesome.ai.state.movable.Player.PlayerBuilder();
        playerBuilder.x(player.getCoordinates().x);
        playerBuilder.y(player.getCoordinates().y);
        playerBuilder.destX(player.getDestinationCoordinates().x);
        playerBuilder.destY(player.getDestinationCoordinates().y);
        playerBuilder.source(player);

        Orientation orientation = Orientation.S;
        float botRotation = player.getRotation();
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
