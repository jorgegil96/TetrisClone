package com.jorgegil.gameboard;

import com.jorgegil.boardobjects.Square;
import com.jorgegil.boardobjects.Tetrominoe;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jorgegil on 7/27/15.
 */
public class GameBoard {

    private boolean[][] board;
    private ArrayList<Square> squares;
    private ArrayList<Square> tetrominoe;


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
    }

    public void update(float delta) {
        boolean pieceCanFall = true;

        // Checks if whole tetrominoe can fall
        for(Square s : tetrominoe) {
            if(!canSquareFall(s.getX(), s.getY()))
                pieceCanFall = false;
        }

         /* If true then increment Y by 25 on all 4 Squares
           If false add squares from falling tetrominoe to non-falling squares ArrayList, update board and spawn new
         */
        if(pieceCanFall) {
            for (Square s : tetrominoe)
                if (canSquareFall(s.getX(), s.getY()))
                    s.fall();
        }
        else {
            for (Square s : tetrominoe) {
                squares.add(new Square(s.getX(), s.getY(), s.getColor()));
                int i = (int) s.getY() / 25;
                int j = (int) s.getX() / 25;
                board[i][j] = true;
            }
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
        int j = (int) x / 25;
        int i = (int) y / 25;

        if(i == 19)
            return false;

        return !board[i + 1][j];
    }

    public void spawnTetrominoe() {
        tetrominoe.clear();

        // Random num (0 - 6) to choose Shape and Color of new tetrominoe
        Random rd = new Random();
        int num = rd.nextInt(7);

        // Gets new tetrominoe shape
        boolean[][] tetrominoeShape = Tetrominoe.getShape(num);

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

                int startX = startJ * 25;
                int startY = startI * 25;

                if(tetrominoeShape[i][j]) {
                    tetrominoe.add(new Square(startX, startY, num));
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
            for (Square s : tetrominoe)
                s.move(25);
        }
    }

    public boolean canSquareMoveRight(float x, float y) {
        // Convert x and y to j and i
        int j = (int) x / 25;
        int i = (int) y / 25;

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
            for (Square s : tetrominoe)
                s.move(-25);
        }
    }

    public boolean canSquareMoveLeft(float x, float y) {
        // Convert x and y to j and i
        int j = (int) x / 25;
        int i = (int) y / 25;

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
            for (Square s : tetrominoe)
                s.fall();
        }
    }
}
