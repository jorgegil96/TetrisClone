package com.jorgegil.gameboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jorgegil.boardobjects.Square;
import com.jorgegil.zbHelpers.AssetLoader;

import java.util.ArrayList;

/**
 * Created by jorgegil on 7/27/15.
 */
public class GameRenderer {

    private GameBoard myBoard;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY, gameHeight;

    public GameRenderer(GameBoard board, int gameHeight, int midPointY) {
        myBoard = board;

        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 250, 500);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    public void render(float runTime) {

        ArrayList<Square> squares = myBoard.getSquares();

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0, 0, 250, 500);

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
            batcher.draw(AssetLoader.pieceI, s.getX(), s.getY(), 25, 25);
        }

        // The bird needs transparency, so we enable that again.
        batcher.enableBlending();


        // End SpriteBatch
        batcher.end();

    }
}
