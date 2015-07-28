package com.jorgegil.tgHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * Created by jorgegil on 7/27/15.
 */
public class AssetLoader {

    public static Texture texture;
    public static TextureRegion pieceI, pieceO, pieceT, pieceJ, pieceL, pieceS, pieceZ;
    public static ArrayList<TextureRegion> colors;
    public static Sound music;

    public static void load() {
        texture = new Texture(Gdx.files.internal("data/tetris.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        pieceI = new TextureRegion(texture, 150, 0, 25, 25);
        pieceO = new TextureRegion(texture, 50, 0, 25, 25);
        pieceT = new TextureRegion(texture, 100, 0, 25, 25);
        pieceJ = new TextureRegion(texture, 0, 0, 25, 25);
        pieceL = new TextureRegion(texture, 125, 0, 25, 25);
        pieceS = new TextureRegion(texture, 75, 0, 25, 25);
        pieceZ = new TextureRegion(texture, 25, 0, 25, 25);

        colors = new ArrayList<TextureRegion>();
        colors.add(pieceI);
        colors.add(pieceO);
        colors.add(pieceT);
        colors.add(pieceJ);
        colors.add(pieceL);
        colors.add(pieceS);
        colors.add(pieceZ);

        music = Gdx.audio.newSound(Gdx.files.internal("data/music.mp3"));

    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }
}
