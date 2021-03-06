package com.sugarplum.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sugarplum.mariobros.MarioBros;
import com.sugarplum.mariobros.Scenes.Hud;
import com.sugarplum.mariobros.Sprites.Enemies.Enemy;
import com.sugarplum.mariobros.Sprites.Items.Item;
import com.sugarplum.mariobros.Sprites.Items.ItemDef;
import com.sugarplum.mariobros.Sprites.Items.Mushroom;
import com.sugarplum.mariobros.Sprites.Mario;
import com.sugarplum.mariobros.Tools.B2WorldCreator;
import com.sugarplum.mariobros.Tools.WorldContactListener;

import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by MikePlum on 2016-12-17.
 */
public class PlayScreen implements Screen {

    private MarioBros game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //zmienne mapy
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Mario player;

    //zmienne box2d
    private World world;
    private Box2DDebugRenderer b2dr; //debug renderer sprawi że będziemy widzieli warstwy obiektów
    private B2WorldCreator creator;

    private Music music;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    public PlayScreen(MarioBros game){

        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;

        //tworzymy kamerę która będzie śledziła poczynania Mario
        gamecam = new OrthographicCamera();

        //tworzymy fitVieport by utrzymać aspect ratio jaki nas interesuje
        gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gamecam);

        //tworzymy Heads-up Display czyli interface graficzy odpowiedzialny za wyświetlanie dodatkowych informacji w grze
        hud = new Hud(game.batch);

        //ładujemy mapę i ustawiamy renderer mapy
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1  / MarioBros.PPM);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //Tworzymy Mario
        player = new Mario(this);

        world.setContactListener(new WorldContactListener());

        music = MarioBros.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.play();

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

    }

    public void spawnItems(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Mushroom.class){
                items.add(new Mushroom(this, idef.position.x, idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    private void handleImput(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(), true);
            System.out.println("Moved up");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            System.out.println("Moved right");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            System.out.println("Moved left");
        }
    }


    public void update(float dt) {
        handleImput(dt);
        handleSpawningItems();

        world.step(1/60f, 6, 2);//dodatkowa informacja: tutaj jest wywoływany ContactListener

        player.update(dt);
        for(Enemy enemy : creator.getGoombas()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / MarioBros.PPM){
                enemy.b2body.setActive(true);
            }
        }

        for(Item item : items){
            item.update(dt);
        }

        hud.update(dt);//odpalamy Timer

        gamecam.position.x = player.b2body.getPosition().x; //kamera ma śledzić poruszającego się Mario

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


        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy : creator.getGoombas()) {
            enemy.draw(game.batch);
        }
        for(Item item : items){
            item.draw(game.batch);
        }
        game.batch.end();
        //ustawiamy batch aby wyświetlał to co widzi kamera Hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        music.dispose();
    }
}
