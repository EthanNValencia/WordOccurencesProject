package WordCounter;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/***
 * Test class for verifying the functionality of the Connect.java class.
 */
class ConnectTest {

    /***
     * Tests that the Connect.getConnection() method is working correctly.
     */
    @Test
    public void testGetConnection(){
        try {
            Connect.getConnection();
            assertTrue(true); // If this statement is reached then it passes.
        } catch (Exception e) {
            fail();
            System.out.println("An exception was thrown.");
        }
    }

    /***
     * Tests that the Connect.createTable() method is working correctly.
     */
    @Test
    public void testCreateTable(){
        try {
            Connect.createTable();
            assertTrue(true); // If this statement is reached then it is a pass.
        } catch (Exception e){
            System.out.println(e);
            fail();
        }
    }

    /***
     * Tests that the Connect.insertWord() method is working correctly.
     */
    @Test
    public void testInsertWord(){
        try {
            Connect.insertWord("THE", 12);
            assertTrue(true);
            Connect.deleteAll();
        } catch (Exception e){
            System.out.println(e);
            fail();
        }
    }

    /***
     * Tests that the Connect.deleteAll() method is working correctly.
     */
    @Test
    public void testDeleteAll(){
        try {
            Connect.deleteAll();
            assertTrue(true);
        } catch (Exception e){
            System.out.println(e);
            fail();
        }
    }

    /***
     * Tests that the Connect.getList() method is functioning correctly.
     */
    @Test
    public void testGetList(){
        ArrayList<Word> wordList = new ArrayList<Word>();
        try {
            Connect.deleteAll();
            Connect.insertWord("THE", 16);
            Connect.insertWord("IS", 30);
            Connect.insertWord("OR", 20);
            Connect.insertWord("BEFORE", 24);
            wordList = Connect.getList();
        } catch (Exception e){
            System.out.println(e);
            fail();
        }
        assertEquals("IS 30", wordList.get(0).toString());
        assertEquals("BEFORE 24", wordList.get(1).toString());
        assertEquals("OR 20", wordList.get(2).toString());
        assertEquals("THE 16", wordList.get(3).toString());
    }

    /***
     * Tests that the ArrayList can be inserted into the database and read from it correctly.
     */
    @Test
    public void testWriteArrayList(){
        try {
            Connect.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Word THE = new Word("THE", 32);
        Word IS = new Word("IS", 34);
        Word FOR = new Word("FOR", 12);
        Word THERE = new Word("THERE", 35);
        ArrayList<Word> arrayList = new ArrayList<>();
        arrayList.add(THE);
        arrayList.add(IS);
        arrayList.add(FOR);
        arrayList.add(THERE);
        try {
            Connect.writeArrayList(arrayList);
            ArrayList<Word> testArray = new ArrayList<Word>();
            testArray = Connect.getList();
            assertEquals("THERE 35", testArray.get(0).toString());
            assertEquals("IS 34", testArray.get(1).toString());
            assertEquals("THE 32", testArray.get(2).toString());
            assertEquals("FOR 12", testArray.get(3).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}