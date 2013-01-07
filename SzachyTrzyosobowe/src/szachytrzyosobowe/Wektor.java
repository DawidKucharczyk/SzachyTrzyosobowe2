package szachytrzyosobowe;

/**
 *
 * @author Dawid
 */
public class Wektor {

    public int x,y;
    
    Wektor(int X,int Y){
        x=X;
        y=Y;
    }
    
    Wektor(int X1,int Y1,int X2,int Y2){
        x=X2-X1;
        y=Y2-Y1;
    }
    
    public boolean PrawoSkret(Wektor B){
        double Skr=x*B.y-y*B.x;
        if(Skr>0)
            return true;
        else
            return false;
    }
    
    @Override
    public String toString(){
        return new String("Wektor: "+x+" "+y);
    }
    
}
