package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class PlayerModel {
    /**
     * Координаты клетки, в которой находится игрок
     */
    private GridPoint2 playerCoordinates;

    /**
     * Координаты клетки, к которой движется игрок
     */
    private GridPoint2 playerDestinationCoordinates;

    /**
     * Прогресс перемещения игрока между клетками (от 0 до 1)
     */
    private float playerMovementProgress;

    /**
     * Угол поворота игрока (в градусах)
     */
    private float playerRotation;

    public PlayerModel(GridPoint2 startCoordinates) {
        this.playerCoordinates = startCoordinates;
        this.playerDestinationCoordinates = startCoordinates;
        this.playerMovementProgress = 0f;
        this.playerRotation = 0f;
    }

    public void moveTo(GridPoint2 destination) {
        if (!isMoving()) {
            this.playerDestinationCoordinates = destination;
            this.playerMovementProgress = 0f;
        }
    }

    public void update(float deltaTime, float movementSpeed) {
        if (isMoving()) {
            playerMovementProgress += deltaTime * movementSpeed;
            if (playerMovementProgress >= 1f) {
                playerCoordinates = playerDestinationCoordinates;
                playerMovementProgress = 0f;
            }
        }
    }

    public boolean isMoving() {
        return !playerCoordinates.equals(playerDestinationCoordinates);
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public GridPoint2 getPlayerDestinationCoordinates() {
        return playerDestinationCoordinates;
    }

    public float getPlayerMovementProgress() {
        return playerMovementProgress;
    }

    public float getPlayerRotation() {
        return playerRotation;
    }

    public void setPlayerRotation(float playerRotation) {
        this.playerRotation = playerRotation;
    }
}
