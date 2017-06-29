package dzikizachod;

import java.util.List;
import java.util.Random;

public abstract class Gracz {
    private int maxPunktyZdrowia;
    private int aktPunktyZdrowia;
    private int zasieg;
    private Strategia strategia;
    private PulaAkcji akcje;
    private boolean maDynamit;
    protected static Random r = new Random();
    private boolean czyZaatakowalSzeryfa;
    private int iluZabilPomocnikow;
    private int iluZabilBandytow;

    public Gracz(Strategia strategia) {
        this.zasieg = 1;
        this.strategia = strategia;
        this.akcje = new PulaAkcji();
        this.maDynamit = false;
        this.czyZaatakowalSzeryfa = false;
        this.iluZabilBandytow = this.iluZabilPomocnikow = 0;
    }

    protected void ustawPoczatkoweZdrowie(int zdrowie) {
        this.aktPunktyZdrowia = this.maxPunktyZdrowia = zdrowie;
    }

    public void zwiekszLiczbeZabitychPomocnikow() {
        iluZabilPomocnikow++;
    }

    public void zwiekszLiczbeZabitychBandytow() {
        iluZabilBandytow++;
    }

    public void atakNaSzeryfa() {
        czyZaatakowalSzeryfa = true;
    }

    public boolean czyZaatakowalSzeryfa() {
        return czyZaatakowalSzeryfa;
    }

    public int iluZabilPomocnikow() {
        return iluZabilPomocnikow;
    }

    public int iluZabilBandytow() {
        return iluZabilBandytow;
    }

    public int aktPunktyZdrowia() {
        return this.aktPunktyZdrowia;
    }

    public void zmniejszZdrowie(int ile) {
        this.aktPunktyZdrowia = Math.max(this.aktPunktyZdrowia - ile, 0);
    }

    public void uleczZdrowie(int ile) {
        this.aktPunktyZdrowia = Math.min(this.aktPunktyZdrowia + ile, maxPunktyZdrowia);
    }

    public boolean czyZyje() {
        return this.aktPunktyZdrowia > 0;
    }

    public boolean ranny() {
        return czyZyje() && maxPunktyZdrowia - aktPunktyZdrowia > 0;
    }

    public int zasieg() {
        return this.zasieg;
    }

    public void zwiekszZasieg(int ile) {
        this.zasieg += ile;
    }

    public void wezAkcje(Akcja akcja) {
        this.akcje.dodaj(akcja);
    }

    public int ileAkcji() {
        return this.akcje.ileAkcji();
    }

    public PulaAkcji pulaAkcji() {
        return this.akcje;
    }

    public void posortujAkcje() {
        pulaAkcji().posortuj();
    }

    public boolean maDynamit() {
        return maDynamit;
    }

    public void dajDynamit() {
        this.maDynamit = true;
    }

    public  void zabierzDynamit() {
        this.maDynamit = false;
    }

    public PulaAkcji wykonajAkcje(List<Gracz> gracze, List<Integer> zywi, int aktualnaTura) {
         PulaAkcji pula = new PulaAkcji();
         PulaAkcji nieuzyte = new PulaAkcji();

         posortujAkcje();

         while (czyZyje() && !pulaAkcji().pusta() && Gra.czyZyje("Bandyta", gracze) && Gra.czyZyje("Szeryf", gracze)) {
            Akcja akcja = pulaAkcji().wezAkcje();
            boolean czyWykonalAkcje = false;
            switch (akcja) {
                case ULECZ:
                    czyWykonalAkcje = strategia.ulecz(this, gracze, zywi);
                    break;
                case ZASIEG_PLUS_JEDEN:
                    czyWykonalAkcje = strategia.zwiekszZasieg(this, 1);
                    break;
                case ZASIEG_PLUS_DWA:
                    czyWykonalAkcje = strategia.zwiekszZasieg(this, 2);
                    break;
                case STRZEL:
                    czyWykonalAkcje = strategia.strzel(this, gracze, zywi, pula, aktualnaTura);
                    break;
                case DYNAMIT:
                    czyWykonalAkcje = strategia.dynamit(this, gracze, zywi);
            }
            if (czyWykonalAkcje) {
                pula.dodaj(akcja);
            } else {
                nieuzyte.dodaj(akcja);
            }
        }

        akcje.dodaj(nieuzyte);

        return pula;
    }
}
