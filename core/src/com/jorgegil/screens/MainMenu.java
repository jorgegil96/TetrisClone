package com.jorgegil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jorgegil.tgHelpers.AssetLoader;

import javax.swing.text.html.HTML;


/**
 * Created by jorgegil on 8/10/15.
 */
public class MainMenu implements Screen {

    private Image gameTitle;
    private TextButton btnPlay, btnHighscores, btnExit;
    private TextButton.TextButtonStyle textButtonStyle;
    private TextureAtlas buttonAtlas;

    private Table table;
    private Stage stage;
    private Skin skin;


    private static final float MIN_SCENE_WIDTH = 160.0f;
    private static final float MIN_SCENE_HEIGHT = 200.0f;

    private Viewport viewport;
    private Camera camera;

    public MainMenu() {

        camera = new PerspectiveCamera();
        viewport = new FitViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, camera);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        gameTitle = new Image(new TextureRegionDrawable(new TextureRegion(AssetLoader.title)));

        btnPlay = new TextButton("PLAY", skin, "default");

        table = new Table();
        table.row();
        table.add(gameTitle).padTop(10.0f).colspan(2).expand();
        table.row();
        table.add(btnPlay).padTop(10.0f).colspan(2);
        table.setFillParent(true);
        table.pack();

        // Play Button Listener
        btnPlay.addListener (new ClickListener() {
           @Override
            public void clicked (InputEvent event, float x, float y) {

           }
        });

        stage.addActor(table);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
