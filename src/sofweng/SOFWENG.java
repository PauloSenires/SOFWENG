/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofweng;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mave
 */
public class SOFWENG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            update_database();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SOFWENG.class.getName()).log(Level.SEVERE, null, ex);
        }
        Login login = new Login();
        login.show();
    }

    private static void update_database() throws FileNotFoundException {
        Scanner dtb = new Scanner(new File("src\\cpe_database.txt"));
        System.out.println("hello");
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?" + "user=root&password=");
            Statement data = conn.createStatement();
            while (dtb.hasNext()) {
                data.addBatch(dtb.nextLine());
            }
            data.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
