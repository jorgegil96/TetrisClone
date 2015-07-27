package com.jorgegil.zbHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jorgegil on 7/27/15.
 */
public class AssetLoader {

    public static Texture texture;
    public static TextureRegion pieceI, pieceO, pieceT, pieceJ, pieceL, pieceS, pieceZ;

    public static void load() {
        texture = new Texture(Gdx.files.internal("data/tetris.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        pieceI = new TextureRegion(texture, 0, 0, 25, 25);
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }
}
