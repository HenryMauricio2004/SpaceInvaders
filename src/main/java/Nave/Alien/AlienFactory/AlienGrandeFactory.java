package Nave.Alien.AlienFactory;

import Nave.Alien.Alien;
import Nave.Alien.AlienGrande;

public class AlienGrandeFactory implements AlienSpawner{
    /**
     * @return AlienGrande
     */
    public Alien createAlien() {
        return new AlienGrande();
    }
}
