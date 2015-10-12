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
    public Life()
    {
        Container mainWindow = getContentPane();
        ColorPanel view = new ColorPanel(Color.WHITE);
        mainWindow.add(view);
        setSize(screenSize.width,screenSize.height);
        setVisible(true);
        addKeyListener(this);
        cells = new int[screenSize.width][screenSize.height];
    }

    public static void main(String[] args) throws InterruptedException
    {
        bounceThread = new Thread(new Life());
        bounceThread.start();
    }

    public void run()

    {
        do{
            do
            {
                if(MouseInfo.getPointerInfo().getLocation().x > screenSize.width || MouseInfo.getPointerInfo().getLocation().y > screenSize.height)
                {

                }
                else
                    cells [MouseInfo.getPointerInfo().getLocation().x][MouseInfo.getPointerInfo().getLocation().y] = 1;

                try
                {
                    Thread.sleep(1);
                }catch (InterruptedException e) { }

                repaint();
            }while(live);
        }while(true);
    }

    public void keyPressed( KeyEvent e)
    {
        int c = e.getKeyCode();
        if(c == 29)
        {
            capture = true;
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
            g.setColor(new Color(24,0,72));
            g.drawString(("("+MouseInfo.getPointerInfo().getLocation().x+", "+MouseInfo.getPointerInfo().getLocation().y+")"),screenSize.width/20, screenSize.height/20); 
            for(int x = 0; x < screenSize.width; x++)
            {
                for(int y = 0; y< screenSize.height; y++)
                {
                    if(cells[x][y] == 1)
                    {
                        g.drawRect(x,y,1,1);
                    }
                }
            }
        }
    }
}
