/*
File: WebSaverTest.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class contains the tests for the WebSaver class.
*/

package WordCounter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/***
 * These are the tests for the WebSaver class. It it difficult to test some of the classes in this method, because they are used to read a webpage. To best test this class it would be best to create my own website that can be read from.
 */
class WebSaverTest {

    /***
     * This tests that the checkLineContent() method is processing the string as expected.
     */
    @Test
    public void checkLineContentTest1() {
        String test = "<br /> This is a test.";
        assertTrue(WebSaver.checkLineContent(test));
    }

    /***
     * This tests that the checkLineContent() method is processing the string as expected.
     */
    @Test
    public void checkLineContentTest2() {
        String test = "<span This is a test. <div";
        assertFalse(WebSaver.checkLineContent(test));
    }

    /***
     * This tests that the checkLineContent() method is processing the string as expected.
     */
    @Test
    public void checkLineContentTest3() {
        String test = "This is a test. <span style=\"margin-left: 20%\">";
        assertTrue(WebSaver.checkLineContent(test));
    }
}