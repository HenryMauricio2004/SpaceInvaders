package Nave.Player;

import Escena.Mediator;
import Nave.Ship;
import Nave.enumDirecciones;
import Sprites.SpriteInterfaces.PlayerSpriteInterface;

/**
 * Clase de nave Jugador
 */
public class Player extends Ship implements PlayerSpriteInterface {
    private int p;
    private String id;
    private Mediator mediador = Mediator.getInstance();

    /**
     * Constructor de la clase Player, donde definimos que tendra 3 de nivelVida y su velocidad inicial es 0
     */
    public Player() {
        super(3, 20, 200, 650);
        setSprite(spriteJugador);
    }

    public void disparar(){
        mediador.notificar(this);
    }


    /**
     * Se encarga de mover el Jugador
     * @param direccion dirrecion del movimiento
     */
    public void move(Enum<enumDirecciones> direccion){

        if (direccion.equals(enumDirecciones.DERECHA)){
            setPosicionX(getPosition()[0] + getxSpeed());
        }

        if (direccion.equals(enumDirecciones.IZQUIERDA)){
            setPosicionX(getPosition()[0] - getxSpeed());
        }
    }

    @Override
    public void getDamage() {
        if (getNivelVida() > 0){
            this.setNivelVida(getNivelVida()-1);
        } else {
            System.out.println("GAME OVER...");
        }
    }

    /**
     * Estado de la partida
     * @return true
     */
    public boolean gameOver(){
        return true;
    }

    /**
     * Mostrar el sprite del Jugador
     */
    public void showShip(){

    }

    /**
     * Obtener el valor de p
     * @return La puntuacion
     */
    public int getP() {
        return p;
    }

    /**
     * Cambiar el valor de p
     * @param p La nueva puntuacion
     */
    public void setP(int p) {
        this.p = p;
    }

    /**
     * Obtener el valor de id
     * @return El id
     */
    public String getId() {
        return id;
    }

    /**
     * Cambiar el valor de id
     * @param id El nuevo id
     */
    public void setId(String id) {
        this.id = id;
    }
}
