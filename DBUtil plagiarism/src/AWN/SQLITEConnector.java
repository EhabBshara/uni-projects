package AWN;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        getConnection();
    }
}
