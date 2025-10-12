package ru.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import ru.mipt.bit.platformer.objects.Player;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.Direction;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class InputHandlerTest {

    private InputHandler inputHandler;
    private Player player;
    private Tree tree;

    @Before
    public void setUp() {
        player = mock(Player.class);
        tree = mock(Tree.class);
        inputHandler = new InputHandler(player, tree);
        
        when(player.isMoving()).thenReturn(false);
    }

    @Test
    public void testHandleInputUp() {
        try (MockedStatic<Gdx> gdxMock = mockStatic(Gdx.class)) {
            Input input = mock(Input.class);
            gdxMock.when(Gdx::input).thenReturn(input);
            when(input.isKeyPressed(Input.Keys.UP)).thenReturn(true);
            when(input.isKeyPressed(Input.Keys.W)).thenReturn(false);
            
            inputHandler.handleInput();
            
            verify(player).move(Direction.UP);
        }
    }

    @Test
    public void testNoMovementWhenPlayerIsMoving() {
        try (MockedStatic<Gdx> gdxMock = mockStatic(Gdx.class)) {
            Input input = mock(Input.class);
            gdxMock.when(Gdx::input).thenReturn(input);
            when(input.isKeyPressed(anyInt())).thenReturn(true);
            when(player.isMoving()).thenReturn(true);
            
            inputHandler.handleInput();
            
            verify(player, never()).move(any(Direction.class));
        }
    }
}