package project.blockchain.main.toolbar.toolbarcontent.Profile;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import project.blockchain.DatabaseHandler.Database;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserinfoController implements Initializable {
    Database handler = Database.getInstance();
    public JFXTextField firstname;
    public JFXTextField lastname;
    public JFXTextField username;
    public JFXTextField dateofcreation;
    String uFname;
    String uLname;
    String uUname;
    String uAccDate;
    private String currentUser;

    public UserinfoController() {
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.userInfo();
    }

    public void setName(String name) {
        this.currentUser = name;
    }

    public void userInfo() {
        System.out.println(" Jina lake ni " + this.currentUser);
        String sql = "SELECT * FROM userauth WHERE username = '" + this.currentUser + "'";
        ResultSet rs = this.handler.execQuery(sql);

        try {
            if (rs.isBeforeFirst()) {
                while(rs.next()) {
                    this.uFname = rs.getString("firstname");
                    this.uLname = rs.getString("lastname");
                    this.uUname = rs.getString("username");
                    this.uAccDate = rs.getString("createtime");
                    this.username.setText(this.uFname);
                    System.out.println("Current user info : " + this.uFname + " " + this.uLname + " " + this.uUname);
                }
            }
        } catch (SQLException var4) {
            Logger.getLogger(UserinfoController.class.getName()).log(Level.SEVERE, null, var4);
        }

    }
}
