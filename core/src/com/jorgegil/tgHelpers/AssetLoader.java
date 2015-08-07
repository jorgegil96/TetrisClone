package com.jorgegil.tgHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * Created by jorgegil on 7/27/15.
 */
public class AssetLoader {

    public static Texture texture;
    public static TextureRegion pieceI, pieceO, pieceT, pieceJ, pieceL, pieceS, pieceZ, pieceG;
    public static TextureRegion tetriminoI, tetriminoO, tetriminoT, tetriminoJ, tetriminoL, tetriminoS, tetriminoZ;
    public static ArrayList<TextureRegion> colors;
    public static ArrayList<TextureRegion> tetriminoImage;
    public static Sound music;
    public static BitmapFont font;

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
        pieceG = new TextureRegion(texture, 175, 0, 25, 25); //ghost

        colors = new ArrayList<TextureRegion>();
        colors.add(pieceI);
        colors.add(pieceO);
        colors.add(pieceT);
        colors.add(pieceJ);
        colors.add(pieceL);
        colors.add(pieceS);
        colors.add(pieceZ);
        colors.add(pieceG);

        tetriminoI = new TextureRegion(new Texture(Gdx.files.internal("data/i.png")), 0, 0, 100, 100);
        tetriminoO = new TextureRegion(new Texture(Gdx.files.internal("data/o.png")), 0, 0, 100, 100);
        tetriminoT = new TextureRegion(new Texture(Gdx.files.internal("data/t.png")), 0, 0, 100, 100);
        tetriminoJ = new TextureRegion(new Texture(Gdx.files.internal("data/j.png")), 0, 0, 100, 100);
        tetriminoJ.flip(false, true);
        tetriminoL = new TextureRegion(new Texture(Gdx.files.internal("data/l.png")), 0, 0, 100, 100);
        tetriminoL.flip(false, true);
        tetriminoS = new TextureRegion(new Texture(Gdx.files.internal("data/s.png")), 0, 0, 100, 100);
        tetriminoS.flip(true, false);
        tetriminoZ = new TextureRegion(new Texture(Gdx.files.internal("data/z.png")), 0, 0, 100, 100);
        tetriminoZ.flip(true, false);

        tetriminoImage = new ArrayList<TextureRegion>();
        tetriminoImage.add(tetriminoI);
        tetriminoImage.add(tetriminoO);
        tetriminoImage.add(tetriminoT);
        tetriminoImage.add(tetriminoJ);
        tetriminoImage.add(tetriminoL);
        tetriminoImage.add(tetriminoS);
        tetriminoImage.add(tetriminoZ);



        music = Gdx.audio.newSound(Gdx.files.internal("data/music.mp3"));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"), false);
        font.getData().setScale(.1f, -.1f);

    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
        font.dispose();
    }
}
