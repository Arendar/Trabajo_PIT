/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import game.Bee;
import game.Blossom;
import game.Fly;
import game.Position;
import org.json.JSONObject;

/**
 *
 * @author gabri
 */
public class TestObjects {
    static public void testToJSON()
    {
        Blossom flor = new Blossom(new Position(), 10, 1);
        JSONObject jsonFlor= flor.toJSONObject();
        System.out.println(jsonFlor.toString());
        
        Bee abeja = new Bee(new Position(2, 6), -5, 1);
        JSONObject jsonbee = abeja.toJSONObject();
        System.out.println(jsonbee.toString());
        
    }
    
    static public void testConstructor()
    {
      Blossom flor = new Blossom(new Position(), 10, 1);
      JSONObject jsonFlor= flor.toJSONObject(); 
      System.out.println(jsonFlor.toString());
      
      Blossom flor2 = new Blossom(jsonFlor);   
      JSONObject jsonflor2= flor2.toJSONObject();
      System.out.println(jsonflor2.toString());
      
      Bee abeja = new Bee (jsonFlor); //Error: no es del mismo tipo de objeto del que queremos implementar
      JSONObject jsonbee = abeja.toJSONObject();
      System.out.println(jsonbee.toString());
      
      Fly mosca =new Fly (new Position (5, 7), 0, 1);
      mosca.printFly();
    }
    
    
    static public void main (String args[])
    {
        testToJSON();
        testConstructor();
    }
}
