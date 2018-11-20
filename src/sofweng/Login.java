/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofweng;

import com.sun.glass.events.KeyEvent;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mave
 */
public class Login extends javax.swing.JFrame {

    private String idNumber;
    private String password;
    private String level;

    /**
     * Creates new form NewJFrame
     */
    public Login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        idNumberField = new javax.swing.JTextField();
        registerBtn = new javax.swing.JButton();
        signInBtn = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(405, 311));
        setResizable(false);
        setSize(new java.awt.Dimension(405, 311));

        jLabel1.setText("ID Number");

        jLabel2.setText("Password");

        idNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idNumberFieldActionPerformed(evt);
            }
        });
        idNumberField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idNumberFieldKeyPressed(evt);
            }
        });

        registerBtn.setText("Register");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        signInBtn.setText("Sign in");
        signInBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInBtnActionPerformed(evt);
            }
        });

        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(signInBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(idNumberField)
                            .addComponent(passwordField))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerBtn)
                    .addComponent(signInBtn))
                .addContainerGap(209, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        //Action
        Register reg = new Register();
        reg.setVisible(true);
        reg.setLocationRelativeTo(this);
    }//GEN-LAST:event_registerBtnActionPerformed

    private void idNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idNumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idNumberFieldActionPerformed

    private void idNumberFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idNumberFieldKeyPressed
        // Action when either field is Empty
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (idNumberField.getText().equals("") || passwordField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Incomplete! Please input either ID number or password");
            } else {
                idNumber = idNumberField.getText();
                password = passwordField.getText();
                Login(idNumber, password);
            }
        }
    }//GEN-LAST:event_idNumberFieldKeyPressed

    private void passwordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyPressed
        // Action when either field is Empty
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (idNumberField.getText().equals("") || passwordField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Incomplete! Please input either ID number or password");
            } else {
                idNumber = idNumberField.getText();
                password = passwordField.getText();
                Login(idNumber, password);
            }
        }
    }//GEN-LAST:event_passwordFieldKeyPressed

    private void signInBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signInBtnActionPerformed
        if (idNumberField.getText().equals("") || passwordField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Incomplete! Please input either ID number or password");
        } else {
            idNumber = idNumberField.getText();
            password = passwordField.getText();
            Login(idNumber, password);
        }
    }//GEN-LAST:event_signInBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    //Public Functions
    public void Login(String idNumber, String Password) {
        //Checking of Connection.
        String url = "jdbc:mysql://localhost:3306/cpe_database";
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "");
        try {
            Driver d = new com.mysql.jdbc.Driver();
            Connection con = d.connect(url, prop);
            if (con == null) {
                System.out.println("connection failed");
                return;
            } else {
                System.out.println("Connected.");
            }
            Statement stat = con.createStatement();

            //Database variables
            String databasePassword = "";
            String databaseVerified = "";
            ResultSet result = stat.executeQuery("Select Password from users where ID = \'" + idNumber + "\'");

            //databasePassword acquisition and comparison to password variable.
            if (!result.next()) {
                System.out.println("ID not in database!");
            } else {
                do {
                    databasePassword = result.getString(1);
                    //System.out.println("password: "+databasePassword);
                } while (result.next());

                if (databasePassword.equals(password)) {
                    //acquire verification of user.
                    result = stat.executeQuery("Select verified from users where ID = \'" + idNumber + "\'");
                    while (result.next()) {
                        databaseVerified = result.getString(1);
                    }

                    //Checks verification, if verified then user continues,
                    //if not then user must wait for admin to verify their account
                    switch (databaseVerified) {
                        default: //not yet verified
                            JOptionPane.showMessageDialog(null, "Your account "
                                    + "has not been verified, please wait for "
                                    + "Admin, thank you.");
                            break;
                        case "1": //verified
                            JOptionPane.showMessageDialog(null, "You have successfully logged in");
                            toUserWindow(idNumber, stat);
                            this.dispose();
                            break;
                        case "2": //rejected
                            JOptionPane.showMessageDialog(null, "This account"
                                    + "has been rejected by Admin.");
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password!");
                }
            }
        } catch (Exception e) {
            System.out.println("Did not connect to DB - Error: " + e);
            e.printStackTrace();
        }
    }

    public void toUserWindow(String idNumber, Statement stat) {
        //get the data needed to know which window to open after login
        try {
            //Database variables
            String databaseLevel = "";
            ResultSet result = stat.executeQuery("Select level from users where ID = \'" + idNumber + "\'");

            //databaseLevel acquisition and comparison to determine which class
            //to go to next.
            while (result.next()) {
                databaseLevel = result.getString(1);
                //System.out.println("Level: "+databaseLevel);
            }
            switch (databaseLevel) {
                default: //Faculty
                    FacultyScreen1 facultyWindow = new FacultyScreen1();
                    facultyWindow.setVisible(true);
                    facultyWindow.setLocationRelativeTo(this);
                    this.dispose();
                    break;
                case "1": //Quality Coordinator
                    QC_Screen qualityCoordWindow = new QC_Screen();
                    qualityCoordWindow.setVisible(true);
                    qualityCoordWindow.setLocationRelativeTo(this);
                    this.dispose();
                    break;
                case "2": //Department Coordinator
                    DC_Screen deptCoordWindow = new DC_Screen();
                    deptCoordWindow.setVisible(true);
                    deptCoordWindow.setLocationRelativeTo(this);
                    this.dispose();
                    break;
                case "3": //Administrator
                    QC_Screen adminWindow = new QC_Screen();
                    adminWindow.setVisible(true);
                    adminWindow.setLocationRelativeTo(this);
                    this.dispose();
                    break;
            }

        } catch (Exception e) {
            System.out.println("Did not connect to DB - Error: " + e);
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField idNumberField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerBtn;
    private javax.swing.JButton signInBtn;
    // End of variables declaration//GEN-END:variables
}
