/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.xml.bind.DatatypeConverter;

/**
 * FXML Controller class
 *
 * @author rant
 */
public class LoginController implements Initializable {
    
    @FXML
    private JFXTextField textfeildUserName;
    @FXML
    private JFXPasswordField textfeildPassword;
    @FXML
    private JFXButton bttnLogin;
    
    Statement statement;
    Alert alert;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        UserName/abeer
//        PassWord/1882000
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
    }
    
    @FXML
    private void bttnLoginHandle(ActionEvent event) throws Exception {
        ResultSet rs = this.statement.executeQuery("Select * from users Where Id = 1");
        String sqlname = "";
        String sqlpass = "";
        if (rs.next()) {
            sqlname = rs.getString("Name");
            sqlpass = rs.getString("Password");
        }
        if (textfeildUserName.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.WARNING, "The Username Feild Is Empty", ButtonType.CANCEL);
            alert.setHeaderText("Be attention");
            alert.show();
        } else if (textfeildPassword.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.WARNING, "The Password Feild Is Empty", ButtonType.CANCEL);
            alert.setHeaderText("Be attention");
            alert.show();
        }
        if (!textfeildUserName.getText().isEmpty() && !textfeildPassword.getText().isEmpty()) {
            if (textfeildUserName.getText().equalsIgnoreCase(sqlname)
                    && sqlpass.equals(hashPassword(textfeildPassword.getText()))) {
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
            } else {
                alert = new Alert(Alert.AlertType.ERROR, "The usrname or password is Errorr", ButtonType.OK);
                alert.show();
            }
        }
    }
    
    private String hashPassword(String pa) throws NoSuchAlgorithmException {
        //String password = "1882000";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pa.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest);
        // System.out.println(myHash);//9917D76887C9BFE80F240B72BC4534F7
    }
}
