package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.objects.Drawable;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.TankAI;
import ru.mipt.bit.platformer.objects.Tree;

import static org.junit.jupiter.api.Assertions.*;

class CharToDrawableConverterTest {

//    @Test
//    void getCharFromTree() {
//        CharToDrawableConverter converter = new CharToDrawableConverter();
//        Drawable tree = new Tree(new Texture("images/tank_blue.png"), new GridPoint2(0,0), null);
//        assertEquals('T', converter.getCharFromDrawable(tree));
//    }
//
//    @Test
//    void getCharFromTank() {
//        CharToDrawableConverter converter = new CharToDrawableConverter();
//        Drawable tank = new Tank(new Texture("images/tank_blue.png"), new GridPoint2(0,
//                0), 0.1f, 1f, 0f, null);
//        assertEquals('X', converter.getCharFromDrawable(tank));
//    }
//
//    @Test
//    void getCharFromTankAI() {
//        CharToDrawableConverter converter = new CharToDrawableConverter();
//        Drawable tankAI = new TankAI(new Texture("images/tank_blue.png"),
//                new GridPoint2(0,0),
//                0.1f,
//                1f,
//                0f,
//                null,
//                null);
//        assertEquals('A', converter.getCharFromDrawable(tankAI));
//    }

    @Test
    void isCharExists() {
        CharToDrawableConverter converter = new CharToDrawableConverter();
        boolean res1 =  converter.isCharExists('A');
        boolean res2 =  converter.isCharExists('X');
        boolean res3 =  converter.isCharExists('T');
        assertTrue(res1 && res2 && res3);
    }
}