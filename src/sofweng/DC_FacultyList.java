/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofweng;

import com.mysql.jdbc.StringUtils;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public final class DC_FacultyList extends javax.swing.JFrame {

    /**
     * Creates new form SubjectList
     */
    ArrayList<String> classList = new ArrayList<String>();
    ArrayList<String> checkList = new ArrayList<String>();
    ArrayList<String> check = new ArrayList<String>();
    ArrayList<String> studentsList = new ArrayList<String>();
    ArrayList<String> timeList = new ArrayList<String>();
    String user = "";
    String tab = "         ";
    JLabel Header, info = null;
    int space = 0;
    int height = 40;
    Font font = new Font("Arial", Font.BOLD, 20);

    public DC_FacultyList(String name) {
        user = name;
        initComponents();
        this.setSize(900, 700);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((size.width - this.getSize().width) / 2, (size.height - this.getSize().height) / 2);
        SubjectName.setText(user);
        fetchClassList();
        isClassListComplete();
        viewClassList(0);

    }

    public void fetchClassList() {
        classList.clear();
        String text = "";
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
                text = text + "SELECT * FROM `users` WHERE name='" + user + "'";
                PreparedStatement pst = conn.prepareStatement(text);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    for (int i = 1; i <= 10; i++) {
                        String str1 = rs.getString("subject" + i);
                        if (!str1.equals("na")) {
                            classList.add(str1);
                            System.out.println(str1);
                        }
                    }
                }
                Collections.sort(classList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fetchStudentsList(int count) {
        studentsList.clear();
        String text = "";
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
                int i = count;
                text = "SELECT `ID` FROM students WHERE";
                for (int j = 1; j <= 10; j++) {
                    System.out.println(j+"");
                    text = text + " (subject" + j + "= '" + classList.get(i - 1).substring(0, 7) + "' AND section" + j + "= '" + classList.get(i - 1).substring(8) + "')";
                    if (j != 10) {
                        text = text + " OR";
                    }
                }
                PreparedStatement pst = conn.prepareStatement(text);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    String str1 = rs.getString("ID");
                    studentsList.add(str1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String sortTime(String time1, String time2) {
        if (time2.equals("na")) {
            return time1;
        }
        Integer[] index = {6, 0, 3, 9, 12, 15};
        for (int i = 0; i < 6; i++) {
            if (Integer.parseInt(time1.substring(index[i], index[i] + 1)) > Integer.parseInt(time2.substring(index[i], index[i] + 1))) {
                return time1;
            } else if (Integer.parseInt(time1.substring(index[i], index[i] + 1)) < Integer.parseInt(time2.substring(index[i], index[i] + 1))) {
                return time2;
            }
        }
        return time1;
    }

    public void isClassListComplete() {
        checkList.clear();
        check.clear();
        String text = "";
        String str1 = "";
        String lateTime = "00/00/00 00:00:00";
        char submission = 9632;
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
                for (int i = 1; i <= classList.size(); i++) {
                    fetchStudentsList(i);
                    submission = 9632;
                    for (int k = 1; k <= studentsList.size(); k++) {
                        for (int j = 1; j <= 10; j++) {
                            text = "SELECT `time" + j + "` FROM students WHERE";
                            text = text + " (subject" + j + "= '" + classList.get(i - 1).substring(0, 7) + "') AND";
                            text = text + " (section" + j + "= '" + classList.get(i - 1).substring(8) + "') AND";
                            text = text + " ID='" + studentsList.get(k - 1) + "'";
                            PreparedStatement pst = conn.prepareStatement(text);
                            ResultSet rs = pst.executeQuery();
                            while (rs.next()) {
                                str1 = rs.getString("time" + j);
                                lateTime = sortTime(lateTime, str1);
                                if (str1.equals("na")) {
                                    submission = 9633;
                                }
                            }
                        }
                    }
                    if (str1.equals("na")) {
                        str1 = "";
                    }
                    timeList.add(str1);
                    str1 = "";
                    checkList.add(submission + "");
                    check.add((submission - 9632) + "");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public char isFacultyListComplete(String name) {
        user = name;
        fetchClassList();
        isClassListComplete();
        if (check.contains("1")) {
            return 9633;
        }
        System.out.println(checkList.toString());
        return 9632;
    }

    public void btnConfig(JButton btn) {
        btn.setFont(font);
        btn.setHorizontalAlignment(JLabel.LEFT);
        btn.setSize(900 / 3 - 50, height);
        btn.setLocation(btn.getX() + btn.getWidth(), btn.getY() + space);
        space = space + height;
    }

    public void viewClassList(int open) {
        MainPane.removeAll();
        MainPane.revalidate();
        MainPane.repaint();
        ScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Header = new JLabel("Course             Section              Submitted            Date and Time submitted");
        Header.setFont(font);
        Header.setSize(MainPane.getWidth() - 50, height);
        MainPane.add(Header);
        Header.setFont(font);
        Header.setLocation(Header.getX() + 100, Header.getY());
        space = space + height;
        generateSection();
        Dimension size = new Dimension(900, space);
        MainPane.setPreferredSize(size);
        space = 0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SubjectName = new javax.swing.JLabel();
        ScrollPane = new javax.swing.JScrollPane();
        MainPane = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        SubjectName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        SubjectName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SubjectName.setText("jLabel1");

        ScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout MainPaneLayout = new javax.swing.GroupLayout(MainPane);
        MainPane.setLayout(MainPaneLayout);
        MainPaneLayout.setHorizontalGroup(
            MainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 872, Short.MAX_VALUE)
        );
        MainPaneLayout.setVerticalGroup(
            MainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 542, Short.MAX_VALUE)
        );

        ScrollPane.setViewportView(MainPane);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton1.setText("<<");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SubjectName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SubjectName, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DC_Screen().setVisible(true);
                dispose();
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    public void generateSection() {
        for (int i = 1; i < classList.size() + 1; i++) {
            for (int j = 0; j < 4; j++) {
                switch (j) {
                    case (0):
                        info = new JLabel(classList.get(i - 1).substring(0, 7));
                        break;
                    case (1):
                        info = new JLabel(classList.get(i - 1).substring(8));
                        break;
                    case (2):
                        info = new JLabel(checkList.get(i - 1));
                        break;
                    case (3):
                        info = new JLabel(timeList.get(i - 1));
                        break;
                    default:
                        info = new JLabel("");
                        break;
                }
                info.setLocation(100 + 170 * j, 40 * i);
                MainPane.add(info);
                info.setFont(font);
                info.setSize(MainPane.getWidth() - 10, height);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String name) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DC_FacultyList(name).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPane;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JLabel SubjectName;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
