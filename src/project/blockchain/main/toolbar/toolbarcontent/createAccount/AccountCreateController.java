package project.blockchain.main.toolbar.toolbarcontent.createAccount;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import project.blockchain.DatabaseHandler.Database;
import project.blockchain.alerts.AlertMaker;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountCreateController implements Initializable {
    Database handler;
    public JFXTextField firstName;
    public JFXTextField lastName;
    public JFXTextField userName;
    public JFXPasswordField pass1;
    public JFXPasswordField pass2;

    public void createNewAccount(ActionEvent actionEvent) {

        String fname = firstName.getText();
        String lname = lastName.getText();
        String username = userName.getText();
        String pswd = pass1.getText();
        String pswd2 = pass2.getText();

        if (pswd.equals(pswd2)){

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            String sql = "INSERT INTO userauth VALUES (" +
                    "'"+ fname +"'," +
                    "'"+ lname +"'," +
                    "'"+ username +"'," +
                    "'"+ pswd +"'," +
                    "'"+ dateFormat.format(date) +"'" + //System.currentTimeMillis
                    ")";
            if (checkName() == true) {
                if (handler.execAtion(sql)) {
                    AlertMaker.showSimpleAlert("Add new user", "New user added successfully");
                    resetValues();
                } else {
                    AlertMaker.showErrorMessage("Could not Add user", "Error occured. could not add new user");
                    resetValues();
                }
            }else{
                AlertMaker.showSimpleAlert("Similar username exists","username is taken. choose a different username");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Error. consult system admin","Account create error",JOptionPane.ERROR_MESSAGE);
        }

    }

    private void resetValues() {
        firstName.setText("");
        lastName.setText("");
        userName.setText("");
        pass1.setText("");
        pass2.setText("");
    }

    //method to see if existing username is similar as new username
    private boolean checkName(){
        String query = "SELECT username FROM userauth";
        ResultSet rs = handler.execQuery(query);
        try{
            while (rs.next()){
                String usernames = rs.getString("username");
                if (usernames.equals(userName.getText())){
                    return false;
                }
            }
        }catch (SQLException ex){
            Logger.getLogger(AccountCreateController.class.getName()).log(Level.SEVERE,null,ex);
        }
        return true;
    }

    public void cancel(ActionEvent actionEvent) {
        ((Stage)pass2.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = Database.getInstance();
    }
}
