/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import common.IToJsonObject;
import guis.*;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import views.IAWTGameView;
import views.VNumberedBox;
import views.VNumberedCircle;

/**
 *
 * @author gabri
 */
public class GameEditor_2  extends JFrame implements KeyListener {
    
    public static final int UP_KEY    = 38;
    public static final int DOWN_KEY  = 40;
    public static final int RIGTH_KEY = 39;
    public static final int LEFT_KEY  = 37;
    
    public static final int CANVAS_WIDTH = 480;
    
    int boxSize = 40;
    int row, col;
    
  
    Canvas canvas;
    JPanel canvasFrame;
    JLabel positionLabel;
    JButton blossom;
    JButton bee;
    JButton fly;
    IGameObject [] elementos= new IGameObject [10]; 
    int numeroElementos =0;

    public GameEditor_2() throws Exception{

       super("Game Editor v1"); //Cambiar a game editor v1
           
       //construyeBotones(); No hay que hacer un método para los botones
       blossom = new JButton("blossom");
       blossom.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                    System.out.println("Clover selected");//No conseguiste implementar la función.
                    if (numeroElementos < elementos.length){
                        elementos[numeroElementos] = new Blossom(new Position(col, row), 10, 1);
                        numeroElementos++;
                        printGameItems();//Aún estabas ocupado intentando entender porqué no aparecían los botones en la ventana.
                        canvas.drawGameItems(elementos); //Imprimir elementos del juego
                    }
                    else {
                        System.out.println("Clover can not be added: too many objects");
                    }
                    requestFocusInWindow();
                }
            }
       );
       
       bee = new JButton("bee");
       bee.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                    System.out.println("Bee selected");//No conseguiste implementar la función.
                    if (numeroElementos < elementos.length){
                        elementos[numeroElementos] = new Bee(new Position(col, row), 5, 1);
                        numeroElementos++;
                        printGameItems();//Aún estabas ocupado intentando entender porqué no aparecían los botones en la ventana.
                        canvas.drawGameItems(elementos); //Imprimir elementos del juego
                    }
                    else {
                        System.out.println("Bee can not be added: too many objects");
                    }
                    requestFocusInWindow();
                }
            }
       );
       
       fly = new JButton ("fly");
       fly.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                    System.out.println("Fly selected");//No conseguiste implementar la función.
                    if (numeroElementos < elementos.length){
                        elementos[numeroElementos] = new Fly(new Position(col, row), 5, 1);
                        numeroElementos++;
                        printGameItems();//Aún estabas ocupado intentando entender porqué no aparecían los botones en la ventana.
                        canvas.drawGameItems(elementos); //Imprimir elementos del juego
                    }
                    else {
                        System.out.println("Fly can not be added: too many objects");
                    }
                    requestFocusInWindow();
                }
            }
       );
       
       
       positionLabel = new JLabel("[" + col + ", " + row + "]");
       positionLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)); 
       positionLabel.setPreferredSize(new Dimension(120,40));
       positionLabel.setHorizontalAlignment(SwingConstants.CENTER);
       
       //Si quieres poner los botones primero debes poner otro JPanel para que sean visibles
       JPanel pnControls = new JPanel(); // nos sirve para integrar los botones y la cuadrícula
       pnControls.setLayout(new GridLayout(2,1));
       JPanel pnButtons = new JPanel();
       pnButtons.add(blossom);
       pnButtons.add(bee);
       pnButtons.add(fly);
       
       pnControls.add(positionLabel);
       pnControls.add(pnButtons);
       
       canvas = new Canvas(CANVAS_WIDTH, boxSize);
       canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_WIDTH));
       canvas.setBorder(BorderFactory.createLineBorder(Color.blue));
       
       canvasFrame = new JPanel();
       canvasFrame.setPreferredSize(new Dimension(CANVAS_WIDTH + 40, CANVAS_WIDTH + 40));
       canvasFrame.add(canvas);
       getContentPane().add(canvasFrame);
       getContentPane().add(positionLabel, BorderLayout.SOUTH);
       
       setSize (CANVAS_WIDTH + 40, CANVAS_WIDTH + 80);
       setResizable(false);
       setVisible(true);         
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
       
       addKeyListener(this);
       System.out.println(this.getFocusableWindowState());
       this.setFocusable(true);
       
       
       
        
    }

    private void printGameItems(){
        System.out.println("Objects Added to Game are: ");
        for (int i = 0; i < numeroElementos; i++){
            System.out.println( ( (IToJsonObject) elementos[i]).toJSONObject());
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    // Version 1
    @Override
    public void keyPressed(KeyEvent ke) {
        int tecla = ke.getKeyCode();
        System.out.println("code --> " + tecla);
        switch (tecla) {
            case UP_KEY:  
                System.out.println("UP_KEY");
                row--;
                break;
            case DOWN_KEY:
                System.out.println("DOWN_KEY");
                row++;                    
                break;
            case RIGTH_KEY:
                System.out.println("RIGTH_KEY");
                col++;
                break;
            case LEFT_KEY:
                System.out.println("LEFT_KEY");
                col--;
                break; 
        }
        positionLabel.setText("[" + col + ", " + row + "]");
        setInLimits();
        canvas.setSquareCoordinates(col, row);  
    }
    
    private void setInLimits(){
        
        int lastBox = (CANVAS_WIDTH/boxSize) - 1;
        
        if (col < 0){
            col = 0;
        }
        else if ( col > lastBox ){
            col = lastBox;
        }
        
        if (row < 0){
            row = 0;
        }
        else if ( row > lastBox){
            row = lastBox;
        } 
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    public static void main(String [] args) throws Exception{
       GameEditor_2 gui = new GameEditor_2();
    }
    
    class Canvas extends JPanel {
        
        int size, boxSize;
        int pX, pY;
        IGameObject objects []; //Necesario para añadir objetos
        
        public Canvas(int size, int boxSize){
            this.size = size;
            this.boxSize = boxSize;
        }
        
        public void setSquareCoordinates(int x, int y){
            pX = x;
            pY = y;
            repaint();
        }
        
        public void paintComponent(Graphics g){ //Expandir paintComponent para objetos
            super.paintComponent(g);   
            drawGrid(g);
            drawSquare(g, pX, pY);
            try{
                drawGameItems(g);
            }catch (Exception ex){
                Logger.getLogger(GameEditor_2.class.getName()).log(Level.SEVERE, null, ex); 
            }
            
        }     
        
        private void drawSquare (Graphics g, int xCoord, int yCoord){
            Graphics2D g2 = (Graphics2D) g; //Usar Graphics2D 
            g.setColor(Color.BLUE);
            //g.fillRect(pX*boxSize+4, pY*boxSize+4, boxSize-8, boxSize-8);
            g2.setStroke(new BasicStroke(2)); //Sirve para las líneas de fuera (anchura)
            g.drawRect(xCoord*boxSize-2, yCoord*boxSize-2, boxSize+2, boxSize+2); //Aplicar función a las coordenadas x/y
        }
        
        
        private void drawGrid(Graphics g){
            Color c = g.getColor();
            g.setColor(Color.LIGHT_GRAY);
            int nLines = size/boxSize;
            System.out.println("---- " + nLines);
            for (int i = 1; i < nLines; i++){
               g.drawLine(i*boxSize, 0, i*boxSize, size);
               g.drawLine(0, i*boxSize, size, i*boxSize);
            } 
            g.setColor(c);
        }
        
        public void drawGameItems(IGameObject [] objs){
            this.objects = objs;
            repaint();
        }
    
        private void drawGameItems(Graphics g) throws Exception{
            
            IAWTGameView view = null;
            
            if (objects != null){
                for (int i = 0; i < objects.length; i++){
                    if (objects[i] != null){
                        if (objects[i] instanceof Blossom){
                            if (objects[i].getValue() >= 10) {
                               view = new VNumberedBox(objects[i], boxSize, Color.GREEN, "Clover"); //VNumberedBox. Importado de una carpeta del proyecto.
                            }
                            else {
                                view = new VNumberedBox(objects[i], boxSize, Color.pink, "DLion");
                            }
                        }
                        else if (objects[i] instanceof Spider){
                            view = new VNumberedCircle(objects[i], boxSize, Color.black, "Spider");
                        }
                        view.draw(g);
                    }
                }
            }
        }
    
    }

        
    }
    
    
    
class Manejador extends JFrame implements ActionListener { // No necesitas una clase nueva

    
    JButton blossom = new JButton("blossom");
    JButton bee = new JButton("bee");
    JButton fly = new JButton("Fly");
    
    @Override
    public void actionPerformed(ActionEvent evento) { 
	if(evento.getSource() == bee){
            System.out.println("Bee, ");
        }
        else if(evento.getSource() == blossom){
            System.out.println("Blossom, ");
        }
        else if(evento.getSource () == fly){
            System.out.println("Fly, ");
        }
    }
}


