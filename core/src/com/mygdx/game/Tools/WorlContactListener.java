package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Sprites.InteractiveTileObject;

public class WorlContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // Obtiene los fixture que tengan que ver con el contacto
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        // Comprobación de si uno de los fixtures anteriores es de la cabeza
        if (fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            // Determinamos cual de los fixtures es el de la cabeza
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object =head == fixA ? fixB : fixA;
            // Analizamos la respuesta de la colision y si coincide llamamos a la función onHeadHit la cual se encargaba de restar puntuaje del HUD
            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }
    }
    // Método de fin de contacto de dos fixtures
    @Override
    public void endContact(Contact contact) {

    }
    // Método que se le llama cuando termina una colisión
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }
    // Método que se llama después de que se haya resuelto una colisión
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
