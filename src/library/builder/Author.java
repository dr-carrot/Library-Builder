/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.builder;

import java.util.Objects;

/**
 *
 * @author 'Aaron Lomba'
 */
public class Author implements DB{

    private final String name;
    private final int id;
    private final String site;
    
    public Author(String n, int i){
        this(n, i, null);
        
    }

    Author(String n, int i, String authorSite) {
        name = n;
        id = i;
        site = authorSite;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof String){
            return name.equals(o);
        }else if(o instanceof Author){
            return ((Author)o).getID() == id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + this.id;
        return hash;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }
    
    public String dbEntry(){
        return dbEntry(",");
    }
    
    public String dbEntry(String delim){
        return "\"" + id +"\"" + delim +"\"" + name +"\"";
    }
    
    public String toString(){
        return dbEntry();
    }
}
