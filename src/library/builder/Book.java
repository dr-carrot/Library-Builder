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
public class Book implements DB{
    private String isbn;
    private String title;
    private int authID;
    private int pubID;
    private String pubYear;
    private String desc;
    private int genre;
    
    public Book(String i, int a, int p, String t, String y, String d, int g){
        isbn = i;
        title = t;
        authID = a;
        pubID = p;
        pubYear = y;
        desc = d;
        genre = g;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the authID
     */
    public int getAuthID() {
        return authID;
    }

    /**
     * @return the pubID
     */
    public int getPubID() {
        return pubID;
    }

    /**
     * @return the pubYear
     */
    public String getPubYear() {
        return pubYear;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof String){
            return title.equals(o);
        }else if(o instanceof Book){
            return ((Book)o).getIsbn().equals(isbn);
        }
        return false;
    }
    

    @Override
    public String dbEntry() {
        return dbEntry(",");
    }

    @Override
    public String dbEntry(String delim) {
        return "\"" + getIsbn() + "\"" + delim + "\"" + authID + "\"" + delim +"\"" + pubID +"\"" + delim +"\"" + title +"\"" + delim +"\"" + pubYear + "\"" + delim  + "\"" + desc + "\"";
    }
    
    public String toString(){
        return dbEntry();
    }
    
   
}
