import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class BetterLife extends JFrame implements KeyListener, Runnable
{
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static Thread bounceThread;
    boolean live=true, die = false, notClicking = true, isStupid = true;
    static int cells [][];
    static int cellsToDie [][];
    int resoultion = 3, speed = 10, shape = 0;
    int rgbValues [][] = new int [3][2];
    public BetterLife()
    {
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
        int counter = 0;
        while(live)
        {
            if(notClicking)
            {
                makeCellUnderMouseAlive();
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

    public void makeCellUnderMouseAlive()
    {
        int xcord=0;
        int ycord=0;
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

    public void killCells()
    {
        int numberOfNeighbors = 0;
        for(int x = 0; x < screenSize.width/resoultion; x++)
        {
            for(int y = 0; y< screenSize.height/resoultion; y++)
            {
                numberOfNeighbors = 0;
                if(x > 0 && cells[x-1][y] == 1 )
                {
                    numberOfNeighbors++;
                }
                if(x > 0 && y < screenSize.height/resoultion -1 && cells[x-1][y+1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(x > 0 && y  > 0 && cells[x-1][y-1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(x < screenSize.width/resoultion-1 && y  > 0 && cells[x+1][y-1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(x < screenSize.width/resoultion-1 && cells[x+1][y] == 1)
                {
                    numberOfNeighbors++;
                }
                try{if(x < screenSize.width/resoultion -1 && y  < screenSize.height/resoultion-1 && cells[x+1][y+1] == 1)
                    {
                        numberOfNeighbors++;
                    }}catch(Exception E) {System.out.println(x + ", " + y);}
                if(y  < screenSize.height/resoultion-1 && cells[x][y+1] == 1)
                {
                    numberOfNeighbors++;
                }
                if(y  > 0 && cells[x][y-1] == 1)
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
        do{
            try{
                if(speed < 1)
                {
                    speed = 2*Integer.parseInt(JOptionPane.showInputDialog(null,"The speed how long pixels take to refresh, \nYou must enter a number that is AT LEAST 1",JOptionPane.INFORMATION_MESSAGE));
                }
                else
                {
                    speed = 2*Integer.parseInt(JOptionPane.showInputDialog(null,"The speed how long pixels take to refresh\nJust enter a number greater than one",JOptionPane.INFORMATION_MESSAGE));
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
        boolean isStupid = false, notDone = true;
        String Advancedoptions []= {"cell shape", "cell color", "RainbowCells!", "I'm done"};
        do{
            int AdvancedOptionChosen = JOptionPane.showOptionDialog(null,"What would you like to modifiy?","Extras!:",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,Advancedoptions,Advancedoptions[2]);
            switch(AdvancedOptionChosen)
            {
                case 0:
                setCellShape();
                break;
                case 1:
                int colorChoices [] = new int [6];
                String namesOfSlots [] = {"Alive Red", "Alive Green", "Alive Blue", "Dead Red", "Dead Green", "Dead Blue"};
                changeCellColor(0,0,0,0,0,0);
                for(int x = 0; x < 6; x++)
                {
                    do{
                        try{
                            colorChoices [x] = Integer.parseInt(JOptionPane.showInputDialog(null, "Set " + namesOfSlots[x] + " Value by entering a value between 0 and 255",JOptionPane.INFORMATION_MESSAGE));
                            isStupid = false;
                        }catch (Exception e) {
                            System.out.println("Um");
                            isStupid = true;
                        }
                    }while(isStupid);
                    if(colorChoices [x] > 255)
                    {
                        colorChoices [x] = 255;
                    }
                    if(colorChoices [x] < 0)
                    {
                        colorChoices [x] = 0;
                    }
                }
                break;
                case 2:

                break;
                case 3:
                notDone = false;
                break;
                default:
                JOptionPane.showMessageDialog(null, "This feature is still being worked on", "Sorry", JOptionPane.INFORMATION_MESSAGE);
            }

        }while(notDone);
    }

    public void setCellShape()
    {
        String options []= {"square", "circle"};
        try{
            shape = JOptionPane.showOptionDialog(null,"What shape would you like your cells to be?","Extras!:",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
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
