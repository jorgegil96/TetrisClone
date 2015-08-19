package com.jorgegil.gameboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.jorgegil.boardobjects.Square;
import com.jorgegil.boardobjects.Tetrominoe;
import com.jorgegil.extraclasses.Point;
import com.jorgegil.extraclasses.RotationData;
import com.jorgegil.tgHelpers.AssetLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by jorgegil on 7/27/15.
 */

public class GameBoard {

    public GameState currentState;
    public enum GameState {
        READY, RUNNING, PAUSE, GAMEOVER
    }

    private static final int LEVEL_GOAL = 10;

    private int num, score = 0, highScore, level = 1, goal = LEVEL_GOAL;

    private int next1 = 0, next2 = 0, next3 = 0, next4 = 0, next5 = 0, next6 = 0, hold = -1;

    private boolean[][] board, tetrominoeShape, tetriminoShape2;
    private ArrayList<Square> squares, tetrominoe, ghost;
    private ArrayList<Integer> nextShape;

    private int curP;

    private float dropDelay, dropTimer = 0.0f, lockTimer = 0.0f, lockDelay = 0.5f;
    private boolean canHold = true;

    private Preferences prefs;

    public float gameWidth, gameHeight;


    public GameBoard() {

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = (gameWidth * 200) / 160;


        // Create and fill board with false values
        board = new boolean[20][10];
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = false;
            }
        }

        // squares contains all non-falling squares
        squares = new ArrayList<Square>();
        // tetrominoe contains current falling squares
        tetrominoe = new ArrayList<Square>();
        // Ghost contains ghost squares
        ghost = new ArrayList<Square>();

        // contains int that correspond to tetrominos shapes
        nextShape = new ArrayList<Integer>();
        for(int i = 0; i < 6; i++) {
            getNextTetrimino();
        }
        getNextTetrimino();

        spawnTetrominoe();

        AssetLoader.music.loop();
        AssetLoader.music.pause();

        currentState = GameState.READY;

        prefs = Gdx.app.getPreferences("My Preferences");
        highScore = prefs.getInteger("highScore", 0);
    }

    public void update(float delta) {
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;
            case PAUSE:
                updateReady(delta);
                break;
            case GAMEOVER:
                updateReady(delta);
                break;
            case RUNNING:
            default:
                updateRunning(delta);
                break;
        }
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isPaused() {
        return currentState == GameState.PAUSE;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public void start() {
        currentState = GameState.RUNNING;
        AssetLoader.music.resume();
    }

    public void stop() {
        currentState = GameState.PAUSE;
        AssetLoader.music.pause();
    }

    public void endGame() {
        currentState = GameState.GAMEOVER;
        AssetLoader.music.pause();

        if (score > highScore) {
            prefs.putInteger("highScore", score);
        }

        prefs.flush();
    }

    public void updateReady(float delta) {

    }

    public void updateRunning(float delta) {
        dropTimer += delta;
        updateDropTime();

        boolean pieceCanFall = true;

        // Checks if whole tetrominoe can fall
        for (Square s : tetrominoe) {
            if (!canSquareFall(s.getX(), s.getY()))
                pieceCanFall = false;
        }


             /* If true then increment Y by 10 on all 4 Squares
               If false add squares from falling tetromino to non-falling squares ArrayList, update board and spawn new
             */
        if (pieceCanFall) {
            if (dropTimer >= dropDelay) {
                for (Square s : tetrominoe)
                    if (canSquareFall(s.getX(), s.getY()))
                        s.fall();
                dropTimer = 0;
            }
        } else {
            lockTimer += delta;
            if (lockTimer >= lockDelay) {
                for (Square s : tetrominoe) {
                    squares.add(new Square(s.getX(), s.getY(), s.getColor()));
                    int i = s.getY();
                    int j = s.getX();
                    board[i][j] = true;
                }

                checkForLine();
                getNextTetrimino();
                spawnTetrominoe();
                canHold = true;

                lockTimer = 0;
            }
        }

    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public ArrayList<Square> getTetrominoe() {
        return tetrominoe;
    }

    public ArrayList<Square> getGhost() {
        ghost.clear();
        boolean pieceCanMoveDown = true;

        for (Square s : tetrominoe) {
            int j = s.getX();
            int i = s.getY();
            int num = s.getColor();
            ghost.add(new Square(j, i, num));
        }

        while (pieceCanMoveDown) {
            for (Square s : ghost) {
                if (!canSquareFall(s.getX(), s.getY())) {
                    pieceCanMoveDown = false;
                }
            }

            if(pieceCanMoveDown) {
                for (Square s : ghost) {
                    s.fall();
                }
            }
        }

        return ghost;
    }

    public void getNextTetrimino() {
        nextShape.clear();
        Random rd = new Random();
        num = rd.nextInt(7);

        next1 = next2;
        next2 = next3;
        next3 = next4;
        next4 = next5;
        next5 = next6;
        next6 = num;

        nextShape.add(next2);
        nextShape.add(next3);
        nextShape.add(next4);
        nextShape.add(next5);
        nextShape.add(next6);


    }

    public void spawnTetrominoe() {
        curP = 0;
        tetrominoe.clear();


        // Gets new tetrominoe shape (boolean array representing the piece)
        tetrominoeShape = Tetrominoe.getShape(next1);
        tetriminoShape2 = new boolean[tetrominoeShape.length][tetrominoeShape.length];
        copyArray();


        // Starting board coordinates
        int startJ = (10 - tetrominoeShape.length) / 2;
        int startI = 0;

        // Gets spawn coordinates and adds new Squares to tetrominoe ArrayList (4)
        for (int i = 0; i < tetrominoeShape.length; i++) {
            for (int j = 0; j < tetrominoeShape[i].length; j++) {
                startJ += j;
                startI = i;


                if(tetrominoeShape[i][j]) {
                    if (!board[startI][startJ]) {
                        tetrominoe.add(new Square(startJ, startI, next1));
                    }
                    else {
                        endGame();
                    }
                }
                startJ = (10 - tetrominoeShape.length) / 2;
            }
        }
    }

    public void hold() {

        if (canHold) {
            int x = next1;

            if (hold >= 0) {
                next1 = hold;
            } else {
                getNextTetrimino();
            }

            hold = x;

            spawnTetrominoe();

            canHold = false;
        }
    }

    public void moveRight() {
        boolean pieceCanMoveRight = true;

        // Checks if whole tetrominoe can move left
        for(Square s : tetrominoe) {
            if(!canSquareMoveRight(s.getX(), s.getY())) {
                pieceCanMoveRight = false;
            }
        }

        if(pieceCanMoveRight) {
            for (Square s : tetrominoe) {
                s.move(1);
            }

        }
    }

    public boolean canSquareMoveRight(int j, int i) {
        if(j == 9)
            return false;

        return !board[i][j + 1];
    }

    public void moveLeft() {

        boolean pieceCanMoveLeft = true;

        // Checks if whole tetrominoe can move left
        for(Square s : tetrominoe) {
            if(!canSquareMoveLeft(s.getX(), s.getY())) {
                pieceCanMoveLeft = false;
            }
        }

        if(pieceCanMoveLeft) {
            for (Square s : tetrominoe) {
                s.move(-1);
            }
        }
    }

    public boolean canSquareMoveLeft(int j, int i) {
        if(j == 0)
            return false;

        return !board[i][j - 1];
    }

    public void moveDown() {
        boolean pieceCanMoveDown = true;

        // Checks if whole tetrominoe can move down
        for(Square s : tetrominoe) {
            if(!canSquareFall(s.getX(), s.getY())) {
                pieceCanMoveDown = false;
            }
        }

        if(pieceCanMoveDown) {
            for (Square s : tetrominoe) {
                s.fall();
            }
        }
    }

    public boolean canSquareFall(int j, int i) {
        if(i == 19)
            return false;

        return !board[i + 1][j];
    }

    public void hardDrop() {
        boolean pieceCanMoveDown = true;

        while (pieceCanMoveDown) {
            for (Square s : tetrominoe) {
                if (!canSquareFall(s.getX(), s.getY())) {
                    pieceCanMoveDown = false;
                }
            }
            if(pieceCanMoveDown) {
                for (Square s : tetrominoe) {
                    s.fall();
                }
            }
        }

        lockTimer = lockDelay;
    }

    public void rotate(int w) { // 0 -> CW, 1 -> CCW
        boolean pieceCanRotate = true;

        // Stores current coordinates of shape (i, j)
        ArrayList<Point> coordinates = new ArrayList<Point>();

        // Store dif in square coord between old and new coords
        ArrayList<Point> difCoordinates = new ArrayList<Point>();

        // Get current coordinates of shape
        for (int i = 0; i < tetriminoShape2.length; i++) {
            for (int j = 0; j < tetriminoShape2[i].length; j++) {
                if (tetriminoShape2[i][j]) {
                    coordinates.add(new Point(i, j));
                }
            }
        }

        // ROTATE SHAPE
        transpose();
        if(w == 0) reverseRow(); // Clockwise
        else reverseCol();       // Counter Clock Wise
        int x = 0, y = 0;

        int n = 0;
        while (n < 5) {

            int count = 0;
            pieceCanRotate = true;
            // Iterates new rotated shape
            for (int i = 0; i < tetriminoShape2.length; i++) {
                for (int j = 0; j < tetriminoShape2[i].length; j++) {
                    if (tetriminoShape2[i][j]) { // if cell is true

                        // previous shape coordinates
                        int prevI = coordinates.get(count).getX();
                        int prevJ = coordinates.get(count).getY();

                        // new coordinates of shape
                        int newI = i;
                        int newJ = j;

                        // difference between new and previous coordinates of cell
                        int difI = newI - prevI;
                        int difJ = newJ - prevJ;

                        // new coordinates of square
                        int fixedI = (tetrominoe.get(count).getY()) + difI;
                        int fixedJ = (tetrominoe.get(count).getX()) + difJ;

                        Point offset = RotationData.getOffset(curP, w, n, next1);
                        x = offset.getX();
                        y = offset.getY();

                        fixedI += y;
                        fixedJ += x;

                        // check if new coordinates of square are valid, if not set rotate to false
                        if (fixedJ < 0 || fixedJ > 9 || fixedI < 0 || fixedI > 19) { // check for border collision
                            pieceCanRotate = false;
                        } else {
                            if (board[fixedI][fixedJ]) { //check for neighboring squares collision
                                pieceCanRotate = false;
                            }
                        }

                        // Add the difference of square coordinates to arraylist
                        difCoordinates.add(new Point(difI, difJ));

                        count++;
                    }
                }
            }


            if (pieceCanRotate) break;
            n++;
        }


        int count2 = 0;
        if(pieceCanRotate) {
            for (Square s : tetrominoe) {

                //get difference to add/substract to square
                int difJ = difCoordinates.get(count2).getX();
                int difI = difCoordinates.get(count2).getY();

                s.rotate(difI, difJ);
                count2++;

            }
            if (w == 0) //CLOCKWISE
                curP += 1;
            else //COUNTER CLOCKWISE
                curP -= 1;

            if (curP > 3) curP = 0;
            if (curP < 0) curP = 3;
            difCoordinates.clear();

            for (Square s : tetrominoe) {
                s.rotate(x, y);
            }
        }
        else { //if rotate not allowed return new shape to old shape (rotate -90º)
            transpose();
            if(w == 0) reverseCol();
            else reverseRow();

        }
    }

    public void copyArray() {
        for(int i = 0; i < tetrominoeShape.length; i++) {
            for (int j = 0; j < tetrominoeShape[i].length; j++) {
                tetriminoShape2[i][j] = tetrominoeShape[i][j];
            }
        }
    }

    public void transpose() {
        for(int i = 0; i < tetriminoShape2.length; i++) {
            for (int j = 0; j < tetriminoShape2[i].length; j++) {
                if(i != j) {
                    boolean hold = tetriminoShape2[i][j];
                    tetriminoShape2[i][j] = tetriminoShape2[j][i];
                    tetriminoShape2[j][i] = hold;
                }
                else {
                    break;
                }
            }
        }
    }

    public void reverseRow() {
        for (int i = 0; i < tetriminoShape2.length; i++) {
            for (int j = 0; j < tetriminoShape2[i].length / 2; j++) {
                boolean hold = tetriminoShape2[i][j];
                tetriminoShape2[i][j] = tetriminoShape2[i][tetriminoShape2[i].length - 1 - j];
                tetriminoShape2[i][tetriminoShape2[i].length - 1 - j] = hold;
            }
        }
    }

    public void reverseCol() {
        for (int i = 0; i < tetriminoShape2.length / 2; i++) {
            for (int j = 0; j < tetriminoShape2[i].length; j++) {
                boolean hold = tetriminoShape2[i][j];
                tetriminoShape2[i][j] = tetriminoShape2[tetriminoShape2.length - 1 - i][j];
                tetriminoShape2[tetriminoShape2.length - 1 - i][j] = hold;
            }
        }
    }

    public void checkForLine () {
        int numLines = 0;
        for(int i = 0; i < board.length; i++) {

            boolean lineCompleted = true;

            for (int j = 0; j < board[0].length; j++) {
                if(!board[i][j]) {
                    lineCompleted = false;
                }
            }

            if(lineCompleted) {
                moveAllDown(i);
                numLines++;
            }
        }

        if(numLines > 0) {
            switch (numLines) {
                case 1:
                    addScore(100 * (getLevel()));
                    break;
                case 2:
                    addScore(300 * (getLevel()));
                    break;
                case 3:
                    addScore(500 * (getLevel()));
                    break;
                case 4:
                    addScore(800 * (getLevel()));
                    break;
            }

            if (getGoal() - numLines <= 0) {
                setGoal(LEVEL_GOAL);
                addLevel();
            } else {
                setGoal(getGoal() - numLines);
            }
        }
    }

    public void moveAllDown(int line) {

        /* Gets all Squares on "squares" ArrayList, if the square is above line it falls down
            if it is in the same line as "line" it gets deleted from ArrayList
         */
        for (Iterator<Square> it = squares.iterator(); it.hasNext(); ) {
            Square s = it.next();
            int i = s.getY();

            if(i < line) {
                s.fall();
            }

            if(i == line) {
                it.remove();
            }
        }

        for(int i = line; i >= 0; i--) {
            for(int j = 0; j < board[i].length; j++) {
                if(i < line) {
                    board[i + 1][j] = board[i][j];
                }
                if(i == 0 || i == line) {
                    board[i][j] = false;
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel() {
        level++;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public ArrayList<Integer> getNextShape() {
        return nextShape;
    }

    public int getHold() {
        return hold;
    }

    public void updateDropTime() {
        if (level <= 8)                         dropDelay = (48.0f - (5.0f * (level - 1))) / 60.0f;
        else if (level == 9)                    dropDelay = 11.0f / 48.0f;
        else if (level >= 10 && level <= 12)    dropDelay = 10.0f / 48.0f;
        else if (level >= 13 && level <= 15)    dropDelay = 9.0f / 48.0f;
        else if (level >= 16 && level <= 18)    dropDelay = 8.0f / 48.0f;
        else if (level >= 19 && level <= 22)    dropDelay = 7.0f / 48.0f;
        else                                    dropDelay = 6.0f / 60.0f;
    }

    public void resetLock() {
        lockTimer = 0;
    }

    public void printBoard() {
        for(int i = 0; i < board.length; i++) {
            System.out.print(i + " - ");
            for(int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("");
        }
    }

    public void printShape() {
        for (int i = 0; i < tetriminoShape2.length; i++) {
            for (int j = 0; j < tetriminoShape2[i].length; j++) {
                System.out.print(tetriminoShape2[i][j] + " | ");
            }
            System.out.println("");
        }
        System.out.println("------------------");
    }
}
