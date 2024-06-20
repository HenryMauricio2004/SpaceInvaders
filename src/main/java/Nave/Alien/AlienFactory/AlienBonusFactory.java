package Nave.Alien.AlienFactory;

import Nave.Alien.Alien;
import Nave.Alien.AlienBonus;

public class AlienBonusFactory implements AlienSpawner{
    int points = randomValuePoints();

    /**
     * @return AlienBonus un alien con un puntaje aleatorio
     */
    public AlienBonus createAlien() {
        return new AlienBonus(points);
    }

    /**
     * Metodo para la creacion de puntos aleatorios que solo esta alien tendra
     * @return un valor entre 50 y 300
     */
    public int randomValuePoints() {
        return (int)(Math.random()*(300-50)+50);
    }
}
