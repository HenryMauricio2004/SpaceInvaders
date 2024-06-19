package Nave.Alien;

/**
 * Clase de Aliens tipo: Bonus
 */
public class AlienBonus extends Alien{
    /**
     * Constructor de la Clase AlienBonus
     * @param valuePoints tiene que ser un valor aleatorio que en encuentre entre (50-300)
     */
    public AlienBonus(int valuePoints) {
        super(valuePoints, sprite_NaveBonus);
    }
}
