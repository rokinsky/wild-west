package dzikizachod;

import java.util.List;

public class StrategiaBandytyCierpliwa extends StrategiaBandyty {
    @Override
    public boolean strzel(Gracz gracz, List<Gracz> gracze, List<Integer> zywi, PulaAkcji pula, int aktualnaTura) {
        for (Gracz gracz1: zasieg(gracz, gracze, zywi)) {
            if (gracz1.toString().equals("Szeryf")) {
                atak(gracz, gracz1, gracze, zywi, pula);
                return true;
            }
        }
        return false;
    }
}
