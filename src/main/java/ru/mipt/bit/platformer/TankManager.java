// ru/mipt/bit/platformer/TankManager.java
package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.commands.MoveCommand;
import ru.mipt.bit.platformer.commands.ShootCommand;
import ru.mipt.bit.platformer.controllers.AIController;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.List;

public class TankManager {
    private final Level level;
    private final List<Tank> aiTanks;
    private final List<AIController> aiControllers;

    public TankManager(Level level) {
        this.level = level;
        this.aiTanks = new ArrayList<>();
        this.aiControllers = new ArrayList<>();
    }

    public void addAITank(Tank tank) {
        aiTanks.add(tank);
        aiControllers.add(new AIController());
    }

    public void updateAITanks(float deltaTime) {
        for (int i = 0; i < aiTanks.size(); i++) {
            Tank tank = aiTanks.get(i);
            AIController controller = aiControllers.get(i);
            
            if (!tank.isMoving()) {
                // Сначала проверяем стрельбу
                if (controller.shouldShoot() && tank.canShoot()) {
                    ShootCommand shootCommand = new ShootCommand(tank);
                    shootCommand.execute();
                } else {
                    // Если не стреляем, то двигаемся
                    MoveCommand moveCommand = new MoveCommand(tank, controller.getInputDirection());
                    moveCommand.execute();
                }
            }
            
            tank.update(deltaTime);
        }
    }

    public List<Tank> getAllTanks() {
        List<Tank> allTanks = new ArrayList<>(aiTanks);
        level.getTanks().stream()
                .filter(Tank::isPlayerControlled)
                .findFirst()
                .ifPresent(allTanks::add);
        return allTanks;
    }
}