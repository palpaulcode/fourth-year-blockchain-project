package project.blockchain.main.mainwindow;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import project.blockchain.DatabaseHandler.Database;
import project.blockchain.main.toolbar.toolbarcontent.Profile.UserinfoController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    public JFXHamburger hamburger;
    public JFXDrawer drawer;
    public static AnchorPane anchorPane;
    public static StackPane stackPane;
    public Label saa;
    public JFXTextField lastname;
    public JFXTextField firstname;
    public JFXTextField logintime;
    public JFXTextField accPriv;
    public TableColumn<MainController.BlockInfo, String> indexCol;
    public TableColumn<MainController.BlockInfo, String> prevhashCol;
    public TableColumn<MainController.BlockInfo, String> timestampCol;
    public TableColumn<MainController.BlockInfo, String> blockhashCol;
    public TableColumn<MainController.BlockInfo, String> userCol;
    public TableView<MainController.BlockInfo> tableView;
    public AnchorPane rootPane;
    ObservableList<MainController.BlockInfo> list = FXCollections.observableArrayList();
    Database handler = Database.getInstance();
    String lName;
    String nName;
    public static String USERNAME;
    public static boolean flag;
    Thread dateThread;
    DateFormat df = new SimpleDateFormat("hh:mm:ss");
    boolean isRunning = true;

    public MainController() {
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.initDrawer();
        this.initCol();
        this.loadData();
        this.setUserInfo();
        this.checkAccMode();
        this.setFont(this.firstname);
        this.setFont(this.lastname);
        this.setFont(this.logintime);
        this.setFont(this.accPriv);
        this.dateThread = new Thread(this::handleThread);
        this.dateThread.start();
    }

    private void initDrawer() {
        try {
            VBox toolbar = FXMLLoader.load(this.getClass().getResource("/project/blockchain/main/toolbar/toolbarmain/toolbar.fxml"));
            this.drawer.setSidePane(toolbar);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(this.hamburger);
        task.setRate(-1);
        this.hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            if (this.drawer.isHidden()) {
                this.drawer.open();
            } else {
                this.drawer.close();
            }

        });
    }

    private void handleThread() {
        while(this.isRunning) {
            Calendar cl = new GregorianCalendar();
            int day = cl.get(5);
            int month = cl.get(2);
            int year = cl.get(1);
            String dateStr = this.df.format(new Date()) + "  " + day + "/" + month + "/" + year;
            Platform.runLater(() -> {
                this.saa.setText(dateStr);
            });

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }
        }

    }

    private void checkAccMode() {
        if (flag) {
            this.setAdminAccInfo();
        }

    }

    public void setAdminAccInfo() {
        this.firstname.setText("SYSTEM");
        this.lastname.setText("ADMINISTRATOR");
        this.logintime.setText(this.logInTime());
        this.accPriv.setText("admin privileges".toUpperCase());
    }

    public void setUserInfo() {
        UserinfoController controller = new UserinfoController();
        controller.setName(USERNAME);
        String sql = "SELECT firstname,lastname FROM userauth WHERE username = '" + USERNAME + "'";
        ResultSet rs = this.handler.execQuery(sql);

        try {
            if (rs.isBeforeFirst()) {
                while(rs.next()) {
                    this.nName = rs.getString("firstname");
                    this.lName = rs.getString("lastname");
                    this.firstname.setText(this.nName);
                    this.lastname.setText(this.lName);
                    this.logintime.setText(this.logInTime());
                    this.accPriv.setText("user mode".toUpperCase());
                    System.out.println("CURRENT INFORMATION : " + this.nName + " : " + this.lName);
                }
            }
        } catch (SQLException var5) {
            var5.getCause().printStackTrace();
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, var5);
        }

    }

    private String logInTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return dateFormat.format(date);
    }

    private void setFont(JFXTextField textField) {
        textField.setFont(Font.font("Verdana", FontWeight.BOLD, 25.0D));
    }

    private void initCol() {
        this.indexCol.setCellValueFactory(new PropertyValueFactory("index"));
        this.prevhashCol.setCellValueFactory(new PropertyValueFactory("prevhash"));
        this.timestampCol.setCellValueFactory(new PropertyValueFactory("timestamp"));
        this.blockhashCol.setCellValueFactory(new PropertyValueFactory("blockhash"));
    }

    private void loadData() {
        String qu = "SELECT * FROM blocks";
        ResultSet rs = this.handler.execQuery(qu);

        try {
            while(rs.next()) {
                String index = rs.getString("blockIndex");
                String prvHash = rs.getString("prevHash");
                String time = rs.getString("blockTimeStamp");
                String hash = rs.getString("blockHash");
                String user = rs.getString("byUser");
                this.list.add(new MainController.BlockInfo(index, prvHash, time, hash, user));
            }
        } catch (SQLException var8) {
            Logger.getLogger(MainController.BlockInfo.class.getName()).log(Level.SEVERE, null, var8);
        }

        this.tableView.getItems().setAll(this.list);
    }

    public static class BlockInfo {
        private final SimpleStringProperty index;
        private final SimpleStringProperty prevhash;
        private final SimpleStringProperty timestamp;
        private final SimpleStringProperty blockhash;
        private final SimpleStringProperty user;

        BlockInfo(String index, String prevHash, String timeStamp, String blkHash, String user) {
            this.index = new SimpleStringProperty(index);
            this.prevhash = new SimpleStringProperty(prevHash);
            this.timestamp = new SimpleStringProperty(timeStamp);
            this.blockhash = new SimpleStringProperty(blkHash);
            this.user = new SimpleStringProperty(user);
        }

        public String getIndex() {
            return this.index.get();
        }

        public String getPrevhash() {
            return this.prevhash.get();
        }

        public String getTimestamp() {
            return this.timestamp.get();
        }

        public String getBlockhash() {
            return this.blockhash.get();
        }

        public String getUser() {
            return this.user.get();
        }
    }
}
