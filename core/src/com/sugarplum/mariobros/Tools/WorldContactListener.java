package com.sugarplum.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Sprites.Enemies.Enemy;
import com.sugarplum.mariobros.Sprites.Items.Item;
import com.sugarplum.mariobros.Sprites.Mario;
import com.sugarplum.mariobros.Sprites.TileObject.InteractiveTileObject;

/**
 * Created by mikeplum on 2017-01-19.
 */
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        //Gdx.app.log("Begin contact","");//poniżej kod tłumaczony w part12 11:|56
        Fixture fixA = contact.getFixtureA();//w contakcie zawsze biorą udział 2 fixtury
        Fixture fixB = contact.getFixtureB();

        int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head") { //czy któryś z fixtures to głowa mario
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;
            //po powyższych przypisaniach wiemy który fixture jest głową a który obiektem
            if (null != object.getUserData() && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) //true jeśli obiekt jest "spowinowacony" z InteractiveTiledObject, nasze obiekty Coin i Brick będziemy chcieli zdeżać z głową, i to one są typu interactiveTiledObject, reszta Ground, pipes nie rozszeżają tej klasy
                ((InteractiveTileObject) object.getUserData()).onHeadHit(); //wywołujemy onHeadHit dla cegły lub monety
        }

        switch (collisionDef){
            case MarioBros.ENEMY_HEAD_BIT | MarioBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else if(fixB.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT)
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;
            case MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reversVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reversVelocity(true, false);
                break;
            case MarioBros.MARIO_BIT | MarioBros.ENEMY_BIT:
                Gdx.app.log("MARIO","DIE");
                break;
            case MarioBros.ENEMY_BIT | MarioBros.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reversVelocity(true, false);
                ((Enemy) fixB.getUserData()).reversVelocity(true, false);
                break;
            case MarioBros.ITEM_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item) fixA.getUserData()).reversVelocity(true, false);
                else if(fixB.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT)
                    ((Item) fixB.getUserData()).reversVelocity(true, false);
                break;
            case MarioBros.ITEM_BIT | MarioBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item) fixA.getUserData()).use((Mario) fixB.getUserData());
                else if(fixB.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item) fixB.getUserData()).use((Mario) fixA.getUserData());
                break;
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
