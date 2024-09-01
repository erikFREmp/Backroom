package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.BackRoom;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Integer tiempoMundo;
    private float tiempoCount;
    private static Integer score;
    private Label regresiva;
    private static Label scoreLabel;
    private Label tiempoLabel;
    private Label nivelLabel;
    private Label mundoLabel;
    private Label backLabel;
    private Label vida;
    public Hud (SpriteBatch sb){
        tiempoMundo = 60;
        tiempoCount  = 0;
        score = 31;
        viewport = new FitViewport(BackRoom.V_WIDTH, BackRoom.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);

        regresiva = new Label(String.format("%03d", tiempoMundo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        vida = new  Label("Vidas:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tiempoLabel = new Label("Tiempo:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        nivelLabel =  new Label("1-0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mundoLabel = new Label("Nivel 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        backLabel = new Label("BackRooms", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(backLabel).expandX().padBottom(180);
        table.add(mundoLabel).expandX().padBottom(180);
        table.add(tiempoLabel).expandX().padBottom(180);
        table.add(regresiva).expand().padBottom(180);
        table.add(vida).expand().padBottom(180);
        table.add(scoreLabel).expandX().padBottom(180);
        table.row();

        stage.addActor(table);
    }
    public void update(float dt){
        tiempoCount += dt;
        if (tiempoCount >= 1){
            tiempoMundo--;
            regresiva.setText(String.format("%03d", tiempoMundo));
            tiempoCount = 0;
        }


        if (score <= 0 || tiempoMundo <= 0) {
            Gdx.app.exit();
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}