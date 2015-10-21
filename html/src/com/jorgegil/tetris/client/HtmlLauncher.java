package com.jorgegil.tetris.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.jorgegil.tetris.TetrisGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(600, 750);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new TetrisGame();
        }
}