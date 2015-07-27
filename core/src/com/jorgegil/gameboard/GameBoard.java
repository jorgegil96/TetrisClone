package com.jorgegil.gameboard;

import com.jorgegil.boardobjects.Square;

import java.util.ArrayList;

/**
 * Created by jorgegil on 7/27/15.
 */
public class GameBoard {

    private boolean[][] board;
    private ArrayList<Square> squares;

    public GameBoard() {
        board = new boolean[20][10];

        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = false;
            }
        }


        squares = new ArrayList<Square>();
        squares.add(new Square(0, 0));

    }

    public void update(float delta) {
        for(Square s : squares) {
            System.out.println(s.getX() + " " + s.getY());
            if(canFall(s.getX(), s.getY()))
                s.fall(delta);
        }
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public boolean canFall(float x, float y) {
        //convert x and y to j and i
        int j = (int) x / 25;
        int i = (int) y / 25;

        if(i == 19)
            return false;

        return !board[i + 1][j];
    }
}
