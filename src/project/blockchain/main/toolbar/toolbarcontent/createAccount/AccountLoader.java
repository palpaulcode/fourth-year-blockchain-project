package project.blockchain.main.toolbar.toolbarcontent.createAccount;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.blockchain.DatabaseHandler.Database;

public class AccountLoader extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AccountCreate.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
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