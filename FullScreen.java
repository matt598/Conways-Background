import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
public class Life extends JFrame implements KeyListener, Runnable
{
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static Thread bounceThread;
    boolean live=true;
    boolean capture;
    static int cells [][];
    static int cellsToDie [][];
    public Life()
    {
        Container mainWindow = getContentPane();
        ColorPanel view = new ColorPanel(Color.WHITE);
        mainWindow.add(view);
        setSize(screenSize.width,screenSize.height);
        setVisible(true);
        addKeyListener(this);
        cells = new int[screenSize.width][screenSize.height];
        cellsToDie = cells;
    }

    public static void main(String[] args) throws InterruptedException
    {
        bounceThread = new Thread(new Life());
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

            cells [xcord][ycord] = 1;
            counter++;
            if(counter >1000)
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
        for(int x = 1; x < screenSize.width-1; x++)
        {
            for(int y = 1; y< screenSize.height-1; y++)
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
                    cellsToDie[x][y] =2;
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
            live = false;
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
            
            for(int x = 0; x < screenSize.width; x++)
            {
                for(int y = 0; y< screenSize.height; y++)
                {
                    g.setColor(new Color(24,0,72));
                    if(cells[x][y] == 1)
                    {
                        g.drawRect(x,y,1,1);
                    }
                    g.setColor(new Color(144,0,48));
                    if(cells[x][y] ==2)
                    {
                        g.drawRect(x,y,1,1);
                    }
                }
            }
        }
    }
}
