/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminui;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Elijah
 */
public class AdminUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  throws SQLException {
        // TODO code application logic here
        setupJBDC();
        //Login, insert login
        LoginUI loginframe = new LoginUI();
        loginframe.setVisible(true);
    }
    
    public static void setupJBDC() throws SQLException
    {
        String url="jdbc:mysql://localhost:3306/sofweng";
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "");
        Driver d = new com.mysql.jdbc.Driver();
        Connection con = d.connect(url, prop);
        if(con==null)   
        {
            System.out.println("connection failed");
            return;
        }
        else
        {
            System.out.println("Connected");
        }
    }
}
