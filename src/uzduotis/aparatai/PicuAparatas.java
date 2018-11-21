package uzduotis.aparatai;

import com.sun.media.jfxmedia.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import uzduotis.picos.Pica;
import uzduotis.picos.Produktas;
import uzduotis.picos.Receptas;
import uzduotis.picos.PicosDydis;

public class PicuAparatas {
    public String numeris;
    private PicuServisas picuServisas;
    private Thread gija;
    public boolean ijungtas = false,
            pranestaApieValyma=false;
    //Kiek picų galima pagaminti iki kito aparato valymo
    public int picuIkiKitoValymo = 2;
    public HashMap<String,Integer> Produktai = new HashMap();
    //Čia sveikasis skaičius nurodo kiek laiko dar keps pica
    private HashMap<Pica,Integer> GaminamosPicos = new HashMap();
    public List<Pica> pagamintosPicos = new ArrayList();
    
    public PicuAparatas(PicuServisas picuServisas, String numeris){
        this.picuServisas = picuServisas;
        this.numeris = numeris;
        sukurkGija();
    }
    private void sukurkGija(){
        gija = 
        new Thread(
            new Runnable(){
                @Override
                public void run() {
                    while(ijungtas){
                        if(picuIkiKitoValymo>0){
                        
                            int i = 3;
                            for (Iterator<Pica> it = GaminamosPicos.keySet().iterator(); it.hasNext();) {
                                Pica pica = it.next();
                                //a = kiek laiko liko praėjus vienai sekundei 
                                int a = GaminamosPicos.get(pica)-1;
                                Logger.logMsg(Logger.INFO, pica.toString()+" dar bus gaminama "+a+" sekundziu.");
                                if(a==0){
                                    //jei picą baigė gaminti
                                    it.remove();
                                    pagamintosPicos.add(pica);
                                    picuIkiKitoValymo-=1;
                                    Logger.logMsg(Logger.INFO, "Iki kito valymo dar galima pagaminti "+picuIkiKitoValymo+" picu.");
                                }else{
                                    GaminamosPicos.put(pica, a);
                                }
                                i--;
                                if(i==0){
                                    break;
                                }
                            }
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {}
                    }
                }
            }
        );
    }
    protected void ijunk(){
        ijungtas=true;
        gija.start();
        Logger.setLevel(Logger.INFO);
    }
    
    public void valyk(){
        picuIkiKitoValymo=2;
        pranestaApieValyma=false;
    }
    protected void isjunk(){
        ijungtas=false;
    }
    public Pica UzsakykPica(Receptas receptas, PicosDydis dydis){
       Pica pica = new Pica(receptas.PicosPavadinimas,dydis);
        //Pirmą kartą patikrinti ar yra užtektinai produktų
        for(Produktas produktas:receptas.produktai.get(dydis)){
            if(Produktai.containsKey(produktas.pavadinimas)){
                int LikesKiekis = Produktai.get(produktas.pavadinimas)-produktas.kiekis;
                //Jei reikia daugiau produktų nei yra, tai reikia pasiūlyti kitą picą
                if(LikesKiekis<0){ 
                    return null;
                }
            }else{
                return null;
            }
        }
        //Antrą kartą jei užtenka, numinusuoti produktus ir pradėti gaminti picą
        for(Produktas produktas:receptas.produktai.get(dydis)){
                 Produktai.put(produktas.pavadinimas,Produktai.get(produktas.pavadinimas)-produktas.kiekis);
        }
        
        GaminamosPicos.put(pica,5);
        rodykLikusiusProduktus();
        return pica;
    }
    public Pica pasiulykKitaPica(){
        boolean uztenka = true; 
        //patikrinti visu dydziu visus picu receptus iki pirmo kuriam uztenka produktu
        for(Receptas receptas:picuServisas.Receptai){
            for(PicosDydis dydis:PicosDydis.values()){
                for(Produktas produktas:receptas.produktai.get(dydis)){
                    if(Produktai.containsKey(produktas.pavadinimas)){
                        int LikesKiekis = Produktai.get(produktas.pavadinimas)-produktas.kiekis;
                        if(LikesKiekis<0){ 
                            uztenka = false;
                            break;
                        }
                    }else{
                        uztenka = false;
                    }
                }
                if(uztenka){
                    //ui.pasiulytiPica(dydis.pavadinimas+" "+receptas.PicosPavadinimas);
                    return new Pica(receptas.PicosPavadinimas,dydis);
                }
            }
        }
        return null;
        //ui.trukstaProduktu();
    }
        
    private void rodykLikusiusProduktus(){
        Logger.logMsg(Logger.INFO, numeris+" aparate liko produktu: ");
        for(String key:Produktai.keySet()){
            Logger.logMsg(Logger.INFO, key+" "+Produktai.get(key));
        }
    }
}