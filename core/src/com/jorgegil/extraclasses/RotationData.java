package com.jorgegil.extraclasses;

/**
 * Created by jorgegil on 8/19/15.
 */
public class RotationData {

    private static Point[][] MainOffsetData  =
            {
                    {new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)},
                    {new Point(0, 0), new Point(1, 0), new Point(1, -1), new Point(0, 2), new Point(1, 2)},
                    {new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0), new Point(0, 0)},
                    {new Point(0, 0), new Point(-1, 0), new Point(-1, -1), new Point(0, 2), new Point(-1, 2)}
            };

    private static Point[][] IOffsetData =
            {
                    {new Point(0, 0), new Point(-1, 0), new Point(2, 0), new Point(-1, 0), new Point(2, 0)},
                    {new Point(-1, 0), new Point(0, 0), new Point(0, 0), new Point(0, 1), new Point(0, -2)},
                    {new Point(-1, 1), new Point(1, 1), new Point(-2, 1), new Point(1, 0), new Point(-2, 0)},
                    {new Point(0, 1), new Point(0, 1), new Point(0, 1), new Point(0, -1), new Point(0, 2)}
            };

    private static Point[][] OOffsetData =
            {
                    {new Point(0, 0)},
                    {new Point(0, -1)},
                    {new Point(-1, -1)},
                    {new Point(-1, 0)}
            };

    public static Point getOffset(int curP , int rot ,int n, int p) {
        int desP;
        int x = 0, y = 0;

        if (rot == 0) { //CLOCKWISE
            desP = curP + 1;
        } else { //COUNTER CLOCKWISE
            desP = curP - 1;
        }

        if (desP > 3) {
            desP = 0;
        }

        if (desP < 0) {
            desP = 3;
        }


        if (p == 0) { // I
            x = IOffsetData[curP][n].getX() - IOffsetData[desP][n].getX();
            y = IOffsetData[curP][n].getY() - IOffsetData[desP][n].getY();
        }
        else if (p == 1) { // O
            n = 0;
            x = OOffsetData[curP][n].getX() - OOffsetData[desP][n].getX();
            y = OOffsetData[curP][n].getY() - OOffsetData[desP][n].getY();
        }
        else { // J, L, S, Z, T
            x = MainOffsetData[curP][n].getX() - MainOffsetData[desP][n].getX();
            y = MainOffsetData[curP][n].getY() - MainOffsetData[desP][n].getY();
        }

        return new Point(x, y);
    }
}
