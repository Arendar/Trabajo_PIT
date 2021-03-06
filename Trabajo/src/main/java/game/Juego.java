/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import common.FileUtilities;
import common.IToJsonObject;
import static common.IToJsonObject.TypeLabel;
import static game.Game_2.CANVAS_WIDTH;
import static game.Game_2.DOWN_KEY;
import static game.Game_2.LEFT_KEY;
import static game.Game_2.RIGTH_KEY;
import static game.Game_2.UP_KEY;
import static game.Game_3.CANVAS_WIDTH;
import static game.Game_3.SPACE_KEY;
import game.RidingHood_2;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import org.json.JSONArray;
import org.json.JSONObject;
import views.boxes.BoxesFactory;
import views.icons.IconsFactory;

/**
 *
 * @author gabri
 */
public class Juego extends JFrame implements KeyListener, ActionListener {
    
    public static final int UP_KEY    = 38;
    public static final int DOWN_KEY  = 40;
    public static final int RIGTH_KEY = 39;
    public static final int LEFT_KEY  = 37;
    public static final int SPACE_KEY = 32;
    int lastKey = DOWN_KEY;
    boolean enableKey= false;
    
    JButton nuevaPartida, escogerVista, cargarPartida, cuadrados, simbolos, volver, guardar;
    JPanel opciones, titulo1, vistas, imagenes;
    JLabel Titulo1, Titulo2, Uno, Dos;
    JFrame vista, game;
    JMenuBar barra;
    JMenu menuFile;
    JMenuItem itSave;
    
    public static final int CANVAS_WIDTH = 480;    
    int boxSize = 40;
    int row, col;
    GameCanvas canvas;
    JPanel canvasFrame;    
    JLabel dataLabel;
    
    // Timer
    Timer timer;
    int tick = 200;
    int iBlossom=1;
    int iBee ;
    int iFly ;
    
    //Timer falso enemigos
    int timerFly=0;
    int timerBee=0;
    int moveBee=4;
    int moveFly=2;
    
    // Game Variables
    ConcurrentLinkedQueue<IGameObject> gObjs = new ConcurrentLinkedQueue<>();
    RidingHood_2 ridingHood; 
    int screenCounter = 0;
    String numero;
    String numeroAbeja;
    String numeroMosca;
    BufferedWriter caracter;
    BufferedWriter moscaE;
    BufferedWriter abejaE;
    BufferedReader escribe;
    BufferedReader moscaC;
    BufferedReader abejaC;
    
    ArrayList <Bee> abejas= new ArrayList<>();
    ArrayList <Blossom> flores = new ArrayList<>();
    ArrayList <Fly> moscas = new ArrayList<>();
    
    String path1= "src/main/resources/games/game.txt";
    String path2="src/main/resources/games/mapNumber.txt";
    String path3 = "src/main/resources/games/moveBee.txt";
    String path4="src/main/resources/games/moveFly.txt";
    ConcurrentLinkedQueue <IGameObject> objects = new ConcurrentLinkedQueue<>();

    
    public Juego () throws Exception, IOException{
        
        super("Juego");
        
 
        ridingHood = new RidingHood_2(new Position(0,0), 0, 1);
        
        //Inicializar elementos
        gObjs.add(ridingHood);
        loadNewBoard(0);
        
        BoxesFactory cajas = new BoxesFactory();
        IconsFactory iconos = new IconsFactory();
        
        ////////////////////////////////////////////////////////////
        //Crear ventanas menus
        this.setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        
        
        vista = new JFrame("Selección de vistas");
        vista.setSize(400,400);
        vista.setLayout(new BorderLayout());
        
        Titulo1 = new JLabel("Caperucita roja: ");
        Titulo1.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
        Titulo1.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        Titulo2 = new JLabel ("El videojuego");
        Titulo1.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        Titulo2.setVerticalAlignment(javax.swing.SwingConstants.NORTH);
        Titulo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        nuevaPartida= new JButton("Nueva partida");
        nuevaPartida.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        System.out.println("Nueva partida");
                        screenCounter =1;
                        enableKey=true;
                        gObjs = new ConcurrentLinkedQueue<>();
                        flores= new ArrayList();
                        abejas= new ArrayList();
                        moscas = new ArrayList();
                        loadNewBoard(screenCounter);
                        ridingHood = new RidingHood_2(new Position(0,0), 0, 1);
                        gObjs.add(ridingHood);
                        timer = new Timer (tick, Juego.this);
                        moveBee=4;
                        moveFly=2;
                        dataLabel.setText(ridingHood.toString());
                        canvas.drawObjects(gObjs);
                        System.out.println(flores);
                        System.out.println(abejas);
                        System.out.println(moscas);
                        System.out.println(ridingHood);
                    }
                }
        );
        
        escogerVista= new JButton ("Escoger Vista");
        escogerVista.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        System.out.println("Escoger Vista");
                        Juego.this.setEnabled(false);
                        vista.setVisible(true);
                        
                    }
                }
        );
        
        cargarPartida= new JButton("Cargar Partida");
        cargarPartida.addActionListener( 
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                       
                            gObjs = new ConcurrentLinkedQueue<>();
                            flores = new ArrayList<>();
                            moscas = new ArrayList<>();
                            abejas= new ArrayList <>();
                            System.out.println("Cargar partida");
                            try {
                                escribe = new BufferedReader(new FileReader (path2));
                                moscaC = new BufferedReader(new FileReader (path4));
                                abejaC = new BufferedReader(new FileReader (path3));
                                screenCounter= Character.getNumericValue(escribe.read());
                                moveBee=Character.getNumericValue(abejaC.read());
                                moveFly= Character.getNumericValue(moscaC.read());
                                System.out.println("Screen counter: "+screenCounter+ ", moveBee: "+ moveBee+ ", moveFly: "+moveFly);
                            } catch (IOException ex) { 
                                ex.printStackTrace();
                            }
                            JSONArray jArray = FileUtilities.readJsonsFromFile(path1);
                            if (jArray != null){
                                
                                System.out.println(gObjs);
                                for (int i = 0; i < jArray.length(); i++){
                                        JSONObject jObj = jArray.getJSONObject(i);
                                        System.out.println(jObj);
                                        IGameObject juego =GameObjectsJSONFactory.getGameObject(jObj);    
                                        gObjs.add(juego);
                                }
                                for(IGameObject object:gObjs){
                                    if(object instanceof Blossom){
                                        flores.add((Blossom)object);
                                    }else
                                    if(object instanceof Bee){
                                        abejas.add((Bee)object);
                                        
                                    }else
                                    if(object instanceof Fly){
                                        moscas.add((Fly)object);
                                    }
                                }
                                timer = new Timer (tick, Juego.this);
                                enableKey=true;
                                printGameItems();
                                dataLabel.setText(ridingHood.toString());
                                canvas.drawObjects(gObjs);
                                System.out.println(flores);
                                System.out.println(abejas);
                                System.out.println(moscas);
                                System.out.println(ridingHood);
                            }  
                    }
                }
        );
        
        cuadrados= new JButton ("Cuadrados");
        cuadrados.addActionListener( 
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        System.out.println("Se ha escogido cuadrados");
                        canvas.setViewsFamily(cajas);
                    }
                }
        );
        
        simbolos=new JButton ("Símbolos");
        simbolos.addActionListener( 
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        System.out.println("Se ha escogido símbolos");
                        canvas.setViewsFamily(iconos);
                    }
                }
        );
        
        volver = new JButton ("Volver");
        volver.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        System.out.println("Volver");
                        vista.setVisible(false);
                        Juego.this.setEnabled(true);
                    }
                }
        );
        
        opciones =new JPanel ();
        titulo1 = new JPanel();
        vistas = new JPanel();
        imagenes = new JPanel();
        
        opciones.setLayout(new BorderLayout());
        opciones.add(nuevaPartida, BorderLayout.NORTH);
        opciones.add(escogerVista, BorderLayout.CENTER);
        opciones.add(cargarPartida, BorderLayout.SOUTH);
        
        titulo1.setLayout(new BorderLayout());
        titulo1.add(Titulo1, BorderLayout.NORTH);
        titulo1.add(Titulo2, BorderLayout.CENTER);
        
        vistas.add(cuadrados, BorderLayout.NORTH);
        vistas.add(simbolos, BorderLayout.CENTER);
        vistas.add(volver,BorderLayout.SOUTH);

        
        vista.add(vistas, BorderLayout.CENTER);

        
        this.add(opciones, BorderLayout.SOUTH);
        this.add(titulo1, BorderLayout.CENTER);
        
        setSize (400, 400);
        setVisible(true);
        
        //////////////////////////////////////////////////////////////////
        //Crear ventana juego
        game= new JFrame();
        
        barra = new JMenuBar();
        menuFile = new JMenu("File");
        itSave = new JMenuItem("Save");
        itSave.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        if(gObjs != null){

                            JSONObject jObjs [] = new JSONObject[gObjs.size()];
                            int i=0;
                            for(IGameObject object:gObjs){
                                    if(object instanceof RidingHood_2){
                                        jObjs[i] = ((RidingHood_2) object).toJSONObject();
                                        i++;
                                    }else
                                    if(object instanceof Blossom){
                                        jObjs[i] = ((Blossom) object).toJSONObject();
                                        i++;
                                    }else
                                    if(object instanceof Bee){
                                        jObjs[i] =((Bee) object).toJSONObject();
                                        i++;
                                    }else
                                    if(object instanceof Fly){
                                        jObjs[i] =((Fly) object).toJSONObject();
                                        i++;
                                }
                            }
                            FileUtilities.writeJsonsToFile(jObjs, path1);
                        }                     
                        
                        numero=Integer.toString(screenCounter);
                        numeroAbeja= Integer.toString(moveBee);
                        numeroMosca = Integer.toString(moveFly);
                        try {
                            caracter= new BufferedWriter(new FileWriter(path2));
                            abejaE= new BufferedWriter (new FileWriter (path3));
                            moscaE= new BufferedWriter (new FileWriter (path4));
                            caracter.write(numero);
                            abejaE.write(numeroAbeja);
                            moscaE.write(numeroMosca);
                            caracter.close();
                            abejaE.close();
                            moscaE.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        System.out.println("Partida guardada");
                    }
                }
        );

        
        menuFile.add(itSave);
        barra.add(menuFile);
        barra.setBorder(BorderFactory.createLineBorder(Color.blue));
        game.setJMenuBar(barra);
        
        
        dataLabel= new JLabel(ridingHood.toString());
        dataLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)); 
        dataLabel.setPreferredSize(new Dimension(120,40));
        dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        canvas = new GameCanvas(CANVAS_WIDTH, boxSize);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_WIDTH));
        canvas.setBorder(BorderFactory.createLineBorder(Color.blue));
        
        canvasFrame = new JPanel();
        canvasFrame.setPreferredSize(new Dimension(CANVAS_WIDTH + 40, CANVAS_WIDTH + 40));
        canvasFrame.add(canvas);
        game.getContentPane().add(canvasFrame);
        game.getContentPane().add(dataLabel, BorderLayout.SOUTH);
        
        game.setSize (CANVAS_WIDTH + 40, CANVAS_WIDTH + 120);
        game.setResizable(false);
        game.setVisible(true);         
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        
        game.addKeyListener(this);
        game.setFocusable(true);

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(enableKey == true){
            lastKey = ke.getKeyCode(); 
            if (lastKey == SPACE_KEY){
                if (timer.isRunning()){
                        timer.stop();
                        menuFile.setEnabled(true);
                        nuevaPartida.setEnabled(true);
                        cargarPartida.setEnabled(true);
                        escogerVista.setEnabled(true);
                    }
                    else{
                        timer.start();
                        menuFile.setEnabled(false);
                        nuevaPartida.setEnabled(false);
                        cargarPartida.setEnabled(false);
                        escogerVista.setEnabled(false);
                    }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Actions on Caperucita
        setDirection(lastKey);
        
        // Moving Caperucita
        ridingHood.moveToNextPosition();
        
        // Check if Caperucita is in board limits
        setInLimits();
        
        //Moving abejas
        if(!abejas.isEmpty()){
            if(timerBee==moveBee){
                movementBee();
                timerBee=0;
            }else{
                timerBee++;
            }
        }else{
            timerBee=0;
        }
        
        
        //Moving moscas
        if(!moscas.isEmpty()){
            if(timerFly==moveFly){
                movementFly();
                timerFly=0;
            }else{
                timerFly++;
            }
        }else{
            timerFly=0;
        }
        //Comprobación posición abejas sobre las flores y los bordes.
        beeBorder();
        
        //Comprueba si hay otro objeto sobre caperucita.
        processCell();
        

        
        // Logic to change to a new screen.
        if (flores.isEmpty()){
            screenCounter++;
            ridingHood.setLifes(ridingHood.lifes+1);
            flores = new ArrayList <>();
            abejas = new ArrayList <>();
            moscas = new ArrayList <>();
            if(screenCounter > 9){
                screenCounter=7;
            }
            int valor=ridingHood.getValue();
            int vidas = ridingHood.getLifes();
            loadNewBoard(screenCounter);

            ridingHood = new RidingHood_2((new Position(0,0)), valor, vidas);
            gObjs.add(ridingHood);
            System.out.println(flores);
            System.out.println(abejas);
            System.out.println(moscas);
            System.out.println(ridingHood);

        }
        
        // Updating graphics and labels
        dataLabel.setText(ridingHood.toString());
        canvas.drawObjects(gObjs); 
        
    }
    
    private ArrayList <Bee> arrayBees (){
        for (IGameObject gObj:gObjs){
            if(gObj instanceof Bee){
                abejas.add((Bee) gObj);
                System.out.println(gObj.toString());
            }   
        }
        return abejas;
    }
    
    private ArrayList <Blossom> arrayBlossoms (){
        for (IGameObject gObj:gObjs){
            if(gObj instanceof Blossom){
                flores.add((Blossom) gObj);
                System.out.println(gObj.toString());
            }   
        }
        return flores;
    }
    
    private ArrayList <Fly> arrayFlies (){
        for (IGameObject gObj:gObjs){
            if(gObj instanceof Fly){
                moscas.add((Fly) gObj);
                System.out.println(gObj.toString());
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
                int v = ridingHood.getValue() - gObj.getValue();
                ridingHood.setValue(v);
                gObjs.remove(gObj);
                moscas.remove((Fly) gObj);
                System.out.println("Caperucita se ha tropezado con una mosca.");
            }
            else if(gObj instanceof Bee && rhPos.isEqual(gObj.getPosition())){
                int v = ridingHood.getValue() - gObj.getValue();
                ridingHood.setValue(v);
                System.out.println("Caperucita se ha tropezado con una abeja.");
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
                System.out.println("Abeja eliminada en: "+gObj.getPosition().toString());
                gObjs.remove(gObj);
                abejas.remove((Bee) gObj);
                
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
            mosca.getPosition().setX(mosca.getPosition().x +(1-masMenosUno.nextInt(3)));
            mosca.getPosition().setY(mosca.getPosition().y+(1-masMenosUno.nextInt(3)));
            System.out.println("Posición mosca: "+mosca.position);
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
            Blossom cerca= (Blossom)AbstractGameObject.getClosest(abeja, flores);
            approachTo(abeja.getPosition(), cerca.getPosition());
            System.out.println("Posición abeja: "+abeja.position);
            if( AbstractGameObject.distance(abeja.getPosition(), cerca.getPosition())==0)
            {
                System.out.println(cerca.toString());
                gObjs.remove(cerca);
                flores.remove(cerca);
                if(!flores.isEmpty()){
                    cerca = (Blossom) AbstractGameObject.getClosest(abeja, flores);
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
    
    private void loadNewBoard(int counter){
        switch(counter){
            
            case 0:
              break;
            
            case 1: 
              JSONArray jArray = FileUtilities.readJsonsFromFile("src/main/resources/maps/mapa1.txt");
              if (jArray != null){
                  for (int i = 0; i < jArray.length(); i++){
                            JSONObject jObj = jArray.getJSONObject(i);
                            gObjs.add(GameObjectsJSONFactory.getGameObject(jObj));
                  }
                  canvas.drawObjects(gObjs);
              }
              arrayBees();
              arrayFlies();
              arrayBlossoms();
              break;
            case 2:
              JSONArray jArray1 = FileUtilities.readJsonsFromFile("src/main/resources/maps/mapa2.txt");
              if (jArray1 != null){
                  gObjs = new ConcurrentLinkedQueue<>();
                  for (int i = 0; i < jArray1.length(); i++){
                            JSONObject jObj = jArray1.getJSONObject(i);
                            gObjs.add(GameObjectsJSONFactory.getGameObject(jObj));
                  }
                  canvas.drawObjects(gObjs);
              }
              arrayBees();
              arrayFlies();
              arrayBlossoms();
              break;
            case 3:
              JSONArray jArray2 = FileUtilities.readJsonsFromFile("src/main/resources/maps/mapa3.txt");
              if (jArray2 != null){
                  gObjs = new ConcurrentLinkedQueue<>();
                  for (int i = 0; i < jArray2.length(); i++){
                            JSONObject jObj = jArray2.getJSONObject(i);
                            gObjs.add(GameObjectsJSONFactory.getGameObject(jObj));
                  }
                  canvas.drawObjects(gObjs);
              }
              arrayBees();
              arrayFlies();
              arrayBlossoms();
              break;
              
            case 4: 
                JSONArray jArray3 = FileUtilities.readJsonsFromFile("src/main/resources/maps/mapa4.txt");
                if (jArray3 != null){
                  gObjs = new ConcurrentLinkedQueue<>();
                  for (int i = 0; i < jArray3.length(); i++){
                            JSONObject jObj = jArray3.getJSONObject(i);
                            gObjs.add(GameObjectsJSONFactory.getGameObject(jObj));
                  }
                  canvas.drawObjects(gObjs);
              }
                arrayBees();
                arrayFlies();
                arrayBlossoms();
                moveBee=3;
                moveFly=1;
                break;
              
            case 5:
                JSONArray jArray4 = FileUtilities.readJsonsFromFile("src/main/resources/maps/mapa5.txt");
                if (jArray4 != null){
                    gObjs = new ConcurrentLinkedQueue<>();
                    for (int i = 0; i < jArray4.length(); i++){
                        JSONObject jObj = jArray4.getJSONObject(i);
                        gObjs.add(GameObjectsJSONFactory.getGameObject(jObj));
                    }
                canvas.drawObjects(gObjs);
                }
                moveBee=3;
                moveFly=1;
                arrayBees();
                arrayFlies();
                arrayBlossoms();
                break;
              
            case 6:
                JSONArray jArray5 = FileUtilities.readJsonsFromFile("src/main/resources/maps/mapa6.txt");
                if (jArray5 != null){
                    gObjs = new ConcurrentLinkedQueue<>();
                    for (int i = 0; i < jArray5.length(); i++){
                        JSONObject jObj = jArray5.getJSONObject(i);
                        gObjs.add(GameObjectsJSONFactory.getGameObject(jObj));
                    }
                canvas.drawObjects(gObjs);
                }
                moveBee=2;
                moveFly=0;                
                arrayBees();
                arrayFlies();
                arrayBlossoms();
                break;
              
            case 7:
                gObjs = new ConcurrentLinkedQueue<>();
                gObjs.add(new Fly ( getRandomPosition(10,10),9,1));
                gObjs.add(new Fly ( getRandomPosition(10,10),9,1));
                gObjs.add(new Fly ( getRandomPosition(10,10),9,1));
                gObjs.add(new Fly ( getRandomPosition(10,10),9,1));
                gObjs.add(new Fly ( getRandomPosition(10,10),9,1));
                
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                
                
                moveFly=0;
                moveBee=1;
                arrayBees();
                arrayFlies();
                arrayBlossoms();
                break;
              
            case 8:
                gObjs = new ConcurrentLinkedQueue<>();
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                
                gObjs.add(new Bee (getRandomPosition(10,10), 12,1));
                gObjs.add(new Bee (getRandomPosition(10,10), 12,1));
                gObjs.add(new Bee (getRandomPosition(10,10), 12,1));
                
                moveFly=0;
                moveBee=1;
                arrayBees();
                arrayFlies();
                arrayBlossoms();
                break;
            
            case 9:
                gObjs = new ConcurrentLinkedQueue<>();
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 4, 10)); 
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                gObjs.add(new Blossom(getRandomPosition(10,10), 10, 10));
                
                gObjs.add(new Bee (getRandomPosition(10,10), 15,1));
                gObjs.add(new Bee (getRandomPosition(10,10), 15,1));
                gObjs.add(new Bee (getRandomPosition(10,10), 15,1));
                
                gObjs.add(new Fly ( getRandomPosition(10,10),12,1));                
                gObjs.add(new Fly ( getRandomPosition(10,10),12,1));
                gObjs.add(new Fly ( getRandomPosition(10,10),12,1));
                gObjs.add(new Fly ( getRandomPosition(10,10),12,1));
                gObjs.add(new Fly ( getRandomPosition(10,10),12,1));
                gObjs.add(new Fly ( getRandomPosition(10,10),12,1));
                
                moveFly=0;
                moveBee=1;
                arrayBees();
                arrayFlies();
                arrayBlossoms();
                break;
        }        
    }
    
    public Position getRandomPosition(int mX, int mY){
        int x = (int)(mX * Math.random());
        int y = (int)(mY * Math.random());
        return new Position(x, y);
    }
    
    
    private void printGameItems(){
        System.out.println("Objects Added to Game are: ");
        for (Iterator<IGameObject> it = gObjs.iterator(); it.hasNext();) {
            IGameObject obj = it.next();
            System.out.println( ( (IToJsonObject) obj).toJSONObject());
        }
    }
    
}
