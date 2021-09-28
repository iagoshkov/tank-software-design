package ru.mipt.bit.platformer.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import javax.print.attribute.standard.Destination;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static com.badlogic.gdx.Input.Keys.*;

public class Player {
    public static final float MOVEMENT_SPEED = 0.4f;
    public final TextureRegion graphics;
    public final Rectangle rectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    public GridPoint2 coordinates = new GridPoint2(1,0);
    // which tile the player want to go next
    public GridPoint2 destinationCoordinates = new GridPoint2(1, 1);
    public float movementProgress = 1f;
    public float rotation = 0f;
    public float currRotation = 0f;
    public GridPoint2 currMovementVector =  new GridPoint2(0, 0);

    public Player(Texture blueTankTexture){
        this.graphics = new TextureRegion(blueTankTexture);
        this.rectangle = createBoundingRectangle(graphics);
    }

    public void movementKey(Input key){
        if(key.isKeyPressed(UP) || key.isKeyPressed(W)){
            currRotation = 90f;
            currMovementVector = new GridPoint2(0,1);
        }else if(key.isKeyPressed(LEFT) || key.isKeyPressed(A)){
            currRotation = -180f;
            currMovementVector = new GridPoint2(-1,0);
        }else if(key.isKeyPressed(RIGHT) || key.isKeyPressed(D)){
            currRotation = 0f;
            currMovementVector = new GridPoint2(1,0);
        }else if(key.isKeyPressed(DOWN) || key.isKeyPressed(S)){
            currRotation = -90f;
            currMovementVector = new GridPoint2(0,-1);
        }
    }

    public GridPoint2 newCoordinates(){
        GridPoint2 newDestination = new GridPoint2();
        newDestination.x = destinationCoordinates.x + currMovementVector.x;
        newDestination.y = destinationCoordinates.y + currMovementVector.y;
        return newDestination;
    }
}
