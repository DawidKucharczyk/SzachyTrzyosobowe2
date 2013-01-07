package szachytrzyosobowe;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
/**
 *
 * @author Dawid
 */
public class GameBoard extends JPanel implements MouseListener, ComponentListener{

    public Pionek[][][] PlanszaA;

    public int idGracza;//aktualny idGracza
    public int[] idPrzegrany;
    
    public ArrayList<TransferPionek> listaRuchow;
    public TransferPionek bazowy;
    public Tablice T;
    
    //obsługa kliknięć myszą
    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mousePressed(MouseEvent e){}
    
    public boolean wygenerowanoTrasy;
    public boolean wykonanoRuch;
    
    @Override
    public void mouseReleased(MouseEvent e){
        TransferPionek TP=traceClick(e.getX(),e.getY());
        if((TP.k!=(-1))&&(TP.x>=0)&&(TP.x<8)&&(TP.y>=0)&&(TP.y<4)){
            //wiemy, że kliknęliśmy na planszę
            TP=traceFigure(TP);
            //wiemy, czy kliknęliśmy na puste pole, czy na pionek
            if(TP.waga==(-1)){
                if(wygenerowanoTrasy&&clickOnPath(TP)){
                    moveFigure(bazowy,TP);
                    bazowy=null;
                    wygenerowanoTrasy=false;
                    listaRuchow=new ArrayList<TransferPionek>();
                    wykonanoRuch=true;
                }
            }
            else{
                if(TP.ID==idGracza){
                    wygenerowanoTrasy=true;
                    bazowy=TP;
                    listaRuchow=new ArrayList<TransferPionek>();
                    createPath(TP);
                }
                else{
                    //obsługa kliknięcia na pionek przeciwnika
                    if(wygenerowanoTrasy&&clickOnPath(TP)){
                        moveFigure(bazowy,TP);
                        bazowy=null;
                        wygenerowanoTrasy=false;
                        listaRuchow=new ArrayList<TransferPionek>();
                        wykonanoRuch=true;
                        //jeśli pionek przeciwnika możliwy do zbicia
                    }
                }
            }
        }
        this.repaint();    
        getData();
        if(wykonanoRuch){
            if((idPrzegrany[0]!=-1)&&(idPrzegrany[1]!=-1))
                gameOver(idGracza);
            else
                nextPlayer();
        }
    }
    
    public boolean clickOnPath(TransferPionek TP){
        boolean px,py,pk;
        boolean znaleziono=false;
        if(listaRuchow.size()>0){
            for(int i=0;i<listaRuchow.size();i++){
                px=false;
                py=false;
                pk=false;
                if(listaRuchow.get(i).k==TP.k)
                    pk=true;
                if(listaRuchow.get(i).x==TP.x)
                    px=true;
                if(listaRuchow.get(i).y==TP.y)
                    py=true;
                if(pk&&px&&py)
                    znaleziono=true;
            }
        }
        return znaleziono;
    }
    
    //obsługa zmian rozmiaru
    @Override
    public void componentHidden(ComponentEvent e){
        repaint();
    }
    @Override
    public void componentMoved(ComponentEvent e){
        repaint();
    }
    @Override
    public void componentResized(ComponentEvent e){
        repaint();
    }
    @Override
    public void componentShown(ComponentEvent e){
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        //super.paint(g);
        //rysowanie planszy
        String nazwa="3chess.gif";
        BufferedImage image=null;
        File imageFile=new File(nazwa);
        try{
            image=ImageIO.read(imageFile);
        }catch(IOException E){}
        Graphics2D g2=(Graphics2D)this.getGraphics();
        g.drawImage(image,0,0,this);
        
        if(!listaRuchow.isEmpty()){
            for(int i=0;i<listaRuchow.size();i++){
                TransferPionek TP=listaRuchow.get(i);
                int x,y;
                x=T.wspKol[TP.k][TP.x][TP.y].x;
                y=T.wspKol[TP.k][TP.x][TP.y].y;
                g.setColor(Color.blue);
                g.fillOval(x-18, y-18, 36,36 );                    
            }                    
        }
        
        //mamy narysowane koła możliwych ruchów
        //teraz rysowanie samych pionków
        for(int k=0;k<3;k++)
            for(int i=0;i<8;i++)
                for(int j=0;j<4;j++){
                    Pionek P=PlanszaA[k][i][j];
                    if(!(P==null)){
                        int x,y;
                        x=T.wspKol[k][i][j].x;
                        y=T.wspKol[k][i][j].y;
                        P.rysuj(x, y, g);
                    }
                }
    }
      
    public GameBoard(int id) {        
        initComponents();     
        this.setBackground(new Color(166,202,240));
        PlanszaA=new Pionek[3][8][4];
        idGracza=id;
        idPrzegrany=new int[2];
        idPrzegrany[0]=-1;
        idPrzegrany[1]=-1;
        wykonanoRuch=false;
        listaRuchow=new ArrayList<TransferPionek>();
        for(int i=0;i<3;i++)
            for(int j=0;j<8;j++)
                for(int k=0;k<4;k++)
                    PlanszaA[i][j][k]=null;
        
        //wstawiamy konkretne pionki
        for(int k=0;k<3;k++){
            //zwykłe piony
            for(int y=0;y<8;y++)
                PlanszaA[k][y][1]=new Pionek(0,k);
            //wieże
            PlanszaA[k][0][0]=new Pionek(1,k);
            PlanszaA[k][7][0]=new Pionek(1,k);
            //skoczek
            PlanszaA[k][1][0]=new Pionek(2,k);
            PlanszaA[k][6][0]=new Pionek(2,k);
            //goniec
            PlanszaA[k][2][0]=new Pionek(3,k);
            PlanszaA[k][5][0]=new Pionek(3,k);
            //królowa
            PlanszaA[k][3][0]=new Pionek(4,k);
            //król
            PlanszaA[k][4][0]=new Pionek(5,k);            
        } 
        
        T=new Tablice();
        this.addComponentListener(this);
        this.addMouseListener(this);
        repaint();
        nextPlayer();
    }
    
    public GameBoard(int id,TransferClass TC){
        initComponents();     
        PlanszaA=new Pionek[3][8][4];
        idGracza=id;
        idPrzegrany=new int[2];
        idPrzegrany[0]=-1;
        idPrzegrany[1]=-1;
        listaRuchow=new ArrayList<TransferPionek>();
        T=new Tablice();
        for(int i=0;i<3;i++)
            for(int j=0;j<8;j++)
                for(int k=0;k<4;k++)
                    PlanszaA[i][j][k]=null;
        for(int i=0;i<TC.size;i++){
            int k,x,y;
            k=TC.get(i).k;
            x=TC.get(i).x;
            y=TC.get(i).y;
            PlanszaA[k][x][y]=new Pionek(TC.get(i).waga,TC.get(i).ID);
        }
        this.addComponentListener(this);
        this.addMouseListener(this);
        repaint();
    }
    
    public TransferPionek traceClick(int x,int y){
        int k;
        k=T.getBoard(x,y);
        if(k>=0){
            String i=T.getField(k,x,y);
            int x1,y1;
            x1=i.charAt(0)-48;
            y1=i.charAt(1)-48;
            TransferPionek TP=new TransferPionek(k,x1,y1,-1,-1);
            return TP; 
        }
        else
            return new TransferPionek(-1,-1,-1,-1,-1);
        
    }
    
    public TransferPionek traceFigure(TransferPionek field){
        
        int k=field.k;
        int i=field.x;
        int j=field.y;
        Pionek Wyn=PlanszaA[k][i][j];

        if(Wyn==null)
            return field;
        else
            return (new TransferPionek(k,i,j,Wyn.waga,Wyn.idGracza));
    }
    
    public int moveFigure(TransferPionek from,TransferPionek where){
        //zwraca wagę przesunięcia figury: jeśli było zbicie - to wagę zbitego pionka
        int i1,i2,j1,j2,k1,k2;
        
        k1=from.k;
        i1=from.x;
        j1=from.y;
        k2=where.k;
        i2=where.x;
        j2=where.y;
        
        Pionek Temp=PlanszaA[k1][i1][j1];
        PlanszaA[k1][i1][j1]=null;
        
        Pionek Zbity=PlanszaA[k2][i2][j2];
        
        int waga=0;
        if(Zbity!=null)        
            waga=Zbity.waga+1;
        
        PlanszaA[k2][i2][j2]=Temp;
        
        return waga;        
    }
    
    public void createPath(TransferPionek field){
        //pobiera łańcuch z traceFigure

        listaRuchow=new ArrayList<TransferPionek>();
        if(!(field.waga==-1)){
            int k,x,y,kto;
            k=field.k;
            x=field.x;
            y=field.y;
            kto=field.waga;
            if(kto==0)
                RuchSzeregowca(k,x,y);
            if(kto==1)
                RuchWiezy(k,x,y);
            if(kto==2)
                RuchSkoczka(k,x,y);
            if(kto==3)
                RuchGonca(k,x,y);
            if(kto==4)
                RuchKrolowej(k,x,y);
            if(kto==5)
                RuchKrola(k,x,y);
            wygenerowanoTrasy=true;
            bazowy=field;
            repaint();
        }
    }
    
    public void RuchSzeregowca(int k,int x,int y){
        //opracować
        int status;
        if(k==idGracza){
            if(y==3){
                int kprim;
                int xprim;
                if((x==3)||(x==4)){
                    int kbis;
                    if(x==3){
                        kprim=(k+2)%3;
                        kbis=(k+1)%3;
                    }
                    else{
                        kprim=(k+1)%3;
                        kbis=(k+2)%3;
                    }
                    xprim=7-x;
                    if(pusta(kprim,xprim,3)){
                        TransferPionek TP=new TransferPionek(kprim,xprim,3,0,0);
                        listaRuchow.add(TP);
                    }
                    if(!pusta(kprim,xprim-1,3)){
                        status=getStrong(kprim,xprim-1,3);
                        TransferPionek TP=new TransferPionek(kprim,xprim-1,3,status,0);
                        listaRuchow.add(TP);
                    }
                    if(!pusta(kprim,xprim+1,3)){
                        status=getStrong(kprim,xprim+1,3);
                        TransferPionek TP=new TransferPionek(kprim,xprim+1,3,status,0);
                        listaRuchow.add(TP);
                    } 
                    if(!pusta(kbis,3,3)){
                        status=getStrong(kbis,3,3);
                        TransferPionek TP=new TransferPionek(kbis,3,3,status,0);
                        listaRuchow.add(TP);
                    }
                    if(!pusta(kbis,4,3)){
                        status=getStrong(kbis,4,3);
                        TransferPionek TP=new TransferPionek(kbis,4,3,status,0);
                        listaRuchow.add(TP);
                    }
                }
                else{
                    if(x<3)
                        kprim=(k+2)%3;
                    else
                        kprim=(k+1)%3;
                    xprim=7-x;
                    if(pusta(kprim,xprim,3)){
                        TransferPionek TP=new TransferPionek(kprim,xprim,3,0,0);
                        listaRuchow.add(TP);
                    }
                    if((xprim>0)&&(!pusta(kprim,xprim-1,3))){
                        status=getStrong(kprim,xprim-1,3);
                        TransferPionek TP=new TransferPionek(kprim,xprim-1,3,status,0);
                        listaRuchow.add(TP);
                    }
                    if((xprim<7)&&(!pusta(kprim,xprim+1,3))){
                        status=getStrong(kprim,xprim+1,3);
                        TransferPionek TP=new TransferPionek(kprim,xprim+1,3,status,0);
                        listaRuchow.add(TP);
                    } 
                }
            }
            else{
                if(pusta(k,x,y+1)){
                    TransferPionek TP=new TransferPionek(k,x,y+1,0,0);
                    listaRuchow.add(TP);
                }
                if((x>0)&&(!pusta(k,x-1,y+1))){
                    status=getStrong(k,x-1,y+1);
                    TransferPionek TP=new TransferPionek(k,x-1,y+1,status,0);
                    listaRuchow.add(TP);
                }
                if((x<7)&&(!pusta(k,x+1,y+1))){
                    status=getStrong(k,x+1,y+1);
                    TransferPionek TP=new TransferPionek(k,x+1,y+1,status,0);
                    listaRuchow.add(TP);
                }
            }
        }
        else{
            if(pusta(k,x,y-1)){
                TransferPionek TP=new TransferPionek(k,x,y-1,0,0);
                listaRuchow.add(TP);
            }
            if((x>0)&&(!pusta(k,x-1,y-1))){
                status=getStrong(k,x-1,y-1);
                TransferPionek TP=new TransferPionek(k,x-1,y-1,status,0);
                listaRuchow.add(TP);
            }
            if((x<7)&&(!pusta(k,x+1,y-1))){
                status=getStrong(k,x+1,y-1);
                TransferPionek TP=new TransferPionek(k,x+1,y-1,status,0);
                listaRuchow.add(TP);
            }
        }
    }
    
    public void RuchWiezy(int k,int x,int y){
        RuchOrtogonalnyG(k,x,y);
        RuchOrtogonalnyD(k,x,y);
        RuchOrtogonalnyP(k,x,y);
        RuchOrtogonalnyL(k,x,y);
    }
    
    public void RuchSkoczka(int k,int x,int y){
    //opracować
    }
    
    public void RuchGonca(int k,int x,int y){
        RuchSkosnyPD(k,x,y);
        RuchSkosnyLD(k,x,y);
        RuchSkosnyLG(k,x,y);
        RuchSkosnyPG(k,x,y);
    }
    
    public void RuchKrolowej(int k,int x,int y){
        RuchOrtogonalnyG(k,x,y);
        RuchOrtogonalnyD(k,x,y);
        RuchOrtogonalnyP(k,x,y);
        RuchOrtogonalnyL(k,x,y);
        RuchSkosnyPD(k,x,y);
        RuchSkosnyLD(k,x,y);
        RuchSkosnyLG(k,x,y);
        RuchSkosnyPG(k,x,y);
    }
    
    public void RuchKrola(int k,int x,int y){
    //opracować
        
    }
    
    public void RuchSkosnyPD(int k,int x,int y){
        int status=0;
        boolean mozna=true;
        int xprim=x;
        
        for(int i=1;i<min(8-x,4-y);i++)
            if(mozna){
                if(pusta(k,x+i,y+i))
                    xprim=x+i;
                else{
                    status=getStrong(k,x+i,y+i)+3;
                    mozna=false;
                }
                TransferPionek TP=new TransferPionek(k,x+i,y+i,status,0);
                listaRuchow.add(TP);
                if(i==(min(8-x,4-y)-1))
                    mozna=false;
            }
        if(mozna){
            int przX,przK;
            przX=0;
            przK=0;
            if((xprim!=7)&&(xprim!=3)){
                przX=7-xprim;
                if(xprim<3)
                    przK=(k+2)%3;
                else
                    przK=(k+1)%3;
                if(!pusta(przK,przX,3))
                    listaRuchow.add(new TransferPionek(przK,przX,3,(getStrong(przK,przX,3)+3),0));
                else{
                    listaRuchow.add(new TransferPionek(przK,przX,3,3,0));
                    RuchSkosnyLG(przK,przX,3);
                }
            }
            if(xprim==3){
                for(int t=1;t<=2;t++){
                    if(!pusta(((k+t)%3),3,3))
                        listaRuchow.add(new TransferPionek((k+t)%3,3,3,getStrong(((k+t)%3),3,3)+3,0));
                    else{
                        listaRuchow.add(new TransferPionek((k+t)%3,3,3,0,0));
                        RuchSkosnyLG(((k+t)%3),3,3);
                    }
                }
            }
        }
    }
    
    public void RuchSkosnyLG(int k,int x,int y){
        int status=0;
        boolean mozna=true;
        for(int i=1;i<(min(x,y)+1);i++)
            if(mozna){
                if(!pusta(k,x-i,y-i)){
                    status=getStrong(k,x-i,y-i);
                    mozna=false;
                }
                listaRuchow.add(new TransferPionek(k,x-i,y-i,status,0));
            }       
    }
    
    public void RuchSkosnyPG(int k,int x,int y){
        int status=0;
        boolean mozna=true;
        for(int i=1;i<(min(7-x,y)+1);i++)
            if(mozna){
                if(!pusta(k,x+i,y-i)){
                    status=getStrong(k,x+i,y-i);
                    mozna=false;
                }
                listaRuchow.add(new TransferPionek(k,x+i,y-i,status,0));
            }        
    }
    
    public void RuchSkosnyLD(int k,int x,int y){
        int status=0;
        boolean mozna=true;
        int xprim=x;
        
        for(int i=1;i<(min(3-y,x)+1);i++)
            if(mozna){
                if(pusta(k,x-i,y+i))
                    xprim=x-i;
                else{
                    status=getStrong(k,x-i,y+i)+3;
                    mozna=false;
                }
                listaRuchow.add(new TransferPionek(k,x-i,y+i,status,0));
            }
        if(mozna){
            int przX,przK;
            przX=0;
            przK=0;
            if((xprim!=0)&&(xprim!=4)){
                przX=8-xprim;
                if(xprim<4)
                    przK=(k+2)%3;
                else
                    przK=(k+1)%3;
                if(!pusta(przK,przX,3))
                    listaRuchow.add(new TransferPionek(przK,przX,3,getStrong(przK,przX,3)+3,0));
                else{
                    listaRuchow.add(new TransferPionek(przK,przX,3,0,0));
                    RuchSkosnyPG(przK,przX,3);
                }
            }
            if(xprim==4){
                for(int t=1;t<=2;t++){
                    if(!pusta(((k+t)%3),3,3))
                        listaRuchow.add(new TransferPionek((k+t)%3,4,3,getStrong(((k+t)%3),4,3)+3,0));
                    else{
                        listaRuchow.add(new TransferPionek((k+t)%3,4,3,0,0));
                        RuchSkosnyLG(((k+t)%3),4,3);
                    }
                }
            }
        }
    }
    
    public void RuchOrtogonalnyG(int k,int x,int y){
        boolean mozna=true;
        int status=0;
        if(y>0){
            for(int i=y-1;i>=0;i--)
                if(mozna){
                    if(!pusta(k,x,i)){
                        status=getStrong(k,x,i);
                        mozna=false;
                    }
                    listaRuchow.add(new TransferPionek(k,x,i,status,0)); 
                }
        }
    }
    
    public void RuchOrtogonalnyD(int k,int x,int y){
        boolean mozna=true;
        int status=0;
        for(int i=1;i<(4-y);i++)
            if(mozna){
                if(!pusta(k,x,y+i)){
                    status=getStrong(k,x,y+i)+3;
                    mozna=false;
                }
                listaRuchow.add(new TransferPionek(k,x,y+i,status,0));
            }
        if(mozna){
            status=0;
            int przK=0;
            int przX=0;
            if(x<=3)
                przK=(k+2)%3;
            else
                przK=(k+1)%3;
            przX=7-x;
            if(!pusta(przK,przX,3))
                status=getStrong(przK,przX,3);
            listaRuchow.add(new TransferPionek(przK,przX,3,status,0));
            if(status==0)
                RuchOrtogonalnyG(przK,przX,3);
            int kbis=0;
            if(x==3)
                kbis=(k+2)%3;
            if(x==4)
                kbis=(k+1)%3;
            for(int r=3;r<=4;r++){
                int status2=0;
                if(x==r){
                    if(!pusta(kbis,r,3))
                        status2=getStrong(kbis,r,3)+3;
                    listaRuchow.add(new TransferPionek(kbis,r,3,status2,0));
                    if(r==3)
                        RuchOrtogonalnyL(kbis,r,3);
                    else
                        RuchOrtogonalnyP(kbis,4,3);
                }
            }
        }
    }
    
    public void RuchOrtogonalnyP(int k,int x,int y){
        boolean mozna=true;
        int status =0;
        int xprim=x;
        for(int i=1;i<(8-x);i++){
            if((xprim==3)&&(y==3)){
                int kprim=(k+1)%3;
                if(!pusta(kprim,3,3))
                    listaRuchow.add(new TransferPionek(kprim,3,3,getStrong(kprim,3,3)+3,0));
                else{
                    listaRuchow.add(new TransferPionek(kprim,3,3,0,0));
                    RuchOrtogonalnyG(kprim,3,3);
                }
            }
            if(mozna){
                if(!pusta(k,x+i,y)){
                    status=getStrong(k,x+i,y)+3;
                    mozna=false;
                }
                else
                    xprim=x+i;
                listaRuchow.add(new TransferPionek(k,x+i,y,status,0));
            }
        }
    }
    
    public void RuchOrtogonalnyL(int k,int x,int y){
        boolean mozna=true;
        int status =0;
        int xprim=x;
        for(int i=1;i<=x;i++){
            if((xprim==4)&&(y==3)){
                int kprim=(k+2)%3;
                if(!pusta(kprim,4,3))
                    listaRuchow.add(new TransferPionek(kprim,4,3,getStrong(kprim,4,3)+3,0));
                else{
                    listaRuchow.add(new TransferPionek(kprim,4,3,0,0));
                    RuchOrtogonalnyG(kprim,4,3);
                }
            }
            if(mozna){
                if(!pusta(k,x-i,y)){
                    status=getStrong(k,x-i,y)+3;
                    mozna=false;
                }
                else
                    xprim=x-i;
                listaRuchow.add(new TransferPionek(k,x-i,y,status,0));
            }
        }
    }
    
    public int getStrong(int k,int x,int y){
        Pionek L=PlanszaA[k][x][y];
        return L.waga;
    }
    
    public boolean pusta(int k,int x,int y){
        if(PlanszaA[k][x][y]==null)
            return true;
        else
            return false;
    }
    
    public int min(int a,int b){
        if(a<=b)
            return a;
        else
            return b;
    }
    
    public boolean przegrana(){
        boolean przegr=true;
        for(int k=0;k<3;k++)
            for(int i=0;i<8;i++)
                for(int j=0;j<4;j++){
                    Pionek L=PlanszaA[k][i][j];
                    if((L.idGracza==idGracza)&&(L.waga==5))
                        przegr=false;
                }
        return przegr;
    }
    
    public boolean przegrany(int y){
        if((y==idPrzegrany[0])||(y==idPrzegrany[1]))
            return true;
        else
            return false;
    }
    
    public void aktualizuj(){
        JOptionPane.showMessageDialog(this, "Ruch gracza "+idGracza, "Szachy Trzyosobowe", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void getData(){
        //sprawdzić i zamienić pionki stojące w miejscu wrogich figur na królową
        for(int k=0;k<3;k++)
            for(int i=0;i<8;i++)
                for(int j=0;j<4;j++){
                    Pionek t=PlanszaA[k][i][j];
                    if((t!=null)&&(t.idGracza!=k)&&(t.waga==0)&&(j==0))
                        PlanszaA[k][i][j]=new Pionek(4,t.idGracza);
                }
        
        boolean A,B,C;
        A=false;
        B=false;
        C=false;
        
        for(int k=0;k<3;k++)
            for(int i=0;i<8;i++)
                for(int j=0;j<4;j++){
                    if(PlanszaA[k][i][j]!=null){
                        if((PlanszaA[k][i][j].idGracza==0)&&(PlanszaA[k][i][j].waga==5))
                            A=true;
                        if((PlanszaA[k][i][j].idGracza==1)&&(PlanszaA[k][i][j].waga==5))
                            B=true;
                        if((PlanszaA[k][i][j].idGracza==2)&&(PlanszaA[k][i][j].waga==5))
                            C=true;
                    }
                }
        idPrzegrany[0]=-1;
        idPrzegrany[1]=-1;
        int u=0;
        if(!A){
            idPrzegrany[u]=0;
            u++;
        }
        if(!B){
            idPrzegrany[u]=1;
            u++;
        }    
        if(!C){
            idPrzegrany[u]=2;
            u++;
        } 
        System.out.println(A+" "+B+" "+C);
    }
    
    public void nextPlayer(){
            int d=idGracza;
            d=(d+1)%3;
            wykonanoRuch=false;
            getData();
            if(!przegrany(d)){
                idGracza=d;
                aktualizuj();
            }
            else{
                d=(idGracza+2)%3;
                if(!przegrany(d)){
                    idGracza=d;
                    aktualizuj();
                }
                else
                    gameOver(idGracza);  
            }
            this.repaint();
    }
    
    public void gameOver(int wygrany){
        JOptionPane.showMessageDialog(this, "Zwyciężył gracz "+wygrany, "Koniec Gry", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}