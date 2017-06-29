package dzikizachod;

import java.util.ArrayList;
import java.util.List;

public class StrategiaSzeryfaDomyslna extends StrategiaSzeryfa {
    @Override
    public boolean strzel(Gracz gracz, List<Gracz> gracze, List<Integer> zywi, PulaAkcji pula, int aktualnaTura) {
        List<Gracz> graczeWZasiegu = zasieg(gracz, gracze, zywi);

        List<Gracz> strzelajacy = new ArrayList<>();

        for (Gracz gracz1: graczeWZasiegu) {
            if (gracz1.czyZaatakowalSzeryfa()) {
                strzelajacy.add(gracz1);
            }
        }

        Gracz cel;

        if (!strzelajacy.isEmpty()) {
            cel = strzelajacy.get(r.nextInt(strzelajacy.size()));
        } else {
            cel = graczeWZasiegu.get(r.nextInt(graczeWZasiegu.size()));
        }

        atak(gracz, cel, gracze, zywi, pula);
        return true;
    }
}
