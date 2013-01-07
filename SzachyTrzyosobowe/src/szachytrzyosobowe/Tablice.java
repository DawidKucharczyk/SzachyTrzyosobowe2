package szachytrzyosobowe;
import java.io.*;
/**
 *
 * @author Dawid
 */
public class Tablice {

    public Pole[][][] wspPol;
    public Pole[] wspPl;
    public Kolo[][][] wspKol;
    
    Tablice(){
        wspPol=new Pole[3][8][4];
        wspPl=new Pole[3];
        wspKol=new Kolo[3][8][4];
        
        Pole P=new Pole(4);
        try{
            int y=0;
            FileReader fr=new FileReader("PA.txt");
            BufferedReader br=new BufferedReader(fr);
            String S;
            while((S=br.readLine())!=null){
                String H[]=S.split(" ");
                int G[]=new int[H.length];
                try{
                    for(int i=0;i<H.length;i++)
                        G[i]=Integer.parseInt(H[i]);
                }catch(Exception EE){System.out.println(EE.getMessage());}
                for(int i=0;i<8;i=i+2)
                    P.add(G[i],G[i+1]);
                wspPol[0][y%8][y/8]=P;
                P=new Pole(4);
                y++;
            }
        }catch(Exception E){System.out.println(E.getMessage());}
        //System.out.println(wspPol);
        try{
            int y=0;
            FileReader fr=new FileReader("PB.txt");
            BufferedReader br=new BufferedReader(fr);
            String S;
            while((S=br.readLine())!=null){
                String H[]=S.split(" ");
                int G[]=new int[H.length];
                try{
                    for(int i=0;i<H.length;i++)
                        G[i]=Integer.parseInt(H[i]);
                }catch(Exception EE){System.out.println(EE.getMessage());}
                for(int i=0;i<8;i=i+2)
                    P.add(G[i],G[i+1]);
                wspPol[1][y%8][y/8]=P;
                P=new Pole(4);
                y++;
            }
        }catch(Exception E){System.out.println(E.getMessage());}
        
        try{
            int y=0;
            FileReader fr=new FileReader("PC.txt");
            BufferedReader br=new BufferedReader(fr);
            String S;
            while((S=br.readLine())!=null){
                String H[]=S.split(" ");
                int G[]=new int[H.length];
                try{
                    for(int i=0;i<H.length;i++)
                        G[i]=Integer.parseInt(H[i]);
                }catch(Exception EE){System.out.println(EE.getMessage());}
                for(int i=0;i<8;i=i+2)
                    P.add(G[i],G[i+1]);
                wspPol[2][y%8][y/8]=P;
                P=new Pole(4);
                y++;
            }
        }catch(Exception E){System.out.println(E.getMessage());}
        
        try{
            int k=0;
            int y=0;
            FileReader fr=new FileReader("PK.txt");
            BufferedReader br=new BufferedReader(fr);
            String S;
            while((S=br.readLine())!=null){
                String H[]=S.split(" ");
                int G[]=new int[2];
                try{
                    G[0]=Integer.parseInt(H[0]);
                    G[1]=Integer.parseInt(H[1]);
                }catch(Exception EE){System.out.println(EE.getMessage());}
                Kolo K=new Kolo(G[0],G[1]);
                wspKol[k][y%8][y/8]=K;
                if(y==31){
                    k=k+1;
                    y=0;
                }
                else
                    y++;
            }   
        }catch(Exception E){System.out.println(E.getMessage());}
        
        
        try{
            int k=0;
            FileReader fr=new FileReader("PL.txt");
            BufferedReader br=new BufferedReader(fr);
            String S;
            while((S=br.readLine())!=null){
                P=new Pole(5);
                String H[]=S.split(" ");
                int T[]=new int[H.length];
                for(int u=0;u<H.length;u++)
                    T[u]=Integer.parseInt(H[u]);
                for(int u=0;u<H.length;u=u+2){
                    P.add(T[u],T[u+1]);
                    
                }
                wspPl[k]=P;
                k++;                   
            }
        }catch(Exception E){System.out.println(E.getMessage());}   
    }
    
    public int getBoard(int x,int y){   
        int board=-1;
        boolean znalazl=false;
        for(int k=0;k<3;k++){
            if(!znalazl){
                boolean ok=true;
                for(int j=0;j<5;j++){
                    if(ok){
                        Wektor A,B;
                        A=new Wektor(wspPl[k].wspX[j],wspPl[k].wspY[j],wspPl[k].wspX[(j+1)%5],wspPl[k].wspY[(j+1)%5]);
                        B=new Wektor(wspPl[k].wspX[(j+1)%5],wspPl[k].wspY[(j+1)%5],x,y);
                        if(!A.PrawoSkret(B))
                            ok=false;
                    }
                }
                if(ok){
                    znalazl=true;
                    board=k;
                }
            }
        }
        return board;
    }
    
    public String getField(int k,int x,int y){
        int fieldX,fieldY;
        fieldX=-1;
        fieldY=-1;
        boolean znalazl=false;
        for(int i=0;i<8;i++)
            for(int j=0;j<4;j++){
                if(!znalazl){
                    boolean ok=true;
                    for(int o=0;o<4;o++){
                        if(ok){
                            Wektor A,B;
                            A=new Wektor(wspPol[k][i][j].wspX[o],wspPol[k][i][j].wspY[o],wspPol[k][i][j].wspX[(o+1)%4],wspPol[k][i][j].wspY[(o+1)%4]);
                            B=new Wektor(wspPol[k][i][j].wspX[(o+1)%4],wspPol[k][i][j].wspY[(o+1)%4],x,y);
                            if(!A.PrawoSkret(B))
                                ok=false;
                        }
                    }
                    if(ok){
                        znalazl=true;
                        fieldX=i;
                        fieldY=j;
                    }
                }    
            }
        return new String(fieldX+""+fieldY);
    }
}
