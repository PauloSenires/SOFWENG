/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofweng;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 *
 * @author Mave
 */
public class FacultyScreen1 extends javax.swing.JFrame {

    public String ID = "11000005";
    public ArrayList<String> subjectList;
    int subjectCount = 0;

    /**
     * Creates new form Faculty
     */
    public FacultyScreen1() throws SQLException {
this.setSize(900, 700);
        initComponents();
        fetchInfo(ID);
        this.setSize(900, 700);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((size.width - this.getSize().width) / 2, (size.height - this.getSize().height) / 2);
        
    }

    public FacultyScreen1(String ID) throws SQLException {
        this.setSize(900, 700);
        initComponents();
        fetchInfo(ID);
        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((size.width - this.getSize().width) / 2, (size.height - this.getSize().height) / 2);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();

        jScrollPane1.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 700));

        jLabel1.setText("Welcome back, ");

        jLabel2.setText("Mr. Albaladejo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(604, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(20, 20, 20))
        );

        jScrollPane2.setViewportView(jTree1);

        jTabbedPane1.addTab("For Submission", jScrollPane2);

        jScrollPane3.setViewportView(jTree2);

        jTabbedPane1.addTab("Submitted", jScrollPane3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FacultyScreen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacultyScreen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacultyScreen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacultyScreen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FacultyScreen1().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(FacultyScreen1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void fetchInfo(String ID) throws SQLException {
        try {
            java.lang.Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=");

            PreparedStatement pst = conn.prepareStatement("Select * from users where ID = ?");
            pst.setString(1, ID);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String name = rs.getString("name");
            name = name.substring(0, 1).toUpperCase() + name.substring(1);

            jLabel2.setText(("male".equals((rs.getString("gender"))) ? "Mr. " : "Ms. ") + name);
            while (!"na".equals(rs.getString("subject" + (subjectCount + 1))) && subjectCount < 10) {
                System.out.println(rs.getString("subject" + (subjectCount + 1)));
                subjectCount++;
                if (subjectCount == 10) {
                    break;
                }
            }
            System.out.println(subjectCount);
            subjectList = new ArrayList<>();
            for (int i = 0; i < subjectCount; i++) {
                subjectList.add(rs.getString("subject" + (i + 1)));
            }
            addSubjectTree();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FacultyScreen1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addSubjectTree() throws SQLException {

        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode("Subjects");
        DefaultMutableTreeNode root2 = new DefaultMutableTreeNode("Subjects");

        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(root1));
        jTree2.setModel(new javax.swing.tree.DefaultTreeModel(root2));
        jTree1.setToggleClickCount(0);
        jTree2.setToggleClickCount(0);
        
        DefaultTreeCellRenderer renderer1 = (DefaultTreeCellRenderer) jTree1.getCellRenderer();
        renderer1.setLeafIcon(renderer1.getClosedIcon());
        DefaultTreeCellRenderer renderer2 = (DefaultTreeCellRenderer) jTree2.getCellRenderer();
        renderer2.setLeafIcon(renderer2.getClosedIcon());
        for (String string : subjectList) {
            try {
                DefaultMutableTreeNode temp1 = new DefaultMutableTreeNode(string);//if not submitted
                DefaultMutableTreeNode temp2 = new DefaultMutableTreeNode(string);//if submitted
                String subj = string.substring(0, string.length() - 3);
//                System.out.println(subj);

                java.lang.Class.forName("com.mysql.jdbc.Driver");

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=");

                PreparedStatement pst = conn.prepareStatement("Select * from students where subject1 LIKE ? or subject2 LIKE ? "
                        + "or subject3 LIKE ? or subject4 LIKE ? or subject5 LIKE ? or subject6 LIKE ? or "
                        + "subject7 LIKE ? or subject8 LIKE ? or subject9 LIKE ? or subject10 LIKE ?");
                for (int i = 1; i < 11; i++) {
                    pst.setString(i, subj + "%");
                }

                ResultSet rs = pst.executeQuery();
                //check whether the faculty have submitted for this subject
                //do this once for every subject with a student (this assumes that every class has a student)
                int subjectNumber = 0;
                boolean isSubmitted = false;
                
                //continue moving the pointer
                while (rs.next()) {
                    String id = rs.getString("ID");
                    DefaultMutableTreeNode student = new DefaultMutableTreeNode(id);
                    
                    
                //find the subject number
                
                for (int i = 1; i < 11; i++) {
                    if(rs.getString("subject"+i).equalsIgnoreCase(subj)){
                        subjectNumber =i;
                    }
                }
//                System.out.println(subjectNumber + " "+subj);
                //check if there is a submission for this subject
                String submissionTime = rs.getString("time"+subjectNumber);
//                    System.out.println(submissionTime + " "+subj);
                if(submissionTime.equals("na")){
                    temp1.add(student);
                    
                }else{
                    temp2.add(student); 
                }
                }
                
                root1.add(temp1);
                root2.add(temp2);
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FacultyScreen1.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        jTree1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    try {
                        System.out.println("double clicked");
                        doMouseClicked(me);
                    } catch (SQLException ex) {
                        Logger.getLogger(FacultyScreen1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

        expandAllNodes(jTree1, 0, jTree1.getRowCount());
        expandAllNodes(jTree2, 0, jTree2.getRowCount());
        System.out.println("hello");
    }

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }


    void doMouseClicked(MouseEvent me) throws SQLException {
        TreePath tp = jTree1.getPathForLocation(me.getX(), me.getY());
        try {
            if (tp != null) {
                
        System.out.println(jTree1.getPathForLocation(me.getX(), me.getY()).toString());
            if(tp.getPath().length==2){
            Object[] path = tp.getPath();
            String subjectAndSection = path[1].toString();
            String subject = subjectAndSection.substring(0, subjectAndSection.length()-3);
            String section = subjectAndSection.toString().substring(subjectAndSection.length()-2);
                System.out.println("section "+section);
            classList cl = new classList(subject, section);
            cl.setVisible(true);
            cl.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    System.out.println("Window closed");
                    
                    addSubjectTree();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(FacultyScreen1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            

        });
            
            }
        }else {
            System.out.println("null");
        }
        } catch (Exception e) {
        }
    }
    public void update() throws SQLException{
        addSubjectTree();
    }
    private java.awt.ScrollPane scrollPane2;
    private ArrayList<javax.swing.JPanel> studentPanel = new ArrayList<>();
    private ArrayList<Boolean> visibility = new ArrayList<>();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTree jTree2;
    // End of variables declaration//GEN-END:variables
}
