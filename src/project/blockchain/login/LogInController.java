package project.blockchain.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;
import project.blockchain.main.toolbar.toolbarcontent.settings.Prefs;
import project.blockchain.util.BlockChainUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogInController implements Initializable {

    public JFXTextField username;
    public JFXPasswordField password;

    Prefs prefs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prefs = Prefs.getPrefs();
    }

    public void handleCancelAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void handleLoginAction(ActionEvent actionEvent) {
        String uname = username.getText();
        String pswd = DigestUtils.shaHex(password.getText());

        if (uname.equals(prefs.getUsername()) && pswd.equals(prefs.getPassword())){
            closeStage();
            loadMainWindow();
        } else {
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
    }
    private void closeStage(){
        ((Stage)password.getScene().getWindow()).close();
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
