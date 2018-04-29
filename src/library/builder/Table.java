/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.builder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 'Aaron Lomba'
 */
public class Table {
    private final Column[] db;
    private final int nCols;
    private int idx = 0;
    private ArrayList<ArrayList<? extends Object>> data = new ArrayList<>();
    
    public Table(int nCols, boolean auto){
        this.nCols = nCols;
        db = new Column[nCols];
    }
    
    
    public void setColumn(Column c, int i){
        db[i] = c;
    }
    
    
    public Table(Column... cols){
        nCols = cols.length;
        db = cols;
    }
    
    public void insertRow(List row){
        if(row.size() != nCols){
            throw new IllegalArgumentException("The row is not the right length!");
        }
        ArrayList rw = new ArrayList();
        int i = 0;
        for(Column c : db){
            if(!c.autoIncrement()){
                c.set(row.get(i));
                i++;
            }else{
                c.set(idx);
            }
        }
        idx++;
    }
    
    public void insertRow(String[] data){
        //getKey();
    }
    
    
}
