package project.blockchain.main.toolbar.toolbarcontent.Properties;


import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PropertiesController implements Initializable {
    public JFXTextField jrname;
    public JFXTextField jvmver;
    public JFXTextField jvmvendor;
    public JFXTextField javmname;
    public JFXTextField jrv;
    public JFXTextField osarch;
    public JFXTextField osname;
    public JFXTextField osversion;
    public JFXTextField fileencoding;
    public JFXTextField datamodel;
    public JFXTextField javaversion;
    public JFXTextField javavendor;
    public JFXTextField sundesktop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSystemValues();
    }

    private void initializeSystemValues() {
        jrname.setText(System.getProperty("java.runtime.name"));
        jvmver.setText(System.getProperty("java.vm.version"));
        jvmvendor.setText(System.getProperty("java.vm.vendor"));
        javmname.setText(System.getProperty("java.vm.name"));
        jrv.setText(System.getProperty("java.runtime.version"));
        osarch.setText(System.getProperty("os.arch"));
        osname.setText(System.getProperty("os.name"));
        osversion.setText(System.getProperty("os.version"));
        fileencoding.setText(System.getProperty("file.encoding"));
        datamodel.setText(System.getProperty("sun.arch.data.model"));
        javaversion.setText(System.getProperty("java.version"));
        javavendor.setText(System.getProperty("java.vendor"));
        sundesktop.setText(System.getProperty("sun.desktop"));
    }
}
//import java.util.Properties;
//        import java.util.Set;
//
//public class SystemProperties {
//    public static void main(String args[]){
////        Properties properties = System.getProperties();
////        properties.list(System.out);
//
////        String javaVersion = System.getProperty("java.version");
////        String javaHome = System.getProperty("java.home");
////        String osName = System.getProperty("os.name");
////        System.out.println(javaVersion+javaHome+osName);
//
//        Properties sysProps = System.getProperties();
//        Set<String> setOfSysProps = sysProps.stringPropertyNames();
//        for (String propName : setOfSysProps){
//            System.out.println(propName + " - " + sysProps.getProperty(propName));
//        }
//
//
//    }
//}