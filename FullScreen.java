import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
public class BetterLife extends JFrame implements KeyListener, Runnable
{
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static Thread bounceThread;
    boolean live=true;
    boolean die = true;
    static int cells [][];
    static int cellsToDie [][];
    int resoultion = 3; //change it to make it process faster
    int speed = 10;
    public BetterLife()
    {
        Container mainWindow = getContentPane();
        ColorPanel view = new ColorPanel((new Color(144,0,48)));
        mainWindow.add(view);
        setSize(screenSize.width,screenSize.height);
        setVisible(true);
        addKeyListener(this);
        //resoultion 
        try{
            resoultion = Integer.parseInt(JOptionPane.showInputDialog(null,"The resouliton how many pixels each cell takes up, \nplease note: if you want it to work just enter a regular number and move on.",JOptionPane.INFORMATION_MESSAGE));
        }catch (Exception e) {resoultion = 3;}
        try{
            speed = Integer.parseInt(JOptionPane.showInputDialog(null,"The speed how many pixels refresh cell takes up, \nplease note: if you want it to work just enter a regular number, and move on.\n(one and above)",JOptionPane.INFORMATION_MESSAGE));
        }catch (Exception e) {speed = 5;}
        cells = new int[screenSize.width/resoultion][screenSize.height/resoultion];
        cellsToDie = cells;
    }

    public static void main(String[] args) throws InterruptedException
    {
        bounceThread = new Thread(new BetterLife());
        bounceThread.start();
    }

    public void run()

    {
        int xcord;
        int ycord;
        int counter = 0;
        do
        {
            xcord = MouseInfo.getPointerInfo().getLocation().x;
            ycord = MouseInfo.getPointerInfo().getLocation().y;
            if( xcord> screenSize.width-1 )
                xcord = screenSize.width-1;
            if(ycord > screenSize.height-1)
                ycord = screenSize.height-1;

            cells [xcord/resoultion][ycord/resoultion] = 1;
            counter++;
            if(counter >speed && die)
            {
                counter = 0;
                cells = cellsToDie;
                killCells();
            }
            try
            {
                Thread.sleep(5);
            }catch (InterruptedException e) { }

            repaint();
        }while(live);
    }

    public void killCells()
    {
        int numberOfNeighbors = 0;
        for(int x = 1; x < screenSize.width/resoultion-1; x++)
        {
            for(int y = 1; y< screenSize.height/resoultion-1; y++)
            {
                numberOfNeighbors = 0;
                if(cells[x+1][y] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x-1][y] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x][y+1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x][y-1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x+1][y+1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x-1][y+1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x+1][y-1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x-1][y-1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(numberOfNeighbors < 2)
                {
                    if(cells[x][y]==1)
                    {
                        cellsToDie[x][y] =2;
                    }
                }
                else if(numberOfNeighbors >3)
                {
                    cellsToDie[x][y] =2;
                }
                else if(numberOfNeighbors == 3)
                {    
                    cellsToDie[x][y] =1;
                }
                else if(numberOfNeighbors == 2)
                {    
                    cellsToDie[x][y] =cellsToDie[x][y];
                }
            }
        }

    }

    public void keyPressed( KeyEvent e)
    {
        int c = e.getKeyCode();
        if(c == 29)
        {
            die = !die;
        }
        if(c == 76)
        {
            live = false;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int c = e.getKeyCode();
        if(c == 76)
        {
            live = false;
        }
    }

    public void keyTyped(KeyEvent e)
    {}

    public class ColorPanel extends JPanel
    {
        public ColorPanel( Color back)
        {
            setBackground(back);
        }

        public void paintComponent(Graphics g)
        {

            for(int x = 0; x < screenSize.width/resoultion; x++)
            {
                for(int y = 0; y< screenSize.height/resoultion; y++)
                {
                    g.setColor(new Color(24,0,72));
                    if(cells[x][y] == 1)
                    {
                        g.fillRect(x*resoultion,y*resoultion,1*resoultion,1*resoultion);
                    }
                    g.setColor(new Color(144,0,48));
                    if(cells[x][y] ==2)
                    {
                        g.fillRect(x*resoultion,y*resoultion,1*resoultion,1*resoultion);
                    }
                }
            }
        }
    }
}
