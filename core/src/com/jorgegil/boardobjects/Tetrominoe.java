package com.jorgegil.boardobjects;

/**
 * Created by jorgegil on 7/27/15.
 */
public class Tetrominoe {

    private static boolean[][] I_PIECE = {
            {true},
            {true},
            {true},
            {true}
    };

    private static boolean[][] O_PIECE = {
            {true, true},
            {true, true}
    };

    private static boolean[][] T_PIECE = {
            {true, true, true},
            {false, true, false}
    };

    private static boolean[][] J_PIECE = {
            {true, true, true},
            {false, false, true}
    };

    private static boolean[][] L_PIECE = {
            {true, true, true},
            {true, false, false}
    };

    private static boolean[][] S_PIECE = {
            {false, true, true},
            {true, true, false}
    };

    private static boolean[][] Z_PIECE = {
            {true, true, false},
            {false, true, true}
    };

    private static boolean[][][] TETRIS_PIECES = {
            I_PIECE, O_PIECE, T_PIECE, J_PIECE, L_PIECE, S_PIECE, Z_PIECE
    };

    public static boolean[][] getShape(int num) {
        return TETRIS_PIECES[num];
    }
}
