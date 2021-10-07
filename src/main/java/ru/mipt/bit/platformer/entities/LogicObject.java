package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogicObject {
    private float rotation;
    private GridPoint2 coordinates;
}
