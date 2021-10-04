package ru.mipt.bit.platformer.player.move;

import com.badlogic.gdx.math.GridPoint2;

public class NewDestination {
    // which tile the player want to go next
    private static GridPoint2 destinationCoordinates = new GridPoint2(1, 1);
    public static GridPoint2 newCoordinates(){
        GridPoint2 newDestination = new GridPoint2();
        newDestination.x = destinationCoordinates.x + Movement.getCurrMovementVector().x;
        newDestination.y = destinationCoordinates.y + Movement.getCurrMovementVector().y;
        return newDestination;
    }

    public static GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public static void setDestinationCoordinates(GridPoint2 destinationCoordinates) {
        NewDestination.destinationCoordinates = destinationCoordinates;
    }
}
