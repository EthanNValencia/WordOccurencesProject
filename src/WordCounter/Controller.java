/*
File: Controller.java
Author: Ethan J. Nephew
Date due: April 4, 2021
Course: CEN-3024C
Description: This class is used to control the GUI and associate GUI components with the backend code.
*/

package WordCounter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
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
    @FXML
    private Button connect;
    @FXML
    private Button noConnect;
    @FXML
    private Button submitBtn;
    @FXML
    private TextField ipEntry;
    @FXML
    private Button restartBtn, quitBtn;

    private String displayContent;
    private String hostIPAddress = "";
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;


    public void displayToLabel(String name, int count){
        displayContent = displayContent.concat(name + " " + count + ", ");
        textArea.setText(displayContent);
        return;
    }

    /***
     * This method controls GUI functions by disabling the begin button, enabling the clean button, sets the explanation label, and displays the downloaded text to the text area. It calls the method that handles the downloading of the page source.
     * @throws FileNotFoundException A file not found exception is a critical application failure. The application cannot continue.
     */
    public void downloadPage() throws FileNotFoundException {
        labelExplain.setText(
                "The text has been downloaded. " +
                "Continue to text normalization. " +
                "Use the Clean Text button to continue.");
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
        labelExplain.setText(
                "The text has been cleaned. " +
                "Continue by inserting, extracting, and displaying the data. " +
                "Continue by clicking the Count Words button.");
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
        labelExplain.setText(
                "The words have been counted. " +
                "This is a list of the words ranked from highest to lowest. " +
                "You may re-run the program with the Download HTML button");
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
        initButtons();
    }

    /***
     * I migrated the initialize method contents to another method, because I wanted to extend the initialize functionality to the restart button.
     */
    public void initButtons(){
        cleanBtn.setVisible(false);
        countBtn.setVisible(false);
        textArea.setVisible(false);
        beginBtn.setVisible(false);
        ipEntry.setVisible(false);
        submitBtn.setVisible(false);
        connect.setVisible(true);
        noConnect.setVisible(true);
        labelExplain.setText(
                "If you have the server running and you wish to connect click: Connect " +
                        "If you wish to run without the server click: Do Not Connect");
    }

    /***
     * If the user decides to not connect, then the application will run without the server support.
     */
    public void doNotConnect(){
        connect.setVisible(false);
        noConnect.setVisible(false);
        cleanBtn.setVisible(true);
        countBtn.setVisible(true);
        textArea.setVisible(true);
        beginBtn.setVisible(true);
        cleanBtn.setDisable(true);
        countBtn.setDisable(true);
        labelExplain.setText(
                "Begin by downloading the web page source document. " +
                        "Click the Download HTML button");
        textArea.setEditable(false);
        textArea.setWrapText(true);
    }

    /***
     * If the user decides to connect to a server, then they will be required to enter the IP address to connect to the server.
     */
    public void getIP(){
        connect.setVisible(false);
        noConnect.setVisible(false);
        labelExplain.setText(
                "Please enter the IP address of the server. " +
                "\nThis will be supplied to you in the server user interface.");
        submitBtn.setVisible(true);
        ipEntry.setVisible(true);
    }

    /***
     * This method will begin the connection process and catch the exception if the connection fails.
     */
    public void beginConnection(){
        try {
            attemptServerConnection();
        } catch (IOException e) {
            labelExplain.setText("The IP address you have entered in incorrect. Please Try again. \n" +
                    "If you are running the server on a different computer, " +
                    "then you may need to change your firewall settings.");
        }
    }

// 192.168.0.95

    /***
     * This is the actual client method that connects to the server. It will connect and the server will send the client the string.
     * @throws IOException Input/Output exception could be thrown.
     */
    public void attemptServerConnection() throws IOException {
        Socket socket = new Socket(ipEntry.getText(), 8000);
        ipEntry.setVisible(false);
        submitBtn.setVisible(false);
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String list = dataInputStream.readUTF();
        socket.close();
        textArea.setWrapText(true);
        textArea.setText(list);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textArea.setVisible(true);
        labelExplain.setText("Your client application has received the textual data from the server.");
    }

    public void quitSystem(){
        Platform.exit();
        System.exit(0);
    }

}
