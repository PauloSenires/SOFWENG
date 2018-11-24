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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public final class QC_SubjectList extends javax.swing.JFrame {

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
    
    public QC_SubjectList(String name) {
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
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
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
            }
        } catch (SQLException ex) {
            Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fetchFacultyList(){
        String text="";
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
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
            }
            } catch (SQLException ex) {
                Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }
    
    public void fetchStudentsList(int count){
        String text="";
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
                int i=count;
                text="SELECT `ID` FROM students WHERE";
                for(int j=1;j<=10;j++){
                    text=text + " (subject"+j+"= '"+classList.get(i-1).substring(0,7)+"' AND section"+j+"= '"+classList.get(i-1).substring(8)+"')";
                    if(j!=10)text=text+" OR";
                }
                PreparedStatement pst = conn.prepareStatement(text);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    String str1 = rs.getString("ID");
                    studentsList.add(str1);
                }
            }
            } catch (SQLException ex) {
                Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void fetchStudentInfo(String ID, int section){
        String text="";
        boolean submission=true;
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
                int i=count;
                for(int j=1;j<=10;j++){
                    text="SELECT `time"+j+"` FROM students WHERE";
                    text=text + " (subject"+j+"= '"+classList.get(i-1).substring(0,7)+"') AND";
                    text=text+" (section"+j+"= '"+classList.get(i-1).substring(8)+"') AND";
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
            }
            } catch (SQLException ex) {
                Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void courseSummary(){
        String text="";
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
                for(int i=1;i<=10;i++){
                    text="SELECT `detail` FROM `classes` WHERE name='"+subject+"'";
                }
                PreparedStatement pst = conn.prepareStatement(text);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    String str1 = rs.getString("detail");
                    description.setText(str1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void isClassListComplete(){
        String text="";
        char submission=9632;
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
                for(int i=1;i<=classList.size();i++){
                    fetchStudentsList(i);
                    for(int k=1;k<=studentsList.size();k++){
                        for(int j=1;j<=10;j++){
                            text="SELECT `time"+j+"` FROM students WHERE";
                            text=text + " (subject"+j+"= '"+classList.get(i-1).substring(0,7)+"') AND";
                            text=text+" (section"+j+"= '"+classList.get(i-1).substring(8)+"') AND";
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
            }
            } catch (SQLException ex) {
                Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
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
            String class1 =classList.get(i-1).substring(8);
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
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int j=0;j<classList.size();j++){
                        if(e.getActionCommand().contains(classList.get(j).substring(8))){
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
                        if(e.getActionCommand().contains(classList.get(j-1).substring(8))){
                           fetchStudentsList(j);
                            try {
                                export(subject,classList.get(j-1).substring(8),j-1,facultyList.get(j-1));
                            } catch (WriteException ex) {
                                Logger.getLogger(QC_SubjectList.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(QC_SubjectList.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
                new QC_Screen().setVisible(true);
                dispose();
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    
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
            student.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                     JLabel temp=(JLabel) e.getComponent();
                     java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                JFrame frame = new JFrame();
                                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                StudentGraph studentGraph = new StudentGraph(temp.getText().substring(0,8));
                                frame.add(studentGraph);
                                frame.setVisible(true);
                                frame.setSize(750,600);
                                studentGraph.start(); 
                            }
                        });
                }
            });
            MainPane.add(student);
            student.setFont(font);
            student.setSize(MainPane.getWidth()-10,height);
            student.setLocation(student.getX()+50,student.getY()+space);
            space=space+height;
        }
    }
    
    public void initHeader(String subject, String class1,int classNo, String faculty) throws WriteException{
        row=new ArrayList<String>();
        column=new ArrayList<ArrayList<String>>();
        String[] header1={subject,class1,faculty};
        row.addAll(Arrays.asList(header1));
        column.add(row);
        row.clear();
        int count1=0;
        ArrayList<String> header2= new ArrayList<String>();
        header2.add("ID");
        header2.add("Final Grade");
        header2.add("QE");
        header2.add("FE");
        header2.add("TE");
        header2.add("PE");
        String text="";
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
                for(int i=1;i<=10;i++){
                    text="SELECT `SO` FROM `classes` WHERE name='"+subject+"'";
                }
                String str1="";
                PreparedStatement pst = conn.prepareStatement(text);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) 
                    str1 = rs.getString("SO");
                    count1=(str1.length()/2)+1;
                    soCount=count1;
                    for(int i=0;i<count1;i++){
                        header2.add(str1.substring(i*2,1+i*2));
                    }
                }
        } catch (SQLException ex) {
            Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
        header2.add("Time of Submission");
        row.addAll(header2);
        write(row);
        row.clear();
    }
    
    ArrayList<String> row;
    ArrayList<ArrayList<String>> column;
    WritableFont fontFormat;
    WritableCellFormat cellFormat;
    WritableSheet sheet;
    int soCount,counter=0;
        
    public void fetchStudentGrade(int section) throws WriteException{
        row = new ArrayList<String>();
        column = new ArrayList<ArrayList<String>>();
        String text="";
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cpe_database?" + "user=root&password=")) {
                int m=section;
                text="SELECT `ID` FROM students WHERE";
                for(int j=1;j<=10;j++){
                    text=text + " (subject"+j+"= '"+classList.get(m).substring(0,7)+"') AND";
                    text=text+" (section"+j+"= '"+classList.get(m).substring(8)+"')";
                    if(j!=10)text=text+" OR";
                }
                PreparedStatement pst = conn.prepareStatement(text);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    String str1 = rs.getString("ID");
                    studentsList.add(str1);
                }
                    for(int j=1;j<=10;j++){
                        text="SELECT * FROM students WHERE";
                        text=text + " (subject"+j+"= '"+classList.get(m).substring(0,7)+"') AND";
                        text=text+" (section"+j+"= '"+classList.get(m).substring(8)+"')" ;
                        PreparedStatement ps = conn.prepareStatement(text);
                        ResultSet rs1 = ps.executeQuery();
                        while(rs1.next()) {
                            String str1="";
                            row.add(rs1.getString("ID"));
                            row.add(rs1.getString("grade"+j));
                            str1=rs1.getString("rawscore"+j);
                            String[] data=new String[4];
                            if(str1.equals("na")) java.util.Arrays.fill(data, "na");
                            else data=str1.split(",");
                            row.addAll(Arrays.asList(data));
                            str1=rs1.getString("so"+j);
                            data=new String[soCount];
                            if(str1.equals("na")) java.util.Arrays.fill(data, "na");
                            else data=str1.split(",");
                            row.addAll(Arrays.asList(data));
                            row.add(rs1.getString("time"+j));
                            write(row);
                            row.clear();
                        }
                    }
                
            }
            } catch (SQLException ex) {
                Logger.getLogger(QC_Screen.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void export(String subject, String class1,int classNo, String faculty) throws WriteException, IOException{
        fontFormat = new WritableFont(WritableFont.TAHOMA, 12);
        cellFormat = new WritableCellFormat(fontFormat);
        String dir="C:\\Data\\";
        String text="C:\\Data\\"+subject+"_"+class1+".xls";
        File directory=new File(dir);
        if(!directory.exists())directory.mkdir();
        File file = new File(text);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet(subject+"_"+class1, 0);
        sheet = workbook.getSheet(0);
        sheet.setColumnView(0, 12);
        sheet.setColumnView(1, 8);
        initHeader(subject,class1,classNo,faculty);
        fetchStudentGrade(classNo);
        workbook.write();
        workbook.close();
        counter=0;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QC_ExcelNotification().setVisible(true);
            }
        });
    }
    
    public void write(ArrayList<String> text) throws WriteException{
        Label input=null;
        for(int i=0;i<text.size();i++){
            input = new Label(i,counter,text.get(i),cellFormat);
            sheet.addCell(input);
        }
        counter++;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String name) {
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
            java.util.logging.Logger.getLogger(QC_SubjectList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QC_SubjectList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QC_SubjectList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QC_SubjectList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QC_SubjectList(name).setVisible(true);
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
