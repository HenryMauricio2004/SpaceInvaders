package Nave.Alien;
import Escena.Mediator;
import Nave.Ship;
import Nave.enumDirecciones;
import Sprites.SpriteInterfaces.AlienSpritesInterface;
import javafx.scene.image.Image;

import static java.lang.Thread.sleep;

/**
 * Clase de Aliens
 */
public class Alien extends Ship implements AlienSpritesInterface {
    private int valuePoints;
    private Mediator mediador = Mediator.getInstance();
    private enumDirecciones direccion;
    private int posicionInicialX;

    /**
     * Aliens en genral con 1 vida y su desplazamiento sera de 2 pixels/segundo
     * @param valuePoints los puntos que tendran todos los aliens
     */
    public Alien(int valuePoints, Image sprite) {
        super(1, 1);
        setSprite(sprite);

        this.valuePoints = valuePoints;
    }

    public void disparar(){
        mediador.notificar(this);
    }

    /**
     * el movimiento de alien in game
     */
    public void moveY(){
        setPosicionY(getPosition()[1] + 10);
    }
    public void moveY(int ySpeed){
        setPosicionY(getPosition()[1] + ySpeed);
    }

    public void moveX()
    {
        if (direccion == enumDirecciones.DERECHA){
            setPosicionX(getPosition()[0] + getxSpeed());
        }
        else {
            setPosicionX(getPosition()[0] - getxSpeed());
        }

    }

    public void setSpeed(int xSpeed){
        super.setxSpeed(xSpeed);
    }

    /**
     * El incremento de la velocidad en general de los aliens
     * @version 1.0
     */
    public void incrementSpeed(){
        setxSpeed(getxSpeed() + 1);
    }

    /**
     *
     * @return los puntos que tiene el alien
     */
    public int getValuePoints() {
        return valuePoints;
    }

    /**
     *
     * @param valuePoints puntaje que se ira inicializando
     */
    public void setValuePoints(int valuePoints) {
        this.valuePoints = valuePoints;
    }

    /**
     * Por cada disparo se ira reduciendo su vida
     */
    @Override
    public void getDamage()
    {}

    public void setDireccion(enumDirecciones direccion){
        this.direccion = direccion;
    }

    public enumDirecciones getDireccion(){
        return direccion;
    }

    public void setPosicionInicialX(int posicionInicialX){
        this.posicionInicialX = posicionInicialX;
    }

    public int getPosicionInicialX(){
        return posicionInicialX;
    }

}
