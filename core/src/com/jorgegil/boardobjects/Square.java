package com.jorgegil.boardobjects;

import com.badlogic.gdx.math.Vector2;
import com.jorgegil.extraclasses.Point;

/**
 * Created by jorgegil on 7/27/15.
 */
public class Square {
    
    private Point position;
    private int color;

    public Square (int j, int i, int color) {
        position = new Point(j, i);
        this.color = color;
    }

    public void fall() {
        position.add(0, 1);
    }

    public void move(int j) {
        position.add(j, 0);
    }

    public int getX() {
        return position.getX();
    }

    public void rotate(int x, int y) {
        position.add(x, y);
    }

    public int getY() {
        return position.getY();
    }

    public int getColor() {
        return color;
    }

    public void printCoords() {
        System.out.println(getX() + ", " + getY());

    }
}
