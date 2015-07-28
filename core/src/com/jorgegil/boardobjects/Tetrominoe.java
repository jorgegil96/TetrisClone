package com.jorgegil.boardobjects;

/**
 * Created by jorgegil on 7/27/15.
 */
public class Tetrominoe {

    private static boolean[][] I_PIECE = {
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false},
            {false, false, false, false}
    };

    private static boolean[][] O_PIECE = {
            {true, true},
            {true, true}
    };

    private static boolean[][] T_PIECE = {
            {false, true, false},
            {true, true, true},
            {false, false, false}
    };

    private static boolean[][] J_PIECE = {
            {true, false, false},
            {true, true, true},
            {false, false, false}
    };

    private static boolean[][] L_PIECE = {
            {false, false, true},
            {true, true, true},
            {false, false, false}
    };

    private static boolean[][] S_PIECE = {
            {false, true, true},
            {true, true, false},
            {false, false, false}
    };

    private static boolean[][] Z_PIECE = {
            {true, true, false},
            {false, true, true},
            {false, false, false}
    };

    private static boolean[][][] TETRIS_PIECES = {
            I_PIECE, O_PIECE, T_PIECE, J_PIECE, L_PIECE, S_PIECE, Z_PIECE
    };

    public static boolean[][] getShape(int num) {
        return TETRIS_PIECES[num];
    }
}
