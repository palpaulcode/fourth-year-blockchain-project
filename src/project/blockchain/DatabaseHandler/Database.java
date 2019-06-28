package project.blockchain.DatabaseHandler;

import javax.swing.*;
import java.sql.*;

public class Database {

    String DATABASE = "USERAUTH";

    private static Database handler = null;

    String db_url = "jdbc:mysql://localhost:3306/";
    String userPass = "?user=root&password=";
    String username = "root";
    String password = "";

    private static Connection conn = null;
    private static Statement stmt = null;
    /*private static PreparedStatement pstmt = null;*/
    /*private static ResultSet rs;*/

    //class constructor
    private Database(){
        createUsersDatabase();
        createConnection();
        setupProfilesTable();
        setupBlocksTable();
    }

    //instantiation of the database object
    public static Database getInstance()
    {
        if(handler == null){
            handler = new Database();
        }
        return handler;
    }

    void createConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(db_url+DATABASE,username,password);
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            JOptionPane.showMessageDialog(null,"Could not load database","Database Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    void createUsersDatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(db_url+userPass);
            stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + DATABASE );
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("UNABLE TO CREATE DATABASE");
            e.printStackTrace();
        }
    }

    void setupProfilesTable(){
        String TABLE_NAME = "USERAUTH";
        try {
            System.out.println("Checking if table " + TABLE_NAME + " exists");

            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            System.out.println("Initializing . . .");
            if (tables.next()){
                System.out.println("Table " +TABLE_NAME + " already exists!");
            }
            else {
                stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                       /* " userid INT NOT NULL AUTO_INCREMENT" +*/
                        " firstname varchar(50),\n" +
                        " lastname varchar(50),\n" +
                        " username varchar(50) primary key,\n" +
                        " password varchar(15),\n" +
                        " createtime varchar(50)\n" +
                        " )");

                System.out.println("Table " + TABLE_NAME + " CREATED SUCCESSFULLY");
            }
        } catch (SQLException e){
            System.err.println(e.getMessage() + " -->> Error creating table" + TABLE_NAME);
        } finally {

        }
    }

    //blockchain table
    void setupBlocksTable(){
        String TABLE_NAME = "BLOCKS";
        try {
            System.out.println("Checking if table "+ TABLE_NAME + " exists");

            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            System.out.println("Initializing . . . ");
            if (tables.next()){
                System.out.println("Table " + TABLE_NAME + " already exists");
            }else {
                stmt.execute("CREATE TABLE IF NOT EXISTS " +TABLE_NAME+ "(" +
                        " blockIndex INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                        " prevHash VARCHAR (500),\n" +
                        " blockTimeStamp VARCHAR (150),\n" +
                        " blockHash VARCHAR (500),\n" +
                        " byUser VARCHAR (50) \n" +
                        /*" FOREIGN KEY (byUser) REFERENCES userauth(username)" +*/
                        " )AUTO_INCREMENT = 10");

                System.out.println("Table " + TABLE_NAME + " CREATED SUCCESSFULLY");
            }
        }catch (SQLException e){
            System.err.println(e.getMessage() + " -->> Error creating table" + TABLE_NAME);
        } finally {

        }
    }

    /*public connectToDatabase(){
        String sql = "SELECT FROM "
    }*/

    public ResultSet execQuery(String query){
        ResultSet result;
        try{
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex){
            System.out.println("exception at execQuery:datahandler 1 " + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }

    public boolean execAtion(String qu){
        try{
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "error:" + ex.getMessage(),"error occurred",JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler 2" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }

}
