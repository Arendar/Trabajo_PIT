/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author gabri
 */
public class Prueba_IO {
    String path;
    int resultado;
    String number;
    BufferedWriter caracter;
    BufferedReader escribe;
   
    
    public Prueba_IO(){
        path="src/main/resources/games/mapNumber.txt";
        resultado=0;
    }
    
     public void guardarNumero (int numero, String path) throws IOException{
        this.path=path;
        number = Integer.toString(numero);
        caracter= new BufferedWriter(new FileWriter(path));
        caracter.write(number);
        caracter.close();
    }
    
    public String sacarNumero(String path) throws IOException{
        this.path=path;
        escribe = new BufferedReader(new FileReader (path));
        resultado= escribe.read()-48;
        return Integer.toString(resultado);
    }
}
