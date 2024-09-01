package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BackRoom;

// Esta clase abstracta representa un objeto de mosaico interactivo en el mundo del juego.
public abstract class InteractiveTileObject {

    protected World world; // El mundo de Box2D al que pertenece este objeto.
    protected TiledMap map; // El TiledMap que contiene este objeto.
    protected TiledMapTile tile; // El TiledMapTile asociado con este objeto.
    protected Rectangle bounds; // Los límites de este objeto.
    protected Body body; // El cuerpo de Box2D asociado con este objeto.
    protected Fixture fixture; // El fixture adjunto al cuerpo.

    // Constructor para InteractiveTileObject.
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;
        // Definición del cuerpo
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        // Estableciendo el tipo de cuerpo en StaticBody.
        bdef.type = BodyDef.BodyType.StaticBody;
        // Estableciendo la posición del cuerpo basado en los límites.
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / BackRoom.PPPM,
                (bounds.getY() + bounds.getHeight() / 2) / BackRoom.PPPM);
        // Creando el cuerpo en el mundo
        body = world.createBody(bdef);

        // Creando una forma de caja para el fixture.
        PolygonShape shape = new PolygonShape();
        // Establecemos parámetros con límites
        shape.setAsBox(bounds.getWidth() / 2 / BackRoom.PPPM, bounds.getHeight() / 2 / BackRoom.PPPM);
        // Asignamos la forma de caja al fixture
        fdef.shape = shape;
        // Creamos el fixture en el cuerpo del objeto interactivo
        fixture = body.createFixture(fdef); // Creando el fixture.
    }

    // Método abstracto para manejar lo que sucede cuando este objeto es golpeado en la cabeza.
    public abstract void onHeadHit();


}
