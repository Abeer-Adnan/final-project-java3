/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
    PrintWriter p;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            p = new PrintWriter(new FileWriter(new File("src/librarymanagementsystem/out.txt"), true));
            p.print("********************** \n");
        } catch (IOException ex) {
            Logger.getLogger(ManagementBooksController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {

                books bb = new books(Id, Name, Description);
                List<books> bblist = new ArrayList<>();
                bblist.add(bb);
                tabelviewBook.getItems().setAll(
                        tabelviewBook.getItems().stream().sorted(
                                Comparator.comparing(books::getId).reversed())
                                .sorted(new Comparator<books>() {
                                    @Override
                                    public int compare(books b1, books b2) {
                                        return -b1.compareTo(b2);
                                    }
                                })
                                .collect(Collectors.toList()
                                ));

                String sql = "Insert Into books values(" + Id + ",'" + Name + "','"
                        + Description + "')";
                this.statement.executeUpdate(sql);
                p.println("Added new Book : \n " + new books(Id, Name, Description));
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
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void bttnUpdateHandle(ActionEvent event) throws Exception {
        try {

            Integer Id = Integer.parseInt(textfeildID.getText());
            String Name = textfeildName.getText();
            String Description = textfeildDescription.getText();
            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Update ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {
                String sql = "Update books Set Name='" + Name + "',Description='"
                        + Description + "'Where Id=" + Id;
                this.statement.executeUpdate(sql);
                p.println("Updating book : \n " + new books(Id, Name, Description));
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
    private void bttnSearchHandle(ActionEvent event) throws Exception {

        try {

            Integer Id = Integer.parseInt(textfeildID.getText());
            ResultSet resultset = this.statement.executeQuery("Select * From books Where Id=" + Id);

            tabelviewBook.getItems().clear();
            while (resultset.next()) {
                books book = new books();
                book.setId(resultset.getInt("Id"));
                book.setName(resultset.getString("Name"));
                book.setDescription(resultset.getString("Description"));
                p.println("Search for the book with the Id :  "+Id);
                p.flush();
                tabelviewBook.getItems().addAll(book);

                tabelviewBook.setVisible(true);

            }

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

            Integer Id = Integer.parseInt(textfeildID.getText());
            String Name = textfeildName.getText();
            String Description = textfeildDescription.getText();
            Optional<ButtonType> confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete ?").showAndWait();
            if (ButtonType.OK == confirm.get()) {
                String sql = "Delete From books Where Id=" + Id;
                this.statement.executeUpdate(sql);
                p.println("Deleted book : \n " + new books(Id, Name, Description));
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
        p.flush();
    }

}
