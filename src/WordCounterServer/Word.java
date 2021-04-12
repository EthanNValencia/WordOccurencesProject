/*
File: WordCounterServer.Word.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class is used to save the words as objects.
*/

package WordCounterServer;

import java.util.Objects;

/***
 * WordCounterServer.Word object that is used to store word name and occurrences.
 */
public class Word {
    private String wordName;
    private int wordCount;


    public Word(String wordName, int wordCount){
        this.wordName = wordName;
        this.wordCount = wordCount;
    }

    /***
     * This method is used to compare word objects.
     * @param o It should be passed a word object.
     * @return It will return whether or not the objects are equal (true/false).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return wordName.equals(word.wordName);
    }

    /***
     * Method to determine the hashcode of a word object.
     * @return Returns the hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(wordName);
    }

    /***
     * Method to translate the object variables to a string.
     * @return Returns the string.
     */
    @Override
    public String toString() {
        return wordName + " " + wordCount;
    }

    /***
     * Constructor method for a word object.
     * @param wordName The word object requires a name; the word.
     */
    public Word(String wordName) {
        this.wordName = wordName;
    }

    /***
     * Increments the wordCount variable of the word object.
     */
    public void addWordCount(){
        this.wordCount++;
    }

    /***
     * WordCounterServer.Word name getter that returns the word name. Depending on class usage, toString() can make this unnecessary.
     * Useful for testing purposes.
     * @return Returns the name of a word object.
     */
    public String getWordName() {
        return wordName;
    }

    /***
     * WordCounterServer.Word name object setter.
     * Useful for testing purposes.
     * @param wordName Used to change the word object name.
     */
    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    /***
     * WordCounterServer.Word count getter method.
     * @return Returns the count int variable of a word object.
     */
    public int getWordCount() {
        return wordCount;
    }

    /***
     * Method used to manually set word count occurences.
     * Useful for testing purposes.
     * @param wordCount Set the number count of a word object
     */
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
}
