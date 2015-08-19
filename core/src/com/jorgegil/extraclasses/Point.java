package com.jorgegil.extraclasses;

/**
 * Created by jorgegil on 8/7/15.
 */
public class Point {

    private int x, y;

    public Point (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
