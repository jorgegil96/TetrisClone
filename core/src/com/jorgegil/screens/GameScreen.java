package com.jorgegil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.jorgegil.gameboard.GameBoard;
import com.jorgegil.gameboard.GameRenderer;

/**
 * Created by jorgegil on 7/27/15.
 */
public class GameScreen implements Screen{

    private GameBoard board;
    private GameRenderer renderer;
    private float runTime = 0, dropTime = 1, moveTime = 0.1f, downTime = 0.1f, rotateTime = 0.1f;

    public GameScreen() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 160;
        float gameHeight = screenHeight / (screenWidth / gameWidth);


        board = new GameBoard();
        renderer = new GameRenderer(board, (int) gameHeight);

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        dropTime -= delta;
        moveTime -= delta;
        downTime -= delta;
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
            if (downTime <= 0) {
                board.moveDown();
                downTime = 0.1f;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            if (rotateTime <= 0) {
                board.rotate(0);
                rotateTime = 0.1f;
            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (rotateTime <= 0) {
                board.rotate(1);
                rotateTime = 0.1f;
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
