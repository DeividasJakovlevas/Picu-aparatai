package uzduotis.picos;

public enum PicosDydis {
    
    MAZA("Maža"),
    VIDUTINE("Vidutinė"),
    DIDELE("Didelė");
    
    public String pavadinimas;
    
    private PicosDydis(String pavadinimas){
        this.pavadinimas = pavadinimas;
    }
    //pagal pavadinimą grąžinti enum'ą
    public static PicosDydis konvertuok(String pavadinimas){
        for(PicosDydis pd:values()){
            if(pd.pavadinimas.equals(pavadinimas)){
                return pd;
            }
        }
        return PicosDydis.MAZA;
    }
}
