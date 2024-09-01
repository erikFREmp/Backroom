package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BackRoom;
import com.mygdx.game.Sprites.Coin;

public class B2CreadorMundo {
    public B2CreadorMundo(World world, TiledMap map){
        // Creación de cuerpos y texturas
        BodyDef bdefER = new BodyDef();
        PolygonShape shapeER = new PolygonShape();
        FixtureDef fdefER = new FixtureDef();
        Body body;
        /*Creamos bucles para recorrer las diferentes capas del mapa(capa 2,3,4 ya que la 1 solo es el background)*/
        for (MapObject objeto : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            bdefER.type = BodyDef.BodyType.StaticBody;
            bdefER.position.set((rect.getX() + rect.getWidth() / 2) / BackRoom.PPPM, (rect.getY() + rect.getHeight() / 2) / BackRoom.PPPM);
            body = world.createBody(bdefER);

            shapeER.setAsBox(rect.getWidth() / 2 / BackRoom.PPPM, rect.getHeight() / 2 / BackRoom.PPPM);
            fdefER.shape = shapeER;
            fdefER.isSensor = true; // Establecer como un "sensor" para que no genere respuesta física
            body.createFixture(fdefER);
        }

        for (MapObject objeto : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            new Coin(world, map, rect);
        }
        for (MapObject objeto : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            new Coin(world, map, rect);
        }
    }
}
