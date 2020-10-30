/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import common.FileUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author juanangel
 */
public class TestRidingHood {
    
    // Práctica 3. Ejercicio 2.1
    /**
     * Crea dos objetos RidingHoog.
     * Comprueba paso a JSON y constructor con Jobjeto JSON
     */
    public static void testConstructorAndToJson(){
        
        System.out.println("testConstructorAndToJson");
        
        RidingHood_1 b1 = new RidingHood_1(new Position(1,1));
        JSONObject jObj = b1.toJSONObject();
        RidingHood_1 b2 = new RidingHood_1(jObj);
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b1.toJSONObject());
        System.out.println(b2.toJSONObject());
        System.out.println("-----------------------------------------------");
    }
    
    /**
     * Se ejecuta el método moveToNextPosition hasta que se alcanzan todos los targets o
     * se realizan n llamadas.
     * @param gObj  objetos en el que se realizan las invocaciones
     * @param targets objetos a alcanzar
     * @param  n número de llamadas al método
     * @return posiciones por las que ha pasado el objeto
     */
    public static Position [] testMoveToNextPosition(IGameObject gObj, Blossom targets[], int n){
        
        System.out.println("testMoveToNextPosition");
        
        // Array para guardar posiciones trayectoria objeto RidingHood
        Position trace[] = new Position[n];
        for (int i = 0; i < trace.length; i++){
           trace[i] = new Position();
        }
        
        // Invocamos moveToNextPosition sobre el último objeto
        // RidingHood creado un número de veces sudiciente para
        // pasar por todos los blossoms.
        // Guardamos las posiciones en trace.
        for (int i = 0; i < trace.length; i++){
            gObj.moveToNextPosition();
            trace[i] = new Position(gObj.getPosition());
        }
        System.out.println("-----------------------------------------------");

        return trace;
    }
    
    /**
     * Guarda un array de posiciones en un fichero, las lee del fichero y las muestra 
     * por consola.
     * @param fileName
     * @param trace 
     */
    public static void testSaveAndLoad(String fileName, Position [] trace) {
        
        System.out.println("testSaveAndLoad");
        
        // Guardamos las posiciones por las que ha pasado el objeto RidingHood 
        // en formato JSON en un fichero.
        JSONObject jPositions [] = new JSONObject[trace.length];
        for (int i = 0; i < trace.length; i++){
            if (trace[i] != null){
                jPositions[i] = trace[i].toJSONObject();
            }
        }
        FileUtilities.writeJsonsToFile(jPositions, fileName);
         
        // Leer posiciones guardadas en el fichero anterior y mostrarlas
        // en consola.
        JSONArray jArray = FileUtilities.readJsonsFromFile(fileName);
        
        for(int i = 0; i < jArray.length(); i++){
            System.out.println(jArray.get(i).toString());
        }
        System.out.println("-----------------------------------------------");
    }
    
    
    public static void main(String [] args){
        
        // Ejercicio 2.1 ....................................................................
        testConstructorAndToJson();

        // Ejercicio 2.2 Invocación constructor con array de blossoms .......................
        // Crear array de Blossoms
        Blossom blossoms[] = {new Blossom(new Position(1,1)),
                       new Blossom(new Position(6,1)),
                       new Blossom(new Position(3,3)),
                       new Blossom(new Position(6,6)),
                       new Blossom(new Position(1,6)),
                       new Blossom(new Position(1,1)),
                       new Blossom(new Position(1,4))
                      };        
        // Crear nuevo objeto RidingHood pasándole el array de blossoms.
        IGameObject b = new RidingHood_1(new Position(1,1), 0, 0, blossoms);
        
        // Ejercicio 2.5 .....................................................................
        Position trace [] = testMoveToNextPosition(b, blossoms, 40);
        
        // Ejercicio 2.6 .....................................................................
        // Create directory for saveing test results if it does not exist.
        FileUtilities.crearDirectorio("src/main/resources/tests");
        testSaveAndLoad("src/main/resources/tests/traces.txt", trace);       
    }       
}
