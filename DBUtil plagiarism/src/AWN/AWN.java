/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWN;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dali
 */
public class AWN {

    public AWN() {
    }

    /**
     * *
     * output: List of synonyms not root words
     *
     * @param word
     * @param isRoot :true => the input word is a root , false => the input word
     * is not a root
     * @return list of synonyms of input word
     */
    public static List<String> getSynonyms(String word, boolean isRoot) {
        List<String> synonyms = new ArrayList<>();
        try {
            //get connection to database
            Connection conn = SQLITEConnector.getConnection();
            //create statment of query
            Statement stmt = conn.createStatement();
            //If the input word is a root
            if (isRoot) {
                // Find the words in form table
                ResultSet rs = stmt.executeQuery("SELECT Distinct w1.value "
                        + "FROM words w1 "
                        + "INNER JOIN (SELECT w2.synsetid "
                        + "FROM words w2  "
                        + "WHERE w2.wordid IN (SELECT f1.wordid "
                        + "FROM forms f1 "
                        + "WHERE f1.value = '" + word + "')) AS res "
                        + "ON w1.synsetid = res.synsetid");
                while (rs.next()) {
                    synonyms.add(rs.getString(1));
                }
                rs.close();
            }
            // If there are no result (not root)
            if (synonyms.isEmpty()) {
                // Find the words in words table
                ResultSet rs = stmt.executeQuery("SELECT w1.value "
                        + "FROM words w1 "
                        + "INNER JOIN words w2 "
                        + "ON w1.synsetid = w2.synsetid "
                        + "AND w2.value = '" + word + "'");
                while (rs.next()) {
                    synonyms.add(rs.getString(1));
                }
                rs.close();
            }
            // If there are no result (not root)
            if (synonyms.isEmpty()) {
                // Find the words in items table
                ResultSet rs = stmt.executeQuery("SELECT w1.value "
                        + "FROM words AS w1 "
                        + "INNER JOIN items AS t1 "
                        + "ON w1.synsetid = t1.itemid "
                        + "AND t1.name = '" + word + "'");
                while (rs.next()) {
                    synonyms.add(rs.getString(1));
                }
                rs.close();
            }
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(AWN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return synonyms;
    }

    /**
     * *
     * return array of Root synonyms of input word
     *
     * @param word input word
     * @param isRoot true => the input word is a root ; false => the input word
     * is not a root
     * @return array of synonyms root words
     */
    public static List<String> getSynonymsAsRoot(String word, boolean isRoot) {
        List<String> synonyms = new ArrayList<>();
        try {
            //get connection to database
            Connection conn = SQLITEConnector.getConnection();

            //create statment of query
            Statement stmt = conn.createStatement();
            //If the input word is a root
            if (isRoot) {
                //Find the words in form table
                ResultSet rs = stmt.executeQuery("SELECT Distinct f1.value "
                        + "FROM forms f1 "
                        + "WHERE f1.wordid IN "
                        + "(SELECT Distinct w1.wordid "
                        + "FROM words w1 "
                        + "INNER JOIN (SELECT w2.synsetid "
                        + "FROM words w2 "
                        + "WHERE w2.wordid IN  "
                        + "(SELECT f2.wordid "
                        + "FROM forms f2 "
                        + "WHERE f2.value = '" + word + "')) AS res "
                        + "ON w1.synsetid = res.synsetid)");
                while (rs.next()) {
                    synonyms.add(rs.getString(1));
                }
                rs.close();
            }
            //  If there are no result
            if (synonyms.isEmpty()) {
                // Find the words in words table
                ResultSet rs = stmt.executeQuery("SELECT Distinct f1.value "
                        + "FROM forms f1 "
                        + "WHERE f1.wordid IN "
                        + "(SELECT w1.wordid "
                        + "FROM words w1 "
                        + "INNER JOIN words w2 "
                        + "ON w1.synsetid = w2.synsetid "
                        + "AND w2.value = '" + word + "')");
                while (rs.next()) {
                    synonyms.add(rs.getString(1));
                }
                rs.close();
            }
            //  If there are no result
            if (synonyms.isEmpty()) {
                // Find the words in items table
                ResultSet rs = stmt.executeQuery("SELECT Distinct f1.value "
                        + "FROM forms f1 "
                        + "WHERE f1.wordid IN "
                        + "(SELECT w1.wordid "
                        + "FROM words w1 "
                        + "INNER JOIN items t1 "
                        + "ON w1.synsetid = t1.itemid "
                        + "AND t1.name = '" + word + "')");
                while (rs.next()) {
                    synonyms.add(rs.getString(1));
                }
                rs.close();
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AWN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return synonyms;
    }

    public static void main(String[] args) {
        AWN awn = new AWN();
        System.out.println(awn.getSynonyms("خرج", false));
        System.out.println(awn.getSynonymsAsRoot("ذهب", false));
    }
}
