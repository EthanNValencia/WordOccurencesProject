/*
File: StringCleaner.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class contains the methods that are used to filter text and remove undesirable text.
*/

package WordCounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/***
 * Class for normalizing text. Text normalization largely consists of removing known undesirable strings and characters from the text.
 */
public class StringCleaner implements CONSTANTS{

    /***
     * Method that reads from a text file. Deletes specified html tags and punctuation. Concludes by setting text to upper case.
     * @param downloadDirectory This is the directory that the webpage text was saved to.
     * @throws IOException This method can throw an IOException.
     */
    public void cleanText(String downloadDirectory) throws IOException {
        File file = new File(downloadDirectory);
        Scanner scan = new Scanner(new FileInputStream(file));
        String reader = "";
            String content = "";
            while (scan.hasNextLine()) {
                reader = scan.nextLine() + "\n";
                reader = cleanString(reader);
                reader = cleanChar(reader);
                reader = deleteSpace(reader);
                reader = reader + "\n"; // ensures that the reader continues to the next line
                reader = reader.toUpperCase(); // converts all to upper case
                content = content.concat(reader); // saves all the changes to the content string
            }
            WriteToFile.writeThisToFile(normalizedDirectory, content);
    }

    /***
     * This method cleans a string of any known undesirable text that is contained in the stringArray constant.
     * @param reader It requires a string that will be processed.
     * @return It returns a string that has had the known undesirable text removed.
     */
    public String cleanString(String reader){
        // This method contains critical functionality.
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
