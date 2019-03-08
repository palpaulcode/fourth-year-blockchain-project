package project.blockchain.main.toolbar.toolbarcontent.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

public class SettingsController implements Initializable {
    public JFXTextField username1;
    public JFXTextField username2;
    public JFXPasswordField password1;
    public JFXPasswordField password2;
    public JFXTextField nonceValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDefaultValues();
    }

    public void saveNewSettings(ActionEvent actionEvent) {
        String uname1 = username1.getText();
        String uname2 = username2.getText();
        String pass1 = password1.getText();
        String pass2 = password2.getText();
        int nonce = Integer.parseInt(nonceValue.getText());

        Prefs prefs =new Prefs();
        if ((uname1 == uname2) && (pass1 == pass2) && (nonce != Integer.parseInt(null)) ){
            prefs.setUsername(uname2);
            prefs.setPassword(pass2);
            prefs.setNonce(nonce);
        } else{
            //alert for unmatching details.
        }

        Prefs.writePrefsToConfigFile(prefs);
    }

    public void cancelNewSettings(ActionEvent actionEvent) {
        ((Stage)password2.getScene().getWindow()).close();
    }

    private void initDefaultValues() {
        Prefs prefs = Prefs.getPrefs();

        username1.setText(valueOf(prefs.getUsername()));
        username2.setText(valueOf(prefs.getUsername()));
        password1.setText(valueOf(prefs.getPassword()));
        password2.setText(valueOf(prefs.getPassword()));
        nonceValue.setText(String.valueOf(Integer.parseInt(valueOf(prefs.getNonce()))));
    }

}
