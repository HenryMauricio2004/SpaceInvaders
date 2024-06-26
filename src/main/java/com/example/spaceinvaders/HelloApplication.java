package com.example.spaceinvaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Sprites.SpriteInterfaces.AlienSpritesInterface;

import java.io.IOException;

public class HelloApplication extends Application implements AlienSpritesInterface{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 755);
        stage.setTitle("Space Invaders");
        stage.getIcons().add(sprite_AlienMediano);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}