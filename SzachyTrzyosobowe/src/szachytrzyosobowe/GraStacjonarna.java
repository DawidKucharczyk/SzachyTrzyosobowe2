/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package szachytrzyosobowe;
import javax.swing.*;
/**
 *
 * @author Dawid
 */
public class GraStacjonarna{
    
    public Ramka ramka;
    public JPanel panelGry;
    
    public int obecnyGracz;
    public boolean Ai;
    
    public TransferClass ProtoPlansza;
    public String[] nazwyGraczy;
    public GameBoard gracz; 
    
    GraStacjonarna(boolean ai,String name1,String name2,String name3){//gdy ai==false - mamy trzech graczy
        
        gracz=new GameBoard(-1);
        ramka=new Ramka(gracz,"");  

    }

}
