/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.JSONParser;
//import net.sf.json.
import json.*;

/**
 *
 * @author 'Aaron Lomba'
 */
public class LibraryBuilder {

    private static DBList<Author> authors = new DBList<>();
    private static DBList<Book> books = new DBList<>();
    private static DBList<Publisher> pubs = new DBList<>();
    private static DBList<Genre> genres = new DBList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //CDL cdl = new CDL();
        //Table pubs = new Table(new Column<Integer>());
        //jsonLookup("9780439324663");
        buildGenres("C:/Users/Aaron/Desktop/libraryData/genreTypes.txt");
        getJsonFile("C:/Users/Aaron/Desktop/library.json");
        System.out.println("The first library has been completed");
        getJsonFile("C:/Users/Aaron/Desktop/lib2.json");
        System.out.println("The second library has been completed");
        getJsonFile("C:/Users/Aaron/Desktop/lib3.json");
        System.out.println("The third library has been completed");
        makeCSVs("C:/Users/Aaron/Desktop/libraryData");
        //System.out.println(authors);
        //System.out.println(pubs);
        //System.out.println(books);

    }

    private static void buildGenres(String fName) {
        File f = new File(fName);
        try {
            if (true) {
                return;
            }
            Scanner reader = new Scanner(f);
            int i = 0;
            while (reader.hasNext()) {
                genres.add(new Genre(i, reader.nextLine().trim()));
                i++;
            }
            System.out.println(genres);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LibraryBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //private void appendC
    private static void makeCSVs(String dir) {
        File par = new File(dir);
        File af = new File(par, "authors.csv");
        File bf = new File(par, "books.csv");
        File pf = new File(par, "pubs.csv");
        File gf = new File(par, "genres.csv");

        if (!par.exists()) {
            par.mkdirs();
        }

        try {
            writeCsv(authors, af);
            writeCsv(pubs, pf);
            writeCsv(books, bf);
            //writeCsv(genres, gf);
        } catch (IOException ex) {
            Logger.getLogger(LibraryBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void writeCsv(DBList<? extends DB> db, File outFile) throws IOException {

        try (FileWriter writer = new FileWriter(outFile)) {
            for (DB i : db) {
                writer.write(i.dbEntry() + "\n");
            }
        }
    }

    public void loadGenres() {

    }

    private static void addData(String isbn, String author, String authorSite, String title, String pubName, String year, String desc, String genre) {
        Author a;
        if (authors.contains(author)) {
            a = authors.getWithKey(author);
        } else {
            a = new Author(author, authors.size() + 1, authorSite);
            authors.add(a);
        }

        Publisher p;

        if (pubs.contains(pubName)) {
            p = pubs.getWithKey(pubName);
        } else {
            p = new Publisher(pubName, pubs.size() + 1);
            pubs.add(p);
        }

        if (books.contains(isbn)) {
            System.err.println("found a duplicate isbn: " + isbn);
            return;
        }

        //Genre g = genres.getWithKey(genre);
        //System.out.println("match " + genre);

        Book b = new Book(isbn, a.getID(), p.getID(), title, year, desc, genres.indexOf(genre));
        books.add(b);

    }

    public static List<String> getJsonFile(String file) {
        List<String> out = new ArrayList<>();
        String dat = "";
        File f = new File(file);
        try {
            try (Scanner reader = new Scanner(f)) {
                String line;
                while (reader.hasNext()) {
                    dat += reader.nextLine();
                }

                JSONObject json = new JSONObject(dat);

                //System.out.println(json.);
                for (int i = 0; i < json.getJSONArray("editions").length(); i++) {

                    JSONObject obj = json.getJSONArray("editions").getJSONObject(i);

                    if (obj.has("isbn_13") && obj.getJSONArray("isbn_13").length() != 0) {
                        //System.out.println(obj.getJSONArray("isbn_13").get(0));
                        String desc = obj.optString("description", "").replace("{\"type\":\"/type/text\",\"value\":\"", "").replace("\"}", "").replace("\\\"", "\"");
                        //System.out.println();
                        String gen = "";
                        if (obj.has("genres")) {
                            gen = obj.getJSONArray("genres").getString(0).replace(".", "");
                        }

                        jsonLookup(obj.getJSONArray("isbn_13").getString(0), desc, gen);
                    }
                    //System.out.println(obj);
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LibraryBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    public static List<String> getIsbns(String file) {
        List<String> out = new ArrayList<>();
        File f = new File(file);
        try {
            try (Scanner reader = new Scanner(f)) {
                String line;
                while (reader.hasNext()) {
                    line = reader.nextLine();
                    out.add(line.trim());
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LibraryBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    public static void jsonLookup(String isbn, String desc, String genre) {
        try {
            URL url = new URL("https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn.replace("-", "") + "&jscmd=data&format=json");
            Scanner s = new Scanner(url.openConnection().getInputStream());
            String data = "";
            while (s.hasNext()) {
                data += s.nextLine();
            }
            JSONObject obj = new JSONObject(data);
            //System.out.println(obj);

            JSONObject source = obj.getJSONObject("ISBN:" + isbn.replace("-", ""));
            String title = source.getString("title");
            //System.out.println(source.getJSONArray("authors"));
            String author = source.getJSONArray("authors").getJSONObject(0).getString("name");
            if (author.contains(",")) {
                String as[] = author.split(",");
                if (as.length > 2) {
                    throw new JSONException("Bad author: " + author);
                }
                author = as[1].trim() + " " + as[0].trim();
            }
            String site = null;
            if (source.has("links") && source.optJSONArray("links").getJSONObject(0).getString("title").equals("Author's Website")) {
                site = source.getJSONArray("links").getJSONObject(0).getString("url");
            }

            //System.out.println(source.getJSONArray("links"));
            String date = source.getString("publish_date");
            date = date.substring(date.length() - 4);

            String publisher = source.getJSONArray("publishers").getJSONObject(0).getString("name");

            //System.out.println(source.keySet());
            addData(isbn.replace("-", ""), author, site, title, publisher, date, desc, genre);
            //System.out.println(title + " " + author + " " + publisher + " " + date);

        } catch (MalformedURLException ex) {
            Logger.getLogger(LibraryBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LibraryBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            //ex.printStackTrace();
            System.err.println("ISBN lookup failed for: " + isbn);
        }
    }

    public void isbnLookup(String isbn) throws IOException {

        //JSONParser parser =
        try {
            URL url = new URL("https://isbnsearch.org/isbn/" + isbn.replace("-", ""));
            Scanner s = new Scanner(url.openConnection().getInputStream());
            String line;
            String title = null;
            String author = null;
            String publisher = null;
            String published = null;
            while (s.hasNext()) {
                line = s.nextLine();
                if (line.contains("<strong>Author:</strong>")) {
                    author = s.nextLine().trim();
                } else if (line.contains("<strong>Publisher:</strong>")) {
                    publisher = s.nextLine().trim();
                } else if (line.contains("<strong>Published:</strong>")) {
                    published = s.nextLine().trim();
                } else if (line.contains("")) {

                }

            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(LibraryBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
