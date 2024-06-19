package com.example.spaceinvaders;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

// DIRECTORES

// OBJETOS
import Escena.*;
import Nave.enumDirecciones;


import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;



public class HelloController implements Initializable {

    private final int max_coolDownDisparo = 40;
    private int coolDownDisparo = max_coolDownDisparo;
    private boolean armaCargada = true;


    @FXML private Pane pn_ventanaNivel;
    @FXML private Label lb_Puntuacion;

    private Escena escena = Escena.getInstance();
    private Mediator mediator = Mediator.getInstance();

    public Pane getPane() {return pn_ventanaNivel;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        escena.setPane(pn_ventanaNivel); //Le da a Escena acceso a Pane para agregar o eliminar los elementos a renderizar

        escena.declararJugador();

        declararEscudos();
        declararEnemigos();

        pn_ventanaNivel.setFocusTraversable(true);
        pn_ventanaNivel.getChildren().add(escena.getJugador().getSpriteViewer());


        animator.start();

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

                if (armaCargada){
                    escena.getJugador().disparar();
                    armaCargada = false;

                }
                break;

            default:
                break;
        }

    }

    private void declararEscudos(){
        mediator.crearEscudos();
        int lenght = escena.getCantidadEscudos();

        for (int i = 0; i < lenght; i++){

            pn_ventanaNivel.getChildren().add(escena.getEscudo(i).getSpriteViewer());
            escena.getEscudo(i).getSpriteViewer().setLayoutX(escena.getEscudo(i).getPosition()[0]);
            escena.getEscudo(i).getSpriteViewer().setLayoutY(escena.getEscudo(i).getPosition()[1]);

            //escena.getEscudo(i).getSpriteViewer().setPickOnBounds(true);

            //escena.getEscudo(i).getSpriteViewer().setFitHeight(75);
            //escena.getEscudo(i).getSpriteViewer().setFitWidth(75);
        }

    }

    private void declararEnemigos(){
        mediator.crearHorda();
        int lenght = escena.getCantidadAliens();

        for (int i = 0; i < lenght; i++){

            pn_ventanaNivel.getChildren().add(escena.getAlien(i).getSpriteViewer());
            escena.getAlien(i).getSpriteViewer().setLayoutX(escena.getAlien(i).getPosition()[0]);
            escena.getAlien(i).getSpriteViewer().setLayoutY(escena.getAlien(i).getPosition()[1]);

        }
    }


    private void actualizarPantalla(){

        lb_Puntuacion.setText( mediator.getPuntuacion() + "" );

        int cantidadProyectiles = escena.getCantidadProyectiles();


        for (int i = 0; i < cantidadProyectiles; i++){
            escena.getProyectil(i).getSpriteViewer().relocate(escena.getProyectil(i).getPosition()[0]*1d, escena.getProyectil(i).getPosition()[1]*1d);

            escena.getProyectil(i).moverse();

            /*
            * Esta parte del codigo causa un conflicto al ser eliminado 2 proyectiles de forma consecutiva
            * no detiene el juego, pero necesita revision
            * */
            if (escena.getProyectil(i).getPosition()[1] < 0){
                escena.eliminarProyectil(escena.getProyectil(i));
            }
        }

        int cantidadAliens = escena.getCantidadAliens();

        for (int i = 0; i < cantidadAliens; i++){
            escena.getAlien(i).getSpriteViewer().relocate(escena.getAlien(i).getPosition()[0]*1d, escena.getAlien(i).getPosition()[1]*1d);
        }



        escena.getJugador().getSpriteViewer().relocate(escena.getJugador().getPosition()[0]* 1d, escena.getJugador().getPosition()[1]*1d);
    }

    AnimationTimer animator = new AnimationTimer() {

        long startTime = 0;

        int repeticionesParaMoverAliens = 0;
        int repeticionesParaDisparar = 0;

        @Override
        public void handle(long now) {


            long currentTime = System.nanoTime();
            if (300 <= (currentTime - startTime)){
                actualizarPantalla();

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


                if (escena.getCantidadAliens() < 1){
                    declararEnemigos();
                }

                mediator.verificarColisionAlien();

                startTime = currentTime;



            }
        }

    };

}