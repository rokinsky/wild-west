package dzikizachod;

public class Bandyta extends Gracz {
    public Bandyta(Strategia strategia) {
        super(strategia);
        ustawPoczatkoweZdrowie(r.nextInt(2) + 3);
    }

    public Bandyta() {
        this(new StrategiaBandytyDomyslna());
    }

    @Override
    public String toString() {
        return "Bandyta";
    }
}
