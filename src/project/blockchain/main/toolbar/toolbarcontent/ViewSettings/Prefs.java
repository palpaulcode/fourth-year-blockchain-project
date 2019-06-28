package project.blockchain.main.toolbar.toolbarcontent.ViewSettings;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import project.blockchain.alerts.AlertMaker;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Prefs {
    public static final String CONFIG_FILE = "configfile.txt";

    private String username;
    private String password;
    private int nonce;

    public Prefs(){
        username = "admin";
        setPassword("admin");
        nonce = 4;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.length() <16 ){
            this.password = DigestUtils.shaHex(password);
        } else {
            this.password = password;
        }
    }
    public int getNonce(){
        return nonce;
    }

    public void setNonce(int nonce){
        this.nonce = nonce;
    }

    public static void initConfigFile(){
        Writer writer =null;

        try {
            Prefs prefs = new Prefs();
            Gson gson =new Gson();
            writer =new FileWriter(CONFIG_FILE);
            gson.toJson(prefs,writer);
        } catch (IOException e) {
            Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    public static Prefs getPrefs(){
        Gson gson =new Gson();
        Prefs prefs =new Prefs();
        try{
            prefs =gson.fromJson(new FileReader(CONFIG_FILE), Prefs.class);
        } catch (FileNotFoundException e) {
            initConfigFile();
            Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, e);
        }
        return prefs;
    }

    public static void writePrefsToConfigFile(Prefs prefs){
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer =new FileWriter(CONFIG_FILE);
            gson.toJson(prefs, writer);

            AlertMaker.showSimpleAlert("Success","Settings updated");
        } catch (IOException e) {
            Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, e);
            AlertMaker.showErrorMessage(e, "Failed", "Could not save Config File");
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
