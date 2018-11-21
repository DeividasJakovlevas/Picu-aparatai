package uzduotis.picos;

import java.util.HashMap;
import java.util.List;

public class Receptas {
    
    public String PicosPavadinimas;
    public HashMap<PicosDydis,List<Produktas>> produktai = new HashMap();
    
    public Receptas(String pavadinimas){
        PicosPavadinimas = pavadinimas;
    }
    
    public void pridekProdukta(PicosDydis dydis, Produktas produktas){
        List sarasas = produktai.get(dydis);
        sarasas.add(produktas);
        produktai.put(dydis, sarasas);
    }
}