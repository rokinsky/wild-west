package dzikizachod;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PulaAkcji {
    private List<Akcja> akcje;

    public PulaAkcji() {
        akcje = new LinkedList<>();
    }

    public void dodaj(Akcja akcja, int ile) {
        for (int i = 0; i < ile; i++)
            akcje.add(akcja);
    }

    public void dodaj(Akcja akcja) {
        dodaj(akcja, 1);
    }

    public Akcja wezAkcje() {
        Akcja akcja = akcje.get(0);
        akcje.remove(0);
        return akcja;
    }

    public boolean pusta() {
        return akcje.isEmpty();
    }

    public void dodaj(PulaAkcji pula) {
        while (!pula.pusta()) {
            dodaj(pula.wezAkcje());
        }
    }

    public int ileAkcji() {
        return akcje.size();
    }

    public void potasuj() {
        Collections.shuffle(akcje);
    }

    public void posortuj() {
        Collections.sort(akcje);
    }

    public int ile(Akcja akcja) {
        int wynik = 0;
        for (Akcja akcja1: akcje) {
            if (akcja == akcja1) {
                wynik++;
            }
        }
        return wynik;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < akcje.size() - 1; i++) {
            res += akcje.get(i) + ", ";
        }
        return res + (akcje.isEmpty() ? "" : akcje.get(akcje.size() - 1));
    }
}