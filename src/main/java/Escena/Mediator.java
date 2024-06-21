package Escena;

import Nave.Alien.Alien;
import Nave.Alien.AlienBonus;
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
import Sprites.SpriteInterfaces.PlayerSpriteInterface;


import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;



public class Mediator implements MediadorAlien, MediadorNaveJugador, MediatorProyectil, PlayerSpriteInterface{

    private Escena nivel = Escena.getInstance();
    private Puntuacion puntuacion = new Puntuacion();
    private AlienFactoryDirector directorAlien = AlienFactoryDirector.getInstance();
    private EscudoDirector directorEscudo = EscudoDirector.getInstance();
    private Pane pn_ventanaNivel = null;

    private int vecesAceleracionHorda = 0;
    private int horda = 0;
    private boolean pausa = false;
    private boolean gameOver = false;

    private PauseTransition pausaCreacionAlienBonus = new PauseTransition(Duration.millis(randomGenerator(2000, 4000)));

    private AnimationTimer animacionGameOver = new AnimationTimer() {

        int frames = 0;
        int framesToReset = 30;
        boolean mostrar = false;
        int repeticiones = 0;

        @Override
        public void handle(long now) {


            if (frames > framesToReset){
                frames = 0;

                if (mostrar){
                    mostrarJugador();
                    repeticiones++;
                } else {
                    ocultarJugador();
                }


                framesToReset -= 4;
                mostrar = !mostrar;
            }
            frames++;

            if (repeticiones >= 7){
                animacionGameOver.stop();
                nivel.eliminarJugador();
            }
        }


    };


    private static Mediator mediador = null;
    private Mediator(){}

    public static Mediator getInstance(){

        if (mediador == null){
            mediador = new Mediator();
        }
        return mediador;
    }

    public void setPane(Pane pn_ventanaNivel){
        this.pn_ventanaNivel = pn_ventanaNivel;
    }

    /**
     * @param proyectil proyectil que actualiza su ubicación para evaluár colisiones
     */
    public void notificar(Proyectil proyectil){
        int cantEnemigos = nivel.getCantidadAliens();
        int cantEscudos = nivel.getCantidadEscudos();


        if (proyectil.getPosition()[1] < 0 || proyectil.getPosition()[1] > pn_ventanaNivel.getHeight()){
            nivel.eliminarProyectil(proyectil);
        }

        for (int i = 0; i < cantEscudos; i++){

            Escudo escudo = nivel.getEscudo(i);

            if ( checarColision(proyectil, escudo) ){

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

                }
                nivel.eliminarProyectil(proyectil);
            }

        } else {

            for (int i = 0; i < cantEnemigos; i++){
                Alien alien = nivel.getAlien(i);

                if (checarColision(proyectil, alien)){
                    puntuacion.sumarPuntuacion(alien.getValuePoints());
                    nivel.eliminarAlien(alien);
                    nivel.eliminarProyectil(proyectil);
                }
            }

            if (nivel.getAlienBonus() != null){
                if (checarColision(proyectil, nivel.getAlienBonus())){
                    puntuacion.sumarPuntuacion(nivel.getAlienBonus().getValuePoints());
                    nivel.eliminarAlienBonus();
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



    public void crearJugador(){
        nivel.agregarJugador(new Player());
    }

    public void crearEscudos(){
        ArrayList<Escudo> escudos = directorEscudo.construirEscudos();

        for (Escudo escudo : escudos){
            nivel.agregarEscudo(escudo);
        }

    }


    public void crearHorda(int initialSpeed){

        vecesAceleracionHorda = 0;
        ArrayList<Alien> horda = directorAlien.crearHorda(initialSpeed);

        for (Alien alien : horda){
            alien.setDireccion(enumDirecciones.DERECHA);
            nivel.agregarAlien(alien);
        }

        crearAlienBonus();
    }

    public void crearAlienBonus(){

        int numeroRandom = randomGenerator(0,20);
        int index;

        if (numeroRandom%2 == 0){index = 0;}
        else {index = 1;}


        int posicionInicialY = 20;
        enumDirecciones[] direccionBonus = {enumDirecciones.IZQUIERDA, enumDirecciones.DERECHA};

        pausaCreacionAlienBonus.setOnFinished(event -> {

            AlienBonus alienBonus = directorAlien.crearAlienBonus(5);
            alienBonus.setDireccion(direccionBonus[index]);

            if (direccionBonus[index] == enumDirecciones.DERECHA){alienBonus.setPosicionX(10);}
            else {alienBonus.setPosicionX(750);}
            alienBonus.setPosicionY(posicionInicialY);

            nivel.agregarAlienBonus(alienBonus);
        });


        pausaCreacionAlienBonus.play();
    }

    public void verificarCantidadEnemigos(){

        if (nivel.getCantidadAliens() < 1 && !gameOver){
            horda++;

            int velocidadInicial = (horda +1);
            if (velocidadInicial > 12){velocidadInicial = 12;}

            crearHorda(velocidadInicial);
        }


    }

    public void moverHorda(){

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

            if (alien.getPosition()[1] <= pn_ventanaNivel.getHeight() - 50){

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


        }

        moverAliensX();
        moverAlienBonus();
    }

    public void moverAlienBonus(){
        Alien alien = nivel.getAlienBonus();

        if (alien != null){

            if (alien.getPosition()[0] >= 750 && alien.getDireccion() == enumDirecciones.DERECHA){nivel.eliminarAlienBonus();}
            else if (alien.getPosition()[0] <= 10 && alien.getDireccion() == enumDirecciones.IZQUIERDA){nivel.eliminarAlienBonus();}
            else {nivel.getAlienBonus().moveX();}

        }
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

        if (horda < 10){
            for (int i = 0; i < nivel.getCantidadAliens(); i++){
                nivel.getAlien(i).moveY();
            }
        } else {
            for (int i = 0; i < nivel.getCantidadAliens(); i++){
                nivel.getAlien(i).moveY(13);
            }
        }

        for (int i = 0; i< nivel.getCantidadAliens(); i++){
            if (nivel.getAlien(i).getPosition()[1] > pn_ventanaNivel.getHeight()-30){
                nivel.getJugador().setNivelVida(0);
            }
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

            if (checarColision(nivel.getAlien(i), nivel.getJugador())){
                nivel.getJugador().setNivelVida(0);
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

    public int getHorda(){
        return horda;
    }

    public boolean getPausa(){
        return pausa;
    }

    public void setPausa(boolean pausa){
        this.pausa = pausa;

        if (pausa){
            pausaCreacionAlienBonus.pause();
        } else {
            pausaCreacionAlienBonus.play();
        }
    }

    public void checkarGameOver(){
        if (nivel.getJugador().getNivelVida() < 1){
            gameOver = true;

            nivel.eliminarAlienBonus();

            int cantidadAliens = nivel.getCantidadAliens();

            while (cantidadAliens > 0){
                nivel.eliminarAlien(nivel.getAlien(0));
                cantidadAliens = nivel.getCantidadAliens();
            }

            int cantidadEscudos = nivel.getCantidadEscudos();

            while (cantidadEscudos > 0){
                nivel.eliminarEscudo(nivel.getEscudo(0));
                cantidadEscudos = nivel.getCantidadEscudos();
            }

            int cantidadProyectiles = nivel.getCantidadProyectiles();

            while (cantidadProyectiles > 0){
                nivel.eliminarProyectil(nivel.getProyectil(0));
                cantidadProyectiles = nivel.getCantidadProyectiles();
            }

            animacionGameOver();
        }
    }

    private void ocultarJugador(){
        nivel.getJugador().setSprite(null);
    }

    private void mostrarJugador(){
        nivel.getJugador().setSprite(spriteJugador);
    }

    private void animacionGameOver(){
        animacionGameOver.start();
    }

    public boolean getGameOver(){
        return gameOver;
    }
}
