package dzikizachod;

import java.util.List;

public class StrategiaPomocnikaSzeryfaDomyslna extends StrategiaPomocnikaSzeryfa {
    @Override
    public boolean strzel(Gracz gracz, List<Gracz> gracze, List<Integer> zywi, PulaAkcji pula, int aktualnaTura) {
        List<Gracz> kandydaciDoOdstrzalu = zasieg(gracz, gracze, zywi);

        for (Gracz gracz1: kandydaciDoOdstrzalu) {
            if (gracz1.toString().equals("Szeryf")) {
                kandydaciDoOdstrzalu.remove(gracz1);
                break;
            }
        }

        if (!kandydaciDoOdstrzalu.isEmpty()) {
            Gracz cel = kandydaciDoOdstrzalu.get(r.nextInt(kandydaciDoOdstrzalu.size()));
            atak(gracz, cel, gracze, zywi, pula);
            return true;
        }

        return false;
    }
}
