package dzikizachod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Strategia {
    protected static Random r = new Random();

    protected boolean atak(Gracz strzelajacy, Gracz atakowany, List<Gracz> gracze , List<Integer> zywi, PulaAkcji pula) {
        System.out.println("      STRZEL " + (gracze.indexOf(atakowany) + 1));
        atakowany.zmniejszZdrowie(1);

        if (!atakowany.czyZyje()) {
            switch (atakowany.toString()) {
                case "Bandyta":
                    strzelajacy.zwiekszLiczbeZabitychBandytow();
                    break;
                case "Pomocnik Szeryfa":
                    strzelajacy.zwiekszLiczbeZabitychPomocnikow();
                    break;
                case "Szeryf":
                    strzelajacy.atakNaSzeryfa();
                    break;
            }

            pula.dodaj(atakowany.pulaAkcji());
            zywi.remove(new Integer(gracze.indexOf(atakowany)));
            return true;
        } else {
            if (atakowany.toString().equals("Szeryf")) {
                strzelajacy.atakNaSzeryfa();
            }
            return false;
        }
    }

    protected boolean czyJestPodejrzany(Gracz gracz) {
        return gracz.czyZaatakowalSzeryfa() ||
                !gracz.toString().equals("Szeryf") && gracz.iluZabilBandytow() < gracz.iluZabilPomocnikow();
    }

    protected final List<Gracz> zasieg(Gracz gracz, List<Gracz> gracze, List<Integer> zywi) {
        List<Gracz> wynik = new ArrayList<>();
        int nrGracza = gracze.indexOf(gracz);

        if (2 * gracz.zasieg() >= zywi.size() - 1) {
            for (Integer x: zywi) {
                if (x != nrGracza) {
                    wynik.add(gracze.get(x));
                }
            }
        } else {
            int idx = zywi.indexOf(nrGracza);

            for (int i = 1; i <= gracz.zasieg(); i++) {
                wynik.add(gracze.get(zywi.get((idx + zywi.size() - i) % zywi.size())));
            }
            for (int i = 1; i <= gracz.zasieg(); i++) {
                wynik.add(gracze.get(zywi.get((idx + i) % zywi.size())));
            }
        }

        return wynik;
    }

    protected final int dajNumerSzeryfa(List<Gracz> gracze) {
        int i = 1;

        for (Gracz gracz: gracze) {
            if (gracz.toString().equals("Szeryf")) {
                break;
            }
            i++;
        }

        return i;
    }

    public boolean ulecz(Gracz gracz, List<Gracz> gracze, List<Integer> zywi) {
        if (gracz.toString().equals("Pomocnik Szeryfa")) {
            int nrGracza = gracze.indexOf(gracz);
            int pozycjaGracza = zywi.indexOf(nrGracza);
            int iluZywych = zywi.size();
            int prawy = (pozycjaGracza + 1) % iluZywych;
            int lewy = (pozycjaGracza + iluZywych - 1) % iluZywych;
            Gracz szeryf = gracze.get(zywi.get(lewy));
            if (szeryf.toString().equals("Szeryf") && szeryf.ranny()) {
                System.out.println("      ULECZ " + (zywi.get(lewy) + 1));
                szeryf.uleczZdrowie(1);
                return true;
            }
            if (szeryf.toString().equals("Szeryf") && szeryf.ranny()) {
                System.out.println("      ULECZ " + (zywi.get(prawy) + 1));
                szeryf.uleczZdrowie(1);
                return true;
            }
        }

        if (gracz.ranny()) {
            gracz.uleczZdrowie(1);
            System.out.println("      ULECZ");
            return true;
        }

        return false;
    }

    public boolean zwiekszZasieg(Gracz gracz, int ile) {
        gracz.zwiekszZasieg(ile);
        System.out.println("      ZASIEG_PLUS_" + (ile == 1 ? "JEDEN" : "DWA"));

        return true;
    }

    public boolean strzel(Gracz gracz, List<Gracz> gracze, List<Integer> zywi, PulaAkcji pula, int aktualnaTura) {
        return false;
    }

    public boolean dynamit(Gracz gracz, List<Gracz> gracze, List<Integer> zywi) {
        return false;
    }
}
