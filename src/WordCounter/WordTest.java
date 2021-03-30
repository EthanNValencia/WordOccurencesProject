/*
File: WordTest.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class is used to verify that the Word objet is functioning correctly.
*/

package WordCounter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/***
 * This test class is used to verify the functionality of the Word.java class.
 */
class WordTest {

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
        assertEquals("THE 0\n", the.toString());
    }

    /***
     * This tests that using the addWordCount() method with the overridden toString() method operates as expected.
     */
    @Test
    public void testToString2(){
        Word the = new Word("THE");
        the.addWordCount();
        assertEquals("THE 1\n", the.toString());
    }

    /***
     * This tests that the setWordCount() method with the overridden toString() method operate as expected.
     */
    @Test
    public void testToString3(){
        Word the = new Word("THE");
        the.setWordCount(500);
        assertEquals("THE 500\n", the.toString());
    }

}