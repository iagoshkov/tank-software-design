package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.generators.IntegerGenerator;
import ru.mipt.bit.platformer.util.TileMovement;

public class DestroyableTankAI extends TankAI implements Destroyable {

    private int health;
    private boolean showHealth = false;

    public DestroyableTankAI(Texture texture,
                             GridPoint2 coordinates,
                             float movementSpeed,
                             float movementProgress,
                             float rotation,
                             TileMovement tileMovement,
                             IntegerGenerator integerGenerator,
                             int health) {
        super(texture, coordinates, movementSpeed, movementProgress, rotation, tileMovement, integerGenerator);
        this.health = health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void destroy() {
        setHealth(0);
    }

    @Override
    public void showHealth(){
        setHealth(true);
    }

    private void setHealth(boolean showHealth) {
        this.showHealth = showHealth;
    }

}
