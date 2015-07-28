package com.jorgegil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.jorgegil.boardobjects.Square;
import com.jorgegil.gameboard.GameBoard;
import com.jorgegil.gameboard.GameRenderer;

import java.util.ArrayList;

/**
 * Created by jorgegil on 7/27/15.
 */
public class GameScreen implements Screen{

    private GameBoard board;
    private GameRenderer renderer;
    private float runTime = 0, dropTime = 1, moveTime = 0.1f, rotateTime = 0.2f;

    public GameScreen() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 250;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);
        board = new GameBoard();
        renderer = new GameRenderer(board, (int) gameHeight, midPointY);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        dropTime -= delta;
        moveTime -= delta;
        rotateTime -= delta;

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            if(!Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
                if (moveTime <= 0) {
                    board.moveLeft();
                    moveTime = 0.1f;
                }
            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            if(!Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
                if (moveTime <= 0) {
                    board.moveRight();
                    moveTime = 0.1f;
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            if (moveTime <= 0) {
                board.moveDown();
                moveTime = 0.1f;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            if (rotateTime <= 0) {
                board.rotateCW();
                rotateTime = 0.2f;
            }

        }

        if(dropTime <= 0) {
            board.update(delta);
            dropTime = 1;
        }

        renderer.render(runTime);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
