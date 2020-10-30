/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.text.DecimalFormat;
import org.json.JSONObject;

/**
 *
 * @author juanangel
 */
public class TestObjects {
    
    
    public static void testToJSON(){
        System.out.println("testToJSON");
        JSONObject jObj;
        
        System.out.println("Blossom ... ");
        Blossom blossom = new Blossom();
        System.out.println(blossom.toJSONObject());
        System.out.println();

        System.out.println("Bee ... ");
        Bee bee = new Bee();
        System.out.println(bee.toJSONObject());
        System.out.println();
        
        System.out.println("Fly ... ");
        Fly fly = new Fly(new Position(1,1), 1);
        System.out.println(fly.toJSONObject());
        System.out.println();
        
        System.out.println("Spider ... ");
        Spider spider = new Spider(new Position(1,1), 1);
        System.out.println(spider.toJSONObject());
        System.out.println();
         
        System.out.println("---------------------------------------------");
    }
    
    public static void testConstructores(){
        System.out.println("testConstructores");
        JSONObject jObj;
        
        System.out.println("Blossoms ... ");
        Blossom blossom1 = new Blossom(new Position(1,1));
        jObj = blossom1.toJSONObject();
        System.out.println(jObj);
        Blossom blossom2 = new Blossom(jObj);
        System.out.println(blossom2.toJSONObject());
        System.out.println();

        System.out.println("Bees ... ");
        Bee bee1 = new Bee(new Position(1,1), 1);
        jObj = bee1.toJSONObject();
        System.out.println(jObj);
        Bee bee2 = new Bee(jObj);
        System.out.println(bee2.toJSONObject());
        System.out.println();
        
        System.out.println("Flies ... ");
        Fly fly1 = new Fly(new Position(1,1), 1, 4);
        jObj = fly1.toJSONObject();
        System.out.println(jObj);
        Fly fly2 = new Fly(jObj);
        System.out.println(fly2.toJSONObject());
        System.out.println();
        
        System.out.println("Spiders ... ");
        Spider sp1 = new Spider(new Position(1,1), 1);
        jObj = sp1.toJSONObject();
        System.out.println(jObj);
        Spider sp2 = new Spider(jObj);
        System.out.println(sp2.toJSONObject());
        System.out.println();
         
        System.out.println("---------------------------------------------");
    }
    
    public static void testDistances(){
        IGameObject gObjs [] = {
            new Fly(new Position(0,0)),
            new Blossom(new Position(1,0)),
            new Bee(new Position(1,1)),
            new Spider(new Position(1,2))
        };
        DecimalFormat form = new DecimalFormat();
        form.setMaximumFractionDigits(2); 
        
        System.out.println("Showing distances matrix using distance method");
        for(int i = 0; i < gObjs.length; i++){
            for(int j = 0; j < gObjs.length; j++){
                System.out.print(form.format(AbstractGameObject.distance(gObjs[i].getPosition(), gObjs[j].getPosition())) + "\t");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------");
        System.out.println("Showing distances matrix using getDistance method");
        for(int i = 0; i < gObjs.length; i++){
            for(int j = 0; j < gObjs.length; j++){
                System.out.print(form.format(AbstractGameObject.getDistance(gObjs[i], gObjs[j])) + "\t");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------");
        IGameObject bl = new Blossom(new Position(1,3));
        System.out.println("Showing closest to " + bl + " ---> " + AbstractGameObject.getClosest(bl.getPosition(), gObjs));
        System.out.println("Showing closest to " + bl + " ---> " + AbstractGameObject.getClosest(bl, gObjs));
    }
    
    public static void testDowncasting(){
        IGameObject gObjs [] = {
            new Fly(new Position(0,0)),
            new Blossom(new Position(1,0)),
            new Bee(new Position(1,1)),
            new Spider(new Position(1,2))
        };
        
        for(int i = 0; i < gObjs.length; i++){
             System.out.print("Position --> " + gObjs[i].getPosition() + "  JSON ---> ");
             if (gObjs[i] instanceof Blossom){
                 ((Blossom) gObjs[i]).printBlossom();
             }
             else if (gObjs[i] instanceof Bee){
                 ((Bee) gObjs[i]).printBee();
             }
             else if (gObjs[i] instanceof Fly){
                 ((Fly) gObjs[i]).printFly();
             }
             else if (gObjs[i] instanceof Spider){
                 ((Spider) gObjs[i]).printSpider();
             }
             
        }
        
    }
    
    public static void main(String [] args){
        testToJSON();
        testConstructores();
        testDistances();
        testDowncasting();
    }
}
