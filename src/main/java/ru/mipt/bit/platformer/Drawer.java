package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.graphics.TankTexture;
import ru.mipt.bit.platformer.graphics.TreeTexture;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class Drawer {
    public static void clearScreen(){
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public static void draw(Batch batch, Tank tank, TankTexture tankTexture, List<TreeTexture> treeTextures) {
        batch.begin();
        // render player
        drawTextureRegionUnscaled(batch, tankTexture.getGraphics(), tankTexture.getRectangle(), tank.getRotation());

        // render tree obstacle
        for (TreeTexture treeTexture : treeTextures) {
            drawTextureRegionUnscaled(batch, treeTexture.getGraphics(), treeTexture.getRectangle(), 0f);
        }

        batch.end();
    }
}
