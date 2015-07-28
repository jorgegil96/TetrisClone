package com.jorgegil.boardobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by jorgegil on 7/27/15.
 */
public class Square {
    
    private Vector2 position;
    private int color;

    public Square (float x, float y, int color) {
        position = new Vector2(x, y);
        this.color = color;
    }

    public void fall(float delta) {
        position.add(0, 25);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getColor() {
        return color;
    }
}
