package com.sugarplum.mariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sugarplum.mariobros.Screens.PlayScreen;

public class MarioBros extends Game {

	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 208;

	public SpriteBatch batch; // kontener przetrzymujący przetrzymujący obrazki i screeny

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
