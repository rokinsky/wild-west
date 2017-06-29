package dzikizachod;

import java.util.List;

public abstract class StrategiaBandyty extends Strategia {
    @Override
    public boolean dynamit(Gracz gracz, List<Gracz> gracze, List<Integer> zywi) {
        int nrGracza = gracze.indexOf(gracz);
        int iluGraczy = gracze.size();
        for (int i = (nrGracza + 1) % iluGraczy, iluZywych = 0; iluZywych < 3; i = (i + 1) % iluGraczy) {
            Gracz gracz1 = gracze.get(i);
            if (gracz1.czyZyje()) {
                iluZywych++;
                if (gracz1.toString().equals("Szeryf")) {
                    gracz1.dajDynamit();
                    System.out.println("      DYNAMIT");
                    return true;
                }
            }
        }

        return false;
    }
}
