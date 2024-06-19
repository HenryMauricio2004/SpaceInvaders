package Escudo;

import java.util.ArrayList;

public class EscudoDirector {

    private static EscudoDirector escudoDirector;
    private EscudoFactory escudoFactory = new EscudoFactory();

    public static EscudoDirector getInstance() {
        if (escudoDirector == null) escudoDirector = new EscudoDirector();
        return escudoDirector;
    }

    private EscudoDirector(){}

    public ArrayList<Escudo> construirEscudos() {

        ArrayList<Escudo> escudos = new ArrayList<Escudo>();

        int[] posicionX = {100, 270, 460, 650};

        for (int i = 0; i < 4; i++) {
            escudos.add(escudoFactory.crearEscudo(posicionX[i], 550));
        }

        return escudos;
    }
}
