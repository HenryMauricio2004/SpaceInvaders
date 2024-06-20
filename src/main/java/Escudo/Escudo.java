package Escudo;

import GameObject.GameObject;
import GameObject.DamagableObjectInterface;
import Sprites.SpriteInterfaces.SpritesEscudosInterface;

public class Escudo extends GameObject implements DamagableObjectInterface, SpritesEscudosInterface {
    private int nivelVida;

    public Escudo(int posicionX, int posicionY) {
        super(posicionX, posicionY);
        this.nivelVida = 7; // Vida inicial del escudo
        cambiarApariencia();
    }

    public Escudo() {
        this.nivelVida = 7;
        cambiarApariencia();// Vida inicial del escudo
    }

    /**
     * @return vida restante del escudo
     * */
    public int getNivelVida() {
        return nivelVida;
    }


    /**
     * @param nivelVida cantidad de golpes que el escudo aguanta antes de destruirse
     * */
    public void setNivelVida(int nivelVida) {
        this.nivelVida = nivelVida;
    }

    /**
     * Reduce 1 punto de vida del Escudo
     * */
    @Override
    public void getDamage() {
        nivelVida--;
        if (nivelVida > 0){ cambiarApariencia(); }
        else { System.out.println("Escudo Destruido"); }
    }

    /**
     * Cambia el sprite del escudo según su vida restante
     * Aún no es funcional
     * */
    public void cambiarApariencia() {

        switch (nivelVida){
            case 1:
                setSprite(spriteEscudo_1);
                break;
            case 2:
                setSprite(spriteEscudo_2);
                break;
            case 3:
                setSprite(spriteEscudo_3);
                break;
            case 4:
                setSprite(spriteEscudo_4);
                break;
            case 5:
                setSprite(spriteEscudo_5);
                break;
            case 6:
                setSprite(spriteEscudo_6);
                break;
            case 7:
                setSprite(spriteEscudo_7);
                break;

            default:

                break;
        }

    }

    public void mostrarSprite(boolean mostrar){

        if (mostrar){
            setSprite(null);
        }
        else{
            cambiarApariencia();
        }

    }
}
