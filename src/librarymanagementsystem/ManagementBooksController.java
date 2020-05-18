/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rant
 */
public class ManagementBooksController implements Initializable {

    @FXML
    private JFXTextField textfeildID;
    @FXML
    private JFXTextField textfeildName;
    @FXML
    private JFXTextArea textfeildDescription;
    @FXML
    private TableView<books> tabelviewBook;
    @FXML
    private TableColumn<books, Integer> tcID;
    @FXML
    private TableColumn<books, String> tcName;
    @FXML
    private TableColumn<books, String> tcDescription;
    @FXML
    private JFXButton bttnAdd;
    @FXML
    private JFXButton bttnBack;
    @FXML
    private JFXButton bttnUpdate;
    @FXML
    private JFXButton bttnSearch;
    @FXML
    private JFXButton bttnClear;
    @FXML
    private JFXButton bttnDelete;
    @FXML
    private JFXButton bttnView;

    Statement statement;
    Alert alert;

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
        tcName.setCellValueFactory(new PropertyValueFactory("Name"));
        tcDescription.setCellValueFactory(new PropertyValueFactory("Description"));

        tabelviewBook.getSelectionModel().selectedItemProperty().addListener(
                event -> viewSelectedBooks());

    }

    private void viewSelectedBooks() {
        tabelviewBook.setVisible(true);
        books book = tabelviewBook.getSelectionModel().getSelectedItem();
        if (book != null) {
            tabelviewBook.setVisible(true);
            textfeildID.setText(String.valueOf(book.getId()));
            textfeildName.setText(book.getName());
            textfeildDescription.setText(book.getDescription());

        }

    }

    @FXML
    private void bttnAddHandle(ActionEvent event) throws Exception {
        try {
            Integer Id = Integer.parseInt(textfeildID.getText());
            String Name = textfeildName.getText();
            String Description = textfeildDescription.getText();
//        if(Id==null||Name==null||Description==null){
//             throw new AgrumentException("Empty values are not allowed.", "value");
//
//        }
            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {

//        alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add ?", ButtonType.YES);
//        alert.setHeaderText("Confirmation alert !");
//        alert.show();
                String sql = "Insert Into books values(" + Id + ",'" + Name + "','"
                        + Description + "')";
                this.statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION, "Add operation completed successfully", ButtonType.CANCEL);
                alert.setHeaderText("Great!");
                alert.show();
                //   bttnViewHandle(event);
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION, "Add operation is faild", ButtonType.CANCEL);
                alert.setHeaderText("Sorry!");
                alert.show();
            }
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.WARNING, "Enter valid data type before pressing the Add button", ButtonType.OK);
            alert.show();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR, "There is somthing Errorr ,Please try again ", ButtonType.PREVIOUS);
            alert.show();

        }

    }

    @FXML
    private void bttnBackHandle(ActionEvent event) {
        try {
            FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Buttons.fxml"));
            Parent p = fxmll.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.setTitle("Select Operation");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void bttnUpdateHandle(ActionEvent event) throws Exception {
        try {
//            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Update ?", ButtonType.YES);
//            alert.setHeaderText("Confirmation alert !");
//            alert.show();

            Integer Id = Integer.parseInt(textfeildID.getText());
            String Name = textfeildName.getText();
            String Description = textfeildDescription.getText();
            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Update ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {
                String sql = "Update books Set Name='" + Name + "',Description='"
                        + Description + "'Where Id=" + Id;
                this.statement.executeUpdate(sql);

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
    private void bttnSearchHandle(ActionEvent event) throws Exception {
//        alert = new Alert(Alert.AlertType.INFORMATION, "Enter the id book that you want to seach it ", ButtonType.OK);
//        alert.show();
//        Integer Id = Integer.parseInt(textfeildID.getText());
//        //String sql ="Select * From books Where Id=" + Id;
//        //this.statement.executeQuery(sql);
//        ResultSet resultset = this.statement.executeQuery("Select * From books Where Id=" + Id);
//        if(!resultset.equals(Id)){
//            alert = new Alert(Alert.AlertType.INFORMATION, "Not found", ButtonType.OK);
//        alert.show();
//            
//        }else{
//             alert = new Alert(Alert.AlertType.INFORMATION, "found", ButtonType.OK);
//        alert.show(); 
//        }
        try {
//            alert = new Alert(Alert.AlertType.INFORMATION, "Enter the id book that you want to seach it ", ButtonType.OK);
//            Optional<ButtonType> isOk = alert.showAndWait();
//            if (isOk.get() == ButtonType.OK ) {
                Integer Id = Integer.parseInt(textfeildID.getText());
                ResultSet resultset = this.statement.executeQuery("Select * From books Where Id=" + Id);
                tabelviewBook.getItems().clear();
                while (resultset.next()) {
                    books book = new books();
                    book.setId(resultset.getInt("Id"));
                    book.setName(resultset.getString("Name"));
                    book.setDescription(resultset.getString("Description"));
                    tabelviewBook.getItems().addAll(book);
                    tabelviewBook.setVisible(true);
                }
//            }catch(NumberFormatException e){
//          alert = new Alert(Alert.AlertType.WARNING, "Enter valid id book that you want to seach it", ButtonType.OK);
//          alert.show();
            //}
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR, "Enter valid id book that you want to Search it before pressing the Search button", ButtonType.PREVIOUS);
            alert.setTitle("Somthing Error");
            alert.show();
        }
    }

    @FXML
    private void bttnClearHandle(ActionEvent event) {
        textfeildID.setText("");
        textfeildName.setText("");
        textfeildDescription.setText("");
        tabelviewBook.getItems().clear();
        tabelviewBook.setVisible(false);
    }

    @FXML
    private void bttnDeleteHandle(ActionEvent event) throws Exception {
        try {
//            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete ?", ButtonType.YES);
//            alert.setHeaderText("Confirmation alert !");
//            alert.show();

            Integer Id = Integer.parseInt(textfeildID.getText());
            String Name = textfeildName.getText();
            String Description = textfeildDescription.getText();
            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {
                String sql = "Delete From books Where Id=" + Id;
                this.statement.executeUpdate(sql);

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
    private void bttnViewHandle(ActionEvent event) throws Exception {
        tabelviewBook.setVisible(true);
        ResultSet resultset = this.statement.executeQuery("Select * From books");
        tabelviewBook.getItems().clear();
        while (resultset.next()) {
            books book = new books();
            book.setId(resultset.getInt("Id"));
            book.setName(resultset.getString("Name"));
            book.setDescription(resultset.getString("Description"));
            tabelviewBook.getItems().add(book);

        }

    }

}
