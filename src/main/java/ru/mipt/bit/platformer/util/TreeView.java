package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

public class TreeView extends GameObjectView {

    public TreeView(TreeModel model, TextureRegion graphics, TiledMapTileLayer groundLayer) {
        super(model, graphics, groundLayer);
        this.model.rotation = 0f;
    }

    @Override
    public void update(float deltaTime) {
        // Trees are static - no updates
    }

    public boolean occupiesPosition(GridPoint2 position) {
        TreeModel treeModel = (TreeModel) model;
        return treeModel.occupiesPosition(position);
    }
}