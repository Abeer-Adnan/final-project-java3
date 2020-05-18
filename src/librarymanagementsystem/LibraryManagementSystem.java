/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author rant
 */
public class LibraryManagementSystem extends Application {

    /**
     * @param args the command line arguments
     */
    ManagementBooksController bc;
    ManagementBorrowersController rc;
    ManagementBorrowerBooksController bbc;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader ff = new FXMLLoader();
        Parent paneTableView = ff.load(getClass().getResource("Login.fxml"));

//        Map<String,Pane>mapPanes=new TreeMap<>(); 
//        mapPanes.put("Library", paneTableView);
//        mapPanes.put("book", ff.load(getClass().getResource("Login.fxml")));
//        mapPanes.put("borrower", ff.load(getClass().getResource("Login.fxml")));
        FXMLLoader bookLoader = new FXMLLoader();
        Parent book = bookLoader.load(getClass().getResource("ManagementBooks.fxml"));
        FXMLLoader borLoader = new FXMLLoader();
        Parent p1 = borLoader.load(getClass().getResource("ManagementBooks.fxml"));
        FXMLLoader bobrLoader = new FXMLLoader();
        Parent p2 = bobrLoader.load(getClass().getResource("ManagementBooks.fxml"));

        Scene scene = new Scene(paneTableView);
        bc = bookLoader.getController();
        rc = borLoader.getController();
        bbc = bobrLoader.getController();
//        FXMLLoader fx = new FXMLLoader();
//        Parent root = fx.load(this.getClass().getResourceAsStream("Login.fxml"));
//        bc = fx.getController();
//        Scene scene = new Scene(root);
        primaryStage.setTitle(" Library Management System desktop application ");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(value -> {
            try {
                bc.p.close();
                rc.p.close();
                bbc.p.close();
            } catch (NullPointerException e) {
                System.out.println("5555555555555");
                System.exit(0);
            }
        });
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
}
