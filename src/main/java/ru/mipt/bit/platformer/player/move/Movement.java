package ru.mipt.bit.platformer.player.move;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.S;

public class Movement {
    private static float currRotation = 0f;
    private static GridPoint2 currMovementVector =  new GridPoint2(0, 0);


    public static void movementKey(Input key){
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

    public static float getCurrRotation() {
        return currRotation;
    }

    public static GridPoint2 getCurrMovementVector() {
        return currMovementVector;
    }

    public static void setCurrRotation(float currRotation) {
        Movement.currRotation = currRotation;
    }

    public static void setCurrMovementVector(GridPoint2 currMovementVector) {
        Movement.currMovementVector = currMovementVector;
    }
}
