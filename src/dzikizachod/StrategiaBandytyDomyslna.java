package dzikizachod;

import java.util.ArrayList;
import java.util.List;

public class StrategiaBandytyDomyslna extends StrategiaBandyty {
    @Override
    public boolean strzel(Gracz gracz, List<Gracz> gracze, List<Integer> zywi, PulaAkcji pula, int aktualnaTura) {
        List<Gracz> kandydaciDoOtstrzalu = zasieg(gracz, gracze, zywi);
        int nrGracza = gracze.indexOf(gracz);
        int nrSzeryfa = dajNumerSzeryfa(gracze);
        int pozycjaGracza = zywi.indexOf(nrGracza);
        int pozycjaSzeryfa = zywi.indexOf(nrSzeryfa);

        for (Gracz kandydat: kandydaciDoOtstrzalu) {
            if (kandydat.toString().equals("Szeryf")) {
                atak(gracz, kandydat, gracze, zywi, pula);
                return true;
            }
        }

        int dl = pozycjaSzeryfa < pozycjaGracza ? pozycjaGracza - pozycjaSzeryfa : zywi.size() - (pozycjaSzeryfa - pozycjaGracza);
        int dr = pozycjaSzeryfa < pozycjaGracza ? zywi.size() - (pozycjaGracza - pozycjaSzeryfa) : pozycjaSzeryfa - pozycjaGracza;

        int iluKandydadow = kandydaciDoOtstrzalu.size();

        List<Gracz> losoweKandydaciLewo = new ArrayList<>();
        for (int i = iluKandydadow / 2 + 1; i < iluKandydadow; i++) {
            if (!kandydaciDoOtstrzalu.get(i).toString().equals("Bandyta")) {
                losoweKandydaciLewo.add(kandydaciDoOtstrzalu.get(i));
            }
        }

        List<Gracz> losoweKandydaciPrawo = new ArrayList<>();
        for (int i = iluKandydadow / 2; i >= 0; i--) {
            if (!kandydaciDoOtstrzalu.get(i).toString().equals("Bandyta")) {
                losoweKandydaciPrawo.add(kandydaciDoOtstrzalu.get(i));
            }
        }

        List<Gracz> losoweKandydaci = null;

        if (dl < dr) {
            if (!losoweKandydaciLewo.isEmpty()) {
                losoweKandydaci = losoweKandydaciLewo;
            }
        } else if (dl > dr) {
            if (!losoweKandydaciPrawo.isEmpty()) {
                losoweKandydaci = losoweKandydaciPrawo;
            }
        }

        if (losoweKandydaci == null) {
            losoweKandydaciLewo.addAll(losoweKandydaciPrawo);
            losoweKandydaci = losoweKandydaciLewo;
        }

        if (!losoweKandydaci.isEmpty()) {
            Gracz cel = losoweKandydaci.get(r.nextInt(losoweKandydaci.size()));

            atak(gracz, cel, gracze, zywi, pula);

            return true;
        }

        return false;
    }
}
