/*
File: WordCounterServer.StringCleaner.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class contains the methods that are used to filter text and remove undesirable text.
*/

package WordCounterServer;

import java.io.*;

/***
 * Class for normalizing text. Text normalization largely consists of removing known undesirable strings and characters from the text.
 */
public class StringCleaner implements CONSTANTS{

    /***
     * I need to remove the file read and write requirements and just pass the string instead
     * @param text This is the directory that the webpage text was saved to.
     * @throws IOException This method can throw an IOException.
     * @return It returns a string that has had the known undesirable text removed.
     */
    public String cleanThisString(String text) throws IOException {
        String reader = text;
        String content = "";
        reader = cleanString(reader);
        reader = cleanChar(reader);
        reader = deleteSpace(reader);
        reader = reader.toUpperCase(); // converts all to upper case
        content = content.concat(reader); // saves all the changes to the content string
        return content;
    }

    /***
     * This method cleans a string of any known undesirable text that is contained in the stringArray constant. This is used for tests.
     * @param reader It requires a string that will be processed.
     * @return It returns a string that has had the known undesirable text removed.
     */
    public String cleanString(String reader){
        for (int i = 0; i < stringArray.length; i++){
            if (reader.contains(stringArray[i])){
                reader = reader.replaceAll(stringArray[i], " ");
            }
        }
        return reader;
    }

    /***
     * This method behaves similar to the cleanString() method, but it removes undesirable characters that are identified in the charArray constant
     * @param reader It requires a string that will be processed.
     * @return It returns a string that has had all the undesirable characters removed.
     */
    public String cleanChar(String reader){
        // This method contains critical functionality.
        for (int i = 0; i < charArray.length; i++){
            reader = reader.replace(charArray[i], ' ');
        }
        return reader;
    }

    /***
     * This method removes excessive spaces. This method is not critical to the programs functionality, but it does make processed text easier for the user to read.
     * @param reader It requires a string that will be processed.
     * @return It will return a string that has had excessive spaces replaced with single spaces.
     */
    public String deleteSpace(String reader){
        // This method contains critical functionality.
        reader = reader.replaceAll("\\s+", " ");
        return reader;
    }
}
