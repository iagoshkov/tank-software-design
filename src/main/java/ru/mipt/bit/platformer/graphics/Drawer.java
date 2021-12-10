package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.graphics.textures.BulletTexture;
import ru.mipt.bit.platformer.graphics.textures.TankTexture;
import ru.mipt.bit.platformer.graphics.textures.TreeTexture;
import ru.mipt.bit.platformer.objects.gameObjects.Bullet;
import ru.mipt.bit.platformer.objects.gameObjects.Tank;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class Drawer {
    public static void clearScreen(){
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public static void draw(Batch batch, List<Tank> tanks, List<TankTexture> tankTextures, List<TreeTexture> treeTextures,
                            List<Bullet> bullets, List<BulletTexture> bulletTextures) {
        batch.begin();

        for (int i = 0; i < tanks.size(); i++) {
            drawTextureRegionUnscaled(batch, tankTextures.get(i).getGraphics(),
                                      tankTextures.get(i).getRectangle(),
                                      tanks.get(i).getRotation());
            if (tankTextures.get(i).isHealthOn()) {
                tankTextures.get(i).getHealthGraphics().setRegionWidth(tanks.get(i).getLife());
                drawTextureRegionUnscaled(batch,
                        tankTextures.get(i).getHealthGraphics(),
                        tankTextures.get(i).getHealthRectangle().setCenter(
                                tankTextures.get(i).getHealthRectangle().getX() + 50f,
                                tankTextures.get(i).getHealthRectangle().getY() - 50f),
                        180f);
            }
        }

        for (TreeTexture treeTexture : treeTextures) {
            drawTextureRegionUnscaled(batch, treeTexture.getGraphics(), treeTexture.getRectangle(), 0f);
        }

        for (int i = 0; i < bullets.size(); i++) {
            drawTextureRegionUnscaled(batch, bulletTextures.get(i).getGraphics(),
                    bulletTextures.get(i).getRectangle(),
                    bullets.get(i).getRotation());
        }

        batch.end();
    }
}
