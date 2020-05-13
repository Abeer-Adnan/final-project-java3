/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rant
 */
public class ButtonsController implements Initializable {

    @FXML
    private Button bttnBook;
    @FXML
    private Button bttnBorrowers;
    @FXML
    private Button bttnborrowerbook;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void bttnBookHandle(ActionEvent event) {
         try {
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Management Books.fxml"));
            Parent p = fxmll.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
          
    }

    @FXML
    private void bttnBorrowersHandle(ActionEvent event) {
         try {
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Management Borrowers.fxml"));
            Parent p = fxmll.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void bttnborrowerbookHandle(ActionEvent event) {
         try {
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Management borrower books.fxml"));
            Parent p = fxmll.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
