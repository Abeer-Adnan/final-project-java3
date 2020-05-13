/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author rant
 */
public class ManagementBorrowersController implements Initializable {

    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> tcID;
    @FXML
    private TableColumn<?, ?> tcFirstName;
    @FXML
    private TableColumn<?, ?> tcLastName;
    @FXML
    private TableColumn<?, ?> tcMobile;
    @FXML
    private TableColumn<?, ?> tcEmail;
    @FXML
    private TableColumn<?, ?> tcAddress;
    @FXML
    private TableColumn<?, ?> tcGender;
    @FXML
    private JFXTextField textfeildID;
    @FXML
    private JFXTextField textfeildFirstName;
    @FXML
    private JFXTextField textfeildMobile;
    @FXML
    private JFXTextField textfeildLastName;
    @FXML
    private JFXTextField textfeildFirstEmail;
    @FXML
    private JFXTextField textfeildFirstAddress;
    @FXML
    private RadioButton bttnMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton bttnFemale;
    @FXML
    private JFXButton bttnAdd;
    @FXML
    private JFXButton bttnView;
    @FXML
    private JFXButton bttnUpdate;
    @FXML
    private JFXButton bttnClear;
    @FXML
    private JFXButton bttnDelete;
    @FXML
    private JFXButton bttnSearch;
    @FXML
    private JFXButton bttnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void bttnAdd(ActionEvent event) {
   
    }

    @FXML
    private void bttnViewHandle(ActionEvent event) {
    }

    @FXML
    private void bttnUpdateHandle(ActionEvent event) {
    }

    @FXML
    private void bttnClearHandle(ActionEvent event) {
    }

    @FXML
    private void bttnDeleteHandle(ActionEvent event) {
    }

    @FXML
    private void bttnSearchHandle(ActionEvent event) {
    }

    @FXML
    private void bttnBackHandle(ActionEvent event) {
    }
    
}
