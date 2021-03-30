/*
File: StringCleanerTest.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This is the test file for the StringCleaner class.
*/

package WordCounter;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;

/***
 * This test verifies that the methods in StringCleaner.java are functioning as expected.
 */
public class StringCleanerTest implements CONSTANTS{

    private StringCleaner sc = new StringCleaner();

    /***
     * This test verifies that a FileNotFoundException will be thrown if an invalid directory is provided.
     */
    @Test
    public void noFileTest() {
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            sc.cleanText("file location that does not exist");
        });
    }

    /***
     * This tests that the cleanString() method is removing undesired text from the string.
     */
    @Test
    public void cleanStringTest1() {
        String test = "</span>test ";
        assertEquals(" test ", sc.cleanString(test));
    }

    /***
     * This tests that the cleanString() method is removing undesired text from the string.
     */
    @Test
    public void cleanStringTest2(){
        String test = "test<br />";
        assertEquals("test ", sc.cleanString(test));
    }

    /***
     * This test verifies that the cleanString() method can remove several different undesired texts from the string.
     */
    @Test
    public void cleanStringLoopTest(){
        String test = "</span>test<br /><I><span style=\"margin-left: 20%\">";
        assertEquals(" test   ", sc.cleanString(test));
    }


    /***
     * This test verifies that the the cleanChar() method removes an undesirabe character from the string.
     */
    @Test
    public void cleanCharTest1() {
        String test = ";test";
        assertEquals(" test", sc.cleanChar(test));
    }

    /***
     * This test verifies that the the cleanChar() method removes an undesirabe character from the string.
     */
    @Test
    public void cleanCharTest2() {
        String test = "test“";
        assertEquals("test ", sc.cleanChar(test));
    }

    /***
     * This test verifies that the cleanChar() method can removes several instances of the same character in the way that is expected.
     */
    @Test
    public void cleanCharMultiTest() {
        String test = ";;test;;";
        assertEquals("  test  ", sc.cleanChar(test));
    }

    /***
     * This test verifies that the cleanChar() method removes several different undesirable characters from the same string in a way that is expected.
     */
    @Test
    public void cleanCharLoopTest() {
        String test = ".“test;;";
        assertEquals("  test  ", sc.cleanChar(test));
    }

    /***
     * This test verifies that the deleteSpace() method removes escaped characters and a tab.
     */
    @Test
    public void deleteEscapedCharactersTest(){
        String test = "\n\n\n\ntest\t"; // all whitespace is replaced with one " "
        assertEquals(" test ", sc.deleteSpace(test));
    }

    /***
     * This tests that several methods operate together as expected. It begins with cleanString(), then cleanChar(), and then it concludes with deleteSpace().
     */
    @Test
    public void cleanMultiMethodTestEquals1(){
        String test = "</span><I>\n\n\n\ntest;;.\"";
        assertEquals(" test ", sc.deleteSpace(sc.cleanChar(sc.cleanString(test))));
    }

    /***
     * This tests that several methods operate together as expected. It begins with cleanString(), then cleanChar(), and then it concludes with deleteSpace().
     */
    @Test
    public void cleanMultiMethodTestEquals2(){
        String test = "</span><I>\t\t\t\ttest;;.\"<I>";
        assertEquals(" test ", sc.deleteSpace(sc.cleanChar(sc.cleanString(test))));
    }

    /***
     * This tests that several methods operate together as expected. It confirms that the pre-processed String is not the same as the post-processed.
     */
    @Test
    public void cleanMultiMethodTestNotEquals(){
        String test = "</span><I>\t\t\t\ttest;;.\"<I>";
        assertNotEquals("</span><I>\t\t\t\ttest;;.\"<I>", sc.deleteSpace(sc.cleanChar(sc.cleanString(test))));
    }

}