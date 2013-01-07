/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package szachytrzyosobowe;
/**
 *
 * @author Dawid
 */
public class Pole {
    
    public int wspX[];
    public int wspY[];
    private int i;
    private int N;
    
    Pole(int n){
        wspX=new int[n];
        wspY=new int[n];
        i=0;
        N=n;
    }
    
    public void add(int x,int y){
        if(!(i==(N))){
            wspX[i]=x;
            wspY[i]=y;
            i++;
        }              
    }
    
    @Override
    public String toString(){
        String H="";
        for(int i=0;i<wspX.length;i++)
            H=H+wspX[i]+"   "+wspY[i]+"\n";
        return H;
    }
}
