package Escena;

import Nave.Alien.Alien;
import Nave.Player.Player;
import Puntaje.Puntuacion;
import Escena.MediatorInterfaces.*;
import Nave.Alien.FactoryDirector.AlienFactoryDirector;
import Escudo.EscudoDirector;
import Proyectil.Proyectil;
import Proyectil.ProyectilFactory;
import Escudo.Escudo;
import GameObject.GameObject;
import Nave.enumDirecciones;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class Mediator implements MediadorAlien, MediadorNaveJugador, MediatorProyectil{

    private Escena nivel = Escena.getInstance();
    private Puntuacion puntuacion = new Puntuacion();
    private AlienFactoryDirector directorAlien = AlienFactoryDirector.getInstance();
    private EscudoDirector directorEscudo = EscudoDirector.getInstance();


    private int vecesAceleracionHorda = 0;

    private static Mediator mediador = null;
    private Mediator(){}

    public static Mediator getInstance(){

        if (mediador == null){
            mediador = new Mediator();
        }
        return mediador;
    }

    /**
     * @param proyectil proyectil que actualiza su ubicación para evaluár colisiones
     */
    public void notificar(Proyectil proyectil){
        int cantEnemigos = nivel.getCantidadAliens();
        int cantEscudos = nivel.getCantidadEscudos();

        for (int i = 0; i < cantEscudos; i++){

            Escudo escudo = nivel.getEscudo(i);

            if ( checarColision(proyectil, escudo) ){

                System.out.println("UN ESCUDO A SIDO DAÑADO!!!");
                escudo.getDamage();

                if (escudo.getNivelVida() < 1){

                    nivel.eliminarEscudo(escudo);
                }

                nivel.eliminarProyectil(proyectil);
                break;
            }

        }

        if (proyectil.getNaveOrigen().equals("enemigo")){

            if ( checarColision(proyectil, nivel.getJugador()) ){
                System.out.println("JUGADOR A SIDO DAÑADO!!!");
                nivel.getJugador().getDamage();

                if (nivel.getJugador().getNivelVida() < 1){
                    //gameOver();
                }
                nivel.eliminarProyectil(proyectil);
            }

        } else {

            for (int i = 0; i < cantEnemigos; i++){
                Alien alien = nivel.getAlien(i);

                if (checarColision(proyectil, alien)){
                    System.out.println("UN ALIEN A SIDO ELIMINADO!!!");
                    puntuacion.sumarPuntuacion(alien.getValuePoints());
                    nivel.eliminarAlien(alien);
                    nivel.eliminarProyectil(proyectil);
                }
            }

        }



    }

    /**
     * Notifica que el que esta disparando es el Jugador!
     * @param naveJugador instancia del Jugador
     */
    @Override
    public void notificar(Player naveJugador) {
        ProyectilFactory PlayerShoot = ProyectilFactory.getInstance();
        Proyectil nuevoProyectil = PlayerShoot.crearProyectil("jugador", nivel.getJugador().getPosition(), nivel.getJugador().getSpriteViewer());
        nivel.agregarProyectil(nuevoProyectil);
    }


    /**
     * AlGun sera quien almacene los proyerctiles de Alien
     * <p>
     * CrearProyectil Mediante la posicion in game de alien, el proyectil se creara y se movera de acuerdo a Proyectil
     * <p>
     * Devuelve la notificacion que Alien ha disparado
     * @param alien alienigena que notifica un nuevo disparo
     */
    @Override
    public void notificar(Alien alien)
    {
        System.out.println("ALIEN HA DISPARADO");

        ProyectilFactory AlGun = ProyectilFactory.getInstance();
        Proyectil nuevoProyectil = AlGun.crearProyectil("enemigo", alien.getPosition(), alien.getSpriteViewer());
        nivel.agregarProyectil(nuevoProyectil);
    }

    /**
     * Verifica si ambos objetos comparten la misma posicion
     * @param objeto1 Primer objeto a verificar
     * @param objeto2 Segundo objeto a verificar
     */
    private boolean checarColision(GameObject objeto1, GameObject objeto2) {
        return objeto1.getSpriteViewer().getBoundsInParent().intersects(objeto2.getSpriteViewer().getBoundsInParent());
    }



    public void crearEscudos(){
        ArrayList<Escudo> escudos = directorEscudo.construirEscudos();

        for (Escudo escudo : escudos){
            nivel.agregarEscudo(escudo);
        }
    }

    public void crearHorda(){
        vecesAceleracionHorda = 0;
        ArrayList<Alien> horda = directorAlien.crearHorda();

        for (Alien alien : horda){
            alien.setDireccion(enumDirecciones.DERECHA);
            nivel.agregarAlien(alien);
        }
    }

    public void moverHorda(){
        System.out.println(nivel.getCantidadAliens());

        if (nivel.getCantidadAliens() < 5 && vecesAceleracionHorda < 5){
            aumentarVelocidadHorda();
            vecesAceleracionHorda++;
        }
        else if (nivel.getCantidadAliens() < 10 && vecesAceleracionHorda < 4){
            aumentarVelocidadHorda();
            vecesAceleracionHorda++;
        }
        else if(nivel.getCantidadAliens() < 20 && vecesAceleracionHorda < 3){
            aumentarVelocidadHorda();
            vecesAceleracionHorda++;
        }
        else if(nivel.getCantidadAliens() < 30 && vecesAceleracionHorda < 2){
            aumentarVelocidadHorda();
            vecesAceleracionHorda++;
        }
        else if(nivel.getCantidadAliens() < 40 && vecesAceleracionHorda < 1){
            aumentarVelocidadHorda();
            vecesAceleracionHorda++;
        }

        for (int i = 0; i < nivel.getCantidadAliens(); i++){

            Alien alien = nivel.getAlien(i);

            if (alien.getPosition()[0] >= 750 && alien.getDireccion() == enumDirecciones.DERECHA){
                moverAliensY();
                setDireccionAlien(enumDirecciones.IZQUIERDA);
                break;
            } else if (alien.getPosition()[0] <= 10 && alien.getDireccion() == enumDirecciones.IZQUIERDA){
                moverAliensY();
                setDireccionAlien(enumDirecciones.DERECHA);
                break;
            }

        }

        moverAliensX();
    }

    private void setDireccionAlien(enumDirecciones direccion){
        for (int i = 0; i < nivel.getCantidadAliens(); i++){
            nivel.getAlien(i).setDireccion(direccion);
        }
    }

    private void moverAliensX(){
        for (int i = 0; i < nivel.getCantidadAliens(); i++){
            nivel.getAlien(i).moveX();
        }
    }

    private void moverAliensY(){
        for (int i = 0; i < nivel.getCantidadAliens(); i++){
            nivel.getAlien(i).moveY();
        }
    }

    private void aumentarVelocidadHorda(){
        for (int i = 0; i < nivel.getCantidadAliens(); i++){
            nivel.getAlien(i).incrementSpeed();
        }
    }

    public void verificarColisionAlien(){

        for (int i = 0; i < nivel.getCantidadAliens(); i++){

            for (int j = 0; j < nivel.getCantidadEscudos(); j++){

                if (checarColision(nivel.getAlien(i), nivel.getEscudo(j))){
                    nivel.eliminarEscudo(nivel.getEscudo(j));
                }

            }

        }
    }

    public void setAliensDisparos(){

        int alien = randomGenerator(1, nivel.getCantidadAliens());

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(randomGenerator(0,500)*1d));

        pauseTransition.setOnFinished(event -> {nivel.getAlien(alien).disparar();});
        pauseTransition.play();

    }

    public int randomGenerator(int limiteInferior, int limiteSuperior){
        return (int)(Math.random() * limiteSuperior) + limiteInferior;
    }

    public int getPuntuacion(){
        return puntuacion.getPuntaje();
    }

}
