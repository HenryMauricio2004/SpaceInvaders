package com.example.spaceinvaders;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

// DIRECTORES

// OBJETOS
import Escena.*;
import Nave.enumDirecciones;


import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;
import Sprites.SpriteInterfaces.PlayerSpriteInterface;


public class HelloController implements Initializable, PlayerSpriteInterface{

    private final int max_coolDownDisparo = 20;
    private int coolDownDisparo = max_coolDownDisparo;
    private boolean armaCargada = true;


    @FXML private Pane pn_ventanaNivel;


    @FXML private Label lb_Puntuacion;
    @FXML private Label lb_Horda;
    @FXML private ImageView imgV_vida1;
    @FXML private ImageView imgV_vida2;
    @FXML private ImageView imgV_vida3;


    private Escena escena = Escena.getInstance();
    private Mediator mediator = Mediator.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        escena.setPane(pn_ventanaNivel); //Le da a Escena acceso a Pane para agregar o eliminar los elementos a renderizar
        mediator.setPane(pn_ventanaNivel);

        mediator.crearJugador();

        mediator.crearEscudos();
        mediator.verificarCantidadEnemigos();

        pn_ventanaNivel.setFocusTraversable(true);


        bucleDeVentana.start();

    }

    @FXML private void movePlayer(KeyEvent key){

        switch (key.getCode()){
            case A:
            case LEFT:
                escena.getJugador().move(enumDirecciones.IZQUIERDA);
                break;

            case D:
            case RIGHT:
                escena.getJugador().move(enumDirecciones.DERECHA);
                break;

            case SPACE:
            case UP:
                escena.getJugador().disparar();
                armaCargada = false;
                break;

            case ENTER:
                mediator.setPausa(!mediator.getPausa());
                break;

            default:
                break;
        }

    }


    private void actualizarPantalla(){

        lb_Puntuacion.setText( mediator.getPuntuacion() + "" );
        lb_Horda.setText(mediator.getHorda() + "");

        int cantidadAliens = escena.getCantidadAliens();
        int cantidadProyectiles = escena.getCantidadProyectiles();

        if (escena.getAlienBonus() != null){
            escena.getAlienBonus().getSpriteViewer().relocate(escena.getAlienBonus().getPosition()[0]*1d, escena.getAlienBonus().getPosition()[1]*1d);
        }

        for (int i = 0; i < cantidadAliens; i++){
            escena.getAlien(i).getSpriteViewer().relocate(escena.getAlien(i).getPosition()[0]*1d, escena.getAlien(i).getPosition()[1]*1d);
        }

        for (int i = 0; i < cantidadProyectiles; i++){
            escena.getProyectil(i).getSpriteViewer().relocate(escena.getProyectil(i).getPosition()[0]*1d, escena.getProyectil(i).getPosition()[1]*1d);
            escena.getProyectil(i).moverse();
        }


        if (!mediator.getGameOver()){
            escena.getJugador().getSpriteViewer().relocate(escena.getJugador().getPosition()[0]* 1d, escena.getJugador().getPosition()[1]*1d);

        }

        int vida = escena.getJugador().getNivelVida();

        if (vida < 1){imgV_vida3.setImage(null);}
        else {imgV_vida3.setImage(spriteJugador);}

        if (vida < 2){imgV_vida2.setImage(null);}
        else {imgV_vida2.setImage(spriteJugador);}

        if (vida < 3){imgV_vida1.setImage(null);}
        else {imgV_vida1.setImage(spriteJugador);}

    }


    //Bucle para ejecutar el juego
    private AnimationTimer bucleDeVentana = new AnimationTimer() {

        long startTime = 0;

        int repeticionesParaMoverAliens = 0;
        int repeticionesParaDisparar = 0;

        @Override
        public void handle(long now) {


            long currentTime = System.nanoTime();
            if (300 <= (currentTime - startTime)){

                if (!mediator.getPausa()){
                    //Frames antes de mover aliens
                    if (repeticionesParaMoverAliens >= 3){
                        repeticionesParaMoverAliens = 0;
                        mediator.moverHorda();
                    }
                    repeticionesParaMoverAliens++;


                    //Frames antes de iniciar el disparo de un alien
                    if (repeticionesParaDisparar >= 400){
                        repeticionesParaDisparar = 0;
                        mediator.setAliensDisparos();
                    }
                    repeticionesParaDisparar++;


                    //Frames antes de habilitar el disparo al jugador otra vez
                    if (coolDownDisparo <= 0){
                        coolDownDisparo = max_coolDownDisparo;
                        armaCargada = true;
                    }
                    coolDownDisparo--;


                    mediator.verificarCantidadEnemigos();
                    mediator.verificarColisionAlien();
                }

                mediator.checkarGameOver();
                actualizarPantalla();

                startTime = currentTime;

                if (mediator.getGameOver()){
                    bucleDeVentana.stop();
                }
            }
        }

    };

}