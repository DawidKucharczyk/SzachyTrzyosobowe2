package szachytrzyosobowe;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author Dawid
 */
public class Pionek {
    
    /* waga pionka - 
     * 0 - pion
     * 1 - wieża
     * 2 - skoczek
     * 3 - goniec
     * 4 - królowa
     * 5 - król
     */
    public int waga;
    public int idGracza;
    
    Pionek(int wag, int id){
        waga=wag;
        idGracza=id;
    }
    
    public void rysuj(int x, int y, Graphics pan){

        int xprim=x-15;
        int yprim=y-15;
        String Kolor;
        if (idGracza==0)
            Kolor="N";
        else
            if(idGracza==1)
                Kolor="C";
            else
                Kolor="Z";
        String TypPionka="";
        if(waga==0)
            TypPionka="pion";
        if(waga==1)
            TypPionka="wieza";
        if(waga==2)
            TypPionka="kon";
        if(waga==3)
            TypPionka="laufr";
        if(waga==4)
            TypPionka="krolowa";
        if(waga==5)
            TypPionka="krol"; 
        String DoRysowania=TypPionka+Kolor+".gif";
        
        BufferedImage image=null;
        File imageFile=new File(DoRysowania);
        try{
            image=ImageIO.read(imageFile);
        }catch(IOException E){}
        pan.drawImage(image,xprim,yprim,null);
    }
}