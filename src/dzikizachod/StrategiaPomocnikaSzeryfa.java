package dzikizachod;

import java.util.List;

public abstract class StrategiaPomocnikaSzeryfa extends Strategia {
    @Override
    public boolean dynamit(Gracz gracz, List<Gracz> gracze, List<Integer> zywi) {
        int ile = gracze.size();
        int iluPodejrzanych = 0;
        int iluPomiedzy = 0;
        Gracz gracz1 = null;
        for (int i = (gracze.indexOf(gracz) + 1) % ile; !gracze.get(i).toString().equals("Szeryf"); i = (i + 1) % ile) {
            if (gracze.get(i).czyZyje()) {
                iluPomiedzy++;
                if (czyJestPodejrzany(gracze.get(i))) {
                    iluPodejrzanych++;
                }
                if (gracz1 == null) {
                    gracz1 = gracze.get(i);
                }
            }
        }

        if (iluPomiedzy > 3 && iluPodejrzanych * 3 >= iluPomiedzy * 2) {
            gracz1.dajDynamit();
            System.out.println("      DYNAMIT");
            return true;
        }

        return false;
    }
}
