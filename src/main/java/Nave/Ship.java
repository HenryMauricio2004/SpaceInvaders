package Nave;

import GameObject.DamagableObjectInterface;
import GameObject.GameObject;

/**
 * Clase Abstracta para las Naves
 */
public abstract class Ship extends GameObject implements DamagableObjectInterface {
    private int nivelVida;
    private int xSpeed;

    /**
     * Constructor de la clase Ship
     * @param nivelVida Nivel de vida de las naves
     * @param xSpeed Velocidda de las naves
     */
    public Ship(int nivelVida, int xSpeed) {
        this.nivelVida = nivelVida;
        this.xSpeed = xSpeed;
    }

    public Ship(int nivelVida, int xSpeed, int positionX, int positionY){
        super(positionX, positionY);
        this.nivelVida = nivelVida;
        this.xSpeed = xSpeed;
    }

    /**
     * Mover las naves en la direcion X
     * @return La cantidad que se mueven
     */
    //public abstract int moveX();
    /**
     * Mover las naves en la direcion Y
     * @return La cantidad que se mueven
     */
    //public abstract int moveY();

    //Unico metodo abstract
    //public abstract void move();

    //Metodos set y get
    /**
     * Obtener el valor de nivelVida
     * @return El nivel de vida
     */
    public int getNivelVida() {return nivelVida;}

    /**
     * Cambiar el valor de nivelVida
     * @param nivelVida El nuevo nivel de vida
     */
    public void setNivelVida(int nivelVida) {this.nivelVida = nivelVida;}

    /**
     * Obtener el valor de xSpeed
     * @return La velocidad
     */
    public int getxSpeed() {return xSpeed;}

    /**
     * Cambiar el valor de xSpeed
     * @param xSpeed La nueva velocidad
     */
    public void setxSpeed(int xSpeed) {this.xSpeed = xSpeed;}
}
