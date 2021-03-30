/*
File: WriteToFileTest.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class is used to test the WriteToFile class.
*/

package WordCounter;

import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/***
 * This class tests the WriteToFile.java class.
 */
class WriteToFileTest {


    /***
     * This tests that the WriteToFile class method can create a file and write to that file.
     */
    @Test
    public void writeToFileTestEquals() {
        String content = "This is a test.";
        try {
            WriteToFile.writeThisToFile("testWriteToFile.txt", content);
            Scanner scan = new Scanner(new FileInputStream("testWriteToFile.txt"));
            assertEquals("This is a test.", scan.nextLine());
        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }
    }

    /***
     * This tests that the WriteToFile class method can create and file and write to that file.
     */
    @Test
    public void writeToFileTestNotEquals(){
        String content = "This is a test.";
        try {
            WriteToFile.writeThisToFile("testWriteToFile.txt", content);
            Scanner scan = new Scanner(new FileInputStream("testWriteToFile.txt"));
            assertNotEquals("THis is a test.", scan.nextLine());
        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }

    }

}