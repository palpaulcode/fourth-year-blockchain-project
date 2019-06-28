package project.blockchain.MultiUserLogin;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.blockchain.DatabaseHandler.Database;
import project.blockchain.login.LogInController;
import project.blockchain.main.toolbar.toolbarcontent.transaction.util.BlockChainUtil;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    Database handler;

    public JFXTextField username;
    public JFXPasswordField userPass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = Database.getInstance();
    }

    public void logIn(ActionEvent actionEvent) {
        String cUser = username.getText();
        String cUserpass = userPass.getText();

        if (cUser.isEmpty() || cUserpass.isEmpty()){
            JOptionPane.showMessageDialog( null, "" +
                    "System detected empty \n username or password","Login error",
                    JOptionPane.ERROR_MESSAGE);
        }else {

            String qu = "SELECT * FROM userauth WHERE username = '"+ cUser +"'" +
                    "AND password = '"+ cUserpass +"'";
            ResultSet rs = handler.execQuery(qu);

            try {
                if (rs.isBeforeFirst()){
                    while (rs.next()){
                        System.out.println("Current user " + rs.getString("firstname") +" "+ rs.getString("lastname"));

                        /*MainController.USERNAME = cUser;*/ //set name of user in class MainController

                        closeStage();
                        loadMainWindow();
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Wrong credentials \n Try again", "Login Error", JOptionPane.ERROR_MESSAGE);
                    clearFields();
                }
            }catch (SQLException ex){
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } //end else
    }

    public void clearFields(){
        username.setText("");
        userPass.setText("");
    }

    public void cancelAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void closeStage(){
        ((Stage)username.getScene().getWindow()).close();
    }

    private void loadMainWindow(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/project/blockchain/main/mainwindow/main.fxml"));
            Stage stage =new Stage(StageStyle.DECORATED);
            stage.setTitle("BlockChain Application");
            stage.setScene(new Scene(parent));
            stage.show();

            BlockChainUtil.setStageIcon(stage);

        } catch (IOException e) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
