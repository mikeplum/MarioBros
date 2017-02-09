package com.sugarplum.mariobros.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by mikeplum on 2017-01-29.
 */
public class ItemDef  {
    public Vector2 position;
    public Class<?> type;

    public  ItemDef(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;

    }
}
