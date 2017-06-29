package dzikizachod;

public class Szeryf extends Gracz {
    public Szeryf(Strategia strategia) {
        super(strategia);
        ustawPoczatkoweZdrowie(5);
    }

    public Szeryf() {
        this(new StrategiaSzeryfaDomyslna());
    }

    @Override
    public String toString() {
        return "Szeryf";
    }
}
