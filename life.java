import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class BetterLife extends JFrame implements KeyListener, Runnable
{
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static Thread bounceThread;
    boolean live=true, die = false, notClicking = true;
    static int cells [][];
    static int cellsToDie [][];
    int resoultion = 3, speed = 10, shape = 0;
    int rgbValues [][] = new int [3][2];
    public BetterLife()
    {
        boolean isStupid =true;
        setCellSize();
        setTurnSpeed();
        resetCellColor();
        String options []= {"yes", "no"};
        if(0==JOptionPane.showOptionDialog(null,"Would you like to acess the advanced options?","Extras!:",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]))
        {
            selectAdvancedOptions();
        }
        JOptionPane.showMessageDialog(null,"Once you've drawn enough pixels on the screen press L to start","Instructions:",JOptionPane.INFORMATION_MESSAGE);
        cells = new int[screenSize.width/resoultion][screenSize.height/resoultion];
        cellsToDie = cells;
        //clearCells();
        Container mainWindow = getContentPane();
        ColorPanel view = new ColorPanel((new Color(144,0,48)));
        mainWindow.add(view);
        setSize(screenSize.width,screenSize.height);
        setVisible(true);
        addKeyListener(this);
    }

    public static void main(String[] args) throws InterruptedException
    {
        bounceThread = new Thread(new BetterLife());
        bounceThread.start();
    }

    public void run()

    {
        int xcord=0;
        int ycord=0;
        int counter = 0;
        while(live)
        {
            if(notClicking)
            {
                xcord = MouseInfo.getPointerInfo().getLocation().x;
                ycord = MouseInfo.getPointerInfo().getLocation().y;
                if( xcord> screenSize.width-1 )
                    xcord = screenSize.width-1;
                if(ycord > screenSize.height-1)
                    ycord = screenSize.height-1;

                try{
                    cells [xcord/resoultion][ycord/resoultion] = 1;
                }catch(Exception e){cells [0][0] = 1;}
            }
            counter++;
            if(counter >speed && die)
            {
                counter = 0;
                killCells();
                cells = cellsToDie;
            }
            try
            {
                Thread.sleep(speed);
            }catch (InterruptedException e) { }
            repaint();
        }
        System.exit(0);
    }

    public void killCells()
    {
        int numberOfNeighbors = 0;
        //checks top corner
        if(cells[1][0] == 1)
        {
            numberOfNeighbors++;
        }
        if(cells[0][1] == 1)
        {
            numberOfNeighbors++;
        }
        if(numberOfNeighbors < 2)
        {
            if(cells[0][0]==1)
            {
                cellsToDie[0][0] =2;
            }
        }
        else if(numberOfNeighbors == 2)
        {    
            cellsToDie[0][0] =cells[0][0];
        }
        for(int x = 1; x < screenSize.width/resoultion-1; x++)//checks top
        {
            numberOfNeighbors = 0;
            if(cells[x+1][0] == 1)
            {
                numberOfNeighbors++;
            }
            if(cells[x+1][1] == 1)
            {
                numberOfNeighbors++;
            }
            if(cells[x-1][0] == 1)
            {
                numberOfNeighbors++;
            }
            if(cells[x-1][1] == 1)
            {
                numberOfNeighbors++;
            }
            if(cells[x][1] == 1)
            {
                numberOfNeighbors++;
            }
            if(numberOfNeighbors < 2)
            {
                if(cells[x][0]==1)
                {
                    cellsToDie[x][0] =2;
                }
            }
            else if(numberOfNeighbors >3)
            {
                cellsToDie[x][0] =2;
            }
            else if(numberOfNeighbors == 3)
            {    
                cellsToDie[x][0] =1;
            }
            else if(numberOfNeighbors == 2)
            {    
                cellsToDie[x][0] =cells[x][0];
            }
        }
        for(int y = 1; y< screenSize.height/resoultion-1; y++)//checks left side
        {
            numberOfNeighbors = 0;
            if(cells[1][y] == 1)
            {
                numberOfNeighbors++;
            }
            if(cells[1][y-1] == 1)
            {
                numberOfNeighbors++;
            }
            if(cells[1][y+1] == 1)
            {
                numberOfNeighbors++;
            }
            if(cells[0][y+1] == 1)
            {
                numberOfNeighbors++;
            }
            if(cells[0][y-1] == 1)
            {
                numberOfNeighbors++;
            }
            if(numberOfNeighbors < 2)
            {
                if(cells[0][y]==1)
                {
                    cellsToDie[0][y] =2;
                }
            }
            else if(numberOfNeighbors >3)
            {
                cellsToDie[0][y] =2;
            }
            else if(numberOfNeighbors == 3)
            {    
                cellsToDie[0][y] =1;
            }
            else if(numberOfNeighbors == 2)
            {    
                cellsToDie[0][y] =cells[0][y];
            }
        }
        for(int x = 1; x < screenSize.width/resoultion-1; x++)
        {
            for(int y = 1; y< screenSize.height/resoultion-1; y++)
            {
                numberOfNeighbors = 0;
                if(cells[x+1][y] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x+1][y-1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x+1][y+1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x-1][y] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x-1][y-1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(cells[x-1][y+1] == 1)
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
                    cellsToDie[x][y] =cells[x][y];
                }
            }
        }
    }

    public void keyPressed( KeyEvent e)
    {
        int c = e.getKeyCode();
        if(c == 76)//L
        {
            die = false;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int c = e.getKeyCode();
        if(c == 76)//L
        {
            die = true;
        }
    }

    public void keyTyped(KeyEvent e)
    {}

    public void setCellSize()
    {
        boolean isStupid;
        do{
            try{
                if(resoultion < 1)
                {
                    resoultion = Integer.parseInt(JOptionPane.showInputDialog(null,"The resouliton how many pixels each cell takes up, \nplease note: A cell can not take up only part of a pixel",JOptionPane.INFORMATION_MESSAGE));
                }
                else
                {
                    resoultion = Integer.parseInt(JOptionPane.showInputDialog(null,"The resouliton how many pixels each cell takes up, \nplease note: if you want it to work just enter a regular number and move on.",JOptionPane.INFORMATION_MESSAGE));
                }
                isStupid = false;
            }catch (Exception e) {
                resoultion = 3;
                System.out.println("Do you read?");
                isStupid = true;
            }
        }while(isStupid || resoultion < 1);
    }

    public void setTurnSpeed()
    {
        boolean isStupid;
        do{
            try{
                if(speed < 1)
                {
                    speed = Integer.parseInt(JOptionPane.showInputDialog(null,"The speed how long pixels take to refresh, \nYou must enter a number that is AT LEAST 1",JOptionPane.INFORMATION_MESSAGE));
                }
                else
                {
                    speed = Integer.parseInt(JOptionPane.showInputDialog(null,"The speed how long pixels take to refresh\nJust enter a number greater than one",JOptionPane.INFORMATION_MESSAGE));
                }
                isStupid = false;
            }catch (Exception e) {
                speed = 10;
                System.out.println("Do you read?");
                isStupid = true;
            }
        }while(isStupid || speed < 1);
    }

    public void resetCellColor()
    {
        changeCellColor(24,0,72,144,0,48);
    }
    
    public void changeCellColor(int r1, int g1, int b1, int r2, int g2, int b2)
    {
        rgbValues[0][0] = r1;
        rgbValues[1][0] = g1;
        rgbValues[2][0] = b1;
        rgbValues[0][1] = r2;
        rgbValues[1][1] = g2;
        rgbValues[2][1] = b2;
    }

    public void selectAdvancedOptions()
    {
        boolean isStupid = false;
        String options []= {"yes", "no"};
        String Advancedoptions []= {"cell shape", "cell color", "I'm done"};
        do{
            int AdvancedOptionChosen = JOptionPane.showOptionDialog(null,"What would you like to modifiy?","Extras!:",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,Advancedoptions,Advancedoptions[2]);
            switch(AdvancedOptionChosen)
            {
                case 0://Cell Shape
                options [0] = "square";
                options [1] = "circle";
                try{
                    shape = JOptionPane.showOptionDialog(null,"Would you like to acess the advanced options?","Extras!:",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                    isStupid = false;
                }catch (Exception e) {
                    shape = 10;
                    System.out.println("HOW did you get this? (Unless you just pressed cancel...)");
                    isStupid = true;
                }
                if(shape == 1 && resoultion < 10)
                {
                    options [0] = "yes";
                    options [1] = "no";
                    int responce = JOptionPane.showOptionDialog(null,"Circular cells work best when the resoultion is atleast 10, would you like to change your resoultion?","Note:",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                    setCellSize();
                }
                break;
                case 1:
                    changeCellColor(0,0,0,0,0,0);
                break;
                case 2:

                break;
                default:
                JOptionPane.showMessageDialog(null, "This feature is still being worked on", "Sorry", JOptionPane.INFORMATION_MESSAGE);
            }

        }while(true);
    }

    public void clearCells()
    {
        for(int x = 1; x < screenSize.width/resoultion-1; x++)
        {
            for(int y = 1; y< screenSize.height/resoultion-1; y++)
            {
                cells[x][y] = 0;
            }
        }
    }
    public class ColorPanel extends JPanel
    {
        public ColorPanel( Color back)
        {
            setBackground(back);
        }

        public void paintComponent(Graphics g)
        {
            switch(shape)
            {
                default: case 0:
                for(int x = 0; x < screenSize.width/resoultion; x++)
                {
                    for(int y = 0; y< screenSize.height/resoultion; y++)
                    {
                        g.setColor(new Color(rgbValues[0][0],rgbValues[1][0],rgbValues[2][0]));//living cell color
                        if(cells[x][y] == 1)
                        {
                            g.fillRect(x*resoultion,y*resoultion,1*resoultion,1*resoultion);
                        }
                        g.setColor(new Color(rgbValues[0][1],rgbValues[1][1],rgbValues[2][1]));//dead cell color
                        if(cells[x][y] ==2)
                        {
                            g.fillRect(x*resoultion,y*resoultion,1*resoultion,1*resoultion);
                        }
                    }
                }
                break;
                case 1:
                for(int x = 0; x < screenSize.width/resoultion; x++)
                {
                    for(int y = 0; y< screenSize.height/resoultion; y++)
                    {
                        g.setColor(new Color(rgbValues[0][0],rgbValues[1][0],rgbValues[2][0]));//living cell color
                        if(cells[x][y] == 1)
                        {
                            g.fillOval(x*resoultion,y*resoultion,resoultion+1,resoultion+1);
                        }
                        g.setColor(new Color(rgbValues[0][1],rgbValues[1][1],rgbValues[2][1]));//dead cell color
                        if(cells[x][y] ==2)
                        {
                            g.fillOval(x*resoultion,y*resoultion,resoultion+1,resoultion+1);
                        }
                    }
                }
                break;
            }
        }
    }
}
