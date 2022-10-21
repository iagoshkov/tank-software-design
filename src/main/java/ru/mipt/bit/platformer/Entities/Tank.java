package ru.mipt.bit.platformer.Entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Controllers.IControl;
import ru.mipt.bit.platformer.Obstacles.IObstacleInLevel;
import ru.mipt.bit.platformer.Render.Visualisation;

public class Tank{
    public TankEntity entity;
    public Visualisation visualisation;
    public IControl movement;
    public Tank(String texturePath, GridPoint2 position, float rotation, IControl movement){
        this.entity = new TankEntity(position, rotation);
        this.visualisation = new Visualisation(texturePath);
        this.movement = movement;
    }
    public void getInValues(Input inputValues, IObstacleInLevel obstacleInLevel){
        this.movement.control(this.entity, inputValues, obstacleInLevel);
    }
}
