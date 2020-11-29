/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game_2.CANVAS_WIDTH;
import static game.Game_2.DOWN_KEY;
import static game.Game_2.LEFT_KEY;
import static game.Game_2.RIGTH_KEY;
import static game.Game_2.UP_KEY;
import static game.Game_3.CANVAS_WIDTH;
import static game.Game_3.SPACE_KEY;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    
    // Game Variables
    ConcurrentLinkedQueue<IGameObject> gObjs = new ConcurrentLinkedQueue<>();
    RidingHood_2 ridingHood = new RidingHood_2(new Position(0,0), 0, 1);
    int screenCounter = 0;
    
    public Juego () throws Exception{
        
        super("Game_1");
        

        
        //Inicializar elementos
        gObjs.add(ridingHood);
        
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
        nuevaPartida.addActionListener(this);
        
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
                    public void actionPerformed(ActionEvent ae){
                        System.out.println("Cargar partida");
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
        timer = new Timer (tick, this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        
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
    
    
}
