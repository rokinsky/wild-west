package dzikizachod;

import java.util.ArrayList;
import java.util.List;

public class StrategiaBandytySprytna extends StrategiaBandyty {

    private int ostatniaTura;

    public StrategiaBandytySprytna() {
        ostatniaTura = -1;
    }

    @Override
    public boolean strzel(Gracz gracz, List<Gracz> gracze, List<Integer> zywi, PulaAkcji pula, int aktualnaTura) {
        List<Gracz> kandydaci = zasieg(gracz, gracze, zywi);

        for (Gracz gracz1: kandydaci) {
            if (gracz1.toString().equals("Szeryf")) {
                atak(gracz, gracz1, gracze, zywi, pula);

                return true;
            }
        }

        List<Gracz> pomocnicy = new ArrayList<>();
        for (Gracz gracz1: kandydaci) {
            if (gracz1.toString().equals("Bandyta")) {
                if (ostatniaTura < aktualnaTura && gracz1.aktPunktyZdrowia() <= 1 + gracz.pulaAkcji().ile(Akcja.STRZEL)) {
                    if (atak(gracz, gracz1, gracze, zywi, pula)) {
                        ostatniaTura = aktualnaTura;
                    }

                    return true;
                }
            } else {
                pomocnicy.add(gracz1);
            }
        }

        if (!pomocnicy.isEmpty()) {
            Gracz cel = pomocnicy.get(r.nextInt(pomocnicy.size()));
            atak(gracz, cel, gracze, zywi, pula);
            return true;
        }

        return false;
    }
}
