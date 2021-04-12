/*
File: WordCounterServer.Server.java
Author: Ethan J. Nephew
Date due: April, 2021
Course: CEN-3024C
Description: This is the class definition for the server-side of the application.
*/

package WordCounterServer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Date;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// --module-path "C:\Program Files (x86)\JavaFx\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml


/***
 * This is the server GUI class. The class also handles WordCounterServer.Server-Client interactivity. WordCounterServer.Server.java must be running for a client-side connection to be made.
 */
public class Server extends Application implements CONSTANTS{

    /***
     * The server is presented as a small interface portal.
     * @param primaryStage This is the primary staging area for the GUI.
     * @throws IOException The method can throw an Input/Output Exception.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.print("Starting server");
        URL url = new URL(websiteURL);
        Scanner scan = new Scanner(url.openStream());
        String content = "";
        content = WebSaver.readText(scan);

        StringCleaner sc = new StringCleaner();
        try {

            content = sc.cleanThisString(content);
            // print = WriteToFile.displayText(normalizedDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(".");
        WordCounter wc = new WordCounter();
        ArrayList<Word> arraylist = new ArrayList();

        try {
            arraylist = wc.countWords(content, arraylist, 1087); // With the textual artifacts the expected number will be more.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.print(".");
        try {
            Connect.createTable();
            Connect.deleteAll();
            Connect.writeArrayList(arraylist); // writes to database
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(".");
        try {
            Connect.deleteArtifacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(".");
        ArrayList<Word> displayList = new ArrayList<Word>();
        try {
            displayList = Connect.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(".");

        TextArea ta = new TextArea();
        Scene scene = new Scene(new ScrollPane(ta), 700, 250);
        primaryStage.setTitle("WordCounterServer.Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        primaryStage.show();
        InetAddress host = null;
        String ip = "";

        try {
            host = InetAddress.getLocalHost();
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        InetAddress finalHost = host;
        String finalIp = ip;
        ArrayList<Word> finalDisplayList = displayList;

        new Thread( () -> {
            try {

                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() -> ta.appendText("WordCounterServer.Server started at " + new Date() + '\n'));
                Platform.runLater(() -> ta.appendText("WordCounterServer.Server name " + finalHost + '\n'));
                Platform.runLater(() -> ta.appendText("You will need to enter in the IP address to your client application.\n"));
                Platform.runLater(() -> ta.appendText("The IP address is: " + finalIp + "\n"));


                while(true) { // This is a rare instance where we want the thread to run on an infinite loop. It ends when the GUI is shut down.
                    Platform.runLater(() -> ta.appendText("Waiting for client connection... \n"));
                    Socket socket = serverSocket.accept(); // it waits here for a client message
                    Platform.runLater(() -> ta.appendText("A client connection has been established from " + socket + "\n"));

                    OutputStream outputStream = socket.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    Platform.runLater(() -> ta.appendText("Preparing to send list to client... \n"));

                    dataOutputStream.writeUTF(finalDisplayList.toString());
                    Platform.runLater(() -> ta.appendText("Sent list to client... \n"));
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    Platform.runLater(() -> ta.appendText("Connection has been closed. \n"));
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
