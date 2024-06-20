package Escena;

import GameObject.GameObject;
import Escudo.*;
import Nave.Alien.Alien;
import Nave.Alien.AlienBonus;
import Nave.Player.Player;
import Proyectil.*;
import com.example.spaceinvaders.HelloController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Escena {

    private static Player jugador;
    private AlienBonus alienBonus = null;
    private static ArrayList<Alien> enemigos = new ArrayList<Alien>();
    private static ArrayList<Proyectil> proyectiles = new ArrayList<Proyectil>();
    private static ArrayList<Escudo> escudos = new ArrayList<Escudo>();

    private static Pane pn_ventanaNivel;
    private static Escena nivel = null;

    private Escena(){}

    public static Escena getInstance(){
        if (nivel == null) nivel = new Escena();
        return nivel;
    }

    public void setPane(Pane pn_ventanaNivel){
        this.pn_ventanaNivel = pn_ventanaNivel;
    }

    /*========================================================================*/

    /**
     * acceder al jugador dentro del nivel*/
    public Player getJugador(){
        return jugador;
    }

    /**
     * @param index numero del alien en el registro
     * @return alien en el registro correspondiente al index
     * */
    public Alien getAlien(int index){
        return enemigos.get(index);
    }

    public Alien getAlienBonus(){return alienBonus;}

    /**
     * @return cantidad de aliens que hay en el nivel*/
    public int getCantidadAliens(){
        return enemigos.size();
    }

    /**
     * @param index numero del escudo en el registro
     * @return escudo en el registro corresopndiente al index*/
    public Escudo getEscudo(int index){
        return escudos.get(index);
    }

    /**
     * @return cantidad de escudos que hay en el nivel*/
    public int getCantidadEscudos(){
        return escudos.size();
    }


    /**
     * @param index numero del proyectil en el registro
     * @return proyectil en el registro correspondiente al index*/
    public Proyectil getProyectil(int index){
        return proyectiles.get(index);
    }

    /**
     * @return cantidad de proyectiles registrados en el nivel*/
    public int getCantidadProyectiles(){
        return proyectiles.size();
    }

    /*========================================================================*/

    private void agregarElemento(GameObject objeto){
        pn_ventanaNivel.getChildren().add(objeto.getSpriteViewer());
        objeto.getSpriteViewer().setLayoutX(objeto.getPosition()[0]);
        objeto.getSpriteViewer().setLayoutY(objeto.getPosition()[1]);
    }

    /**
     * Inicializa al jugador en el nivel*/
    public void declararJugador(){
        jugador = new Player();
    }

    /**
     * @param nuevoAlien tipo de alien específico a agregar en el registro*/
    public void agregarAlien(Alien nuevoAlien){
        enemigos.add(nuevoAlien);
        agregarElemento(nuevoAlien);
    }

    public void agregarAlienBonus(AlienBonus alienBonus){
        if (this.alienBonus == null && alienBonus != null){
            this.alienBonus = alienBonus;
            System.out.println("alien bonus agregado a la escena!");
            agregarElemento(alienBonus);
        }
    }

    /**
     * @param nuevoEscudo escudo a agregar en el registro*/
    public void agregarEscudo(Escudo nuevoEscudo){
        escudos.add(nuevoEscudo);
        agregarElemento(nuevoEscudo);
    }

    /**
     * @param nuevoProyectil proyectil a agregar en el registro*/
    public void agregarProyectil(Proyectil nuevoProyectil){
        proyectiles.add(nuevoProyectil);
        agregarElemento(nuevoProyectil);
    }

    /*========================================================================*/

    public void eliminarAlien(Alien enemigo){

        pn_ventanaNivel.getChildren().remove(enemigo.getSpriteViewer());
        enemigos.remove(enemigo);
        enemigo.setSpriteViewer(null);
    }

    public void eliminarAlienBonus(){
        pn_ventanaNivel.getChildren().remove(alienBonus.getSpriteViewer());
        alienBonus = null;
    }

    public void eliminarProyectil(Proyectil proyectil){

        pn_ventanaNivel.getChildren().remove(proyectil.getSpriteViewer());
        proyectiles.remove(proyectil);
        proyectil.setSpriteViewer(null);
    }

    public void eliminarEscudo(Escudo escudo){
        pn_ventanaNivel.getChildren().remove(escudo.getSpriteViewer());
        escudos.remove(escudo);
        escudo.setSpriteViewer(null);
    }

}
