package com.sugarplum.mariobros.Sprites.TileObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Scenes.Hud;
import com.sugarplum.mariobros.Screens.PlayScreen;

/**
 * Created by mikeplum on 2017-01-16.
 */
public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","collision");
        setCategoryFilter(MarioBros.DESTROYED_BIT); // kiedy Mario pierwszy zderzy się z daną cegłą ta cegła zmieni swój Bit na destroyed a w definicji Mario ustaliliśmy, że Mario nie koliduje z ciałami które mają Destroyed_bit
        getCell().setTile(null);
        Hud.addScore(200);//nie najlpsza praktyka korzystanie z metody statycznej
        MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();

    }



}
