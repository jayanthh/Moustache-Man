package com.mustacheman.game.Screens;

import com.MainClass.game.MainClass;
import com.Scenes.Hud;
import com.Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.MainClass.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.*;
import com.sprites.MoustacheMan;

import sun.applet.Main;

public class PlayScreen implements Screen{
    private OrthographicCamera gamecam;
    private Viewport gameport;


    TextureAtlas atlas;
    com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> run;
    float elapsedTime;
    Hud hud;
    private World world;
    private Box2DDebugRenderer b2dr;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private MoustacheMan player;








    private MainClass game;


    public PlayScreen(MainClass game){

        gamecam = new OrthographicCamera();
        gameport = new FitViewport(MainClass.V_Width / MainClass.PPM, MainClass.V_Height / MainClass.PPM ,gamecam);

        this.game = game;
        game.batch = new SpriteBatch();
        atlas = new TextureAtlas("RunJumpKoRoll.atlas");
        run = new com.badlogic.gdx.graphics.g2d.Animation(1/15f, atlas.findRegions("run/run"), com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainClass.PPM);



        gamecam.position.set(gameport.getWorldWidth()/2, gameport.getWorldHeight()/2, 0);
        world = new World(new Vector2(0,-10 ), true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world, map);
        player = new MoustacheMan(world);



    }

    @Override
    public void show() {

    }
    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || hud.isUpPressed()) {
            player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);}
        if ((( Gdx.input.isKeyPressed(Input.Keys.RIGHT) || hud.isRightPressed()) && (player.b2body.getLinearVelocity().x <= 2))) {
            player.b2body.applyLinearImpulse(new Vector2(0.3f, 0), player.b2body.getWorldCenter(), true);}
        if ((( Gdx.input.isKeyPressed(Input.Keys.LEFT) || hud.isLeftPressed()) && (player.b2body.getLinearVelocity().x >= -2))){
            player.b2body.applyLinearImpulse(new Vector2(-0.3f, 0), player.b2body.getWorldCenter(), true); }



    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1/60f,6,2);
        gamecam.position.x = player.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);


    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        elapsedTime += Gdx.graphics.getDeltaTime();
        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.setProjectionMatrix(hud.hudStage.getCamera().combined);
        hud.hudStage.draw();
        //game.batch.begin();
        //game.batch.draw(run.getKeyFrame(elapsedTime, true), 400 ,384 );
        //game.batch.end();

        b2dr.render(world,gamecam.combined);






    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width,height);
        hud.resize(width,height);

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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
