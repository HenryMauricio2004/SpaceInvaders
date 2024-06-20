package Proyectil;

import GameObject.GameObject;
import Sprites.SpriteInterfaces.ProyectilSpriteInterface;
import Escena.Mediator;
import javafx.scene.image.ImageView;

import static java.lang.Math.round;

public class Proyectil extends GameObject implements ProyectilSpriteInterface {

    private int velocidad = 5;
    private String naveOrigen;
    private Mediator mediator = Mediator.getInstance();

    /**
     * Crea un nuevo proyectil y determina su dirección según la nave que lo invoca
     *
     * @param naveOrigen el tipo de nave que invocó el proyectil y determina en que dirección se mueve ("enemigo"->hacia abajo, "jugador"->hacia arriba)
     * @param posicionNave posicion de la nave que invocó el proyectil (arreglo de formato: [posicion X, posicion Y])
     * */
    public Proyectil(String naveOrigen, int[] posicionNave, ImageView spriteNave){

        this.naveOrigen = naveOrigen;
        if (naveOrigen.equals("jugador")){
            velocidad *= -1;
        }

        int[] posicionInicial = {posicionNave[0] + (int)(spriteNave.getImage().getWidth()/2) -2, posicionNave[1]};

        super.setPosicion(posicionInicial);
        setSprite(spriteProyectil);
    }


    /**
     * Actualiza la posición del proyectil en base a su velocidad
     * */
    public void moverse(){

        super.setPosicion(super.getPosition()[0], super.getPosition()[1] + velocidad);
        mediator.notificar(this);
    }

    public String getNaveOrigen(){
        return naveOrigen;
    }

}
