/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    Alert alert;
    PrintWriter p;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection
                    = DriverManager.
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
                event -> viewSelectedBorrowers());
        
        try {
            p = new PrintWriter(new FileWriter(new File("src/librarymanagementsystem/out.txt"), true));
            p.print("********************** \n");
        } catch (IOException ex) {
            Logger.getLogger(ManagementBorrowersController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            gender.setUserData(borrowers.getGender());
            
            if (gender.getUserData().equals("Male")) {
                bttnMale.setSelected(true);
            } else {
                bttnFemale.setSelected(true);
            }
            
        }
        
    }
    
    @FXML
    private void bttnAdd(ActionEvent event) throws Exception {
        try {
            Integer Id = Integer.parseInt(textfeildID.getText());
            String FirstName = textfeildFirstName.getText();
            String LastName = textfeildLastName.getText();
            String Mobile = textfeildMobile.getText();
            String Email = textfeildEmail.getText();
            String Address = textfeildAddress.getText();
            String genderText = "";
            if (bttnMale.isSelected()) {
                genderText += bttnMale.getText();
            }
            if (bttnFemale.isSelected()) {
                genderText += bttnFemale.getText();
            }
            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {
                
                borrowers bb = new borrowers(Id, FirstName, LastName, Mobile, Email, Address, genderText);
                List<borrowers> bblist = new ArrayList<>();
                bblist.add(bb);
                tableviewborrowers.getItems().setAll(
                        tableviewborrowers.getItems().stream().sorted(
                                Comparator.comparing(borrowers::getId).reversed())
                                .sorted(new Comparator<borrowers>() {
                                    @Override
                                    public int compare(borrowers t, borrowers t1) {
                                        return -t.compareTo(t1);
                                    }
                                })
                                .collect(Collectors.toList()
                                ));
                
                String sql = "Insert Into borrowers values(" + Id + ",'" + FirstName + "','"
                        + LastName + "','" + Mobile + "','" + Email + "','" + Address + "','" + genderText + "')";
                this.statement.executeUpdate(sql);
                p.println("Added Borrower : \n " + new borrowers(Id, FirstName, LastName, Mobile, Email, Address, genderText));
                p.flush();
                alert = new Alert(Alert.AlertType.INFORMATION, "Add operation completed successfully", ButtonType.CANCEL);
                alert.setHeaderText("Great!");
                alert.show();
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION, "Add operation is faild", ButtonType.CANCEL);
                alert.setHeaderText("Sorry!");
                alert.show();
            }
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.WARNING, "Enter valid data type before pressing the Add button", ButtonType.OK);
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR, "There is somthing Errorr ,Please try again ", ButtonType.PREVIOUS);
            alert.show();
            
        }
    }
    
    @FXML
    private void bttnViewHandle(ActionEvent event) throws Exception {
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
        p.flush();
    }
    
    @FXML
    private void bttnUpdateHandle(ActionEvent event) throws Exception {
        try {
            Integer Id = Integer.parseInt(textfeildID.getText());
            String FirstName = textfeildFirstName.getText();
            String LastName = textfeildLastName.getText();
            String Mobile = textfeildMobile.getText();
            String Email = textfeildEmail.getText();
            String Address = textfeildAddress.getText();
            String gender = "";
            if (bttnMale.isSelected()) {
                gender += bttnMale.getText();
            }
            if (bttnFemale.isSelected()) {
                gender += bttnFemale.getText();
            }
            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Update ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {
                String sql = "Update borrowers Set FirstName='" + FirstName + "',FirstName='"
                        + FirstName + "',LastName='" + LastName + "',Mobile='" + Mobile
                        + "',Email='" + Email + "',Address='" + Address + "',Gender='" + gender + "'Where Id=" + Id;
                this.statement.executeUpdate(sql);
                p.println("Update Borrower : \n" + new borrowers(Id, FirstName, LastName, Mobile, Email, Address, gender));
                p.flush();
                alert = new Alert(Alert.AlertType.INFORMATION, "Update operation completed successfully", ButtonType.CANCEL);
                alert.setHeaderText("Great!");
                alert.show();
                
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION, "Update operation is faild", ButtonType.CANCEL);
                alert.setHeaderText("Sorry!");
                alert.show();
            }
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.WARNING, "Enter valid data type or choose the record that you want to update from the table before pressing the update button", ButtonType.OK);
            alert.show();
            
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR, "There is somthing Errorr ,Please try again ", ButtonType.PREVIOUS);
            alert.show();
        }
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
    private void bttnDeleteHandle(ActionEvent event) throws Exception {
        try {
            Integer Id = Integer.parseInt(textfeildID.getText());
            String FirstName = textfeildFirstName.getText();
            String LastName = textfeildLastName.getText();
            String Mobile = textfeildMobile.getText();
            String Email = textfeildEmail.getText();
            String Address = textfeildAddress.getText();
            String gender = "";
            if (bttnMale.isSelected()) {
                gender += bttnMale.getText();
            }
            if (bttnFemale.isSelected()) {
                gender += bttnFemale.getText();
            }
            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {
                String sql = "Delete From borrowers Where Id=" + Id;
                this.statement.executeUpdate(sql);
                p.println("Deleted Borrower : \n" + new borrowers(Id, FirstName, LastName, Mobile, Email, Address, gender));
                p.flush();
                alert = new Alert(Alert.AlertType.INFORMATION, "Delete operation completed successfully", ButtonType.CANCEL);
                alert.setHeaderText("Great!");
                alert.show();
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION, "Delete operation is faild", ButtonType.CANCEL);
                alert.setHeaderText("Sorry!");
                alert.show();
            }
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.WARNING,
                    "Enter valid data type or choose the record that you want to Delete from the table before pressing the Delete button", ButtonType.OK);
            alert.show();
            
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR, "There is somthing Errorr ,Please try again ", ButtonType.PREVIOUS);
            alert.show();
        }
    }
    
    @FXML
    private void bttnSearchHandle(ActionEvent event) {
        try {
            
            Integer Id = Integer.parseInt(textfeildID.getText());
            ResultSet resultset = this.statement.executeQuery("Select * From borrowers Where Id=" + Id);
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
                p.println("Search for the borrower with the Id :  " + Id);
                p.flush();
                tableviewborrowers.getItems().addAll(borrowers);
                tableviewborrowers.setVisible(true);
                
            }
        } catch (Exception e) {
            
            alert = new Alert(Alert.AlertType.ERROR, "Enter valid id borrower that you want to seach it before pressing the Search button", ButtonType.PREVIOUS);
            alert.setTitle("Somthing Error");
            alert.show();
        }
    }
    
    @FXML
    private void bttnBackHandle(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Buttons.fxml"));
            Parent p = fxmll.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.setTitle("Select Operation");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
