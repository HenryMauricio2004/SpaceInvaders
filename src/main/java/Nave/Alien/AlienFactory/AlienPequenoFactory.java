package Nave.Alien.AlienFactory;

import Nave.Alien.Alien;
import Nave.Alien.AlienPequeno;

public class AlienPequenoFactory implements AlienSpawner{
    /**
     * @return AlienPequeno
     */
    public Alien createAlien() {
        return new AlienPequeno();
    }
}
