package ru.mipt.bit.platformer.movementCommand;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.AI;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.movementCommand.aiAdapter.AiAdapter;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class AiMovementCommand implements MovementCommand {
    private ArrayList<OnScreenObject> obstacles;
    private ArrayList<Player> players;
    private int fieldWidth, fieldHeight;
    private static AiAdapter adapter;
    public AiMovementCommand(ArrayList<OnScreenObject> obstacles, ArrayList<Player> players, int fieldWidth, int fieldHeight) {
        this.obstacles = obstacles;
        this.players = players;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        adapter = new AiAdapter(fieldWidth, fieldHeight, new NotRecommendingAI());
    }

    @Override
    public HashMap<Player, GridPoint2> getTankActions(ArrayList<OnScreenObject> obstacles, ArrayList<Player> players, int fieldWidth, int fieldHeight) {
        if (fieldWidth != this.fieldWidth || fieldHeight != this.fieldHeight)
            adapter = new AiAdapter(fieldWidth, fieldHeight, new NotRecommendingAI());
        return adapter.getNextMovement(obstacles, players);
    }
}
