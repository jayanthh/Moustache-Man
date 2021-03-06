package com.MainClass.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
    public PlayScreen playScreen;

    public WorldContactListener(PlayScreen screen){
        playScreen = screen;



    }



    @Override
    public void beginContact(Contact contact) {


        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        /*if(fixA.getUserData() == "playerbody" || fixB.getUserData() == "playerbody"){
            Fixture player = fixA.getUserData() == "playerbody" ? fixA : fixB;
            Fixture object = player == fixA ? fixB : fixA;

            if (object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();            }
        } */





        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;




        switch (cDef) {
            case MainClass.ENEMY_HEAD_BIT | MainClass.MAN_BIT:
                if (fixA.getFilterData().categoryBits == MainClass.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                    fixA.getFilterData().categoryBits = MainClass.DESTROYED_BIT;
                    fixA.getFilterData().maskBits = MainClass.NOTHING_BIT;
                } else if (fixB.getFilterData().categoryBits == MainClass.ENEMY_HEAD_BIT) {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                    fixB.getFilterData().categoryBits = MainClass.DESTROYED_BIT;
                    fixB.getFilterData().maskBits = MainClass.NOTHING_BIT;
                }
                break;


            /*case (MainClass.COIN_BIT | MainClass.MAN_HEAD_BIT) :
                if (fixA.getFilterData().categoryBits == MainClass.COIN_BIT)
                    ((Coin) fixA.getUserData()).onHit();

                else if (fixB.getFilterData().categoryBits == MainClass.COIN_BIT)
                    ((Coin) fixB.getUserData()).onHit(); */




            case MainClass.GROUND_BIT | MainClass.MAN_BIT:
                playScreen.currentjump = 0;

        }


        if (doesCollide(fixA,fixB, MainClass.ENEMY_BIT, MainClass.ENEMYBORDER_BIT)) {


                if (fixA.getFilterData().categoryBits == MainClass.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);


                else if (fixB.getFilterData().categoryBits == MainClass.ENEMY_BIT)
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);


            }

        if (doesCollide(fixA,fixB, MainClass.MAN_BIT, MainClass.COIN_BIT) || doesCollide(fixA,fixB, MainClass.MAN_HEAD_BIT, MainClass.COIN_BIT)){

                if (fixA.getFilterData().categoryBits == MainClass.COIN_BIT)
                    ((Coin) fixA.getUserData()).onHit();


                else if (fixB.getFilterData().categoryBits == MainClass.COIN_BIT)
                    ((Coin) fixB.getUserData()).onHit();

            }



        if (doesCollide(fixA,fixB, MainClass.MAN_BIT, MainClass.ENEMY_BIT)){

            if (fixA.getFilterData().categoryBits == MainClass.MAN_BIT) {
                ((MoustacheMan) fixA.getUserData()).onHit();

            }

            else if (fixB.getFilterData().categoryBits == MainClass.MAN_BIT){
                ((MoustacheMan) fixB.getUserData()).onHit();
            }

        }

        if (doesCollide(fixA, fixB, MainClass.MAN_BIT , MainClass.EXIT_BIT)){
            playScreen.gameoverb =true;
            playScreen.gameoverb = true;
            playScreen.isalive = true;


        }

        if (doesCollide(fixA,fixB, MainClass.MAN_BIT, MainClass.GROUND_BIT)){

            if (fixA.getFilterData().categoryBits == MainClass.MAN_BIT) {


            }

            else if (fixB.getFilterData().categoryBits == MainClass.MAN_BIT){


            }

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

    public boolean doesCollide(Fixture fixA, Fixture fixB, short bit1, short bit2){

        if ((fixB.getFilterData().categoryBits == bit1 && fixA.getFilterData().categoryBits == bit2) || (fixA.getFilterData().categoryBits == bit1 && fixB.getFilterData().categoryBits == bit2)){
            return true;
        }

        else
            return false;



    }
}
