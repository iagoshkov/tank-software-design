package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.generator.LevelGenerator;
import ru.mipt.bit.platformer.movementCommand.MovementCommand;
import ru.mipt.bit.platformer.movementCommand.RandomMovementCommand;
import ru.mipt.bit.platformer.movementCommand.TankAction;
import ru.mipt.bit.platformer.movementCommand.UserInputMovementCommand;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.OnScreenObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import static ru.mipt.bit.platformer.GameDesktopLauncher.TANK_INITIAL_MOVEMENT_SPEED;
import static ru.mipt.bit.platformer.GameDesktopLauncher.BULLET_MOVEMENT_SPEED;

public class GameLogic {
    private final HashSet<Tank> tanks = new HashSet<>();
    private final HashSet<OnScreenObject> obstacles = new HashSet<>();
    private final HashSet<Bullet> bullets = new HashSet<>();
    private GameGraphics gameGraphics;

    GameLogic(LevelGenerator generator, GameGraphics gameGraphics) {
        this.gameGraphics = gameGraphics;

        for (var coordinatePair : generator.getObstaclesCoordinates()) {
            var o = new OnScreenObject("images/greenTree.png", coordinatePair);
            obstacles.add(o);
        }

        var tanksCoords = generator.getPlayersCoordinates();
        for (int i = 0; i < tanksCoords.size(); ++i) {
            var coordinatePair = tanksCoords.get(i);
            var o = new Tank("images/tank_blue.png", coordinatePair, TANK_INITIAL_MOVEMENT_SPEED,
                    new GridPoint2(gameGraphics.getFieldWidth(), gameGraphics.getFieldHeight()));
            tanks.add(o);

            if (i == tanksCoords.size() - 1)
                o.setManuallyControlled(true);
        }

        gameGraphics.addDrawableObjects(obstacles);
        gameGraphics.addDrawableObjects(tanks);
    }

    private void updateTanksPositions(float deltaTime) {
        MovementCommand automaticMovement = new RandomMovementCommand();
//        AiMovementCommand aiMovement = new AiMovementCommand(obstacles, tanks, fieldWidth, fieldHeight);
        MovementCommand userInputMovement = new UserInputMovementCommand(Gdx.input);

        Map<Tank, TankAction> userControlledTanksActions = userInputMovement.getTankActions(obstacles, tanks,
                gameGraphics.getFieldWidth(), gameGraphics.getFieldHeight());
        Map<Tank, TankAction> autoControlledTanksActions = automaticMovement.getTankActions(obstacles, tanks,
                gameGraphics.getFieldWidth(), gameGraphics.getFieldHeight());

        updateTanks(userControlledTanksActions, deltaTime);
        updateTanks(autoControlledTanksActions, deltaTime);
    }

    private void updateTanks(Map<Tank, TankAction> actions, float deltaTime) {
        for (var tank : actions.keySet()) {
            if (tank.isDead()) {
                gameGraphics.removeDrawableObject(tank);
                tanks.remove(tank);
                continue;
            }
            TankAction tankAction = actions.get((tank));
            Bullet bullet = tank.update(tankAction, obstacles, tanks, deltaTime);
            if (bullet != null) {
                bullets.add(bullet);
                gameGraphics.addDrawableObject(bullet);
            }
        }
    }

    private void updateBullets(float deltaTime) {
        ArrayList<Bullet> toRemove = new ArrayList<>();
        for (var bullet : bullets) {
            if (bullet.isDead()) {
                toRemove.add(bullet);
                continue;
            }
            bullet.update(obstacles, tanks, deltaTime, BULLET_MOVEMENT_SPEED);
        }
        toRemove.forEach(bullets::remove);
        gameGraphics.removeDrawableObjects(toRemove);
    }

    public void tick(float deltaTime) {
        updateTanksPositions(deltaTime);
        updateBullets(deltaTime);

        gameGraphics.drawAllObjects();
    }
}
