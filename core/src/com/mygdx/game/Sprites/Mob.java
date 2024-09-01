package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BackRoom;
import com.mygdx.game.screens.PlayScreen;

public class Mob extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion mobTextureJump;

    public Mob(World world, PlayScreen screen){
        // llamad al constructor de la clase base
        super();
        // Asignación del mundo Box2d
        this.world = world;
        // llamada al método defineMoB
        defineMob();
        // Cargamos region de la textura del atlas asociado a la pantalla
        mobTextureJump = new TextureRegion(screen.getAtlas().findRegion("saltar"));
        // Establecemos limites del sprite
        setBounds(0, 0, 192/10, 256/10);
        // Establecer la región de textura de salto como región inicial
        setRegion(mobTextureJump);
    }

    public void defineMob() {
        // Creamos definición del cuerpo
        BodyDef bdef = new BodyDef();
        // Establecer la posición inicial en el centro de la pantalla
        bdef.position.set(BackRoom.V_WIDTH / 2 / BackRoom.PPPM, BackRoom.V_HEIGHT / 2 / BackRoom.PPPM); // Cambiado de PPPM a PPM
        // Establecemos el tipo de cuerpo como dinámico
        bdef.type = BodyDef.BodyType.DynamicBody;
        // Creamos el cuerpo
        b2body = world.createBody(bdef);

        // Creamos definición de fixure
        FixtureDef fdef = new FixtureDef();
        // Creamos la bolita o objeto circular
        CircleShape shape = new CircleShape();
        // Establecemos tamaño de l bolita
        shape.setRadius(6 / BackRoom.PPPM);
        // Establecemos la categoria de bits de colisión del personaje
        fdef.filter.categoryBits = BackRoom.MOB_BIT;
        // Establecemos mascara de bits de colisión del personaje
        fdef.filter.maskBits = BackRoom.DEFAULT | BackRoom.COIN_BIT;
        // asignamos la forma circular de la bolita
        fdef.shape = shape;
        // Creamos el fixture
        b2body.createFixture(fdef);
        // Creamos el borde de la cabeza para la colisiones
        EdgeShape head = new EdgeShape();
        // Inicio y fin de la forma del borde
        head.set(new Vector2(-2 / BackRoom.PPPM, 6 / BackRoom.PPPM), new Vector2(2 / BackRoom.PPPM, 6 / BackRoom.PPPM));
        //  Asignamos la forma de borde al fixture
        fdef.shape = head;
        // Establecemos un sensor
        fdef.isSensor = true;
        // Creamos el fixture para la cabeza del personaje
        b2body.createFixture(fdef).setUserData("head");
    }


    // Método para actualizar la posición del Sprite basado en la posición del cuerpo físico
    public void update(float dt) {
        // Ajusta la posición del sprite para que esté centrado en el cuerpo físico
        setPosition((b2body.getPosition().x - getWidth() / 2) * BackRoom.PPPM,
                (b2body.getPosition().y - getHeight() / 2) * BackRoom.PPPM);
    }

}