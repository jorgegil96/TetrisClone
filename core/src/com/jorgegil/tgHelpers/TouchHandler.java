package com.jorgegil.tgHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.jorgegil.gameboard.GameBoard;

/**
 * Created by jorgegil on 8/7/15.
 */
public class TouchHandler implements GestureDetector.GestureListener {

    private GameBoard myBoard;
    public boolean panRight, panLeft, panDown, tapStart = false, fling, spacePressed, enterPressed, pPressed,
            shiftTap;

    private Vector2 lastTouch = new Vector2();

    public TouchHandler(GameBoard board) {
        myBoard = board;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        if (tapStart) {
            float xStart = (myBoard.gameWidth * 5) / 160;
            float xEnd = (myBoard.gameWidth * 25) / 160;


            float yStart = ((myBoard.gameHeight * 12) / 200) + (Gdx.graphics.getHeight() - myBoard.gameHeight) / 2;
            float yEnd = ((myBoard.gameHeight * 37) / 200) + (Gdx.graphics.getHeight() - myBoard.gameHeight) / 2;

            if (x >= xStart && x <= xEnd && y >= yStart && y <= yEnd) {
                shiftTap = true;
            } else {
                myBoard.rotate(0);
                System.out.println("rotate called");
            }
        }

        tapStart = true;
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityY > 300) {
            fling = true;
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (!fling) {
            if (deltaX > 3) {
                panLeft = false;
                panRight = true;
            } else if (deltaX < -3) {
                panRight = false;
                panLeft = true;
            } else {
                panRight = false;
                panLeft = false;
            }
        }
        if (deltaY > 3) {
            panDown = true;
        }
        if (deltaY <= 0) {
            panDown = false;
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        panRight = false;
        panLeft = false;
        panDown = false;
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
