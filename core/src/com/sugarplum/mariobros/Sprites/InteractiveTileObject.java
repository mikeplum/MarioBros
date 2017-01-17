package com.sugarplum.mariobros.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.sugarplum.mariobros.MarioBros;

/**
 * Created by mikeplum on 2017-01-16.
 */
public class InteractiveTileObject {

    protected World world;
    protected TiledMapTile tile;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MarioBros.PPM , (bounds.getY() + bounds.getHeight() / 2) / MarioBros.PPM);

        body = world.createBody(bdef);

        shape.setAsBox( (bounds.getWidth() / 2 ) / MarioBros.PPM, (bounds.getHeight() / 2 ) / MarioBros.PPM );
        fdef.shape = shape;
        body.createFixture(fdef);
    }

}

