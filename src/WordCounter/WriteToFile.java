/*
File: WriteToFile.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class is used to write a string to a specified txt file directory. This class is not program specific, so it can be used in other programs.
*/

package WordCounter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/***
 * Class definition used to write a string to a text file.
 */
public class WriteToFile {

    /***
     * Method that receives a string and writes that string to a text file at the specified directory.
     * @param directory Requires the path directory for the file to be written to.
     * @param content Requires the string of content that is to be written to the file.
     * @throws IOException Throws an input output exception, which is a critical failure.
     */
    public static void writeThisToFile(String directory, String content) throws IOException {
        FileWriter fw = new FileWriter(directory);
        PrintWriter writer = new PrintWriter(directory); // this will erase the file
        writer.print("");
        writer.close();
        fw.write(content);
        fw.close();
        // This relies heavily on external libraries. It might not be in my scope to test this.
    }
}
