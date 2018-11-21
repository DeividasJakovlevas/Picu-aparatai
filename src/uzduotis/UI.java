package uzduotis;

import uzduotis.picos.Receptas;
import uzduotis.picos.Pica;
import uzduotis.picos.PicosDydis;
import uzduotis.aparatai.PicuServisas;
import uzduotis.aparatai.PicuAparatas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class UI extends JFrame{
    private Thread gija;
    private ButtonGroup buttonGroup;
    private PicuAparatas pasirinktasAparatas;
    private PicuServisas picuServisas;
    private JPanel picuSarasas,aparatuSarasas;
 public UI(){
    setTitle("Užduotis");
    setBounds(500,200,900,500);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(null);
 }
 public void sukurkUI(PicuServisas picuServisas){
    this.picuServisas = picuServisas;
    picuSarasas = new JPanel(); 
    picuSarasas.setLayout(new BoxLayout(picuSarasas,BoxLayout.Y_AXIS));
    picuSarasas.setBackground(Color.white);
    picuSarasas.setBorder(BorderFactory.createLineBorder(Color.black));
     
    aparatuSarasas = new JPanel(); 
    aparatuSarasas.setLayout(new BoxLayout(aparatuSarasas,BoxLayout.Y_AXIS));
    aparatuSarasas.setBackground(Color.white);
    aparatuSarasas.setBorder(BorderFactory.createLineBorder(Color.black));
    
    buttonGroup = new ButtonGroup();
     
    JScrollPane picuSarasas0 = new JScrollPane(picuSarasas);
    picuSarasas0.setBounds(0, 50, 300, 300);
    
    JScrollPane aparatuSarasas0 = new JScrollPane(aparatuSarasas);
    aparatuSarasas0.setBounds(500, 50, 300, 300);
    
    JButton naujas = new JButton("Pridėti naują");
    naujas.setBounds(550, 350, 200, 30);
    naujas.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           pridekAparata(picuServisas.sukurkPicuAparata());
           
        }
    });
     
    JButton istrynti = new JButton("Ištrinti aparatą");
    istrynti.setBounds(550, 380, 200, 30);
    istrynti.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           picuServisas.pasalinkPicuAparata(pasirinktasAparatas);
           atnaujinkAparatuSarasa();
        }
    });
    JButton valyti = new JButton("Valyti aparatą");
    valyti.setBounds(550, 410, 200, 30);
    valyti.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           pasirinktasAparatas.valyk();
           aparatasIsvalytas(pasirinktasAparatas.numeris);
        }
    });
     
    JLabel pavadinimas = new JLabel("Picų sarašas:");
    pavadinimas.setBounds(0, 0, 300, 50);
    Font sriftas =pavadinimas.getFont();
    pavadinimas.setFont(sriftas.deriveFont(20f));
     
    JLabel pavadinimas0 = new JLabel("Picų aparatai:");
    pavadinimas0.setBounds(500, 0, 400, 50);
    Font sriftas0 =pavadinimas.getFont();
    pavadinimas0.setFont(sriftas0.deriveFont(20f));

    add(pavadinimas);
    add(pavadinimas0);
    add(naujas);
    add(istrynti);
    add(valyti);
    add(aparatuSarasas0);
    add(picuSarasas0);
    repaint();
    revalidate();
 }
 public void pridekPica(Receptas receptas){
    JLabel l = new JLabel(receptas.PicosPavadinimas);
    Font sriftas =l.getFont();
    l.setFont(sriftas.deriveFont(17f));
     
    l.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(pasirinktasAparatas!=null){
                UzsakymoUI(receptas);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    });
    picuSarasas.add(l);
    revalidate();
 }
 public void sukurkGija(){
     gija = new Thread(new Runnable(){
         @Override
         public void run(){
             //gijai nereikes sustot kol nesibaigs programa
            while(true){
                for(PicuAparatas aparatas:picuServisas.PicuAparatai){
                    Iterator<Pica> iterator =aparatas.pagamintosPicos.iterator();
                    Pica pica;
                    while(iterator.hasNext()){
                        pica = iterator.next();
                        iterator.remove();
                        baigtaPica(pica);
                    }
                     if(aparatas.picuIkiKitoValymo==0){
                            if(!aparatas.pranestaApieValyma){
                                aparatas.pranestaApieValyma=true;
                                reikiaValyti(aparatas.numeris);
                            }
                            return;
                        }
                    //
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         }
     });
     gija.start();
 }
  public void pridekAparata(PicuAparatas aparatas){
    JRadioButton l = new JRadioButton("Aparatas "+aparatas.numeris);
    l.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           pasirinktasAparatas = aparatas;
        }
    });
     
    aparatuSarasas.add(l);
    buttonGroup.add(l);
    revalidate();
 }
  public void atnaujinkAparatuSarasa(){
    aparatuSarasas.removeAll();
    for(PicuAparatas aparatas: picuServisas.PicuAparatai){
        pridekAparata(aparatas);
    }
    repaint();
    revalidate();
 }
  public void atnaujinkPicuSarasa(){
    picuSarasas.removeAll();
    for(Receptas receptas : picuServisas.Receptai){
        pridekPica(receptas);
    }
    repaint();
    revalidate();
 }
 private void UzsakymoUI(Receptas receptas){
    JFrame frame = new JFrame("Picos užsakymas");
    frame.setBounds(700,300,300,200);
    frame.setVisible(true);
     
    JPanel panel = new JPanel();
    String[] Dydziai = new String[PicosDydis.values().length];
    for(int i=0;i<PicosDydis.values().length;i++){
        PicosDydis pd=PicosDydis.values()[i];
        Dydziai[i] = pd.pavadinimas;
    }
    JLabel pavadinimas = new JLabel(receptas.PicosPavadinimas);
    JComboBox DydzioPasirinkimai = new JComboBox(Dydziai);
    
    JButton patvirtinimas = new JButton("Patvirtinti");
    patvirtinimas.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             frame.setVisible(false);
             if(pasirinktasAparatas.picuIkiKitoValymo==0){
                 reikiaValyti(pasirinktasAparatas.numeris);
                 return;
             }
            Pica pica = pasirinktasAparatas.UzsakykPica(receptas, PicosDydis.konvertuok(DydzioPasirinkimai.getItemAt(DydzioPasirinkimai.getSelectedIndex()).toString()));
            if(pica==null){
                pica = pasirinktasAparatas.pasiulykKitaPica();
                if(pica==null){
                    trukstaProduktu();
                }else{
                    pasiulytiPica(pica.toString());
                }
            }
        }
    });
    
    panel.add(pavadinimas);
    panel.add(DydzioPasirinkimai);
    panel.add(patvirtinimas);
    panel.setSize(300, 200);
    frame.add(panel);
 }
 

 public void baigtaPica(Pica pica){
     JFrame frame = new JFrame("");
     frame.setBounds(700,300,500,200);
     frame.setVisible(true);
     frame.setLayout(null);
     JLabel tekstas = new JLabel(pica+" pagaminta.");
     tekstas.setBounds(0, 0, 500, 30);
     Font sriftas =tekstas.getFont();
     tekstas.setFont(sriftas.deriveFont(17f));
     frame.add(tekstas);
     repaint();
 }
  public void trukstaProduktu(){
     JFrame frame = new JFrame("");
     frame.setBounds(700,300,500,200);
     frame.setVisible(true);
     frame.setLayout(null);
     JLabel tekstas = new JLabel("Visom picom trūksta produktų.");
     tekstas.setBounds(0, 0, 500, 30);
     Font sriftas =tekstas.getFont();
     tekstas.setFont(sriftas.deriveFont(17f));
     frame.add(tekstas);
 }
 public void pasiulytiPica(String pavadinimas){
     JFrame frame = new JFrame("");
     frame.setBounds(700,300,500,200);
     frame.setVisible(true);
     frame.setLayout(null);
     JLabel tekstas = new JLabel("Užsakytai picai trūksta produktų.");
     tekstas.setBounds(0, 0, 500, 30);
     Font sriftas =tekstas.getFont();
     tekstas.setFont(sriftas.deriveFont(17f));
     JLabel tekstas1 = new JLabel("Siūlome užsisakyti "+pavadinimas+".");
     tekstas1.setBounds(0, 30, 500, 30);
     tekstas1.setFont(sriftas.deriveFont(17f));
     frame.add(tekstas1);
     frame.add(tekstas);
 }
  public void reikiaValyti(String aparatoNumeris){
     JFrame frame = new JFrame("");
     frame.setBounds(700,300,500,200);
     frame.setVisible(true);
     frame.setLayout(null);
     JLabel tekstas = new JLabel("Reikia valyti aparatą "+aparatoNumeris+".");
     tekstas.setBounds(0, 0, 500, 30);
     Font sriftas =tekstas.getFont();
     tekstas.setFont(sriftas.deriveFont(17f));
     frame.add(tekstas);
     repaint();
 }
 private void aparatasIsvalytas(String aparatoNumeris){
     JFrame frame = new JFrame("");
     frame.setBounds(700,300,500,200);
     frame.setVisible(true);
     frame.setLayout(null);
     JLabel tekstas = new JLabel("Aparatas "+aparatoNumeris+" išvalytas.");
     tekstas.setBounds(0, 0, 500, 30);
     Font sriftas =tekstas.getFont();
     tekstas.setFont(sriftas.deriveFont(17f));
     frame.add(tekstas);
     repaint();
 }
}
