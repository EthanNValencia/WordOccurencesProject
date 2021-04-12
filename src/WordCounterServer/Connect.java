/*
File: WordCounterServer.Connect.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: Class that is used to establish the db connection and read and write to the db.
*/

package WordCounterServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

/***
 * This is a fairly basic class that is used for storing and accessing the parameters that are associated with word objects.
 */
public class Connect {

    /***
     * Simple method for establishing a connection to the database. These are the database parameters on my personal computer.
     * @return This method returns the Connection object.
     * @throws Exception I doubt it can throw an exception, but the tutorial told me to keep this here.
     */
    public static Connection getConnection() throws Exception {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/word_occ";
        String username = "ServerUser";
        String password = "serverpassword";
        Class.forName(driver);

        Connection conn = DriverManager.getConnection(url, username, password);
        //System.out.println("Connection worked.");
        return conn;
    }

    /***
     * This is a method that is used to create a word table if a table does not exist.
     * @throws Exception I doubt it can throw an exception, but the tutorial told me to keep this here.
     */
    public static void createTable() throws Exception {
        Connection con = getConnection();
        assert con != null;
        PreparedStatement createTable = con.prepareStatement("CREATE TABLE IF NOT EXISTS word (id int NOT NULL AUTO_INCREMENT, word_name varchar(25), word_count int, PRIMARY KEY(id))");
        createTable.executeUpdate();
    }

    /***
     * Method that is used to insert a single word into the database table. This class is too slow for normal usage.
     * @param wordName This is the name of the word object.
     * @param wordCount This is the count of the word object.
     * @throws Exception I doubt it can throw an exception, but the tutorial told me to keep this here.
     */
    public static void insertWord(String wordName, int wordCount) throws Exception {
        final String word_name = wordName;
        final int word_count = wordCount;
        String sql = "INSERT INTO word (word_name, word_count) VALUES('" + word_name + "' , '" + word_count + "')";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    /***
     * Method that is used to delete all the contents of the word table.
     * @throws Exception I doubt it can throw an exception, but the tutorial told me to keep this here.
     */
    public static void deleteAll() throws Exception{
        String sql = "DELETE FROM word_occ.word;";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    /***
     * Method that is used to sort and store the contents of the database table.
     * @return It returns the WordList array that contains WordCounterServer.Word objects.
     * @throws Exception I doubt it can throw an exception, but the tutorial told me to keep this here.
     */
    public static ArrayList<Word> getList() throws Exception{
        ArrayList<Word> wordList = new ArrayList<Word>();
        String sql = "SELECT * FROM word ORDER BY word_count DESC";
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            wordList.add(new Word(rs.getString(2), rs.getInt(3)));
        }
        return wordList;
    }

    /***
     * This method is used to insert the contents of an array list into the database. Edit: I changed this to use a batch. If I used individual inserts it would take over 20 seconds to run this, but with the batch it's reduced time is around 7 seconds.
     * @param arrayList Requires an ArrayList of words.
     * @throws Exception I doubt it can throw an exception, but the tutorial told me to keep this here.
     */
    public static void writeArrayList(ArrayList<Word> arrayList) throws Exception{
        Connection con = getConnection();
        String sql = "INSERT INTO word (word_name, word_count) VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        Iterator<Word> iterator = arrayList.iterator();

        while(iterator.hasNext()){
            Word word = iterator.next();
            ps.setString(1, word.getWordName());
            ps.setInt(2, word.getWordCount());
            //System.out.println(ps.toString());
            ps.executeUpdate();
        }
    }

    /***
     * When I run the program as a .jar, during the database writing portion, it seems that there are textual artifacts that are being generated. This method deletes those objects.
     * @throws Exception This method will throw an exception, especially if something goes wrong during the db connection and updating processes.
     */
    public static void deleteArtifacts() throws Exception {
        Connection con = getConnection();
        assert con != null;
        String sql = "UPDATE word_occ.word SET word_name=REPLACE(word_name,'�',''), \n" +
                                              "word_name=REPLACE(word_name,'Â€',''),\n" +
                                              "word_name=REPLACE(word_name,'Œ',''),\n" +
                                              "word_name=REPLACE(word_name,'™','');";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
        String sqlDelete = "DELETE FROM word_occ.word WHERE word_name='' OR word_name IS NULL;";
        ps = con.prepareStatement(sqlDelete);
        ps.executeUpdate();

    }


    /***
     * This method is used to drop the table from the db.
     * @throws Exception This method will throw an exception, especially if something goes wrong during the db connection and updating processes.
     */
    public static void dropTable() throws Exception {
        Connection con = getConnection();
        assert con != null;
        String sql = "DROP TABLE word_occ.word;";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    /***
     * This is an overridden toString method that is useful for testing purposes.
     * @return Returns a string of the object name.
     */
    @Override
    public String toString() {
        return "WordCounterServer.Connect";
    }
}
