package Puntaje;

public class Puntuacion {

    private int puntaje = 0;
    private String nickname;

    public Puntuacion(){
    }

    public void sumarPuntuacion(int puntos){
        puntaje += puntos;
    }

    public void resetearPuntuacion(){
        puntaje = 0;
    }

    public void setNickname(String nuevoNickname){
        nickname = nuevoNickname;
    }

    public String getNickname(){
        return nickname;
    }

    public int getPuntaje(){
        return puntaje;
    }
}
