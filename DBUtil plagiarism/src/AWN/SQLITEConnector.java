package AWN;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.common.collect.Sets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author dali
 */
public class SQLITEConnector {

    static Connection conn = null;

    public static void connect() {
        try {
            String pathToDBFile = new StringBuffer(System.getProperty("user.dir") + System.getProperty("file.separator") + "\\src\\assets\\AWNDatabase" + System.getProperty("file.separator")).toString();
            String DBfileName="ArabicWordNet.sqlite";
            // db parameters
//            String url = "jdbc:sqlite:C:/sqlite/db/chinook.db";
            String url = "jdbc:sqlite:"+pathToDBFile+DBfileName;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static Connection getConnection()
    {
        if(conn!=null)
            return conn;
        connect();
        return conn;
    }

    public static void main(String []args)
    {
         HashSet<String> h1=new HashSet<>();
        HashSet<String> h2=new HashSet<>();
        h1.add("البطاطا");
        h1.add("على");
        h1.add("الفراولة");
        h1.add("احب");
        h1.add("البطاطا");
        h1.add("والفراولة");
        h1.add("على");
        h1.add("ما");
        h1.add("احب");
        
        h2.add("كنت");
        h2.add("احب");
        h2.add("البطاطا");
        h2.add("الاستوائية");
        h2.add("والفراولة");
        h2.add("اللذيذة");
        h2.add("على");
        Sets.SetView<String> intersection = Sets.intersection(h1, h2);
        System.out.println(intersection.size());
        System.out.println(Arrays.asList(intersection.toArray()));
        
    }
}
