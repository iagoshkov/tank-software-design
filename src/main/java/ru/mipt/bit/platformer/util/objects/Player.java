package ru.mipt.bit.platformer.util.objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;


import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Player {

    public Texture blueTankTexture;
    public TextureRegion playerGraphics;
    public Rectangle playerRectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    public GridPoint2 playerCoordinates;
    // which tile the player want to go next
    public GridPoint2 playerDestinationCoordinates;
    public float playerMovementProgress;
    public float playerRotation;



    public void createPlayerObject(){

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        blueTankTexture = new Texture("images/tank_blue.png");
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        playerGraphics = new TextureRegion(blueTankTexture);
        playerRectangle = createBoundingRectangle(playerGraphics);
        // set player initial position
        playerDestinationCoordinates = new GridPoint2(1, 1);
        playerCoordinates = new GridPoint2(playerDestinationCoordinates);
        playerRotation = 0f;
    }




}
