package Nave.Alien.FactoryDirector;

import Nave.Alien.Alien;
import Nave.Alien.AlienBonus;
import Nave.Alien.AlienFactory.*;
import Nave.Alien.enumAliens;

import java.util.ArrayList;

/**
 * Clase AlienFactoryDirector
 */
public class AlienFactoryDirector {

    private static AlienFactoryDirector alienDirector = null;

    private AlienFactoryDirector(){}

    /**
     * Solo existe una iteración de AlienFactoryDirector
     * @return El Director de los factories de aliens
     */
    public static AlienFactoryDirector getInstance(){
        if (alienDirector == null){
            alienDirector = new AlienFactoryDirector();
        }
        return alienDirector;
    }

    AlienGrandeFactory alienGrandeFactory = new AlienGrandeFactory();
    AlienMedianoFactory alienMedianoFactory = new AlienMedianoFactory();
    AlienPequenoFactory alienPequenoFactory = new AlienPequenoFactory();
    AlienBonusFactory alienBonusFactory = new AlienBonusFactory();

    /**
     * nuevoAlien quien almacene el alien concreto
     * @param tipoAlien Que tipo de alien va a ir creandose
     * @return Nuevo Alienígena según las especificaciones dadas
     */
    private Alien crearAlien(enumAliens tipoAlien){
        Alien nuevoAlien;

        switch(tipoAlien){
            case ALIEN_PEQUENO:
                nuevoAlien = alienPequenoFactory.createAlien();
                break;

            case ALIEN_MEDIANO:
                nuevoAlien = alienMedianoFactory.createAlien();
                break;

            case ALIEN_GRANDE:
                nuevoAlien = alienGrandeFactory.createAlien();
                break;

            default:
                nuevoAlien = null;
                break;
        }

        return nuevoAlien;
    }

    public AlienBonus crearAlienBonus(int initialSpeed){
        AlienBonus alienBonus = alienBonusFactory.createAlien();
        alienBonus.setxSpeed(initialSpeed);
        return alienBonus;
    }

    public ArrayList<Alien> crearHorda(int initialSpeed){

        int filas = 5;
        int columnas = 9;
        int espacioEntreColumnas = 70;
        int espacioEntreFilas = 55;
        int xInicial = 15;
        int yInicial = 30;

        enumAliens alien;

        ArrayList<Alien> horda = new ArrayList<Alien>();

        for (int i = 0; i < filas; i++){

            if (i < 2){alien = enumAliens.ALIEN_PEQUENO;}
            else if (i < 4){alien = enumAliens.ALIEN_MEDIANO;}
            else {alien = enumAliens.ALIEN_GRANDE;}

            for (int j = 0; j < columnas; j++){

                Alien nuevoAlien = crearAlien(alien);
                nuevoAlien.setPosicion(xInicial + (espacioEntreColumnas * j), yInicial + (espacioEntreFilas * i));
                nuevoAlien.setPosicionInicialX(xInicial + (espacioEntreColumnas  * j));
                nuevoAlien.setxSpeed(initialSpeed);
                horda.add(nuevoAlien);

            }

        }

        return horda;
    }
}
