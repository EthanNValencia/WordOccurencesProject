/*
File: Controller.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class is used to control the GUI and associate GUI components with the backend code.
*/

package WordCounter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

/***
 * Controller for the graphical user interface. Requires the JavaFX library to operate.
 */
public class Controller implements CONSTANTS, Initializable {

    @FXML
    private TextArea textArea;
    @FXML
    private Button beginBtn;
    @FXML
    private Button cleanBtn;
    @FXML
    private Button countBtn;
    @FXML
    private Label labelExplain;

    private String displayContent;

    public void displayToLabel(String name, int count){
        displayContent = displayContent.concat(name + " " + count + ", ");
        textArea.setText(displayContent);
        return;
    }

    /***
     * This method controls GUI functions by disabling the begin button, enabling the clean button, sets the explanation label, and displays the downloaded text to the text area. It calls the method that handles the downloading of the page source.
     * @param event This event is initiated by the begin button.
     * @throws FileNotFoundException A file not found exception is a critical application failure. The application cannot continue.
     */
    public void downloadPage(ActionEvent event) throws FileNotFoundException {
        labelExplain.setText("The text has been downloaded. \nContinue to text normalization.");
        beginBtn.setDisable(true);
        WebSaver.writePageToFile();
        String display = displayText(downloadDirectory);
        textArea.setText(display);
        cleanBtn.setDisable(false);
    }

    /***
     * This method controls GUI functions by disabling the clean button, enabling the count button, sets the explanation label, and displays the cleaned/normalized text to the text area. It calls the method that handles the text normalization.
     */
    public void cleanText() {
        labelExplain.setText("The text has been cleaned. \nContinue by counting the word occurrences.");
        cleanBtn.setDisable(true);
        StringCleaner sc = new StringCleaner();
        try {
            sc.cleanText(downloadDirectory);
            String display = displayText(normalizedDirectory);
            textArea.setText(display);
        } catch (IOException e) {
            e.printStackTrace();
        }
        countBtn.setDisable(false);
    }

    /***
     * This method controls GUI functions by disabling the count button, enabling the begin button, sets the explanation label, and displays the list of word occurrences to the text area. It calls the method that counts the word occurrences.
     * @throws FileNotFoundException A file not found exception is a critical application failure. The application cannot continue.
     */
    public void countWords() throws IOException {
        labelExplain.setText("The words have been counted. \nThis is a list of the words ranked from highest to lowest.");
        countBtn.setDisable(true);
        WordCounter wc = new WordCounter();
        wc.countTheWords();
        ArrayList<Word> displayList = new ArrayList<Word>();
        try {
            displayList = Connect.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        textArea.setText(displayList.toString());
        beginBtn.setDisable(false);
    }

    /***
     * This is a method that reads a text file and returns it as a string.
     * @param directory This is the text file directory. Refer to CONSTANTS.java for specifications.
     * @return It returns the text contained in the specified file directory.
     * @throws FileNotFoundException A file not found exception is a critical application failure. The application cannot continue.
     */
    public static String displayText(String directory) throws FileNotFoundException {
        File file = new File(directory);
        String reader = "";
        String content = "";
        try (Scanner scan = new Scanner(new FileInputStream(file))) {
            while (scan.hasNextLine()) {
                reader = scan.nextLine() + "\n";
                content = content.concat(reader);
            }
        }
        return content;
    }

    /***
     * This method provides baseline GUI settings. Disables the clean button and the count button, sets the explanation label, and disables editing of the text area.
     * @param location The location used to resolve root object paths.
     * @param resources The resources that are used on the root object.
     */
    public void initialize(URL location, ResourceBundle resources){
        cleanBtn.setDisable(true);
        countBtn.setDisable(true);
        labelExplain.setText("Begin by downloading the web page source document.");
        textArea.setEditable(false);
        textArea.setWrapText(true);
    }
}
