package ru.mipt.bit.platformer.game.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public void setCoordinates(Point coordinate) {
        this.layout.coordinates = new Point(coordinate.x, coordinate.y);
    }

    private static class Layout {
        TextureRegion graphics;
        Rectangle rectangle;
        Point coordinates;
        float rotation = 0f;

        public Layout(TextureRegion graphics, Rectangle rectangle) {
            this.graphics = graphics;
            this.rectangle = rectangle;
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public Point getCoordinates() {
        return new Point(layout.coordinates.x, layout.coordinates.y);
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
