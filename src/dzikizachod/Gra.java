package dzikizachod;

import java.util.*;

public class Gra {
    private static final int maxTury = 42;
    private static final Random r = new Random();

    public static boolean czyZyje(String kto, List<Gracz> gracze) {
        boolean wynik = false;
        for (Gracz gracz: gracze) {
            if (gracz.toString().equals(kto)) {
                if (gracz.czyZyje()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void wypiszGraczy(List<Gracz> gracze) {
        int j = 0;
        System.out.println("  Gracze:");
        for (Gracz gracz: gracze) {
            j++;
            if (gracz.czyZyje()) {
                System.out.println("    " + j + ": " + gracz + " (liczba żyć: " + gracz.aktPunktyZdrowia() + ")");
            } else {
                System.out.println("    " + j + ": X (" + gracz + ")");}
        }
        System.out.println();
    }

    public void rozgrywka(List<Gracz> gracze, PulaAkcji akcje) {
        Collections.shuffle(gracze);
        List<Integer> zywi = new ArrayList<>();
        for (int i = 0; i < gracze.size(); i++) {
            zywi.add(i);
        }
        akcje.potasuj();
        PulaAkcji zuzyte = new PulaAkcji();
        int szeryf;
        for (szeryf = 0; szeryf < gracze.size() && !gracze.get(szeryf).toString().equals("Szeryf"); szeryf++);

        boolean koniec = false;
        String wynik = "REMIS - OSIĄGNIĘTO LIMIT TUR";

        System.out.println("** START");
        wypiszGraczy(gracze);

        for (int i = 0; i < maxTury && !koniec; i++) {
            System.out.println("** TURA " + (i+1));

            for (int j = 0; j < gracze.size() && !koniec; j++) {
                int nr = (j + szeryf) % gracze.size() + 1;
                Gracz gracz = gracze.get(nr - 1);

                System.out.println("  " + "GRACZ " + nr + " (" + gracz + "):");
                if (gracz.czyZyje()) {
                    while (gracz.ileAkcji() < 5) {
                        if (akcje.pusta()) {
                            while (!zuzyte.pusta()) {
                                Akcja akcja = zuzyte.wezAkcje();
                                if (akcja != Akcja.DYNAMIT) {
                                    akcje.dodaj(akcja);
                                }
                            }
                            akcje.potasuj();
                        }
                        gracz.wezAkcje(akcje.wezAkcje());
                    }
                }
                if (gracz.czyZyje()) {
                    System.out.println("    Akcje: [" + gracz.pulaAkcji() + "]");
                }
                if (gracz.maDynamit()) {
                    if (gracz.czyZyje() && r.nextInt(6) + 1 == 1) {
                        System.out.println("    Dynamit: WYBUCHŁ");
                        gracz.zmniejszZdrowie(3);
                        if (!gracz.czyZyje()) {
                            zywi.remove(new Integer(nr - 1));
                            zuzyte.dodaj(gracz.pulaAkcji());
                        }
                    } else {
                        if (gracz.czyZyje()) {
                            System.out.println("    Dyanmit: PRZECHODZI DALEJ");
                        }
                        gracz.zabierzDynamit();
                        gracze.get(nr % gracze.size()).dajDynamit();
                    }
                }
                if (gracz.czyZyje()) {
                    System.out.println("    Ruchy:");
                    zuzyte.dodaj(gracz.wykonajAkcje(gracze, zywi, i + 1));
                    System.out.println();
                    wypiszGraczy(gracze);
                } else {
                    System.out.println("    MARTWY");
                    System.out.println();
                }
                boolean czyZyjeSzeryf = czyZyje("Szeryf", gracze);
                boolean czyZyjeBandyta = czyZyje("Bandyta", gracze);
                if (koniec = !czyZyjeBandyta || !czyZyjeSzeryf) {
                    wynik = "  WYGRANA STRONA: " + (czyZyjeBandyta ? "bandyci" : "szeryf i pomocnicy");
                }
            }
        }

        System.out.println("** KONIEC");
        System.out.println(wynik);
    }

    public static void main(String[] args) {
        List<Gracz> gracze = new ArrayList<Gracz>();
        gracze.add(new Szeryf(new StrategiaSzeryfaDomyslna()));
        for(int i=0;i<2;i++) gracze.add(new PomocnikSzeryfa());
        for(int i=0;i<2;i++) gracze.add(new Bandyta());
        PulaAkcji pulaAkcji = new PulaAkcji();
        pulaAkcji.dodaj(Akcja.ULECZ, 30);
        pulaAkcji.dodaj(Akcja.STRZEL, 60);
        pulaAkcji.dodaj(Akcja.ZASIEG_PLUS_JEDEN, 1);
        pulaAkcji.dodaj(Akcja.ZASIEG_PLUS_DWA, 1);
        pulaAkcji.dodaj(Akcja.DYNAMIT, 1);

        Gra gra = new Gra();
        gra.rozgrywka(gracze, pulaAkcji);
    }
}