/*
File: WordCounterServer.WordCounter.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class is used to count the words from a post-filtered string.
*/

package WordCounterServer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/***
 * This class is used to count post-normalized text from a specified .txt directory.
 */
public class WordCounter implements CONSTANTS{

    /***
     * This method takes a string object, an Array List, and the expected total number count. It reads the string and counts the words in the file. The string must be normalized to be counted.
     * @param content This is the string that will be processed.
     * @param wordList The Array List will be populated and returned.
     * @param expectedNumber This is the number of words that the user expects to be counted.
     * @return The Array List will be populated as words are counted and returned.
     * @throws FileNotFoundException If the file that is to be read is not found, then it will throw this exception.
     */
    public ArrayList<Word> countWords(String content, ArrayList<Word> wordList, int expectedNumber) throws FileNotFoundException {
        int count = 0;
        Scanner scan = new Scanner(new String(content));
        while(scan.hasNext()){
            Word nextWord = new Word(scan.next());
            if(wordList.contains(nextWord)) {
                wordList.get(wordList.indexOf(nextWord)).addWordCount();
            } else {
                nextWord.addWordCount();
                wordList.add(nextWord);
            }
            count++; // this is just for counting the total amount of words in the file.
            // It seems that if I run this server as a .jar, the expected number will be higher.
        }
        // System.out.println("Number of words expected: " + expectedNumber + ". Number of words counted: " + count);
        return wordList;
    }



}
