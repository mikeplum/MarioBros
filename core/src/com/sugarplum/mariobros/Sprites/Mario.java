package com.sugarplum.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Screens.PlayScreen;

/**
 * Created by mikeplum on 2017-01-07.
 */
public class Mario extends Sprite {


    public enum State { FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer; //będzie mierzył ilość czasu w aktualnym stadium animacji
    private boolean runningRight;

    public Mario(PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i=1; i<4; i++)
            frames.add(new TextureRegion(getTexture(), i*16, 10, 16, 18));
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i=4; i<6; i++)
            frames.add(new TextureRegion(getTexture(), i*16, 10, 16, 18));

        marioJump = new Animation(0.2f, frames);

        marioStand = new TextureRegion(getTexture(), 0, 10, 16, 18);
        defineMario();
        setBounds(0, 0, 16/MarioBros.PPM, 16/MarioBros.PPM);
        setRegion(marioStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch(currentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true); //loopable animation second parameter "true"
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        //if mario is running to the left and Region isnt turned to the left then turn it to the left
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return  region;
    }

    private State getState() {
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    private void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/MarioBros.PPM, 32/MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7/MarioBros.PPM);

        fdef.filter.categoryBits = MarioBros.MARIO_BIT; // jakiej kategorii jest ten fixture czyli w tym wypadku Mario Mario jest kategori Mario Bit
        fdef.filter.maskBits = MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                MarioBros.OBJECT_BIT|
                MarioBros.ENEMY_HEAD_BIT | // ustawiamy z czym Mario może kolidować
                MarioBros.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        //sensor for MarioHead
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-3 / MarioBros.PPM, 10 / MarioBros.PPM), new Vector2( 3 / MarioBros.PPM, 10 / MarioBros.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");


    }

}
