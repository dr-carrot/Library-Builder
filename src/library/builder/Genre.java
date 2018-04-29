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
public class Genre implements DB{
    private int id;
    private String genre;

    Genre(int i, String g) {
        id = i;
        genre = g;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof String){
            return genre.equalsIgnoreCase(o.toString());
        }else if(o instanceof Author){
            return ((Genre)o).getID() == id;
        }
        return false;
    }
    
    public int getID(){
        return id;
    }

    @Override
    public String dbEntry() {
        return dbEntry(",");
    }

    @Override
    public String dbEntry(String delim) {
        return "\"" + id + "\"" + delim + "\"" + genre +  "\"";
    }
    
    public String toString(){
        return dbEntry();
    }
    
}
