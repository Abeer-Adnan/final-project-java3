/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rant
 */
public class ManagementBorrowersController implements Initializable {

    @FXML
    private TableView<borrowers> tableviewborrowers;
    @FXML
    private TableColumn<borrowers, Integer> tcID;
    @FXML
    private TableColumn<borrowers, String> tcFirstName;
    @FXML
    private TableColumn<borrowers, String> tcLastName;
    @FXML
    private TableColumn<borrowers, String> tcMobile;
    @FXML
    private TableColumn<borrowers, String> tcEmail;
    @FXML
    private TableColumn<borrowers, String> tcAddress;
    @FXML
    private TableColumn<borrowers, String> tcGender;
    @FXML
    private JFXTextField textfeildID;
    @FXML
    private JFXTextField textfeildFirstName;
    @FXML
    private JFXTextField textfeildMobile;
    @FXML
    private JFXTextField textfeildLastName;
    @FXML
    private JFXTextField textfeildEmail;
    @FXML
    private JFXTextField textfeildAddress;
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
    Statement statement;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
               try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection =
               DriverManager.
                getConnection("jdbc:mysql://127.0.0.1:3306/LibraryManagement?serverTimezone=UTC",
                        "root", "");
            this.statement = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tcID.setCellValueFactory(new PropertyValueFactory("Id"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory("FirstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory("LastName"));
        tcMobile.setCellValueFactory(new PropertyValueFactory("Mobile"));
        tcEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        tcAddress.setCellValueFactory(new PropertyValueFactory("Address"));
        tcGender.setCellValueFactory(new PropertyValueFactory("Gender"));
        
        tableviewborrowers.getSelectionModel().selectedItemProperty().addListener(
                event-> viewSelectedBorrowers() );
        
    }    
     private void viewSelectedBorrowers() {
         tableviewborrowers.setVisible(true);
        borrowers borrowers = tableviewborrowers.getSelectionModel().getSelectedItem();
        if (borrowers != null) {
            textfeildID.setText(String.valueOf(borrowers.getId()));
            textfeildFirstName.setText(borrowers.getFirstName());
            textfeildLastName.setText(borrowers.getLastName());
            textfeildMobile.setText(borrowers.getMobile());
            textfeildEmail.setText(borrowers.getEmail());
            textfeildAddress.setText(borrowers.getAddress());
           // gender.selectToggle(borrowers.getGender());
            gender.setUserData(borrowers.getGender());
            
            
           
        }

    }  

    @FXML
    private void bttnAdd(ActionEvent event) throws Exception{
        Integer Id = Integer.parseInt(textfeildID.getText());
        String FirstName = textfeildFirstName.getText();
        String LastName = textfeildLastName.getText();
        String Mobile = textfeildMobile.getText();
        String Email = textfeildEmail.getText();
        String Address = textfeildAddress.getText();
        String gender = "";
        if(bttnMale.isSelected()){
            gender+=bttnMale.getText();
        } if(bttnFemale.isSelected()){
             gender+=bttnFemale.getText();
        }
        String sql = "Insert Into borrowers values(" + Id + ",'" + FirstName + "','"
                + LastName+"','"+Mobile+"','"+Email+"','"+Address+"','"+gender + "')";
        this.statement.executeUpdate(sql);
   
    }

    @FXML
    private void bttnViewHandle(ActionEvent event) throws Exception{
         tableviewborrowers.setVisible(true);
         ResultSet resultset = this.statement.executeQuery("Select * From borrowers");
        tableviewborrowers.getItems().clear();
        while (resultset.next()) {
            borrowers borrowers = new borrowers();
            borrowers.setId(resultset.getInt("Id"));
            borrowers.setFirstName(resultset.getString("FirstName"));
            borrowers.setLastName(resultset.getString("LastName"));
            borrowers.setMobile(resultset.getString("Mobile"));
            borrowers.setEmail(resultset.getString("Email"));
            borrowers.setAddress(resultset.getString("Address"));
            borrowers.setGender(resultset.getString("Gender"));
            tableviewborrowers.getItems().add(borrowers);
            
            
        }
    }

    @FXML
    private void bttnUpdateHandle(ActionEvent event) throws Exception{
        Integer Id = Integer.parseInt(textfeildID.getText());
        String FirstName = textfeildFirstName.getText();
        String LastName = textfeildLastName.getText();
        String Mobile = textfeildMobile.getText();
        String Email = textfeildEmail.getText();
        String Address = textfeildAddress.getText();
        String gender = "";
        if(bttnMale.isSelected()){
            gender+=bttnMale.getText();
        } if(bttnFemale.isSelected()){
             gender+=bttnFemale.getText();
        }
       // String Gender = textfeild.getText();
         String sql = "Update borrowers Set FirstName='" + FirstName + "',FirstName='"
                + FirstName +"',LastName='"+LastName+"',Mobile='"+Mobile+
                 "',Email='"+Email+"',Address='"+Address+"',Gender='"+gender+"'Where Id=" + Id;
        this.statement.executeUpdate(sql);
    }

    @FXML
    private void bttnClearHandle(ActionEvent event) {
        textfeildID.setText("");
        textfeildFirstName.setText("");
        textfeildLastName.setText("");
        textfeildMobile.setText("");
        textfeildAddress.setText("");
        textfeildEmail.setText("");
        gender.selectToggle(null);
        //gender.setUserData("");
        tableviewborrowers.getItems().clear();
        tableviewborrowers.setVisible(false);
    }

    @FXML
    private void bttnDeleteHandle(ActionEvent event)throws Exception{
        Integer Id = Integer.parseInt(textfeildID.getText());
        String FirstName = textfeildFirstName.getText();
        String LastName = textfeildLastName.getText();
        String Mobile = textfeildMobile.getText();
        String Email = textfeildEmail.getText();
        String Address = textfeildAddress.getText();
        //String Gender = textfeild.getText();
        String gender = "";
        if(bttnMale.isSelected()){
            gender+=bttnMale.getText();
        }if(bttnFemale.isSelected()){
             gender+=bttnFemale.getText();
        }
        String sql = "Delete From borrowers Where Id=" + Id;
        this.statement.executeUpdate(sql);
    }

    @FXML
    private void bttnSearchHandle(ActionEvent event) {
    }

    @FXML
    private void bttnBackHandle(ActionEvent event)throws Exception{
                  try {
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Buttons.fxml"));
            Parent p = fxmll.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
