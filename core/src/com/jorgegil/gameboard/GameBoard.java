package com.jorgegil.gameboard;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;
import com.jorgegil.boardobjects.Square;
import com.jorgegil.boardobjects.Tetrominoe;
import com.jorgegil.tgHelpers.AssetLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by jorgegil on 7/27/15.
 */
public class GameBoard {

    public enum GameState {
        READY, RUNNING, PAUSE, GAMEOVER
    }

    private boolean[][] board, tetrominoeShape, tetriminoShape2;
    private ArrayList<Square> squares;
    private ArrayList<Square> tetrominoe;
    private int num, score = 0, level = 1, goal = 20;
    public GameState currentState;



    public GameBoard() {
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

        spawnTetrominoe();

        AssetLoader.music.loop();
        AssetLoader.music.pause();

        currentState = GameState.READY;
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
    }

    public void updateReady(float delta) {

    }

    public void updateRunning(float delta) {
        boolean pieceCanFall = true;

        // Checks if whole tetrominoe can fall
        for(Square s : tetrominoe) {
            if(!canSquareFall(s.getX(), s.getY()))
                pieceCanFall = false;
        }

         /* If true then increment Y by 10 on all 4 Squares
           If false add squares from falling tetrominoe to non-falling squares ArrayList, update board and spawn new
         */
        if(pieceCanFall) {
            for (Square s : tetrominoe)
                if (canSquareFall(s.getX(), s.getY())) {
                    s.fall();
                    //System.out.println("fall");
                }
        }
        else {
            for (Square s : tetrominoe) {
                squares.add(new Square(s.getX(), s.getY(), s.getColor()));
                int i = (int) s.getY() / 10;
                int j = (int) s.getX() / 10;
                board[i][j] = true;
            }
            checkForLine();
            spawnTetrominoe();
        }
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public ArrayList<Square> getTetrominoe() {
        return tetrominoe;
    }

    public boolean canSquareFall(float x, float y) {
        //convert x and y to j and i
        int j = (int) x / 10;
        int i = (int) y / 10;

        if(i == 19)
            return false;

        return !board[i + 1][j];
    }

    public void spawnTetrominoe() {
        tetrominoe.clear();

        // Random num (0 - 6) to choose Shape and Color of new tetrominoe
        Random rd = new Random();
        num = rd.nextInt(7);

        // Gets new tetrominoe shape
        tetrominoeShape = Tetrominoe.getShape(num);
        tetriminoShape2 = new boolean[tetrominoeShape.length][tetrominoeShape.length];
        copyArray();

        int maxCol = 0;
        //gets max # of cols
        for(int i = 0; i < tetrominoeShape.length; i++)
            if(tetrominoeShape[i].length > maxCol)
                maxCol = tetrominoeShape[i].length;


        int startJ = (10 - maxCol) / 2;
        int startI = 0;

        // Gets spawn coordinates and adds new Squares to tetrominoe ArrayList (4)
        for (int i = 0; i < tetrominoeShape.length; i++) {
            for (int j = 0; j < tetrominoeShape[i].length; j++) {
                startJ += j;
                startI = i;

                int startX = startJ * 10;
                int startY = startI * 10;



                if(tetrominoeShape[i][j]) {
                    if (!board[startI][startJ]) {
                        tetrominoe.add(new Square(startX, startY, num));
                    }
                    else {
                        endGame();
                    }
                }
                startJ = (10 - maxCol) / 2;
            }
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
                s.move(10);
            }

        }
    }

    public boolean canSquareMoveRight(float x, float y) {
        // Convert x and y to j and i
        int j = (int) x / 10;
        int i = (int) y / 10;

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
                s.move(-10);
            }
        }
    }

    public boolean canSquareMoveLeft(float x, float y) {
        // Convert x and y to j and i
        int j = (int) x / 10;
        int i = (int) y / 10;

        if(j == 0)
            return false;

        return !board[i][j - 1];
    }

    public void moveDown() {
        boolean pieceCanMoveDown = true;

        // Checks if whole tetrominoe can move left
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

        transpose();
        if(w == 0) reverseRow(); // Clockwise
        else reverseCol();       // Counter Clock Wise

        int count = 0;
        // Iterates new rotated shape
        for (int i = 0; i < tetriminoShape2.length; i++) {
            for (int j = 0; j < tetriminoShape2[i].length; j++) {
                if (tetriminoShape2[i][j]) { // if cell is true

                    // previous shape coordinates
                    int prevI = (int) coordinates.get(count).getX();
                    int prevJ = (int) coordinates.get(count).getY();

                    // difference between new and previous coordinates of cell
                    int difI = i - prevI;
                    int difJ = j - prevJ;

                    // new coordinates of shape
                    int newI = prevI + difI;
                    int newJ = prevJ + difJ;

                    // new coordinates of square
                    int fixedI = (int) (tetrominoe.get(count).getY() / 10) + difI;
                    int fixedJ = (int) (tetrominoe.get(count).getX() / 10) + difJ;

                    // check if new coordinates of square are valid, if not set rotate to false
                    if(fixedJ < 0 || fixedJ > 9 || fixedI < 0 || fixedI > 19) { // check for border collision
                        pieceCanRotate = false;
                    }
                    else {
                        if (board[newI][newJ]) { //check for neighboring squares collision
                            pieceCanRotate = false;
                        }
                    }

                    // Add the difference of square coordinates to arraylist
                    difCoordinates.add(new Point(difI * 10, difJ * 10));

                    count++;
                }
            }
        }

        int count2 = 0;
        if(pieceCanRotate) {
            for (Square s : tetrominoe) {

                //get difference to add/substract to square
                int difJ = (int) difCoordinates.get(count2).getX();
                int difI = (int) difCoordinates.get(count2).getY();

                s.rotate(difI, difJ);
                count2++;
            }
            difCoordinates.clear();
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
                setGoal(20);
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
            int i = (int) s.getY() / 10;

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
