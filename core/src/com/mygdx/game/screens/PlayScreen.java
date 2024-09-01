package com.mygdx.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BackRoom;
import com.mygdx.game.Sprites.Mob;
import com.mygdx.game.Tools.B2CreadorMundo;
import com.mygdx.game.Tools.WorlContactListener;
import com.mygdx.game.scenes.Hud;

public class PlayScreen implements Screen{
    private BackRoom game;
    private TextureAtlas atlas;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    public Mob player;
    private Music music;


    public PlayScreen(BackRoom game) {
        //  Cargamos archivo atlas
        atlas = new TextureAtlas("Mob_and_enemies.pack");
        // Asignación de la instancia del juego proporcionada a un constructor local
        this.game = game;
        // Creación de camara ortográfica
        gamecam = new OrthographicCamera();
        // Ajustes del tamaño de la pestaña del juego
        gamePort = new FitViewport(BackRoom.V_WIDTH / BackRoom.PPPM, BackRoom.V_HEIGHT / BackRoom.PPPM, gamecam);
        // Creación del HUD
        hud = new Hud(game.batch);
        // Cargamos mapa
        maploader = new TmxMapLoader();
        map = maploader.load("prueba10.tmx");
        // Renderizador del mapa ortogonal para renderizar el mapa cargado
        renderer = new OrthogonalTiledMapRenderer(map, 1 / BackRoom.PPPM);
        // Configuración de la posicion inicial de la cámara del juego
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        // Creación nuevo mundo con gravedad en el eje y
        world = new World(new Vector2(0, -10), true);
        // Depurador de Box2D
        b2dr = new Box2DDebugRenderer();
        // Aquí creamos un objeto encargado de crear cuerpos físicos en el mundo Box2d
        new B2CreadorMundo(world, map);
        player = new Mob(world, this);
        world.setContactListener(new WorlContactListener());
        // Música
        music = BackRoom.manager.get("audio/music/musica1.ogg", Music.class);
        music.setLooping(true);
        music.play();
    }
    // Acceso a las regiones de textura atlas
    public TextureAtlas getAtlas(){
        return atlas;
    }
    @Override
    public void show() {
    }
    // Movilidad
    public void handleInput(float dt){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 2.5f), player.b2body.getWorldCenter(),true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(),true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }
    public void update(float dt){
        handleInput(dt);
        // Tiempo avanza simulación / velocidad iteración de la resolución de colisiones / velocidad de iteración de la resolución de la posición
        world.step(1/60f, 6, 2);
        player.update(dt);
        hud.update(dt);
        // Pilla cambiod de posicion en una variable x e y
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();
    }


    @Override
    public void render(float delta) {
        update(delta);
        // limpiar bufer de color
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // configurar vista del renderizador para su ajuste con la camara del juego
        renderer.setView(gamecam);
        renderer.render();
        // renderización mundo junto a la camara de posición
        b2dr.render(world, gamecam.combined);
        // configuración de la matriz de proyección del Batch(con el que pintamos cosas en pantalla)
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        // (El dibujo)(inicia/dibuja/termina)
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Se le llama al método automaticamente cuando cambia la ventana del juego cambia de tamaño
        gamePort.update(width, height);
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
    // Proceso de liberar recursos
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}