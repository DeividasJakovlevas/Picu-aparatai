package uzduotis;

import uzduotis.aparatai.PicuServisas;

public class Main {
    private PicuServisas picuServisas;
    private UI ui;
    public Main(){
        ui = new UI();
        picuServisas = new PicuServisas();
        ui.sukurkUI(picuServisas);
        picuServisas.uzkraukReceptus();
        picuServisas.uzkraukPicuAparatus();
        ui.atnaujinkAparatuSarasa();
        ui.atnaujinkPicuSarasa();
        ui.sukurkGija();
    }
    public static void main(String[] args) {
       Main main = new Main();
    } 
}
