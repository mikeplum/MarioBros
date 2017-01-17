package com.sugarplum.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Screens.PlayScreen;

/**
 * Created by mikeplum on 2017-01-07.
 */
public class Mario extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion marioStand;

    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMario();

        marioStand = new TextureRegion(getTexture(), 0, 10, 16, 16);
        setBounds(0, 0, 16/MarioBros.PPM, 16/MarioBros.PPM);
        setRegion(marioStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    private void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/MarioBros.PPM, 32/MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7/MarioBros.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

}
