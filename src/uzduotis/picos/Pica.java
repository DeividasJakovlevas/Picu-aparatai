package uzduotis.picos;

public class Pica {
    public String pavadinimas;
    public PicosDydis dydis;

    public Pica(String pavadinimas, PicosDydis dydis) {
        this.pavadinimas = pavadinimas;
        this.dydis = dydis;
    }
    @Override
    public String toString(){
        return dydis.pavadinimas+" "+pavadinimas;
    }
}
