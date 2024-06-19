package Nave.Alien.AlienFactory;

import Nave.Alien.Alien;
import Nave.Alien.AlienMediano;

public class AlienMedianoFactory implements AlienSpawner {
    /**
     * @return AlienMediano
     */
    public Alien createAlien() {
        return new AlienMediano();
    }
}
