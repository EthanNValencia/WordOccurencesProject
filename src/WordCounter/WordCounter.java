/*
File: WordCounter.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class is used to count the words from a post-filtered txt file.
*/


package WordCounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/***
 * This class is used to count post-normalized text from a specified .txt directory.
 */
public class WordCounter implements CONSTANTS{

    /***
     * This method counts the word occurrences in a pre-normalized text file. It saves the occurence count in an ArrayList and it sorts the occurrences from most frequent to least frequent. The method concludes with writing the ArrayList to a text file.
     * @throws IOException This method can throw an IOException.
     */
    public void countTheWords() throws IOException {
        ArrayList<Word> wordList = new ArrayList<Word>();
        File file = new File(normalizedDirectory);
        wordList = countWords(file, wordList, 1087);
        try {
            Connect.deleteAll();
            Connect.writeArrayList(wordList); // writes to database
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WriteToFile.writeThisToFile(wordReportDirectory, wordList.toString());
    }

    /***
     * This method uses the JCF to sort word objects in an Array List.
     * @param wordList The method must be passed a populated Array List of Word objects that is to be sorted.
     * @return The sorted Array List of Word objects is returned.
     */
    public ArrayList<Word> sortWords(ArrayList<Word> wordList){
        Collections.sort(wordList, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return Integer.valueOf(o2.getWordCount()).compareTo(o1.getWordCount());
            }
        });
        return wordList;
    }

    /***
     * This method takes a File object and an Array List. It reads the file and counts the words in the file. The file must be normalized to be counted.
     * @param file The file object is the file that contain the words that are to be processed.
     * @param wordList The Array List will be populated and returned.
     * @param expectednumber This is the number of words that the user expects to be counted.
     * @return The Array List will be populated as words are counted and returned.
     * @throws FileNotFoundException If the file that is to be read is not found, then it will throw this exception.
     */
    public ArrayList<Word> countWords(File file, ArrayList<Word> wordList, int expectednumber) throws FileNotFoundException {
        int count=0;
        Scanner scan = new Scanner(new FileInputStream(file));
            while(scan.hasNext()){
                Word nextWord = new Word(scan.next());
                if(wordList.contains(nextWord)) {
                    wordList.get(wordList.indexOf(nextWord)).addWordCount();
                } else {
                    nextWord.addWordCount();
                    wordList.add(nextWord);
                }
                count++; // this is just for counting the total amount of words in the file.
        }
            System.out.println("Number of words expected: " + expectednumber + ". Number of words counted: " + count);
            return wordList;
    }


}
