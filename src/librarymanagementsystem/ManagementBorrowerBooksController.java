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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rant
 */
public class ManagementBorrowerBooksController implements Initializable {

    @FXML
    private JFXTextField textfeildbookId;
    @FXML
    private JFXTextField textfeildborrowerId;
    @FXML
    private JFXTextField textfeildborrowerDate;
    @FXML
    private JFXTextField textfeildreturnDate;
    @FXML
    private JFXButton bttnClear;
    @FXML
    private JFXButton bttnAdd;
    @FXML
    private JFXButton bttnView;
    @FXML
    private TableView<borrower_books> tableviewborrbook;
    @FXML
    private TableColumn<borrower_books, Integer> tcBookId;
    @FXML
    private TableColumn<borrower_books, Integer> tcBorrowerId;
    @FXML
    private TableColumn<borrower_books, String> tcBorrowersDate;
    @FXML
    private TableColumn<borrower_books, String> tcReturnDate;
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
        tcBookId.setCellValueFactory(new PropertyValueFactory("Book_id"));
        tcBorrowerId.setCellValueFactory(new PropertyValueFactory("Borrower_id"));
        tcBorrowersDate.setCellValueFactory(new PropertyValueFactory("borrowers_date"));
        tcReturnDate.setCellValueFactory(new PropertyValueFactory("Return_date"));
        
        
        tableviewborrbook.getSelectionModel().selectedItemProperty().addListener(
                event-> viewSelectedBorrowerBook() );
        
    }    
     private void viewSelectedBorrowerBook() {
         tableviewborrbook.setVisible(true);
        borrower_books borrowerbooks = tableviewborrbook.getSelectionModel().getSelectedItem();
        if (borrowerbooks != null) {
            textfeildbookId.setText(String.valueOf(borrowerbooks.getBook_id()));
            textfeildborrowerId.setText(String.valueOf(borrowerbooks.getBorrower_id()));
            textfeildborrowerDate.setText(borrowerbooks.getBorrowers_date());
            textfeildreturnDate.setText(borrowerbooks.getReturn_date());
            
        }
    }    

    @FXML
    private void bttnClearHandle(ActionEvent event) {
        textfeildbookId.setText("");
        textfeildborrowerId.setText("");
        textfeildborrowerDate.setText("");
        textfeildreturnDate.setText("");
        tableviewborrbook.getItems().clear();
        tableviewborrbook.setVisible(false);
    }

    @FXML
    private void bttnAddHandle(ActionEvent event)throws Exception{
        Integer BookId = Integer.parseInt(textfeildbookId.getText());
        Integer BorrowerId = Integer.parseInt(textfeildborrowerId.getText());
        String BorrowersDate = textfeildborrowerDate.getText();
        String ReturnDate = textfeildreturnDate.getText();
       
        String sql = "Insert Into borrower_books values(" + BookId + "," + BorrowerId + ",'"
                + BorrowersDate+"','"+ReturnDate + "')";
        this.statement.executeUpdate(sql);
    }

    @FXML
    private void bttnViewHandle(ActionEvent event)throws Exception{
        tableviewborrbook.setVisible(true);
         ResultSet resultset = this.statement.executeQuery("Select * From borrower_books");
        tableviewborrbook.getItems().clear();
        while (resultset.next()) {
            borrower_books borrowerbooks = new borrower_books();
            borrowerbooks.setBook_id(resultset.getInt("Book_id"));
            borrowerbooks.setBorrower_id(resultset.getInt("Borrower_id"));
            borrowerbooks.setBorrowers_date(resultset.getString("Borrowers_date"));
            borrowerbooks.setReturn_date(resultset.getString("Return_date"));
           
            tableviewborrbook.getItems().add(borrowerbooks);
            
            
        }
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
    }
    
