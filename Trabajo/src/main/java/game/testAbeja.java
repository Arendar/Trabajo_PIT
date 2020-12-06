/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import common.FileUtilities;
import static common.IToJsonObject.TypeLabel;
import static game.Game_2.CANVAS_WIDTH;
import static game.Game_2.DOWN_KEY;
import static game.Game_2.SPACE_KEY;
import static game.Juego.CANVAS_WIDTH;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author gabri
 */
public class testAbeja extends JFrame implements KeyListener, ActionListener {
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
    
    // Game Variables
    ConcurrentLinkedQueue<IGameObject> gObjs = new ConcurrentLinkedQueue<>();
    ArrayList <Bee> abejas= new ArrayList<>();
    ArrayList <Blossom> flores = new ArrayList<>();
    Blossom cerca;
    int timerBee=0;
    int moveBee=4;
    int screenCounter=0;

    public testAbeja () throws Exception{
      
        super("abeja");
        loadNewBoard(0);
        
        canvas = new GameCanvas(CANVAS_WIDTH, boxSize);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_WIDTH));
        canvas.setBorder(BorderFactory.createLineBorder(Color.blue));
       
       
       
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
    
    private ArrayList <Bee> arrayBees (){
        for (IGameObject gObj:gObjs){
            if(gObj instanceof Bee){
                abejas.add((Bee) gObj);
            }   
        }
        return abejas;
    }
    
    private ArrayList <Blossom> arrayBlossoms (){
        for (IGameObject gObj:gObjs){
            if(gObj instanceof Blossom){
                flores.add((Blossom) gObj);
            }   
        }
        return flores;
    }
    
    private void beeBorder(){
        int lastBox = (CANVAS_WIDTH/boxSize) - 1;
        for (IGameObject gObj:gObjs){
            if(gObj instanceof Bee && (gObj.getPosition().x<0 
                    || gObj.getPosition().y<0||
                    gObj.getPosition().x> lastBox
                    ||gObj.getPosition().y > lastBox)){
                gObjs.remove(gObj);
                abejas.remove((Bee) gObj);
                System.out.println(gObj.toString());
            }
        }
    }
    
   
    
    private void movementBee (){
        for (Bee abeja: abejas){
            System.out.println(flores.size());
            Blossom cerca= (Blossom)AbstractGameObject.getClosest(abeja, flores);
            approachTo(abeja.getPosition(), cerca.getPosition());
            System.out.println(abeja.toString());
            System.out.println(cerca.toString());
            if( AbstractGameObject.distance(abeja.getPosition(), cerca.getPosition())==0)
            {
                System.out.println(cerca.toString());
                gObjs.remove(cerca);
                flores.remove(cerca);
                System.out.println("Flor borrada.");
                System.out.println(flores.size());
                if(!flores.isEmpty()){
                    cerca = (Blossom) AbstractGameObject.getClosest(abeja, flores);
                    System.out.println(cerca.toString());
                }
            }
  
        }
    }
    
    /*
    Este método permite a las abejas moverse hacía el objeto.
    */
    
    private void approachTo(Position p1, Position p2){
        if (p1.x != p2.x){
            if(p1.x > p2.x){
             p1.x= p1.x-1;
            }else
            if(p1.x < p2.x){
               p1.x= p1.x+1;
            }
        }
        if(p1.y != p2.y){
            if(p1.y > p2.y){
               p1.y= p1.y-1;
            }else
            if(p1.y < p2.y){
               p1.y= p1.y+1;
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(timerBee==moveBee){
            movementBee();
            timerBee=0;
        }else{
            timerBee++;
        }
        //Comprobación posición abejas sobre las flores y los bordes.
        beeBorder();
        if (flores.isEmpty()){
            screenCounter++;
            if(screenCounter > 9){
                screenCounter=7;
            }
            gObjs= new ConcurrentLinkedQueue <>();
            abejas = new ArrayList<>();
            flores = new ArrayList<>();
            
            loadNewBoard(screenCounter);
        }
        canvas.drawObjects(gObjs);        
    }
    
    private void loadNewBoard(int counter){
        switch(counter){
            case 0: 
              gObjs.add(new Bee (new Position (4,4), 4,10));
              
              gObjs.add(new Blossom(new Position(2,2), 10, 10));
              gObjs.add(new Blossom(new Position(2,8), 4, 10));
              gObjs.add(new Blossom(new Position(8,8), 10, 10));
              gObjs.add(new Blossom(new Position(8,2), 4, 10));
              arrayBees();
              arrayBlossoms();
              System.out.println(gObjs.toString());
              break;
            case 1:
              gObjs.add(new Bee (new Position (4,4), 4,10));
              gObjs.add(new Blossom(new Position(2,2), 10, 10));
              gObjs.add(new Blossom(new Position(2,8), 4, 10));
              gObjs.add(new Blossom(new Position(8,8), 10, 10));
              gObjs.add(new Blossom(new Position(8,2), 4, 10));
              arrayBees();
              arrayBlossoms();
              System.out.println(gObjs.toString());
              break;
            default:
              gObjs.add(new Bee (new Position (4,4), 4,10));
              gObjs.add(new Blossom(new Position(2,2), 10, 10));
              gObjs.add(new Blossom(new Position(2,8), 4, 10));
              gObjs.add(new Blossom(new Position(8,8), 10, 10));
              gObjs.add(new Blossom(new Position(8,2), 4, 10));
              arrayBees();
              arrayBlossoms();
              System.out.println(gObjs.toString());
        }        
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
        testAbeja tBee= new testAbeja();
    }


}
