package project.blockchain.main.toolbar.toolbarcontent.transaction.Transact;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import project.blockchain.alerts.AlertMaker;
import project.blockchain.main.toolbar.toolbarcontent.settings.Prefs;
import project.blockchain.main.toolbar.toolbarcontent.transaction.BlockChain.BlockChain;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    public JFXTextField inst=null;
    public JFXTextField amt=null;
    public JFXTextArea trsDesc=null;
    public StackPane rootPane;
    public AnchorPane rootAnchorPane;

    Prefs prefs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prefs = Prefs.getPrefs();
    }

    public void commitTransaction(ActionEvent actionEvent) {

        JFXButton yesBtn = new JFXButton("Yes");
            yesBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                if (inst == null | amt != null | trsDesc != null) {
                    JFXButton noData = new JFXButton("Okay, I'm new to the System!  :[ ");
                    AlertMaker.showMaterialDialog(rootPane, rootAnchorPane,Arrays.asList(noData),"Commit Failed"
                            ,"System Detected No Data Entered");
                  } else {
                    commitTransaction();
                    JFXButton btn = new JFXButton("Done");
                    AlertMaker.showMaterialDialog(rootPane, rootAnchorPane, Arrays.asList(btn),"Transaction " +
                            "Committed",null);
                }
            });

        //Action taken if user clicks No ()
        JFXButton noBtn = new JFXButton("No");
        noBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) ->{
            JFXButton okBtn = new JFXButton("Okay");
            AlertMaker.showMaterialDialog(rootPane,rootAnchorPane,Arrays.asList(okBtn),"Transaction cancelled",
                    null);

            clearEntries();
        });

        AlertMaker.showMaterialDialog(rootPane,rootAnchorPane,Arrays.asList(yesBtn,noBtn),"Commit Transaction?",
                "Are you sure you want to commit transaction?");
//        MainController.mainAlert();

    }

    private void clearEntries() {
        inst.clear();
        amt.clear();
        trsDesc.clear();
    }

    private void commitTransaction() {

        int nonce = prefs.getNonce();
        String institution = inst.getText();
        String amount = amt.getText();
        String description = trsDesc.getText();

        StringBuilder builder = new StringBuilder();
        builder.append(institution).append(amount).append(description); //.append(new Date(timeStamp))

        String data = String.valueOf(builder);

        BlockChain blockChain = new BlockChain(nonce);
        blockChain.addBlock(blockChain.newBlock(data));

        //prints info to console
        System.out.println(blockChain);
    }


}
