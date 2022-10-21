package ru.mipt.bit.platformer.Controllers;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.Entities.Tank;
import ru.mipt.bit.platformer.Entities.TankEntity;
import ru.mipt.bit.platformer.Obstacles.IObstacleInLevel;

public interface IControl {
    void control(TankEntity tank, Input input, IObstacleInLevel levelLet);
}