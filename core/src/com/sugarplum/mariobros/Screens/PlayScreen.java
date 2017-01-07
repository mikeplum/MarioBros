package com.sugarplum.mariobros.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Scenes.Hud;

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

    public PlayScreen(MarioBros game){
        this.game = game;

        //tworzymy kamerę która będzie śledziła poczynania Mario
        gamecam = new OrthographicCamera();

        //tworzymy fitVieport by utrzymać aspect ratio jaki nas interesuje
        gamePort = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, gamecam);

        //tworzymy Heads-up Display czyli interface graficzy odpowiedzialny za wyświetlanie dodatkowych informacji w grze
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2,0);

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

        gamecam.update();
        //będziemy renderować czyli podawać na wyjście na ekran tylko to co widzi nasza kamera
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1); // Color + Aplha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the screen

        renderer.render();

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
