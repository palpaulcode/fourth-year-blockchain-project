package project.blockchain.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.blockchain.main.toolbar.toolbarcontent.transaction.util.BlockChainUtil;


public class LogInLoader extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));

        Scene scene = new Scene(root);

        BlockChainUtil.setStageIcon(stage);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
