package ru.mipt.bit.platformer.game.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.entities.Coordinates;
import ru.mipt.bit.platformer.game.entities.GameEntity;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class LevelEntity {
    /*
    Класс объекта, размещенного на карте. Может быть кем угодно, лишь бы была нужная текстурка.
    */
    private final GameEntity gameEntity;
    private final Texture texture;
    private final Layout layout;

    public LevelEntity(GameEntity entity, String texturePath) {
        this.gameEntity = entity;
        this.texture = new Texture(texturePath);
        TextureRegion graphics = new TextureRegion(texture);
        Rectangle rectangle = createBoundingRectangle(graphics);
        this.layout = new Layout(graphics, rectangle);
    }

    private static class Layout {
        TextureRegion graphics;
        Rectangle rectangle;

        public Layout(TextureRegion graphics, Rectangle rectangle) {
            this.graphics = graphics;
            this.rectangle = rectangle;
        }
    }

    public void dispose() {
        texture.dispose();
    }


    public Rectangle getRectangle() {
        return layout.rectangle;
    }


    public TextureRegion getGraphics() {
        return layout.graphics;
    }

    public Coordinates getCoordinates() {
        return gameEntity.getCoordinates();
    }

    public float getRotation() {
        return gameEntity.getRotation();
    }

}
