/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import game.Blossom;
import game.IGameObject;

/**
 *
 * @author juanangel
 */
public abstract class AbstractGameView implements IAWTGameView{
    
    IGameObject gObj;
    int length = 20;
    
    public AbstractGameView(IGameObject obj, int length) throws Exception {
        
        if (obj != null){
            gObj = obj;
        }
        else {
            throw new Exception();
        }
        this.length = length;
    }  
}
