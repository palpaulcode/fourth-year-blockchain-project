package project.blockchain.accountToLogin;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import project.blockchain.main.toolbar.toolbarcontent.transaction.util.BlockChainUtil;

public class SelectAccountController {
    public JFXButton admin;
    public JFXButton user;

    public void loadAdminLogin(ActionEvent actionEvent) {
        closeStage();
        BlockChainUtil.loadWindow(getClass().getResource("/project/blockchain/login/LogIn.fxml"),"Administrator Account",null);
    }

    public void loadUserLogIn(ActionEvent actionEvent) {
        closeStage();
        BlockChainUtil.loadWindow(getClass().getResource("/project/blockchain/MultiUserLogin/sample.fxml"),"User Login",null);
    }

    private void closeStage(){
        ((Stage)admin.getScene().getWindow()).close();
    }
}
