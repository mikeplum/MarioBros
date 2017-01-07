package com.sugarplum.mariobros.Scenes;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sugarplum.mariobros.MarioBros;

import java.awt.*;

/**
 * Created by MikePlum on 2016-12-17.
 * Viewport dla Hud'a jest stworzony osobno ponieważ będzie renderowany
 * niezależnie od reszty świata gry. i pote
 */
public class Hud {
    public Stage stage; //
    private Viewport viewport; //Viewport dla Huda

    private  Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label worldLabel;
    Label marioLabel;
    Label levelLabel;


    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,sb);  // będziemy używać tego samego spriteBatcha co MarioBros nie tworzymy żadnego nowego żeby mieć pewność że nie został nigdzie w pamięci utworzony niepotrzebnie nowy spriteBatch
        /*
        o stage'u myślimy jak o pudle w którym znajdują się wigety
        żeby były one uporządkowane musimy je poustawiać w odpowienich miejscach
        pudła. A do tego potrzebne są "przegródki" posłuży do tego klasa Table.
        */

        Table table = new Table();
        table.top();
        table.setFillParent(true); //table is the size of the stage

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }
}
