package com.sugarplum.mariobros.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Scenes.Hud;
import com.sugarplum.mariobros.Sprites.Mario;

/**
 * Created by MikePlum on 2016-12-17.
 */
public class PlayScreen implements Screen {

    private MarioBros game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //zmienne mapy
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private  Mario player;

    //zmienne box2d
    private World world;
    private Box2DDebugRenderer b2dr; //debug renderer sprawi że będziemy widzieli warstwy obiektów

    public PlayScreen(MarioBros game){
        this.game = game;

        //tworzymy kamerę która będzie śledziła poczynania Mario
        gamecam = new OrthographicCamera();

        //tworzymy fitVieport by utrzymać aspect ratio jaki nas interesuje
        gamePort = new FitViewport(MarioBros.V_WIDTH , MarioBros.V_HEIGHT , gamecam);

        //tworzymy Heads-up Display czyli interface graficzy odpowiedzialny za wyświetlanie dodatkowych informacji w grze
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2,0);

        world = new World( new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        player = new Mario(world);
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
                bdef.position.set(rect.getX() + rect.getWidth() / 2 , rect.getY() + rect.getHeight() / 2 );

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2 );
                fdef.shape = shape;
                body.createFixture(fdef);
            }

            //będziemy pobierać obiekty z 4 warsty czyli pipes
            for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set(rect.getX() + rect.getWidth() / 2 , rect.getY() + rect.getHeight() / 2 );

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2 );
                fdef.shape = shape;
                body.createFixture(fdef);
            }

            //będziemy pobierać obiekty z 6 warsty czyli bricks
            for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set(rect.getX() + rect.getWidth() / 2 , rect.getY() + rect.getHeight() / 2 );

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2 );
                fdef.shape = shape;
                body.createFixture(fdef);
            }

             //będziemy pobierać obiekty z 5 warsty czyli coins
            for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set(rect.getX() + rect.getWidth() / 2 , rect.getY() + rect.getHeight() / 2 );

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2 );
                fdef.shape = shape;
                body.createFixture(fdef);
            }


    }

    @Override
    public void show() {

    }

    private void handleImput(float dt) {
        if(Gdx.input.isTouched())
            //Tymczaswoe sprawdzenie działania input: jeśli damy cokolwiek na wejście jakieklowiek klikanie to kamera ma się przesówać
            gamecam.position.x += 100 * dt;
    }


    public void update(float dt) {
        handleImput(dt);

        world.step(1/60f, 6, 2);

        gamecam.update();
        //będziemy renderować czyli podawać na wyjście na ekran tylko to co widzi nasza kamera
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1); // Color + Aplha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the screen

        renderer.render(); //renderowanie mapy

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
