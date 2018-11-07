package com.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mustacheman.game.Screens.PlayScreen;
import com.sprites.Coin;
import com.sprites.Enemy;
import com.sprites.InteractiveTileObject;
import com.MainClass.game.MainClass;
import com.mustacheman.game.Screens.PlayScreen;
import com.sprites.MoustacheMan;

public class WorldContactListener implements ContactListener {
    public PlayScreen playScreen;

    public WorldContactListener(PlayScreen screen){
        playScreen = screen;



    }

    MoustacheMan man;
    int currentjump;

    @Override
    public void beginContact(Contact contact) {


        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();




        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;


        if(fixA.getUserData() == "player" || fixB.getUserData() == "player"){
            Fixture player = fixA.getUserData() == "player" ? fixA : fixB;
            Fixture object = player == fixA ? fixB : fixA;

            if (object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();            }
        }


        switch (cDef){
            case MainClass.ENEMY_HEAD_BIT | MainClass.MAN_BIT:
                if (fixA.getFilterData().categoryBits == MainClass.ENEMY_HEAD_BIT){
                    ( (Enemy)fixA.getUserData()).hitOnHead();
                       fixA.getFilterData().categoryBits = MainClass.DESTROYED_BIT;
                       fixA.getFilterData().maskBits = MainClass.NOTHING_BIT;}
                else if (fixB.getFilterData().categoryBits == MainClass.ENEMY_HEAD_BIT)
                {( (Enemy)fixB.getUserData()).hitOnHead();
                    fixB.getFilterData().categoryBits = MainClass.DESTROYED_BIT;
                    fixB.getFilterData().maskBits = MainClass.NOTHING_BIT;}
                break;


            case MainClass.COIN_BIT | MainClass.MAN_BIT:
                if (fixA.getFilterData().categoryBits == MainClass.COIN_BIT)
                    ( (Coin)fixA.getUserData()).onHit();

                else if (fixB.getFilterData().categoryBits == MainClass.COIN_BIT)
                    ( (Coin)fixB.getUserData()).onHit();

            case MainClass.GROUND_BIT | MainClass.MAN_BIT:
                playScreen.currentjump = 0;

            case MainClass.ENEMY_BIT | MainClass.ENEMYBORDER_BIT:
                Gdx.app.log("EnemyBorder", "Collision");
                if (fixA.getFilterData().categoryBits == MainClass.ENEMY_BIT)
                    ( (Enemy)fixA.getUserData()).reverseVelocity(true, true);


                else if (fixA.getFilterData().categoryBits == MainClass.ENEMY_BIT)
                    ( (Enemy)fixB.getUserData()).reverseVelocity(true, true);



        }




    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
