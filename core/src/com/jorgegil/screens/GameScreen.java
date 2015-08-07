package com.jorgegil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.jorgegil.gameboard.GameBoard;
import com.jorgegil.gameboard.GameRenderer;
import com.jorgegil.tgHelpers.InputHandler;

/**
 * Created by jorgegil on 7/27/15.
 */
public class GameScreen implements Screen{

    private GameBoard board;
    private GameRenderer renderer;
    InputHandler handler;
    private float runTime = 0, dropTime = 1, moveTime = 0.15f, downTime = 0.15f, rotateTime = 0.1f,
            hardDropTime = 0.3f, pauseTime = 0.5f, holdTime = 0.3f;

    public GameScreen() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 160;
        float gameHeight = screenHeight / (screenWidth / gameWidth);


        board = new GameBoard();
        renderer = new GameRenderer(board, (int) gameHeight);

        handler = new InputHandler(board);
        Gdx.input.setInputProcessor(handler);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        dropTime -= delta;
        moveTime -= delta;
        downTime -= delta;
        rotateTime -= delta;
        hardDropTime -= delta;
        pauseTime -= delta;
        holdTime -= delta;

        if (board.isRunning()) {
            if (handler.leftPressed) {
                if (!handler.rightPressed) {
                    if (moveTime <= 0) {
                        board.moveLeft();
                        moveTime = 0.15f;
                    }
                }
            }
            if (handler.rightPressed) {
                if (!handler.leftPressed) {
                    if (moveTime <= 0) {
                        board.moveRight();
                        moveTime = 0.15f;
                    }
                }
            }
            if (handler.downPressed) {
                if (downTime <= 0) {
                    board.moveDown();
                    downTime = 0.15f;
                }
            }
            if (handler.upPressed) {
                if (rotateTime <= 0) {
                    board.rotate(0);
                    rotateTime = 0.1f;
                }

            }
            if (handler.zPressed) {
                if (rotateTime <= 0) {
                    board.rotate(1);
                    rotateTime = 0.1f;
                }

            }
            if (handler.spacePressed) {
                if(hardDropTime <= 0) {
                    board.hardDrop();
                    dropTime = 0;
                    hardDropTime = 0.3f;
                }
            }
            if (handler.shiftPressed) {
                if (holdTime <= 0) {
                    board.hold();
                    holdTime = 0.3f;
                }
            }
        }
        if (handler.pPressed) {
            if(pauseTime <= 0) {
                if (board.isPaused()) {
                    board.start();
                } else {
                    board.stop();
                }
                pauseTime = 0.5f;
            }
        }
        if (handler.enterPressed) {
            if(board.isReady()) {
                board.start();
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
