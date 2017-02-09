package com.sugarplum.mariobros.Sprites.TileObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.SortedIntList;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Scenes.Hud;
import com.sugarplum.mariobros.Screens.PlayScreen;
import com.sugarplum.mariobros.Sprites.Items.ItemDef;
import com.sugarplum.mariobros.Sprites.Items.Mushroom;

/**
 * Created by mikeplum on 2017-01-16.
 */
public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin","collision");
        Hud.addScore(100);
        if(BLANK_COIN == getCell().getTile().getId()) {
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        } else {
            if(object.getProperties().containsKey("mushroom")){
                screen.spawnItems(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / MarioBros.PPM), Mushroom.class));
                MarioBros.manager.get("audio/sounds/smb_powerup_appears.wav", Sound.class).play();
            }
            MarioBros.manager.get("audio/sounds/coin.wav", Sound.class).play();

        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
}
