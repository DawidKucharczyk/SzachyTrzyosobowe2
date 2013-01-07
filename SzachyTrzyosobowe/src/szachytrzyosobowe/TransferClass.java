/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package szachytrzyosobowe;
import java.util.*;
/**
 *
 * @author Dawid
 */
public class TransferClass {
    
    public int size;
    public ArrayList<TransferPionek> lista;
    
    TransferClass(){
        lista=new ArrayList<TransferPionek>();
        size=0;
    }
    
    TransferClass(int t){
        lista=new ArrayList<TransferPionek>();
        size=0;
        for(int k=0;k<3;k++){
            //zwykłe piony
            for(int y=0;y<8;y++)
                this.add(new TransferPionek(k,y,1,0,k));
            //wieże
            if(t==0){
            this.add(new TransferPionek(k,0,0,1,k));
            this.add(new TransferPionek(k,7,0,1,k));
            }
            
            //skoczek
            //this.add(new TransferPionek(k,1,0,2,k));
            //this.add(new TransferPionek(k,6,0,2,k));
            //goniec
            //this.add(new TransferPionek(k,2,0,3,k));
            //this.add(new TransferPionek(k,5,0,3,k));
            //królowa
            //this.add(new TransferPionek(k,3,0,4,k));
            //król
            this.add(new TransferPionek(k,4,0,5,k));      
        } 
        
    }
    
    public TransferPionek get(int i){
        return lista.get(i);
    }
    
    public void add(TransferPionek TP){
        lista.add(TP);
        size++;
    }

    public void add(Pionek P,int k,int i,int j){
        TransferPionek TP=new TransferPionek(k,i,j,P.waga,P.idGracza);
        lista.add(TP);
        size++;
    }
    @Override
    public String toString(){
        String H="";
        for(int i=0;i<lista.size();i++)
            H=H+lista.get(i).toString()+"   ";
        return H;
    }
}
