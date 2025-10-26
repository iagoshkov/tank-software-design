package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class TankView extends GameObjectView {
    private final TileMovement tileMovement;

    public TankView(TankModel model, TextureRegion graphics, TiledMapTileLayer groundLayer, TileMovement tileMovement) {
        super(model, graphics, groundLayer);
        this.tileMovement = tileMovement;
    }

    @Override
    public void update(float deltaTime) {
        TankModel tankModel = (TankModel) model;
        tankModel.update(deltaTime);
        tileMovement.moveRectangleBetweenTileCenters(rectangle, tankModel.getCoordinates(), tankModel.getDestinationCoordinates(), tankModel.getMovementProgress());
    }
}