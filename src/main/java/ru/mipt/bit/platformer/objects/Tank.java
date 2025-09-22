package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.graphics.g2d.Batch;


public class Tank {
    private Texture blueTankTexture;
    private TextureRegion TankGraphics;
    private Rectangle TankRectangle;
    

    private GridPoint2 TankCoordinates;
    private GridPoint2 TankDestinationCoordinates;
    private float TankSpeed = 0.4f;
    private float TankRotation;
    private float TankMovementProggress = 1f;


    // Texture decodes an image file and loads it into GPU memory, it represents a native resource
    public Tank() {
        blueTankTexture = new Texture("images/tank_blue.png");
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        TankGraphics = new TextureRegion(blueTankTexture);
        TankRectangle = createBoundingRectangle(TankGraphics);

        TankDestinationCoordinates = new GridPoint2(1, 1);
        TankCoordinates = new GridPoint2(TankDestinationCoordinates);
        TankRotation = 0f;
    }
        
    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, TankGraphics, TankRectangle, TankRotation);
    }
    

    public Rectangle getTankRectangle(){
        return TankRectangle;
    }

    public GridPoint2 getTankCoordinates() {
        return TankCoordinates;
    }

    public void dispose() {
        blueTankTexture.dispose();
    }

    public void move (float dx, float dy ) {
        TankDestinationCoordinates.x += dx;
        TankDestinationCoordinates.y += dy;
    }

    public void setRotation(float angle) {
        this.TankRotation = angle;
    }

    public float getTankMovementProggress() {
        return TankMovementProggress;
    }

    public void setTankMovementProggress(float x) {
        TankMovementProggress = x;
    }

    public void update(float deltaTime, TileMovement tileMovement) {
        // обновляем прогресс движения от 0 до 1
        TankMovementProggress = continueProgress(TankMovementProggress, deltaTime, TankSpeed);

        // плавное перемещение прямоугольника между тайлами
        tileMovement.moveRectangleBetweenTileCenters(TankRectangle, TankCoordinates, TankDestinationCoordinates, TankMovementProggress);

        // если достигли цели, фиксируем координаты
        if (isEqual(TankMovementProggress, 1f)) {
            TankCoordinates.set(TankDestinationCoordinates);
        }
    }
}

