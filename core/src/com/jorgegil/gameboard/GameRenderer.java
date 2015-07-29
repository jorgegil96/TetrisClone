package com.jorgegil.gameboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jorgegil.boardobjects.Square;
import com.jorgegil.tgHelpers.AssetLoader;

import java.util.ArrayList;

/**
 * Created by jorgegil on 7/27/15.
 */
public class GameRenderer {

    private GameBoard myBoard;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int gameHeight, veticalBar;

    public GameRenderer(GameBoard board, int gameHeight) {
        myBoard = board;

        this.gameHeight = gameHeight;
        veticalBar = (gameHeight - 200) / 2;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 160, gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);


    }

    public void render(float runTime) {

        ArrayList<Square> squares = myBoard.getSquares();
        ArrayList<Square> tetrominoe = myBoard.getTetrominoe();

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Border Colors
        shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 153 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 30, gameHeight);
        shapeRenderer.rect(130, 0, 30, gameHeight);
        shapeRenderer.rect(30, 0, 100, veticalBar);
        shapeRenderer.rect(30, gameHeight - veticalBar, 100, veticalBar);

        // Draw Background color
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(30, veticalBar, 100, 200);

        // Draw Hold Square
        shapeRenderer.rect(5, veticalBar + 12, 20, 20);
        //Draw Next Squares
        shapeRenderer.rect(135, veticalBar + 12, 20, 20);
        shapeRenderer.rect(135, veticalBar + 37, 20, 20);
        shapeRenderer.rect(135, veticalBar + 62, 20, 20);
        shapeRenderer.rect(135, veticalBar + 87, 20, 20);
        shapeRenderer.rect(135, veticalBar + 112, 20, 20);

        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        //batcher.draw(AssetLoader.pieceI, 25, 0, 25 / 2, 25 / 2);

        for(Square s : squares) {
            batcher.draw(AssetLoader.colors.get(s.getColor()), s.getX() + 30, s.getY() + veticalBar, 10, 10);
        }

        for(Square s : tetrominoe) {
            batcher.draw(AssetLoader.colors.get(s.getColor()), s.getX() + 30, s.getY() + veticalBar, 10, 10);
        }

        AssetLoader.font.draw(batcher, "HOLD", 8, 3);
        AssetLoader.font.draw(batcher, "NEXT", 137, 3);
        AssetLoader.font.draw(batcher, "LEVEL", 6, 75);
        AssetLoader.font.draw(batcher, "" + myBoard.getLevel(), 13, 85);
        AssetLoader.font.draw(batcher, "GOAL", 7, 100);
        AssetLoader.font.draw(batcher, "" + myBoard.getGoal(), 10, 110);
        AssetLoader.font.draw(batcher, "SCORE", 5, 125);
        AssetLoader.font.draw(batcher, "" + myBoard.getScore(), 9, 135);
        AssetLoader.font.draw(batcher, "HIGH", 7, 165);
        AssetLoader.font.draw(batcher, "SCORE", 5, 175);
        AssetLoader.font.draw(batcher, "" + myBoard.getScore(), 9, 185);

        if (myBoard.isReady()) {
            AssetLoader.font.draw(batcher, "READY", 70, (gameHeight / 2) - 10);
        }
        else if (myBoard.isPaused()) {
            AssetLoader.font.draw(batcher, "PAUSED", 70, (gameHeight / 2) - 10);
        }
        else if (myBoard.isGameOver()) {
            AssetLoader.font.draw(batcher, "GAME OVER", 60, (gameHeight / 2) - 10);
        }


        // End SpriteBatch
        batcher.end();

    }
}
