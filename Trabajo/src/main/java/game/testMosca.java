/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game_2.DOWN_KEY;
import static game.Game_2.SPACE_KEY;
import static game.Juego.CANVAS_WIDTH;
import static game.testAbeja.CANVAS_WIDTH;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author gabri
 */
public class testMosca extends JFrame implements KeyListener, ActionListener{
    
    // Game Panel and 
    public static final int CANVAS_WIDTH = 480;    
    int boxSize = 40;
    int row, col;
    GameCanvas canvas;
    JPanel canvasFrame;
    JLabel dataLabel;
    JButton prueba;
    
    // Timer
    Timer timer;
    int tick = 200;
    int lastKey = DOWN_KEY;
    int timerFly=0;
    int moveFly=2;
    
    // Game Variables
    ConcurrentLinkedQueue<IGameObject> gObjs = new ConcurrentLinkedQueue<>();
    ArrayList <Fly> moscas= new ArrayList<>();
    int timerBee=0;
    int moveBee=3;
    int screenCounter=0;
    
    public testMosca () throws Exception {
        
        canvas = new GameCanvas(CANVAS_WIDTH, boxSize);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_WIDTH));
        canvas.setBorder(BorderFactory.createLineBorder(Color.blue));
       
        gObjs.add(new Fly (new Position (4,4), 4,10));
        arrayFlies();
       
        canvasFrame = new JPanel();
        canvasFrame.setPreferredSize(new Dimension(CANVAS_WIDTH + 40, CANVAS_WIDTH + 40));
        canvasFrame.add(canvas);
        getContentPane().add(canvasFrame);

        
        setSize (CANVAS_WIDTH + 40, CANVAS_WIDTH + 80);
        setResizable(false);
        setVisible(true);         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
       
        addKeyListener(this);
        this.setFocusable(true);
        timer = new Timer(tick, this);
    }
    
    private ArrayList <Fly> arrayFlies (){
        for (IGameObject gObj:gObjs){
            if(gObj instanceof Fly){
                moscas.add((Fly) gObj);
            }   
        }
        return moscas;
    }
    
    private void movementFly(){
        int lastBox = (CANVAS_WIDTH/boxSize) - 1;
        
        Random masMenosUno = new Random();
        for (Fly mosca:moscas){
            mosca.getPosition().setX(mosca.getPosition().x +(1-masMenosUno.nextInt(3)));
            mosca.getPosition().setY(mosca.getPosition().y+(1-masMenosUno.nextInt(3)));
            if(mosca.getPosition().getX() < 0){
                mosca.position.x=0;
            }
            else if(mosca.getPosition().getX() > lastBox){
                mosca.position.x=lastBox;
            }
            
            if(mosca.getPosition().getY() < 0){
                mosca.position.y=0;
            }
            else if(mosca.getPosition().getY() > lastBox){
                mosca.position.y=lastBox;
            }
        }    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Moving moscas
        if(timerFly==moveFly){
            movementFly();
            timerFly=0;
        }else{
            timerFly++;
        }
        canvas.drawObjects(gObjs);
    }
    
        @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        lastKey = ke.getKeyCode(); 
        if (lastKey == SPACE_KEY){
            if (timer.isRunning()){
                    timer.stop();
                }
                else{
                    timer.start();
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

 
    
    public static void main (String args[]) throws Exception{
        testMosca tMosca= new testMosca();
    }

    

}
