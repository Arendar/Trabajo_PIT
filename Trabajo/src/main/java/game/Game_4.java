/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import common.FileUtilities;
import static common.IToJsonObject.TypeLabel;
import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author juanangel
 */
public class Game_4 extends JFrame implements KeyListener, ActionListener {

    // KeyBoard
    public static final int UP_KEY    = 38;
    public static final int DOWN_KEY  = 40;
    public static final int RIGTH_KEY = 39;
    public static final int LEFT_KEY  = 37;
    public static final int SPACE_KEY = 32;
    int lastKey = DOWN_KEY;
    
    // Game Panel and 
    public static final int CANVAS_WIDTH = 480;    
    int boxSize = 40;
    int row, col;
    GameCanvas canvas;
    JPanel canvasFrame;
    JLabel dataLabel;
    
    // Timer
    Timer timer;
    int tick = 200;
    
    // Game Variables
    ConcurrentLinkedQueue<IGameObject> gObjs = new ConcurrentLinkedQueue<>();
    RidingHood_2 ridingHood = new RidingHood_2(new Position(0,0), 1, 1);
    int screenCounter = 0;
    
    ArrayList <Bee> abejas= new ArrayList<>();
    ArrayList <Blossom> flores = new ArrayList<>();
    ArrayList <Fly> moscas = new ArrayList<>();

    
    public Game_4() throws Exception{

       super("Game_2");
       
       // Game Initializations.
       gObjs.add(ridingHood);
       loadNewBoard(0);
  
       // Window initializations.
       dataLabel = new JLabel(ridingHood.toString());
       dataLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)); 
       dataLabel.setPreferredSize(new Dimension(120,40));
       dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
       canvas = new GameCanvas(CANVAS_WIDTH, boxSize);
       canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_WIDTH));
       canvas.setBorder(BorderFactory.createLineBorder(Color.blue));
       
       canvasFrame = new JPanel();
       canvasFrame.setPreferredSize(new Dimension(CANVAS_WIDTH + 40, CANVAS_WIDTH + 40));
       canvasFrame.add(canvas);
       getContentPane().add(canvasFrame);
       getContentPane().add(dataLabel, BorderLayout.SOUTH);
       
       setSize (CANVAS_WIDTH + 40, CANVAS_WIDTH + 80);
       setResizable(false);
       setVisible(true);         
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
       
       addKeyListener(this);
       this.setFocusable(true);
       timer = new Timer(tick, this);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    // Version 1
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
    public void keyReleased(KeyEvent ke) {
    }

    /**
     * Se invoca en cada tick de reloj
     * @param ae 
     */  
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        
       
        // Actions on Caperucita
        setDirection(lastKey);
        
        // Moving Caperucita
        ridingHood.moveToNextPosition();
        
        processCell();
        
        // Check if Caperucita is in board limits
        setInLimits();
        
        // Logic to change to a new screen.
        if (flores.isEmpty()==true){
            screenCounter++;
            ridingHood.incLifes(1);
            loadNewBoard(screenCounter);
        }
        
        // Updating graphics and labels
        dataLabel.setText(ridingHood.toString());
        canvas.drawObjects(gObjs);
    }

    /*
    Procesa la celda en la que se encuentra caperucita.
    Si Caperucita está sobre un blossom añade su valor al de Caperucita
    y lo elimina del tablero.
    Si Caperucita está sobre una mosca añade su valor (negativo) al de 
    Caperucita y lo elimina del tablero.
    Si Caperucita está sobre una abeja añade su valor (negativo) al de 
    Caperucita y sigue en el tablero
    Devuelve el número de blossoms que hay en el tablero.
    */

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
    
    private ArrayList <Fly> arrayFlies (){
        for (IGameObject gObj:gObjs){
            if(gObj instanceof Fly){
                moscas.add((Fly) gObj);
            }   
        }
        return moscas;
    }
    
    private void processCell(){
        Position rhPos = ridingHood.getPosition();
        for (IGameObject gObj: gObjs){
            if(gObj instanceof Blossom && rhPos.isEqual(gObj.getPosition())){
                int v = ridingHood.getValue() + gObj.getValue();
                ridingHood.setValue(v);
                gObjs.remove(gObj);
                flores.remove((Blossom) gObj);
            }
            else if(gObj instanceof Fly && rhPos.isEqual(gObj.getPosition())){
                int v = ridingHood.getValue() + gObj.getValue();
                ridingHood.setValue(v);
                gObjs.remove(gObj);
                moscas.remove((Fly) gObj);
            }
            else if(gObj instanceof Bee && rhPos.isEqual(gObj.getPosition())){
                int v = ridingHood.getValue() + gObj.getValue();
                ridingHood.setValue(v);
            }
        }
    }
    
    /*
    Comprueba si hay alguna abeja por los bordes del tablero. 
    Si la hay se elimina del juego.
    Para determinar si ha pasado el borde calculamos lastBox
    */
    private void beeBorder(){
        int lastBox = (CANVAS_WIDTH/boxSize) - 1;
        for (IGameObject gObj:gObjs){
            if(gObj instanceof Bee && (gObj.getPosition().x<0 
                    || gObj.getPosition().y<0||
                    gObj.getPosition().x> lastBox
                    ||gObj.getPosition().y > lastBox)){
                gObjs.remove(gObj);
                abejas.remove((Bee) gObj);
            }
        }
    }
    
    private void beeFlower(){
        
        for (Bee abeja:abejas){
            for (IGameObject gObj:gObjs){
                if(gObj instanceof Blossom && 
                        abeja.getPosition().equals(gObj.getPosition())){
                    gObjs.remove(gObj);
                    flores.remove((Blossom)gObj);
                }
            }
        }
    }
    
    /*
    Fija la dirección de caperucita.
    Caperucita se moverá en esa dirección cuando se invoque
    su método moveToNextPosition.
    */    
    private void setDirection(int lastKey){
        switch (lastKey) {
            case UP_KEY:  
                ridingHood.moveUp();
                break;
            case DOWN_KEY:
                ridingHood.moveDown();                    
                break;
            case RIGTH_KEY:
                ridingHood.moveRigth();
                break;
            case LEFT_KEY:
                ridingHood.moveLeft();
                break; 
        }
    }
    
    private void movementFly(){
        int lastBox = (CANVAS_WIDTH/boxSize) - 1;
        
        Random masMenosUno = new Random();
        for (Fly mosca:moscas){
            mosca.getPosition().setX(mosca.getPosition().x +(1-masMenosUno.nextInt(2)));
            mosca.getPosition().setY(mosca.getPosition().y+(1-masMenosUno.nextInt(2)));
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
    
    private void movementBee (){
        for (Bee abeja: abejas){
            if(flores.isEmpty()== false){
                Blossom cerca= (Blossom)AbstractGameObject.getClosest(abeja, flores);
                approachTo(abeja.getPosition(), cerca.getPosition());
            }
        }
    }
    
    /*
    Este método permite a las abejas moverse hacía el objeto.
    */
    
    private void approachTo(Position p1, Position p2){
        if (p1.x != p2.x){
            p1.x = p1.x > p2.x? p1.x-1:p2.x+1;
        }
        if (p1.y != p2.y){
            p1.y = p1.y > p2.y? p1.y-1:p1.y+1;
        }
    }
    
    /*
    Comprueba que Caperucita no se sale del tablero.
    En caso contrario corrige su posición
    */
    private void setInLimits(){
        
        int lastBox = (CANVAS_WIDTH/boxSize) - 1;
        
        if (ridingHood.getPosition().getX() < 0){
            ridingHood.position.x = 0;
        }
        else if ( ridingHood.getPosition().getX() > lastBox ){
            ridingHood.position.x = lastBox;
        }
        
        if (ridingHood.getPosition().getY() < 0){
            ridingHood.position.y = 0;
        }
        else if (ridingHood.getPosition().getY() > lastBox){
            ridingHood.position.y = lastBox;
        } 
    }
    
    /*
    Carga un nuevo tablero
    */
    private void loadNewBoard(int counter){
        switch(counter){
            case 0: 
              gObjs.add(new Blossom(new Position(2,2), 10, 10));
              gObjs.add(new Blossom(new Position(2,8), 4, 10));
              gObjs.add(new Blossom(new Position(8,8), 10, 10));
              gObjs.add(new Blossom(new Position(8,2), 4, 10));
              arrayBees();
              arrayFlies();
              arrayBlossoms(); 
              break;
            case 1:
              gObjs.add(new Blossom(new Position(1,8), 10, 10));
              gObjs.add(new Blossom(new Position(2,7), 4, 10));
              gObjs.add(new Blossom(new Position(3,6), 10, 10));
              gObjs.add(new Blossom(new Position(4,5), 4, 10));
              gObjs.add(new Blossom(new Position(5,4), 10, 10));
              gObjs.add(new Blossom(new Position(6,3), 4, 10));
              gObjs.add(new Blossom(new Position(7,2), 10, 10));
              gObjs.add(new Blossom(new Position(8,1), 4, 10));
              arrayBees();
              arrayFlies();
              arrayBlossoms();
              break;
            default:
              gObjs.add(new Blossom(new Position(2,2), 10, 10));
              gObjs.add(new Blossom(new Position(2,8), 4, 10));
              gObjs.add(new Blossom(new Position(8,8), 10, 10));
              gObjs.add(new Blossom(new Position(8,2), 4, 10)); 
              arrayBees();
              arrayFlies();
              arrayBlossoms();
        }        
    }
    
    public static void main(String [] args) throws Exception{
       Game_4 gui = new Game_4();
    }
}
