package com.MainClass.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.mustacheman.game.Screens.PlayScreen;


public class MainClass extends Game {
	public SpriteBatch batch;
	public static final int V_Width = 1500;
	public static final int V_Height =  768;
	public static final float PPM = 100;
	public static AssetManager manager;
	public Music music;



	
	@Override
	public void create () {

		setScreen(new PlayScreen(this));
		manager = new AssetManager();
		manager.load("sounds/music.ogg", Music.class);
		//manager.load("sounds/coin.wav", Sound.class);
		manager.finishLoading();
		music = MainClass.manager.get("sounds/music.ogg", Music.class);

	}

	@Override
	public void render () {
		super.render();


	}
	
	@Override
	public void dispose () {

	}
}
