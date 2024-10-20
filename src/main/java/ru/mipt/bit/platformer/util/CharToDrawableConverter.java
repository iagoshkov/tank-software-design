package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.objects.Drawable;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.TankAI;
import ru.mipt.bit.platformer.objects.Tree;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CharToDrawableConverter {
    public final static Map<Character, Class<? extends Drawable>> charToClass = new HashMap<>();
    static
    {
        charToClass.put(TankAI.getDrawableCharacterStatic(), TankAI.class);
        charToClass.put(Tank  .getDrawableCharacterStatic(), Tank  .class);
        charToClass.put(Tree  .getDrawableCharacterStatic(), Tree.class);
    }

    public static Character getCharFromClass(Class<? extends Drawable> clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (Character) clazz.getDeclaredMethod("getDrawableCharacter").invoke(clazz);

    }

    public static Character getCharFromDrawable(Drawable drawable) {
        return drawable.getDrawableCharacter();
    }

    public static boolean isCharExists(Character character) {
        return charToClass.containsKey(character);
    }

    public static Class<? extends Drawable> getClassFromChar(Character character) {
        return charToClass.get(character);
    }
}
