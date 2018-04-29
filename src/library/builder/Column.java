/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.builder;

/**
 *
 * @author 'Aaron Lomba'
 */
public class Column<T> {
    private T item;
    private boolean autoIncrement = false;
    
    public Column(){
        this.item = item;
    }
    
    
    
    public Column(boolean b){
        //this(item);
        autoIncrement = false;
    }
    
    public Class getType(){
        return item.getClass();
    }
    
    public void set(T t){
        
    }
    
    public T get(){
        return item;
    }

    boolean autoIncrement() {
        return autoIncrement;
    }
}
