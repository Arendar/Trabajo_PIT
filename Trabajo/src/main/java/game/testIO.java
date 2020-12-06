/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;

/**
 *
 * @author gabri
 */
public class testIO {
        public static void main (String args[]) throws IOException{
        String path="src/main/resources/games/mapNumber.txt";
        Prueba_IO manhattan = new Prueba_IO();
        int numero = 3;
        manhattan.guardarNumero(numero, path);
        System.out.println(manhattan.sacarNumero(path));
    }
}
