/*
File: WordCounterTest.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class is used to test WordCounter class and verify that it is functioning correctly.
*/

package WordCounter;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/***
 * Class for testing the WordCounter.java class.
 */
class WordCounterTest {

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
        assertEquals("[THE 40\n, IS 35\n]", wc.sortWords(wordList).toString());
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
        assertEquals("[THE 3\n, IS 2\n, A 2\n, I 2\n, YOU 1\n]", arrayList.toString());
    }

}