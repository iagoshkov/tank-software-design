package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class HERO {
    private static final float MOVEMENT_SPEED = 0.4f;
    private static Texture TankTexture;
    private static TextureRegion TankGraphics;
    private static Rectangle TankRectangle;
    private static GridPoint2 TankDestinationCoordinates = new GridPoint2(1, 1);
    private static GridPoint2 TankCoordinates;
    private static float TankMovementProgress = 1f;
    private static float TankRotation = 0f;

    public void CreateTankGraphics() {
        this.TankTexture = new Texture("images/tank_blue.png");
        this.TankGraphics = new TextureRegion(this.TankTexture);
        this.TankRectangle = createBoundingRectangle(this.TankGraphics);
    }

    public HERO(GridPoint2 coordinates, float rotation) {
        this.TankDestinationCoordinates = coordinates;
        this.TankCoordinates = coordinates;
        this.TankRotation = rotation;
        this.CreateTankGraphics();
    }

    public static GridPoint2 TankCoordinates() {
        return TankCoordinates;
    }

    public static GridPoint2 TankDestinationCoordinates() {
        return TankDestinationCoordinates;
    }

    public static float TankMovementProgress() {
        return TankMovementProgress;
    }

    public static Rectangle TankRectangle() {
        return TankRectangle;
    }

    public void checkMove(ANGLE direction, TREE tree, TREES trees) {
        if (!isEqual(TankMovementProgress, 1f))
            return;
        GridPoint2 new_xy = new GridPoint2(TankCoordinates);
        new_xy.add(direction.Coordinates());

        TankRotation = direction.ANGLE();
        GridPoint2 estimatedCoordinates = TankCoordinates.add(direction.Coordinates());
        for (TREE _tree : trees.treeslist)
            if (_tree.TreeCoordinates().equals(estimatedCoordinates))
                return;
        TankMovementProgress = 0f;
        TankDestinationCoordinates = new_xy;
    }

    public void Move(TREE tree, TREES trees) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W))
            checkMove(new ANGLE(0, 1), tree, trees);
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A))
            checkMove(new ANGLE(-1, 0), tree, trees);
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S))
            checkMove(new ANGLE(0, -1), tree, trees);
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D))
            checkMove(new ANGLE(1, 0), tree, trees);

        TankMovementProgress = continueProgress(TankMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(TankMovementProgress, 1f)) {
            TankCoordinates.set(TankDestinationCoordinates);
        }
    }

    public void Render(Batch batch){
        drawTextureRegionUnscaled(batch, TankGraphics, TankRectangle, TankRotation);
    }

    public void dispose(){
        TankTexture.dispose();
    }

}