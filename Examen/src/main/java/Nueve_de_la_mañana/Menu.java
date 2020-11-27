/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nueve_de_la_mañana;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author gabri
 */
public class Menu extends JFrame {
    JButton nuevaPartida, escogerVista, cargarPartida, cuadrados, simbolos, volver;
    JPanel opciones, titulo1, vistas, imagenes, titulo2 ;
    JLabel Titulo1, Titulo2;
    
    
    public Menu (){
        super();
        this.setLayout(new BorderLayout());
        
        Icon uno = new ImageIcon("1.jpg");
        Icon dos = new ImageIcon("2.jpg");
    
        Titulo1 = new JLabel("Caperucita roja: ");
        Titulo1.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
        Titulo1.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        Titulo2 = new JLabel ("El videojuego");
        Titulo1.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        Titulo2.setVerticalAlignment(javax.swing.SwingConstants.NORTH);
        Titulo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        nuevaPartida= new JButton("Nueva partida");
        nuevaPartida.addActionListener( 
                new ActionListener(){
                    
                }
        );
        
        
        escogerVista= new JButton ("Escoger Vista");
        cargarPartida= new JButton("Cargar Partida");
        cuadrados= new JButton ("Cuadrados");
        simbolos=new JButton ("Símbolos");
        volver = new JButton ("Volver");
        opciones =new JPanel ();
        titulo1 = new JPanel();
        opciones.setLayout(new BorderLayout());
        opciones.add(nuevaPartida, BorderLayout.NORTH);
        opciones.add(escogerVista, BorderLayout.CENTER);
        opciones.add(cargarPartida, BorderLayout.SOUTH);
        
        titulo1.setLayout(new BorderLayout());
        titulo1.add(Titulo1, BorderLayout.NORTH);
        titulo1.add(Titulo2, BorderLayout.CENTER);
        
        this.add(opciones, BorderLayout.SOUTH);
        this.add(titulo1, BorderLayout.CENTER);
        
        setSize (400, 400);
        setVisible(true);
        
    }
    
   
    
}
