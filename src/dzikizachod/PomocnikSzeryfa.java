package dzikizachod;

public class PomocnikSzeryfa extends Gracz {
    public PomocnikSzeryfa(Strategia strategia) {
        super(strategia);
        ustawPoczatkoweZdrowie(r.nextInt(2) + 3);
    }

    public PomocnikSzeryfa() {
        this(new StrategiaPomocnikaSzeryfaDomyslna());
    }

    @Override
    public String toString() {
        return "Pomocnik Szeryfa";
    }
}