package project.blockchain.MultiUserLogin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.blockchain.DatabaseHandler.Database;
import project.blockchain.main.toolbar.toolbarcontent.transaction.util.BlockChainUtil;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root);

        BlockChainUtil.setStageIcon(stage);

        stage.setScene(scene);
        /*stage.setTitle("Multi-user login platform");*/
        stage.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Database.getInstance();
            }
        }).start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
