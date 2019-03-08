package project.blockchain.main.mainwindow;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import project.blockchain.alerts.AlertMaker;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class MainController implements Initializable{
    public JFXHamburger hamburger;
    public JFXDrawer drawer;
    public static AnchorPane anchorPane;
    public static StackPane stackPane;
    public Label saa;

    Thread dateThread;
    DateFormat df = new SimpleDateFormat("hh:mm:ss");

    boolean isRunning = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDrawer();

        //Thread to assign new values to FX time UI
        dateThread = new Thread(this::handleThread);
        dateThread.start();
    }

    private void initDrawer() {
        try {
            VBox toolbar = FXMLLoader.load(getClass().getResource("/project/blockchain/main/toolbar/toolbarmain/toolbar.fxml"));
            drawer.setSidePane(toolbar);
            //drawer.setDefaultDrawerSize(125);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
             task.setRate(task.getRate() * -1);
             task.play();

            if(drawer.isHidden()){
                drawer.open();
            }else{
                drawer.close();
            }
        });

        //clock();
    }

    //called in class TransactionController
    /*public static void mainAlert(){
        AlertMaker.showMaterialDialog(stackPane,anchorPane, null,null,null);
    }*/

    //clock method
    private void handleThread(){

        while (isRunning){
            //String dateStr = ("current time: " + df.format(new Date()));
            //new code
            Calendar cl = new GregorianCalendar();

            int day = cl.get(Calendar.DAY_OF_MONTH);
            int month = cl.get(Calendar.MONTH);
            int year = cl.get(Calendar.YEAR);

            /*int sec = cl.get(Calendar.SECOND);
            int min = cl.get(Calendar.MINUTE);
            int hour = cl.get(Calendar.HOUR);
            int am_pm = cl.get(Calendar.AM_PM);

            String d_n = "";
            if (am_pm == 1){
                d_n = "PM";
            }else {
                d_n = "AM";
            }*/

            String dateStr =  (df.format(new Date()) + "  " + day +"/"+ month +"/"+ year);

            Platform.runLater(()->{
                saa.setText(dateStr);
            } );

            try{
                Thread.sleep(1000);
            } catch (InterruptedException iex){
                iex.printStackTrace();
            }
        }
    }
}
