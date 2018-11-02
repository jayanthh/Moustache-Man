package com.sprites;

import com.MainClass.game.MainClass;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mustacheman.game.Screens.PlayScreen;

import java.util.ArrayList;

public class lilMon extends Enemy {

    private  float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private TextureAtlas atlas;
    public Body b2body;
    public World world;
    private boolean setToDestroy;
    private boolean destroyed;


    public lilMon(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        atlas = new TextureAtlas("LittleMonster.atlas");
        frames = new Array<TextureRegion>();
        walkAnimation = new Animation<TextureRegion>(0.01f, atlas.findRegions("w"));
        stateTime = 0;
        setToDestroy = false;
        destroyed = false;






    }

    public void update(float dt){
        stateTime += dt;

        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setBounds(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2, 40, 20);

        }
        else if (!destroyed) {
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));

        }

    }

    @Override
    protected void defineEnemy() {
        setBounds(50/ MainClass.PPM , 300 / MainClass.PPM, 40 /MainClass.PPM, 40/MainClass.PPM);

        BodyDef bdef = new BodyDef();
        bdef.position.set( getX(), getY());


        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = screen.getWorld().createBody(bdef);



        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius( 20 /MainClass.PPM);



        fdef.shape = shape;
        b2body.createFixture(fdef);

        //create Head
        PolygonShape head = new PolygonShape();
        Vector2[] veritice = new Vector2[4];
        veritice[0] = new Vector2(-20, 30).scl(1 /MainClass.PPM);
        veritice[1] = new Vector2(20, 30).scl(1 /MainClass.PPM);
        veritice[2] = new Vector2(-10, 20).scl(1 /MainClass.PPM);
        veritice[3] = new Vector2(10, 20).scl(1 /MainClass.PPM);
        head.set(veritice);

        fdef.shape = head;

        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = MainClass.ENEMY_HEAD_BIT;
        fdef.filter.maskBits = MainClass.GROUND_BIT | MainClass.BRICK_BIT | MainClass.ENEMY_BIT |
                MainClass.OBJECT_BIT;
        b2body.createFixture(fdef).setUserData(this);


    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }


}