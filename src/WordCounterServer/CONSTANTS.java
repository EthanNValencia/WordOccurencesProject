/*
File: WordCounterServer.CONSTANTS.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class contains the various constants for the word counter application. Such as: directories and arrays of content that will be deleted.
*/

package WordCounterServer;

/***
 * Constants interface that is used to provide easy of use for changing the web page URL and directory locations. Contains the stringArray and charArray, these arrays are used to specify text deletion. If the user is experience I/O exceptions, modifying the directories is a viable troubleshooting technique.
 */
public interface CONSTANTS {

    /***
     *  This is the URL path of the website that the program will process.
     */
    String websiteURL = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
    /***
     *  This is the array of strings that the program will delete during the text normalization process.
     */
    String[] stringArray = new String[]{"<br />", "Â€", "???", "<I>", "&mdash;", "</i>", "<i>", "</span>", "<span style=\"margin-left: 20%\">"};
    /***
     * This is the array of chars that the program will delete during the text normalization process.
     */
    char[] charArray = {'”', ',', '.', '\'', ';', '\"', '!', '?', '“'};
}
