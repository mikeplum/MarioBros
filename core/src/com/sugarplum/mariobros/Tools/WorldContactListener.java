package com.sugarplum.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.sugarplum.mariobros.Sprites.InteractiveTileObject;

/**
 * Created by mikeplum on 2017-01-19.
 */
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        //Gdx.app.log("Begin contact","");//poniżej kod tłumaczony w part12 11:|56
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if (null != object.getUserData() && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
        }
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("End contact", "");

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
