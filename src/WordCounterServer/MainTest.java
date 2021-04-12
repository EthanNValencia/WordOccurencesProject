/*
File: WordCounterServer.MainTest.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: I added all the unit tests to this singular test file. It just makes more sense to run them all from a singular location.
*/

package WordCounterServer;

import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/***
 * When I initially wrote my unit tests I had them run in several different class folders. This makes it tedious so I moved them all to one class.
 */
class MainTest {

    /***
     * This tests that the WordCounterServer.WebSaver.checkLineContent() method is processing the string as expected.
     */
    @Test
    public void checkLineContentTest1() {
        String test = "<br /> This is a test.";
        assertTrue(WebSaver.checkLineContent(test));
    }

    /***
     * This tests that the WordCounterServer.WebSaver.checkLineContent() method is processing the string as expected.
     */
    @Test
    public void checkLineContentTest2() {
        String test = "<span This is a test. <div";
        assertFalse(WebSaver.checkLineContent(test));
    }

    /***
     * This tests that the WordCounterServer.WebSaver.checkLineContent() method is processing the string as expected.
     */
    @Test
    public void checkLineContentTest3() {
        String test = "This is a test. <span style=\"margin-left: 20%\">";
        assertTrue(WebSaver.checkLineContent(test));
    }

    /***
     * This test verifies that if a non-WordCounterServer.Word object gets compared to a WordCounterServer.Word object, that they will not be equal, even if they have similarities.
     */
    @Test
    public void voidTestNonWordObject(){
        Word word = new Word("THE");
        String string = "THE";
        assertFalse(word.equals(string));
    }

    /***
     * This tests overridden equals() method from WordCounterServer.Word.java.
     */
    @Test
    public void testBooleanEquals1(){
        Word word1 = new Word("THE");
        Word word2 = new Word("THE");
        assertTrue(word1.equals(word2));
    }

    /***
     * This tests that hashCode() method from WordCounterServer.Word.java does not return the word name.
     */
    @Test
    public void testHash1(){
        Word the = new Word("THE");
        assertNotEquals("THE", the.hashCode());
    }

    /***
     * This tests that two hashCode() method calls from the same WordCounterServer.Word object return the same hashcode.
     */
    @Test
    public void testHash2(){
        Word the = new Word("THE");
        assertEquals(the.hashCode(), the.hashCode()); // is this a useful test?
    }

    /***
     * This tests the addWordCount() method and getWordCount() method from WordCounterServer.Word.java.
     */
    @Test
    public void testWordCount1(){
        Word the = new Word("THE");
        the.addWordCount();
        assertEquals(1, the.getWordCount());
    }

    /***
     * This tests that the getWordCount() method is returning the anticipated value.
     */
    @Test
    public void testWordCount2(){
        Word the = new Word("THE");
        the.setWordCount(500);
        the.addWordCount();
        assertEquals(501, the.getWordCount());
    }

    /***
     * This tests that the getWordName() method is returning the expected String.
     */
    @Test
    public void testWordName1(){
        Word the = new Word("THE");
        assertEquals("THE", the.getWordName());
    }

    /***
     * This tests that a WordCounterServer.Word object can have it's name changed.
     */
    @Test
    public void testChangeWordName2(){ // this might not be a valuable test
        Word the = new Word("THE");
        the.setWordName("IS"); // it does not seem like changing an existing word object name will happen.
        assertEquals("IS", the.getWordName());
    }

    /***
     * This tests that the setWordCount() method operates as expected.
     */
    @Test
    public void testSetWordGetWordName(){
        Word the = new Word("THE");
        the.setWordCount(25); // setting the word count, should not change the word name.
        assertEquals("THE", the.getWordName());
    }

    /***
     * This tests that the overridden toString() method operated as expected.
     */
    @Test
    public void testToString1(){
        Word the = new Word("THE");
        assertEquals("THE 0", the.toString());
    }

    /***
     * This tests that using the addWordCount() method with the overridden toString() method operates as expected.
     */
    @Test
    public void testToString2(){
        Word the = new Word("THE");
        the.addWordCount();
        assertEquals("THE 1", the.toString());
    }

    /***
     * This tests that the setWordCount() method with the overridden toString() method operate as expected.
     */
    @Test
    public void testToString3(){
        Word the = new Word("THE");
        the.setWordCount(500);
        assertEquals("THE 500", the.toString());
    }

    private StringCleaner sc = new StringCleaner();


    /***
     * This tests that the cleanString() method is removing undesired text from the string.
     */
    @org.junit.Test
    public void cleanStringTest1() {
        String test = "</span>test ";
        assertEquals(" test ", sc.cleanString(test));
    }

    /***
     * This tests that the cleanString() method is removing undesired text from the string.
     */
    @org.junit.Test
    public void cleanStringTest2(){
        String test = "test<br />";
        assertEquals("test ", sc.cleanString(test));
    }

    /***
     * This test verifies that the cleanString() method can remove several different undesired texts from the string.
     */
    @org.junit.Test
    public void cleanStringLoopTest(){
        String test = "</span>test<br /><I><span style=\"margin-left: 20%\">";
        assertEquals(" test   ", sc.cleanString(test));
    }


    /***
     * This test verifies that the the cleanChar() method removes an undesirabe character from the string.
     */
    @org.junit.Test
    public void cleanCharTest1() {
        String test = ";test";
        assertEquals(" test", sc.cleanChar(test));
    }

    /***
     * This test verifies that the the cleanChar() method removes an undesirabe character from the string.
     */
    @org.junit.Test
    public void cleanCharTest2() {
        String test = "test“";
        assertEquals("test ", sc.cleanChar(test));
    }

    /***
     * This test verifies that the cleanChar() method can removes several instances of the same character in the way that is expected.
     */
    @org.junit.Test
    public void cleanCharMultiTest() {
        String test = ";;test;;";
        assertEquals("  test  ", sc.cleanChar(test));
    }

    /***
     * This test verifies that the cleanChar() method removes several different undesirable characters from the same string in a way that is expected.
     */
    @org.junit.Test
    public void cleanCharLoopTest() {
        String test = ".“test;;";
        assertEquals("  test  ", sc.cleanChar(test));
    }

    /***
     * This test verifies that the deleteSpace() method removes escaped characters and a tab.
     */
    @org.junit.Test
    public void deleteEscapedCharactersTest(){
        String test = "\n\n\n\ntest\t"; // all whitespace is replaced with one " "
        assertEquals(" test ", sc.deleteSpace(test));
    }

    /***
     * This tests that several methods operate together as expected. It begins with cleanString(), then cleanChar(), and then it concludes with deleteSpace().
     */
    @org.junit.Test
    public void cleanMultiMethodTestEquals1(){
        String test = "</span><I>\n\n\n\ntest;;.\"";
        assertEquals(" test ", sc.deleteSpace(sc.cleanChar(sc.cleanString(test))));
    }

    /***
     * This tests that several methods operate together as expected. It begins with cleanString(), then cleanChar(), and then it concludes with deleteSpace().
     */
    @org.junit.Test
    public void cleanMultiMethodTestEquals2(){
        String test = "</span><I>\t\t\t\ttest;;.\"<I>";
        assertEquals(" test ", sc.deleteSpace(sc.cleanChar(sc.cleanString(test))));
    }

    /***
     * This tests that several methods operate together as expected. It confirms that the pre-processed String is not the same as the post-processed.
     */
    @org.junit.Test
    public void cleanMultiMethodTestNotEquals(){
        String test = "</span><I>\t\t\t\ttest;;.\"<I>";
        assertNotEquals("</span><I>\t\t\t\ttest;;.\"<I>", sc.deleteSpace(sc.cleanChar(sc.cleanString(test))));
    }

    /***
     * Tests that the WordCounterServer.Connect.getConnection() method is working correctly.
     */
    @Test
    public void testGetConnection1(){
        try {
            Connect.getConnection();
            assertTrue(true); // If this statement is reached then it passes.
        } catch (Exception e) {
            fail();
        }
    }

    /***
     * Tests that the WordCounterServer.Connect.getConnection() method is working correctly.
     */
    @Test
    public void testGetConnection2(){
        try {
            Connect connect = new Connect();
            connect.getConnection();
            assertEquals("WordCounterServer.Connect", connect.toString());
            assertTrue(true); // If this statement is reached then it passes.
        } catch (Exception e) {
            fail();
        }
    }

    /***
     * Tests that the WordCounterServer.Connect.createTable() method is working correctly.
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
     * Tests that the WordCounterServer.Connect.insertWord() method is working correctly.
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
     * Tests that the WordCounterServer.Connect.deleteAll() method is working correctly.
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
     * Tests that the WordCounterServer.Connect.getList() method is functioning correctly.
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

    /***
     * This tests that the WordCounterServer.StringCleaner.cleanThisString method is functioning correctly.
     */
    @Test
    public void testCleanThisStringMethod1(){
        String content = "This </span> of";
        StringCleaner sc = new StringCleaner();
        try {
            content = sc.cleanThisString(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("THIS OF\n", content);
    }

    /***
     * This tests that the WordCounterServer.StringCleaner.cleanThisString method is functioning correctly.
     */
    @Test
    public void testCleanThisStringMethod2(){
        String content = "This </span> of <i> &mdash;";
        StringCleaner sc = new StringCleaner();
        try {
            content = sc.cleanThisString(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("THIS OF \n", content);
    }

    /***
     * This tests that the WordCounterServer.WordCounter.countWords method is functioning correctly.
     */
    @Test
    public void testCountWords(){
        String content = "THE THIS OF THAT OF THIS THIS";
        WordCounter wc = new WordCounter();
        ArrayList<Word> arraylist = new ArrayList();

        try {
            arraylist = wc.countWords(content, arraylist, 6);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals("[THE 1, THIS 3, OF 2, THAT 1]", arraylist.toString());
    }

    /***
     * This tests the WordCounterServer.Connect.deleteArtifacts. If this doesn't throw an exception then it can be considered a pass. If this test is failing it probably means that the java application is not connecting to the database.
     */
    @Test
    public void testDeleteArtifacts(){
        try {
            assertDoesNotThrow(Connect:: deleteArtifacts);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * This tests the WordCounterServer.Connect.dropTable. If this doesn't throw an exception then it can be considered a pass. If this test is failing it probably means that the java application is not connecting to the database.
     */
    @Test
    public void testDropTable(){
        try {
            Connect.createTable();
            assertDoesNotThrow(Connect:: dropTable);
            Connect.createTable();
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}