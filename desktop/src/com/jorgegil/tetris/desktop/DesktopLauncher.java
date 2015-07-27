package com.jorgegil.tetris.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jorgegil.tetris.TetrisGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tetris";
		config.width = 250;
		config.height = 500;
		new LwjglApplication(new TetrisGame(), config);
	}
}
