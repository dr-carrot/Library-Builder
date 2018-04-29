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
public class Publisher implements DB{
    private int id;
    private String name;
    private String address;

    Publisher(String pubName, int size) {
        name = pubName;
        id = size;
    }
    
    Publisher(String pubName, int size, String a) {
        name = pubName;
        id = size;
        address = a;
    }

    @Override
    public String dbEntry() {
        return dbEntry(",");
    }

    @Override
    public String dbEntry(String delim) {
        return "\"" + id + "\"" + delim + "\"" + name + "\"" + delim + "\"" + (address == null ? "" : address) + "\"";
    }

    /**
     * @return the id
     */
    public int getID() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setID(int id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof String){
            return name.equals(o);
        }else if(o instanceof Publisher){
            return ((Publisher)o).getID() == id;
        }
        return false;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    
    public String toString(){
        return dbEntry();
    }
    
}
