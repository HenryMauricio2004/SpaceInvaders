package Proyectil;

import javafx.scene.image.ImageView;

public class ProyectilFactory {

    private static ProyectilFactory proyectilFactory;

    public static ProyectilFactory getInstance(){
        if (proyectilFactory == null){
            proyectilFactory = new ProyectilFactory();
        }
        return proyectilFactory;
    }

    private ProyectilFactory(){}

    public Proyectil crearProyectil(String naveOrigen, int[] posicionInicial, ImageView sprite){
        return new Proyectil(naveOrigen, posicionInicial, sprite);
    }

}