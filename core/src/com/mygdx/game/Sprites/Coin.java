package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.scenes.Hud;

public class Coin extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 1201;
    private MapObject mapObject;

    public Coin(World world, TiledMap map, Rectangle bounds){
        // Llamada al constructor de la super clase pasando(el mundo Box2D, el mapa Tiled y los límites)
        super(world, map, bounds);
        // Pillamos el tile poolroom de assets
        tileSet = map.getTileSets().getTileSet("poolroom");
        // Asignamos el objeto Coin como el dato de usuario del fixture
        fixture.setUserData(this);
        // Asignar el objeto del mapa asociado a esta moneda
        this.mapObject = findMapObject(map, bounds);
    }
    // Mñetodo de colisión con la cabeza
    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "colision");
        Hud.addScore(-1);
    }
    // Buscamos un objeto del mapa Tiled que este contenido dentro de los limites especificados

    private MapObject findMapObject(TiledMap map, Rectangle bounds) {
       return null;
    }
}
