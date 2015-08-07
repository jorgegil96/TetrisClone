package com.jorgegil.gameboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

    GlyphLayout layout;

    private int gameHeight, veticalBar;

    private final float HOLD_W, NEXT_W, LEVEL_W, SCORE_W, HIGH_W, GOAL_W, HIGH_SCORE_W;

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

        layout = new GlyphLayout();

        layout.setText(AssetLoader.font, "HOLD");
        HOLD_W = layout.width;
        layout.setText(AssetLoader.font, "NEXT");
        NEXT_W = layout.width;
        layout.setText(AssetLoader.font, "LEVEL");
        LEVEL_W = layout.width;
        layout.setText(AssetLoader.font, "SCORE");
        SCORE_W = layout.width;
        layout.setText(AssetLoader.font, "HIGH");
        HIGH_W = layout.width;
        layout.setText(AssetLoader.font, "GOAL");
        GOAL_W = layout.width;
        layout.setText(AssetLoader.font, String.valueOf(myBoard.getHighScore()));
        HIGH_SCORE_W = layout.width;

    }

    public void render(float runTime) {

        ArrayList<Square> squares = myBoard.getSquares();
        ArrayList<Square> tetrominoe = myBoard.getTetrominoe();
        ArrayList<Square> ghost = myBoard.getGhost();
        ArrayList<Integer> nextShape = myBoard.getNextShape();

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

        for (int i = 0; i < 5; i++) {
            batcher.draw(AssetLoader.tetriminoImage.get(nextShape.get(i)), 135, veticalBar + 12 + (25 * i), 20, 20);
        }

        if (myBoard.getHold() >= 0) {
            batcher.draw(AssetLoader.tetriminoImage.get(myBoard.getHold()), 5, veticalBar + 12, 20, 20);
        }

        for(Square s : squares) {
            batcher.draw(AssetLoader.colors.get(s.getColor()), s.getX() + 30, s.getY() + veticalBar, 10, 10);
        }

        for (Square s : ghost) {
            batcher.draw(AssetLoader.colors.get(7), s.getX() + 30, s.getY() + veticalBar, 10, 10);
        }

        for(Square s : tetrominoe) {
            batcher.draw(AssetLoader.colors.get(s.getColor()), s.getX() + 30, s.getY() + veticalBar, 10, 10);
        }

        // DRAW STATIC TEXT
        AssetLoader.font.draw(batcher, "HOLD", (30 - HOLD_W) / 2, 3);
        AssetLoader.font.draw(batcher, "LEVEL", (30 - LEVEL_W) / 2, 75);
        AssetLoader.font.draw(batcher, "GOAL", (30 - GOAL_W) / 2, 100);
        AssetLoader.font.draw(batcher, "SCORE", (30 - SCORE_W) / 2, 125);
        AssetLoader.font.draw(batcher, "HIGH", (30 - HIGH_W) / 2, 165);
        AssetLoader.font.draw(batcher, "SCORE", (30 - SCORE_W) / 2, 175);
        AssetLoader.font.draw(batcher, "NEXT", 130 + (30 - NEXT_W) / 2, 3);
        AssetLoader.font.draw(batcher, String.valueOf(myBoard.getHighScore()), (30 - HIGH_SCORE_W) / 2, 185);


        // DRAW LEVEL, GOAL, SCORE AND HIGHSCORE
        layout.setText(AssetLoader.font, String.valueOf(myBoard.getLevel()));
        AssetLoader.font.draw(batcher, String.valueOf(myBoard.getLevel()), (30 - layout.width) / 2, 85);

        layout.setText(AssetLoader.font, String.valueOf(myBoard.getGoal()));
        AssetLoader.font.draw(batcher, String.valueOf(myBoard.getGoal()), (30 - layout.width) / 2, 110);

        layout.setText(AssetLoader.font, String.valueOf(myBoard.getScore()));
        AssetLoader.font.draw(batcher, String.valueOf(myBoard.getScore()), (30 - layout.width) / 2, 135);

        // DRAW GAME STATES WHEN TRIGGERED
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
