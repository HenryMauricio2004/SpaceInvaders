package Nave.Alien.AlienFactory;

import Nave.Alien.Alien;

public interface AlienSpawner {
    /**
     * Es aplicable a los demas, devolvera un alien como concreto según sea el constructor
     * <p></p>
     * Metodo para crear un nuevo Alien de cualquier tipo
     */
    public Alien createAlien();
}
