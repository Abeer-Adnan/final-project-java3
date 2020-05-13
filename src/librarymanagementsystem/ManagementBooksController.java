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
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        tcName.setCellValueFactory(new PropertyValueFactory("Name"));
        tcDescription.setCellValueFactory(new PropertyValueFactory("Description"));
       
        tabelviewBook.getSelectionModel().selectedItemProperty().addListener(
                event-> viewSelectedBooks() );
        tabelviewBook.setVisible(true);
    }    
     private void viewSelectedBooks() {
         tabelviewBook.setVisible(true);
        books book = tabelviewBook.getSelectionModel().getSelectedItem();
        if (book != null) {
            textfeildID.setText(String.valueOf(book.getId()));
            textfeildName.setText(book.getName());
            textfeildDescription.setText(book.getDescription());
            
           
        }

    }

    @FXML
    private void bttnAddHandle(ActionEvent event) throws Exception{
        Integer Id = Integer.parseInt(textfeildID.getText());
        String Name = textfeildName.getText();
        String Description = textfeildDescription.getText();
        String sql = "Insert Into books values(" + Id + ",'" + Name + "','"
                + Description + "')";
        this.statement.executeUpdate(sql);
        tabelviewBook.setVisible(true);
    }

    @FXML
    private void bttnBackHandle(ActionEvent event) {
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

    @FXML
    private void bttnUpdateHandle(ActionEvent event)throws Exception{
        Integer Id = Integer.parseInt(textfeildID.getText());
        String Name = textfeildName.getText();
        String Description = textfeildDescription.getText();
        String sql = "Update books Set Name='" + Name + "',Description='"
                + Description + "'Where Id=" + Id;
        this.statement.executeUpdate(sql);
        tabelviewBook.setVisible(true);
    }

    @FXML
    private void bttnSearchHandle(ActionEvent event) {
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
    private void bttnDeleteHandle(ActionEvent event) throws Exception{
        Integer Id = Integer.parseInt(textfeildID.getText());
        String Name = textfeildName.getText();
        String Description = textfeildDescription.getText();
        String sql = "Delete From books Where Id=" + Id;
        this.statement.executeUpdate(sql);
        tabelviewBook.setVisible(true);
    }

    @FXML
    private void bttnViewHandle(ActionEvent event)throws Exception{
         ResultSet resultset = this.statement.executeQuery("Select * From books");
        tabelviewBook.getItems().clear();
        while (resultset.next()) {
            books book = new books();
            book.setId(resultset.getInt("Id"));
            book.setName(resultset.getString("Name"));
            book.setDescription(resultset.getString("Description"));
            tabelviewBook.getItems().add(book);
            tabelviewBook.setVisible(true);
            
        }
        
    }
    
    
}
