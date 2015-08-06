package com.jorgegil.tgHelpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.jorgegil.gameboard.GameBoard;

/**
 * Created by jorgegil on 7/30/15.
 */
public class InputHandler implements InputProcessor {

    private GameBoard myBoard;
    public boolean rightPressed, leftPressed, downPressed, upPressed, zPressed, spacePressed, enterPressed, pPressed;

    public InputHandler(GameBoard board) {
        myBoard = board;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.DPAD_RIGHT:
                rightPressed = true;
                break;
            case Input.Keys.DPAD_LEFT:
                leftPressed = true;
                break;
            case Input.Keys.DPAD_DOWN:
                downPressed = true;
                break;
            case Input.Keys.DPAD_UP:
                upPressed = true;
                break;
            case Input.Keys.Z:
                zPressed = true;
                break;
            case Input.Keys.SPACE:
                spacePressed = true;
                break;
            case Input.Keys.ENTER:
                enterPressed = true;
                break;
            case Input.Keys.P:
                pPressed = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.DPAD_RIGHT:
                rightPressed = false;
                break;
            case Input.Keys.DPAD_LEFT:
                leftPressed = false;
                break;
            case Input.Keys.DPAD_DOWN:
                downPressed = false;
                break;
            case Input.Keys.DPAD_UP:
                upPressed = false;
                break;
            case Input.Keys.Z:
                zPressed = false;
                break;
            case Input.Keys.SPACE:
                spacePressed = false;
                break;
            case Input.Keys.ENTER:
                enterPressed = false;
                break;
            case Input.Keys.P:
                pPressed = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
