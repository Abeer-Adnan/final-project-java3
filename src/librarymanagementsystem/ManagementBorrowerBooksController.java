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
import java.sql.SQLException;
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
    @FXML
    private TableView<books> tableviewborrbook1;

    @FXML
    private TableColumn<books, Integer> tcBookId1;

    @FXML
    private TableView<borrowers> tableviewborrbook2;

    @FXML
    private TableColumn<borrowers, Integer> tcBorrowerId1;

    Statement statement;
    Alert alert;

    PrintWriter p;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        tcBookId.setCellValueFactory(new PropertyValueFactory("Book_id"));
        tcBorrowerId.setCellValueFactory(new PropertyValueFactory("Borrower_id"));
        tcBorrowersDate.setCellValueFactory(new PropertyValueFactory("borrowers_date"));
        tcReturnDate.setCellValueFactory(new PropertyValueFactory("Return_date"));

        //for tabel bookid
        tcBookId1.setCellValueFactory(new PropertyValueFactory("Id"));
        //for tabel borrower id
        tcBorrowerId1.setCellValueFactory(new PropertyValueFactory("Id"));
        try {
            showBookId();
            showBorrowerId();
        } catch (SQLException ex) {
            ex.printStackTrace();;
        }
        try {
            p = new PrintWriter(new FileWriter(new File("src/librarymanagementsystem/out.txt"), true));
            p.print("********************** \n");
        } catch (IOException ex) {
            Logger.getLogger(ManagementBorrowersController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableviewborrbook1.getSelectionModel().selectedItemProperty().addListener(
                event -> viewSelectedBook());
        tableviewborrbook2.getSelectionModel().selectedItemProperty().addListener(
                event -> viewSelectedBoorower());
        try {
            p = new PrintWriter(new FileWriter(new File("src/librarymanagementsystem/out.txt"), true));
        } catch (IOException ex) {
            Logger.getLogger(ManagementBorrowersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void viewSelectedBoorower() {

        borrowers borrowerid = tableviewborrbook2.getSelectionModel().getSelectedItem();
        if (borrowerid != null) {
            textfeildbookId.setText(String.valueOf(borrowerid.getId()));
        }
    }

    private void viewSelectedBook() {

        books bookid = tableviewborrbook1.getSelectionModel().getSelectedItem();
        if (bookid != null) {
            textfeildbookId.setText(String.valueOf(bookid.getId()));

        }
    }

    private void showBookId() throws SQLException {
        ResultSet resultset = this.statement.executeQuery("Select Id From books");
        tableviewborrbook1.getItems().clear();
        while (resultset.next()) {
            books book = new books();
            book.setId(resultset.getInt("Id"));
            tableviewborrbook1.getItems().add(book);

        }
    }

    private void showBorrowerId() throws SQLException {
        ResultSet resultset = this.statement.executeQuery("Select Id From borrowers");
        tableviewborrbook2.getItems().clear();
        while (resultset.next()) {
            borrowers borrower = new borrowers();
            borrower.setId(resultset.getInt("Id"));
            tableviewborrbook2.getItems().add(borrower);
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
    private void bttnAddHandle(ActionEvent event) throws Exception {
        tableviewborrbook1.setVisible(true);
        tableviewborrbook2.setVisible(true);
        tableviewborrbook.setVisible(false);
        try {
            Integer BookId = Integer.parseInt(textfeildbookId.getText());
            Integer BorrowerId = Integer.parseInt(textfeildborrowerId.getText());
            String BorrowersDate = textfeildborrowerDate.getText();
            String ReturnDate = textfeildreturnDate.getText();

            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {

                borrower_books bb = new borrower_books(BookId, BorrowerId, BorrowersDate, ReturnDate);
                List<borrower_books> bblist = new ArrayList<>();
                bblist.add(bb);
                tableviewborrbook.getItems().setAll(tableviewborrbook.getItems().stream().sorted(Comparator.comparing(borrower_books::getBook_id).reversed())
                        .sorted(new Comparator<borrower_books>() {
                            @Override
                            public int compare(borrower_books brb, borrower_books brb1) {
                                return -brb.compareTo(brb1);
                            }
                        })
                        .collect(Collectors.toList()
                        ));

                String sql = "Insert Into borrower_books values(" + BookId + "," + BorrowerId + ",'"
                        + BorrowersDate + "','" + ReturnDate + "')";
                this.statement.executeUpdate(sql);
                p.println("Added information about Borrowers and books : \n " + new borrower_books(BookId, BookId, BorrowersDate, ReturnDate));
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
            alert = new Alert(Alert.AlertType.WARNING, "Enter valid data type OR,choose the record from the table below before pressing the Add button", ButtonType.OK);
            alert.show();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR, "There is somthing Errorr ,Please try again ", ButtonType.PREVIOUS);
            alert.show();

        }
    }

    @FXML
    private void bttnViewHandle(ActionEvent event) throws Exception {
        tableviewborrbook1.setVisible(false);
        tableviewborrbook2.setVisible(false);
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
        p.flush();
    }

    @FXML
    private void bttnBackHandle(ActionEvent event) {
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
