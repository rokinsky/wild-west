package dzikizachod;

import java.util.ArrayList;
import java.util.List;

public class StrategiaPomocnikaSzeryfaZliczajaca extends StrategiaPomocnikaSzeryfa {
    @Override
    public boolean strzel(Gracz gracz, List<Gracz> gracze, List<Integer> zywi, PulaAkcji pula, int aktualnaTura) {
        List<Gracz> bandyci = new ArrayList<>();

        for (Gracz gracz1: zasieg(gracz, gracze, zywi)) {
            if (czyJestPodejrzany(gracz1)) {
                bandyci.add(gracz1);
            }
        }

        if (!bandyci.isEmpty()) {
            Gracz cel = bandyci.get(r.nextInt(bandyci.size()));
            atak(gracz, cel, gracze, zywi, pula);
            return true;
        }

        return false;
    }
}
