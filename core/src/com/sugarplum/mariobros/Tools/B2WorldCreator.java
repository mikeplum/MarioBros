package com.sugarplum.mariobros.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Sprites.Brick;
import com.sugarplum.mariobros.Sprites.Coin;

/**
 * Created by mikeplum on 2017-01-16.
 */
public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map){

        /*
            dodajemy obiekty 2d i ich właściwości(fixtures): kształ, tarcie z innymi obiektami etc
            w tym tutorialu jest to zrobione w konstruktorze PlayScreen, lecz poprawną praktyką
            jest tworzenie osobnych klas dla obiektów.
        */

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape(); //for fixtures
        FixtureDef fdef = new FixtureDef(); // definiujemy właściwości żeby przypisać je obiektom 2Dbody
        Body body;

        //będziemy chcieli powiązać  obiekty 2d z level1.tmx z obiektami java typu Bodies i Fixtures
        //będziemy pobierać obiekty z 3 warsty czyli ground
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM , (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM  );

            body = world.createBody(bdef);

            shape.setAsBox( (rect.getWidth() / 2) / MarioBros.PPM  , (rect.getHeight() / 2) / MarioBros.PPM );
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //będziemy pobierać obiekty z 4 warsty czyli pipes
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM , (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM );

            body = world.createBody(bdef);

            shape.setAsBox( (rect.getWidth() / 2 ) / MarioBros.PPM , (rect.getHeight() / 2 ) / MarioBros.PPM );
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //będziemy pobierać obiekty z 6 warsty czyli bricks
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(world, map, rect);
        }

        //będziemy pobierać obiekty z 5 warsty czyli coins
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(world, map, rect);

        }

    }

}