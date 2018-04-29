/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.builder;

import java.util.ArrayList;

/**
 *
 * @author 'Aaron Lomba'
 */
public class DBList<T extends DB> extends ArrayList<T>{
    @Override
    public boolean contains(Object o){
        if(o instanceof String){
            for(T obj : this){
                if(obj.equals(o)){
                    return true;
                }
            }
        }
        return super.contains(o);
    }
    
    public T getWithKey(String name){
        for(T obj : this){
            if(obj.equals(name)){
                return obj;
            }
        }
        return null;
    }
}
