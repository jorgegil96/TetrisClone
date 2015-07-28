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
        board = new boolean[20][10];

        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = false;
            }
        }

        squares = new ArrayList<Square>();
        tetrominoe = new ArrayList<Square>();

        spawnTetrominoe();

        //squares.add(new Square(0, 0));

    }

    public void update(float delta) {
        boolean pieceCanFall = true;

        for(Square s : tetrominoe) {
            if(!canSquareFall(s.getX(), s.getY()))
                pieceCanFall = false;
        }

        if(pieceCanFall) {
            for (Square s : tetrominoe)
                if (canSquareFall(s.getX(), s.getY()))
                    s.fall(delta);
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

        Random rd = new Random();
        int num = rd.nextInt(7);

        boolean[][] tetrominoeShape = Tetrominoe.getShape(num);
        int maxCol = 0;

        //gets max # of cols
        for(int i = 0; i < tetrominoeShape.length; i++)
            if(tetrominoeShape[i].length > maxCol)
                maxCol = tetrominoeShape[i].length;


        int startJ = (10 - maxCol) / 2;
        int startI = 0;

        for (int i = 0; i < tetrominoeShape.length; i++) {
            for (int j = 0; j < tetrominoeShape[i].length; j++) {
                startJ += j;
                startI = i;

                int startX = startJ * 25;
                int startY = startI * 25;

                if(tetrominoeShape[i][j]) {
                    tetrominoe.add(new Square(startX, startY, num));
                    System.out.println("New at " + startX + ", " + startY);
                }
                startJ = (10 - maxCol) / 2;
            }

        }


    }


}
