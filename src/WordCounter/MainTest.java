package WordCounter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


/***
 * When I initially wrote my unit tests I had them run in several different class folders. This makes it tedious so I moved them all to one class.
 */
class MainTest {

    /***
     * This tests that the WriteToFile class method can create a file and write to that file.
     */
    @Test
    public void writeToFileTestEquals() {
        String content = "This is a test.";
        try {
            WriteToFile.writeThisToFile("testWriteToFile.txt", content);
            Scanner scan = new Scanner(new FileInputStream("testWriteToFile.txt"));
            assertEquals("This is a test.", scan.nextLine());
        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }
    }

    /***
     * This tests that the WriteToFile class method can create and file and write to that file.
     */
    @Test
    public void writeToFileTestNotEquals(){
        String content = "This is a test.";
        try {
            WriteToFile.writeThisToFile("testWriteToFile.txt", content);
            Scanner scan = new Scanner(new FileInputStream("testWriteToFile.txt"));
            assertNotEquals("THis is a test.", scan.nextLine());
        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }

    }

    /***
     * This tests that the WebSaver.checkLineContent() method is processing the string as expected.
     */
    @Test
    public void checkLineContentTest1() {
        String test = "<br /> This is a test.";
        assertTrue(WebSaver.checkLineContent(test));
    }

    /***
     * This tests that the WebSaver.checkLineContent() method is processing the string as expected.
     */
    @Test
    public void checkLineContentTest2() {
        String test = "<span This is a test. <div";
        assertFalse(WebSaver.checkLineContent(test));
    }

    /***
     * This tests that the WebSaver.checkLineContent() method is processing the string as expected.
     */
    @Test
    public void checkLineContentTest3() {
        String test = "This is a test. <span style=\"margin-left: 20%\">";
        assertTrue(WebSaver.checkLineContent(test));
    }

    /***
     * This test verifies that if a non-Word object gets compared to a Word object, that they will not be equal, even if they have similarities.
     */
    @Test
    public void voidTestNonWordObject(){
        Word word = new Word("THE");
        String string = "THE";
        assertFalse(word.equals(string));
    }

    /***
     * This tests overridden equals() method from Word.java.
     */
    @Test
    public void testBooleanEquals1(){
        Word word1 = new Word("THE");
        Word word2 = new Word("THE");
        assertTrue(word1.equals(word2));
    }

    /***
     * This tests that hashCode() method from Word.java does not return the word name.
     */
    @Test
    public void testHash1(){
        Word the = new Word("THE");
        assertNotEquals("THE", the.hashCode());
    }

    /***
     * This tests that two hashCode() method calls from the same Word object return the same hashcode.
     */
    @Test
    public void testHash2(){
        Word the = new Word("THE");
        assertEquals(the.hashCode(), the.hashCode()); // is this a useful test?
    }

    /***
     * This tests the addWordCount() method and getWordCount() method from Word.java.
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
     * This tests that a Word object can have it's name changed.
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
     * This test verifies that a FileNotFoundException will be thrown if an invalid directory is provided.
     */
    @org.junit.Test
    public void noFileTest() {
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            sc.cleanText("file location that does not exist");
        });
    }

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
     * This tests the sortWords method from WordCounter.java. It also makes use of methods from Word.java.
     */
    @Test
    public void sortWordsTest() {
        WordCounter wc = new WordCounter();
        ArrayList<Word> wordList = new ArrayList<Word>();
        Word is = new Word("IS");
        is.setWordCount(35);
        Word the = new Word("THE");
        the.setWordCount(40);
        wordList.add(is);
        wordList.add(the);
        assertEquals("[THE 40, IS 35]", wc.sortWords(wordList).toString());
    }

    /***
     * This tests the countWords() method from WordCounter.java. It also uses methods from WriteToFile.java.
     */
    @Test
    public void countWordsTest() {
        String testString = "THE THE THE IS IS A A I I YOU";
        File file = new File("testfile.txt");
        ArrayList<Word> arrayList = new ArrayList<Word>();
        try {
            WriteToFile.writeThisToFile("testfile.txt", testString);
            WordCounter wc = new WordCounter();
            arrayList = wc.countWords(file, arrayList, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("[THE 3, IS 2, A 2, I 2, YOU 1]", arrayList.toString());
    }

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