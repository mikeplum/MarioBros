package com.sugarplum.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Scenes.Hud;

/**
 * Created by mikeplum on 2017-01-16.
 */
public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","collision");
        setCategoryFilter(MarioBros.DESTROYED_BIT); // kiedy Mario pierwszy zderzy się z daną cegłą ta cegła zmieni swój Bit na destroyed a w definicji Mario ustaliliśmy, że Mario nie koliduje z ciałami które mają Destroyed_bit
        getCell().setTile(null);
        Hud.addScore(200);//nie najlpsza praktyka korzystanie z metody statycznej
    }

}
