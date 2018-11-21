package uzduotis.aparatai;

import com.sun.media.jfxmedia.logging.Logger;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import uzduotis.picos.PicosDydis;
import uzduotis.picos.Produktas;
import uzduotis.picos.Receptas;

public class PicuServisas {
    private int index = 0;
    public List<PicuAparatas> PicuAparatai = new ArrayList();
    public List<Receptas> Receptai = new ArrayList();
    
  
    public PicuServisas(){
        Logger.setLevel(Logger.ERROR);
    }
    public PicuAparatas sukurkPicuAparata(){
        HashMap<String,Integer> produktai = new HashMap();
        produktai.put("Pomidorai", 20);
        produktai.put("Padai", 10);
        produktai.put("Padazas", 20);
        produktai.put("Desra", 30);
        produktai.put("Suris", 25);
        PicuAparatas aparatas = new PicuAparatas(this,String.valueOf(index++));
        aparatas.ijunk();
        aparatas.Produktai = produktai;
        PicuAparatai.add(aparatas);
        return aparatas;
    }
    
    public void pasalinkPicuAparata(PicuAparatas aparatas){
        PicuAparatai.remove(aparatas);
        aparatas.isjunk();
    }
    private void pridekRecepta(Receptas receptas){
        Receptai.add(receptas);
        //ui.pridetiPica(receptas);
    }
    public void uzkraukReceptus(){
        File f = new File("receptai.txt");
        try {
            FileInputStream fi = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fi);
            String linija;
            Receptas receptas = new Receptas(dis.readLine());
            while((linija=dis.readLine())!=null){
                if(linija.equals("Maza")){
                    List<Produktas> produktaiMazaiPicai = new ArrayList();
                    while(!(linija=dis.readLine()).equals("Vidutine")){
                        String[] produktai = linija.split(",");
                        produktaiMazaiPicai.add(new Produktas(produktai[0],Integer.valueOf(produktai[1])));
                    }
                    receptas.produktai.put(PicosDydis.MAZA, produktaiMazaiPicai);

                    List<Produktas> produktaiVidutineiPicai = new ArrayList();
                    while(!(linija=dis.readLine()).equals("Didele")){
                        String[] produktai = linija.split(",");
                        produktaiVidutineiPicai.add(new Produktas(produktai[0],Integer.valueOf(produktai[1])));
                    }
                    receptas.produktai.put(PicosDydis.VIDUTINE, produktaiVidutineiPicai);
                    
                    List<Produktas> produktaiDideleiPicai = new ArrayList();
                    while(!(linija=dis.readLine()).equals("#")){
                        String[] produktai = linija.split(",");
                        produktaiDideleiPicai.add(new Produktas(produktai[0],Integer.valueOf(produktai[1])));
                    }
                    receptas.produktai.put(PicosDydis.DIDELE, produktaiDideleiPicai);
                    pridekRecepta(receptas);
                    receptas = new Receptas(dis.readLine());
                }
            }
        } catch (IOException ex) {
            Logger.logMsg(Logger.ERROR, ex.toString());
        }
    }
    public void uzkraukPicuAparatus(){
        File f = new File("aparatai.txt");
        try {
            FileInputStream fi = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fi);
            PicuAparatas aparatas = null;
            String linija;
            while((linija=dis.readLine())!=null){
                if(linija.equals("#")){
                    if(aparatas!=null){
                        aparatas.ijunk();
                        PicuAparatai.add(aparatas);
                    }
                    aparatas = new PicuAparatas(this,String.valueOf(index++));
                }else{
                    String[] produktai = linija.split(",");
                    aparatas.Produktai.put(produktai[0],Integer.valueOf(produktai[1]));
                }           
            }
            aparatas.ijunk();
            PicuAparatai.add(aparatas);
        } catch (IOException ex) {
             Logger.logMsg(Logger.ERROR, ex.toString());
        }
    }
}