package ru.mipt.bit.platformer.game.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class LevelEntity {
    /*
    Класс объекта, размещенного на карте. Может быть кем угодно, лишь бы была нужная текстурка.
    */
    private final Texture texture;
    private final Layout layout;

    public LevelEntity(String texturePath) {
        this.texture = new Texture(texturePath);
        TextureRegion graphics = new TextureRegion(texture);
        Rectangle rectangle = createBoundingRectangle(graphics);
        this.layout = new Layout(graphics, rectangle);
    }

    public void setCoordinates(int x, int y) {
        this.layout.coordinates = new GridPoint2(x, y);
    }

    private static class Layout {
        TextureRegion graphics;
        Rectangle rectangle;
        GridPoint2 coordinates = new GridPoint2();
        float rotation = 0f;

        public Layout(TextureRegion graphics, Rectangle rectangle) {
            this.graphics = graphics;
            this.rectangle = rectangle;
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public GridPoint2 getCoordinates() {
        return layout.coordinates;
    }

    public Rectangle getRectangle() {
        return layout.rectangle;
    }

    public void setRotation(float rotation) {
        layout.rotation = rotation;
    }

    public float getRotation() {
        return layout.rotation;
    }

    public TextureRegion getGraphics() {
        return layout.graphics;
    }
}
