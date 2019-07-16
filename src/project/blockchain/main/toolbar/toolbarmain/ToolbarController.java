package project.blockchain.main.toolbar.toolbarmain;

import javafx.event.ActionEvent;
import project.blockchain.main.toolbar.toolbarcontent.transaction.util.BlockChainUtil;

public class ToolbarController {

    public void loadTransaction(ActionEvent actionEvent) {
        BlockChainUtil.loadWindow(getClass().getResource("/project/blockchain/main/toolbar/toolbarcontent/transaction/Transact/InitTransaction.fxml"),"New Transaction",null);
    }

    public void loadProfile(ActionEvent actionEvent) {
    }

    public void loadSystem(ActionEvent actionEvent) {
        BlockChainUtil.loadWindow(getClass().getResource("/project/blockchain/main/toolbar/toolbarcontent/Properties/properties.fxml"), "System Properties", null);
    }

    public void loadSettings(ActionEvent actionEvent) {
        BlockChainUtil.loadWindow(getClass().getResource("/project/blockchain/main/toolbar/toolbarcontent/settings/settings.fxml"),"Settings",null);
    }
}
