package com.jorgegil.tetris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.jorgegil.screens.GameScreen;
import com.jorgegil.screens.MainMenu;
import com.jorgegil.tgHelpers.AssetLoader;

public class TetrisGame extends Game {
	
	@Override
	public void create () {
		Gdx.app.log("ZBGame", "created");
		AssetLoader.load();
		setScreen(new GameScreen());
	}
}
