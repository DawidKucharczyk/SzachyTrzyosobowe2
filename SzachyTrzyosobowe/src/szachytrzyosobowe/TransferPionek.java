/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package szachytrzyosobowe;
/**
 *
 * @author Dawid
 */
public class TransferPionek {
    
    public int k;
    public int x;
    public int y;
    public int waga;
    public int ID;

    TransferPionek(int kp,int ip,int jp,int jw,int jid){
        k=kp;
        x=ip;
        y=jp;
        waga=jw;
        ID=jid;  
    }
    
    @Override
    public String toString(){
        String S="";
        S=S+k;
        S=S+x;
        S=S+y;
        S=S+waga;
        S=S+ID;
        return S;
    }

}
