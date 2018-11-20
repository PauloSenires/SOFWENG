/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofweng;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.FileOutputStream;

/**
 *
 * @author Mave
 */
public class SubjectList extends javax.swing.JFrame {

    /**
     * Creates new form SubjectList
     */
    ArrayList<String> classList=new ArrayList<String>();
    ArrayList<String> facultyList=new ArrayList<String>();
    ArrayList<String> studentsList=new ArrayList<String>();
    ArrayList<String> submissionCheckList=new ArrayList<String>();
    String studentsInfoList,subject="";
    String tab="           ";
    JButton classBtn,graphBtn,exportBtn;
    JLabel faculty,student;
    JTextArea description=new JTextArea();
    int count,space=0;
    int height=40;
    Font font = new Font("Arial", Font.BOLD,20);
    
    public SubjectList(String name) {
        subject=name;
        initComponents();
        this.setSize(900, 700);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((size.width-this.getSize().width)/2,(size.height-this.getSize().height)/2);
        SubjectName.setText(subject);
        fetchClassList();
        isClassListComplete();
        fetchFacultyList();
        viewClassList(0);
        
    }

    public void fetchClassList(){
        String text="";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=");
            for(int i=1;i<=10;i++){
                text=text+"SELECT `subject"+i+"` FROM `users` WHERE subject"+i+" LIKE '"+subject+"_%'";
                if(i!=10)text=text+ " UNION ";
            }
            PreparedStatement pst = conn.prepareStatement(text);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) { 
                String str1 = rs.getString("subject1");
                classList.add(str1);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(QualityCoordinator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fetchFacultyList(){
        String text="";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=");
                for(int i=1;i<=classList.size();i++){
                    text="SELECT `name` FROM users WHERE";
                    for(int j=1;j<=10;j++){
                        text=text + " subject"+j+"= '"+classList.get(i-1)+"'";
                        if(j!=10)text=text+" OR";
                    }
                    PreparedStatement pst = conn.prepareStatement(text);
                    ResultSet rs = pst.executeQuery();
                    while(rs.next()) { 
                        String str1 = rs.getString("name");
                        facultyList.add(str1);
                    }
                }
            
            conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(QualityCoordinator.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }
    
    public void fetchStudentsList(int count){
        String text="";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=");
                    int i=count;
                    text="SELECT `ID` FROM students WHERE";
                    for(int j=1;j<=10;j++){
                        text=text + " (subject"+j+"= '"+classList.get(i-1).toString().substring(0,7)+"' AND section"+j+"= '"+classList.get(i-1).toString().substring(8)+"')";
                        if(j!=10)text=text+" OR";
                    }
                    PreparedStatement pst = conn.prepareStatement(text);
                    ResultSet rs = pst.executeQuery();
                    while(rs.next()) { 
                        String str1 = rs.getString("ID");
                        studentsList.add(str1);
                    }
            conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(QualityCoordinator.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void fetchStudentInfo(String ID, int section){
        String text="";
        boolean submission=true;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=");
                    int i=count;
                    for(int j=1;j<=10;j++){
                        text="SELECT `time"+j+"` FROM students WHERE";
                        text=text + " (subject"+j+"= '"+classList.get(i-1).toString().substring(0,7)+"') AND";
                        text=text+" (section"+j+"= '"+classList.get(i-1).toString().substring(8)+"') AND";
                        text=text+" ID ='"+ID+"'";
                        PreparedStatement pst = conn.prepareStatement(text);
                        ResultSet rs = pst.executeQuery();
                        while(rs.next()) { 
                            String str1 = rs.getString("time"+j);
                            if(str1.equals("na")) submission=false;
                            if(submission==true){ 
                                char box=9632;
                                str1=box+tab+tab+tab+str1;
                            }
                            else{
                                char box=9633;
                                str1=box+"";
                            }
                            studentsInfoList=str1;
                        }
                    }
            conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(QualityCoordinator.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void courseSummary(){
        String text="";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=");
            for(int i=1;i<=10;i++){
                text="SELECT `detail` FROM `classes` WHERE name='"+subject+"'";
            }
            PreparedStatement pst = conn.prepareStatement(text);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) { 
                String str1 = rs.getString("detail");
                description.setText(str1);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(QualityCoordinator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void isClassListComplete(){
        String text="";
        char submission=9632;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=");
                for(int i=1;i<=classList.size();i++){
                    fetchStudentsList(i);
                    for(int k=1;k<=studentsList.size();k++){
                        for(int j=1;j<=10;j++){
                            text="SELECT `time"+j+"` FROM students WHERE";
                            text=text + " (subject"+j+"= '"+classList.get(i-1).toString().substring(0,7)+"') AND";
                            text=text+" (section"+j+"= '"+classList.get(i-1).toString().substring(8)+"') AND";
                            text=text+" ID='"+studentsList.get(k-1)+"'";
                            PreparedStatement pst = conn.prepareStatement(text);
                            ResultSet rs = pst.executeQuery();
                            while(rs.next()) { 
                                String str1 = rs.getString("time"+j);
                                if(str1.equals("na")) submission=9633;
                            }
                        }
                    }
                    submissionCheckList.add(submission+"");
                }
            conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(QualityCoordinator.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void btnConfig(JButton btn,int order){
        btn.setFont(font);
        btn.setHorizontalAlignment(JLabel.LEFT);
        btn.setSize(900/3-50, height);
        btn.setLocation(btn.getX()+btn.getWidth()*(order-1),btn.getY()+space);
        if(order==3)space=space+height;
    }
    
    public void initDetails(){
        MainPane.add(scrollDetail);
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(font);
        description.setPreferredSize(new Dimension(scrollDetail.getWidth()-25,MainPane.getHeight()));
        description.setSize(description.getPreferredSize());
        scrollDetail.setViewportView(description);
        scrollDetail.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        space=scrollDetail.getHeight();
    }
    public void viewClassList(int open){
        MainPane.removeAll();
        MainPane.revalidate();
        MainPane.repaint();
        ScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        courseSummary();
        initDetails();
        for (int i=1;i<classList.size()+1;i++){
            String class1 =classList.get(i-1).toString().substring(8);
            faculty = new JLabel("Faculty: " + facultyList.get(i-1));
            faculty.setFont(font);
            faculty.setSize(MainPane.getWidth()-50, height);
            MainPane.add(faculty);
            faculty.setFont(font);
            MainPane.add(faculty);
            faculty.setLocation(faculty.getX()+25, faculty.getY()+space);
            space=space+height;
            classBtn = new JButton("Section " +class1+tab+submissionCheckList.get(i-1));
            classBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int j=0;j<classList.size();j++){
                        if(e.getActionCommand().contains(classList.get(j).toString().substring(8))){
                            if(count==j+1) count=0;
                            else count=j+1;
                            viewClassList(count);
                        }
                    }
                }
            });
            MainPane.add(classBtn);
            btnConfig(classBtn,1);
            graphBtn = new JButton("SO Graph " +class1);
            graphBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int j=0;j<classList.size();j++){
                        if(e.getActionCommand().contains(classList.get(j).toString().substring(8))){
                           //Lagay dito SO graph ito param(subject,classList.get(j).toString().substring(8)) //subject at class
                        }
                    }
                }
            });
            MainPane.add(graphBtn);
            btnConfig(graphBtn,2);
            exportBtn = new JButton("Export " +class1);
            exportBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int j=1;j<=classList.size();j++){
                        if(e.getActionCommand().contains(classList.get(j-1).toString().substring(8))){
                           fetchStudentsList(j);
                           //export(subject,classList.get(j).toString().substring(8));
                           viewClassList(count);
                        }
                    }
                }
            });
            MainPane.add(exportBtn);
            btnConfig(exportBtn,3);
            if(count==i) generateStudents(i);
        }
        Dimension size = new Dimension(900,space);
        MainPane.setPreferredSize(size);
        space=0;
    }
    
    public void export(String subject, String class1) throws FileNotFoundException{
        /*FileOutputStream fileOut;
        fileOut = new FileOutputStream("file.xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("Sheet 0");
        Row row1 = worksheet.createRow((short)0);
        row1.createCell(0).setCellValue("Name");
        row1.createCell(1).setCellValue("Address");
        Row row2 ;*/
    }
    
    public void generateStudents(int section){
        studentsList.clear();
        JLabel header = new JLabel("[ID Number]"+tab+"[Submission]"+tab+"[Date and Time of Submission]");
        header.setFont(font);
        header.setSize(MainPane.getWidth(),height);
        header.setLocation(header.getX()+40,header.getY()+space);
        space=space+height;
        fetchStudentsList(section);
        MainPane.add(header);
        for (int i=1;i<studentsList.size()+1;i++){
            fetchStudentInfo(studentsList.get(i-1),section);
            student = new JLabel(studentsList.get(i-1)+tab+tab+studentsInfoList);
            MainPane.add(student);
            student.setFont(font);
            student.setSize(MainPane.getWidth()-10,height);
            student.setLocation(student.getX()+50,student.getY()+space);
            space=space+height;
        }
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
        scrollDetail = new javax.swing.JScrollPane();
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
            .addGroup(MainPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        MainPaneLayout.setVerticalGroup(
            MainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(377, Short.MAX_VALUE))
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
                new QualityCoordinator().setVisible(true);
                dispose();
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(SubjectList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubjectList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubjectList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubjectList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubjectList("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPane;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JLabel SubjectName;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane scrollDetail;
    // End of variables declaration//GEN-END:variables
}
