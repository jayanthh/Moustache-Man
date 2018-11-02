package com.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sprites.Enemy;
import com.sprites.InteractiveTileObject;
import com.MainClass.game.MainClass;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if (object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef){
            case MainClass.ENEMY_HEAD_BIT | MainClass.MAN_BIT:
                if (fixA.getFilterData().categoryBits == MainClass.ENEMY_HEAD_BIT)
                    ( (Enemy)fixA.getUserData()).hitOnHead();
                else if (fixB.getFilterData().categoryBits == MainClass.ENEMY_HEAD_BIT)
                    ( (Enemy)fixA.getUserData()).hitOnHead();
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