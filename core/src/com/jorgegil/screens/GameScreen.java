package com.jorgegil.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jorgegil.gameboard.GameBoard;
import com.jorgegil.gameboard.GameRenderer;
import com.jorgegil.tgHelpers.InputHandler;
import com.jorgegil.tgHelpers.TouchHandler;

/**
 * Created by jorgegil on 7/27/15.
 */
public class GameScreen implements Screen{

    private static final float MIN_SCENE_WIDTH = 160.0f;
    private static final float MIN_SCENE_HEIGHT = 200.0f;

    private Viewport viewport;
    private Camera camera;

    private GameBoard board;
    private GameRenderer renderer;

    InputHandler handler;
    TouchHandler touchHandler;
    InputMultiplexer inputMultiplexer;

    private float runTime = 0, dropTime, moveTime = 0.15f, downTime = 0.15f, rotateTime = 0.1f,
            hardDropTime = 0.3f, pauseTime = 0.5f, holdTime = 0.3f;

    private int level;

    public GameScreen() {

        camera = new PerspectiveCamera();
        viewport = new FitViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, camera);

        board = new GameBoard();
        renderer = new GameRenderer(board);

        handler = new InputHandler(board);
        touchHandler = new TouchHandler(board);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(handler);
        inputMultiplexer.addProcessor(new GestureDetector(touchHandler));




        Gdx.input.setInputProcessor(inputMultiplexer);

        getDropTime();

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
            if (handler.leftPressed || touchHandler.panLeft) {
                if (!handler.rightPressed || !touchHandler.panRight) {
                    if (moveTime <= 0) {
                        board.moveLeft();
                        moveTime = 0.15f;
                    }
                }
            }
            if (handler.rightPressed || touchHandler.panRight) {
                if (!handler.leftPressed || !touchHandler.panLeft) {
                    if (moveTime <= 0) {
                        board.moveRight();
                        moveTime = 0.15f;
                    }
                }
            }
            if (handler.downPressed ||touchHandler.panDown) {
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
            if (handler.spacePressed || touchHandler.fling) {
                if(hardDropTime <= 0) {
                    board.hardDrop();
                    dropTime = 0;
                    hardDropTime = 0.3f;
                }
                touchHandler.fling = false;
            }
            if (handler.shiftPressed || touchHandler.shiftTap) {
                if (holdTime <= 0) {
                    board.hold();
                    holdTime = 0.3f;
                }
                touchHandler.shiftTap = false;
            }
            if(dropTime <= 0) {
                board.update(delta);
                getDropTime();
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
        if (handler.enterPressed || touchHandler.tapStart) {
            if(board.isReady()) {
                board.start();
            }
        }

        renderer.render(runTime);
    }

    public void getDropTime() {

        level = board.getLevel();

        if (level <= 8) {
            dropTime = (48.0f - (5.0f * (level - 1))) / 60.0f;
        }
        else if (level == 9) {
            dropTime = 6.0f / 48.0f;
        }
        else if (level >= 10 && level <= 12) {
            dropTime = 5.0f / 48.0f;
        }
        else if (level >= 13 && level <= 15) {
            dropTime = 4.0f / 48.0f;
        }
        else if (level >= 16 && level <= 18) {
            dropTime = 3.0f / 48.0f;
        }
        else if (level >= 19 && level <= 28) {
            dropTime = 2.0f / 48.0f;
        }
        else {
            dropTime = 1.0f / 60.0f;
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
