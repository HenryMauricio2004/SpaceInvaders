package GameObject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract public class GameObject implements ObjectPositionInterface {

    private int[] posicion = new int[2]; // {eje X, eje Y} (unicad --> pixeles)
    private int[] dimensiones = new int[2]; // {altura, anchura} (unidad --> pixeles)

    private ImageView spriteViewer = new ImageView();

    //public void mostrar(); PENDIENTE

    public GameObject(int posicionX, int posicionY){
        posicion[0] = posicionX;
        posicion[1] = posicionY;
    }

    public GameObject(){}

    public void setSprite(Image sprite){
        spriteViewer.setImage(sprite);
    }

    public ImageView getSpriteViewer(){
        return spriteViewer;
    }

    public void setSpriteViewer(ImageView spriteViewer){
        this.spriteViewer = spriteViewer;
    }

    /**
     * @return Posicion del objeto en un arreglo de 2 elementos con el siguiente formato: [posicion en X, posicion en Y]
     * */
    public int[] getPosition(){
        return posicion;
    }


    /**
     * @param posicionX
     * @param posicionY
     * */
    public void setPosicion(int posicionX, int posicionY){
        posicion[0] = posicionX;
        posicion[1] = posicionY;
    }


    /**
     * @param posicion arrego de 2 elementos en el siguiente formato: [posición en X, posición en Y]
     * */
    public void setPosicion(int[] posicion){
        this.posicion[0] = posicion[0];
        this.posicion[1] = posicion[1];
    }

    public void setPosicionX(int posicionX){
        posicion[0] = posicionX;
    }

    public void setPosicionY(int posicionY){
        posicion[1] = posicionY;
    }


    /**
     * @return arreglo de 2 elementos en el siguiente formato: [anchura desde el centro del objeto, altura desde el centro del objeto]
     * */
    public int[] getDimensiones(){
        return dimensiones;
    }

    /**
     * @return cantidad de pixeles hasta donde llega el objeto en un arreglo de 2 elementos en el siguiente formato: [limite izquierdo, limite derecho]
     * */
    public int[] getAlcanceDimensionesX(){

        int[] alcanceDimensiones = {posicion[0]-dimensiones[0], posicion[0]+dimensiones[0]};
        return alcanceDimensiones;
    }

    /**
     * @return cantidad de pixeles hasta donde llega el objeto en un arreglo de 2 elementos en el siguiente formato: [limite abajo, limite arriba]
     * */
    public int[] getAlcanceDimensionesY(){
        int[] alcanceDimensiones = {posicion[1]-dimensiones[1], posicion[1]+dimensiones[1]};
        return alcanceDimensiones;
    }

    /**
     * @param altura cantidad de pixeles verticales (desde el centro del objeto) hasta donde se extiende el objeto
     * @param anchura cantidad de pixeles horizontales (desde el centro del objeto) hasta donde se extiende el objeto
     * */
    public void setDimensiones(int altura, int anchura){
        dimensiones[0] = altura;
        dimensiones[1] = anchura;
    }


}
