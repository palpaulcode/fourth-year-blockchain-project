package project.blockchain.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;
import project.blockchain.inputValidator.AlphabetTextField;
import project.blockchain.inputValidator.PasswordTextField;
import project.blockchain.main.mainwindow.MainController;
import project.blockchain.main.toolbar.toolbarcontent.settings.Prefs;
import project.blockchain.main.toolbar.toolbarcontent.transaction.util.BlockChainUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogInController implements Initializable {
    public AlphabetTextField username;
    public PasswordTextField password;
    Prefs prefs;

    public LogInController() {
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.prefs = Prefs.getPrefs();
    }

    public void handleCancelAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void handleLoginAction(ActionEvent actionEvent) {
        String uname = this.username.getText();
        String pswd = DigestUtils.shaHex(this.password.getText());
        if (uname.equals(this.prefs.getUsername()) && pswd.equals(this.prefs.getPassword())) {
            MainController.flag = true;
            this.closeStage();
            this.loadMainWindow();
        } else {
            this.username.getStyleClass().add("wrong-credentials");
            this.password.getStyleClass().add("wrong-credentials");
        }

    }

    private void closeStage() {
        ((Stage)this.password.getScene().getWindow()).close();
    }

    private void loadMainWindow() {
        try {
            Parent parent = FXMLLoader.load(this.getClass().getResource("/project/blockchain/main/mainwindow/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("BlockChain Application");
            stage.setScene(new Scene(parent));
            stage.show();
            BlockChainUtil.setStageIcon(stage);
        } catch (IOException var3) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, var3);
        }

    }
}