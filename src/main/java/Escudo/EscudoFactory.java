package Escudo;

public class EscudoFactory {

    public EscudoFactory(){};

    public Escudo crearEscudo(int posicionX, int posicionY) {
        return new Escudo(posicionX, posicionY);
    }
}
