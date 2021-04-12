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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
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
    private TextArea textExplain;
    @FXML
    private Button beginBtn;
    @FXML
    private Button cleanBtn;
    @FXML
    private Button countBtn;
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
    @FXML
    private Button option1, option2, option3, helpCenterBtn, opt2Next, opt2Back;

    @FXML
    private ImageView imageView;

    private int helpCenterIndex = 0;
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
        textExplain.setText(
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
        textExplain.setText(
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
        textExplain.setText(
                "The words have been counted. " +
                "This is a list of the words ranked from highest to lowest. " +
                "You may re-run the program with the Download HTML button. Alternatively, you may click the Restart button to connect to the server.");
        countBtn.setDisable(true);
        WordCounter wc = new WordCounter();
        wc.countTheWords();
        ArrayList<Word> displayList = new ArrayList<Word>();
        try {
            Connect.deleteArtifacts();
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
        textExplain.setWrapText(true);
        opt2Next.setVisible(false);
        opt2Back.setVisible(false);
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        imageView.setVisible(false);
        helpCenterBtn.setVisible(false);
        cleanBtn.setVisible(false);
        countBtn.setVisible(false);
        textArea.setVisible(false);
        beginBtn.setVisible(false);
        ipEntry.setVisible(false);
        submitBtn.setVisible(false);
        connect.setVisible(true);
        noConnect.setVisible(true);
        textExplain.setText(
                "If you have the server running and you wish to connect click: Connect \n" +
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
        textExplain.setText(
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
        textExplain.setText(
                "Please enter the IP address of the server. " +
                "This will be supplied to you in the server user interface.");
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
            imageView.setVisible(false);
            helpCenterBtn.setVisible(false);
            option1.setVisible(true);
            option2.setVisible(true);
            option3.setVisible(true);
            textExplain.setText(helpCenterText);
        }
    }

    /***
     * This method displays the help center menu buttons as well as supportive text. It deactivates and activates various buttons.
     */
    public void returnToHelpCenter(){
        opt2Next.setVisible(false);
        opt2Back.setVisible(false);
        helpCenterBtn.setVisible(false);
        imageView.setVisible(false);
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
        textExplain.setText(helpCenterText);

    }

// 192.168.0.95

    /***
     * This is the actual client method that connects to the server. It will connect and the server will send the client the string.
     * @throws IOException Input/Output exception could be thrown.
     */
    public void attemptServerConnection() throws IOException {
        Socket socket = new Socket(ipEntry.getText(), 8000);
        ipEntry.setVisible(false);
        helpCenterBtn.setVisible(false);
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
        textExplain.setText("Your client application has received the textual data from the server.");
    }

    /***
     * This is used to safely terminate the system.
     */
    public void quitSystem(){
        Platform.exit();
        System.exit(0);
    }

    /***
     * This is used to set the help options to invisible.
     */
    public void setHelpOptionsInvisible(){
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        helpCenterBtn.setVisible(false);
    }

    /***
     * This method is used to navigate the option 1 of the help center menu.
     */
    public void helpCenter_Option1(){
        setHelpOptionsInvisible();
        textExplain.setText("This is an example of what the Server IP address will look like in the Server interface. " +
                "Please enter your Server's IP address into the text field and submit it.");
        Image image = new Image("WordCounter/JavaProjectHelpImages/Option1pic1.png");
        imageView.setVisible(true);
        imageView.setImage(image);
        helpCenterBtn.setVisible(true);
    }

    /***
     * This method is used to navigate the option 1 of the help center menu.
     * @param event This function can be caused by two controls, so it requires the event parameter to differentiate origins.
     */
    public void helpCenter_Option2(ActionEvent event){
        boolean go = true;

        if (event.getSource().equals(option2)) {
            helpCenterIndex = 1;
            opt2Next.setVisible(true);
            opt2Back.setVisible(true);
        } else if (event.getSource().equals(opt2Next)){
            helpCenterIndex++;
        } else if (event.getSource().equals(opt2Back)){
            helpCenterIndex--;
        }

        while(go){
            setHelpOptionsInvisible();
            textExplain.setText(getNextOption(helpCenterIndex));
            Image image = new Image("WordCounter/JavaProjectHelpImages/Option2pic" + helpCenterIndex + ".png");
            imageView.setVisible(true);
            imageView.setImage(image);
            helpCenterBtn.setVisible(true);
            go = false;
        }
    }

    /***
     * This is used to switch between option 2 sections in the help center.
     * @param position It requires the position index.
     * @return It returns a string that will be assigned to the label.
     */
    public String getNextOption(int position){
        if (position == 1){
            opt2Back.setVisible(true);
            opt2Back.setDisable(true);
            opt2Next.setVisible(true);
            opt2Next.setDisable(false);
        } else if (position > 1 && position < 13){
            opt2Back.setVisible(true);
            opt2Back.setDisable(false);
            opt2Next.setVisible(true);
            opt2Next.setDisable(false);
        } else if (position == 13){
            opt2Back.setVisible(true);
            opt2Back.setDisable(false);
            opt2Next.setVisible(true);
            opt2Next.setDisable(true);
        }
        return getHelpMessage(position);
    }

    /***
     * This is a series of conditions that are used to determine what text will be displayed when navigating the option 2 help center.
     * @param position It requires the position of the index in the option 2 help center.
     * @return It returns the label that is associated with the provided index.
     */
    public String getHelpMessage(int position){
        if (position == 1){
            return "Begin by searching windows defender firewall in your start menu.";
        } else if (position == 2){
            return "Select Advanced settings.";
        } else if (position == 3){
            return "Create new Inbound Rule by selecting New Rule.";
        } else if (position == 4){
            return "Select Port and click next.";
        } else if (position == 5 || position == 10){
            return "Select TCP and specify the port number of 8000.";
        } else if (position == 6 || position == 11){
            return "Allow the connection.";
        } else if (position == 7 || position == 12){
            return "If you are running this software on a home network then select Private.";
        } else if (position == 8 || position == 13){
            return "Create a name and description. Your name and description does not need to be exactly what I wrote. Click finish to create the new rule.";
        } else if (position == 9){
            return "Next create a new Outbound Rule.";
        }
        return null;
    }

    /***
     * This method is used to navigate the option 3 of the help center menu.
     */
    public void helpCenter_Option3(){
        setHelpOptionsInvisible();
        textExplain.setText("Port forwarding is difficult and can vary between routers. " +
                "Generally, the user will be presented with a screen similar to this. " +
                "It is important that you forward port 8000.");
        Image image = new Image("WordCounter/JavaProjectHelpImages/Option3pic1.png");
        imageView.setVisible(true);
        imageView.setImage(image);
        helpCenterBtn.setVisible(true);
    }


}
