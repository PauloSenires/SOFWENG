/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofweng;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.Program;
import static acm.program.Program.NORTH;
//import java.awt.Dimension;
import java.awt.Panel;
//import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JLabel;

/**
 *
 * @author Harley Orines
 */
public class SOGraph extends Program {

    //private final int applicationWidth = 650;
    //private final int applicationHeight = 650;
    private final int width = 500;
    private final int height = 500;

    private final GCanvas canvas = new GCanvas();

    @SuppressWarnings("FieldMayBeFinal")
    private JLabel UpperLabel;
    private JLabel CanvasSOLabel;

    Panel panelOne = new Panel();
    Panel panelTwo = new Panel();

    private Statement statement;

    private static String TEST_SUBJ_NAME;
    private static String TEST_SECTION;
    private static int currentSubjNumberOfSos;

    public SOGraph(String subjName, String Section) {
        
        TEST_SUBJ_NAME = subjName;
        TEST_SECTION = Section;
        //this.setSize(applicationWidth, applicationHeight);
        //this.setPreferredSize(new Dimension(650, 650));
        this.setTitle(subjName + " " + Section + " " + "Student Outcome summary");
        canvas.setSize(width, height);
        add(panelOne, NORTH);
        UpperLabel = new JLabel(subjName + " " + Section + " " + "Student Outcome summary");
        panelOne.add(UpperLabel);
        add(canvas);
        ArrayList<String> SOLettersArrayList = new ArrayList<>(); //var storage of SO letters; arraylist para variable length

        try {
            String url = "jdbc:mysql://localhost:3306/cpe_database";
            Properties prop = new Properties();
            prop.setProperty("user", "root");
            prop.setProperty("password", "");
            Driver d = new com.mysql.jdbc.Driver();
            Connection con = d.connect(url, prop);
            if (con == null) {
                System.out.println("Connection failed");
                return;
            } else {
                System.out.println("Connected");
            }
            statement = con.createStatement();
            statement.execute("USE cpe_database;");
        } catch (SQLException e) {
            System.out.println("ERROR");
        }

        try {
            ResultSet soLetterRS = statement.executeQuery("SELECT Name, SO FROM classes WHERE Name = '" + subjName + "'");
            String[] var = {" ", " "};
            while (soLetterRS.next()) {
                var = soLetterRS.getString(2).split(",");
                currentSubjNumberOfSos = var.length;
                
                //soLetters.addAll(Arrays.asList(SOStringArray));
            }
            
            for (int i = 0; i < var.length; i++) {
                    SOLettersArrayList.add(var[i]);
            }
            
        } catch (SQLException ex) {
            System.out.println("Class SOs not obtained");
        }

        drawCanvas(SOLettersArrayList);
    }

    public void drawCanvas(ArrayList<String> SOLettersArrayListInput) {
        
        int p = 0;
        ArrayList<Integer> idlist = new ArrayList<>();
        String[] tempString = new String[currentSubjNumberOfSos];

        try {
            ResultSet IdSoSet1 = statement.executeQuery("SELECT * FROM students WHERE ((subject1 = '" + TEST_SUBJ_NAME + "') AND (section1 = '" + TEST_SECTION + "')) OR ((subject2 = '" + TEST_SUBJ_NAME + "') AND (section2 = '" + TEST_SECTION + "')) OR ((subject3 = '" + TEST_SUBJ_NAME + "') AND (section3 = '" + TEST_SECTION + "')) OR ((subject4 = '" + TEST_SUBJ_NAME + "') AND (section4 = '" + TEST_SECTION + "')) OR ((subject5 = '" + TEST_SUBJ_NAME + "') AND (section5 = '" + TEST_SECTION + "')) OR ((subject6 = '" + TEST_SUBJ_NAME + "') AND (section6 = '" + TEST_SECTION + "')) OR ((subject7 = '" + TEST_SUBJ_NAME + "') AND (section7 = '" + TEST_SECTION + "')) OR ((subject8 = '" + TEST_SUBJ_NAME + "') AND (section8 = '" + TEST_SECTION + "')) OR ((subject9 = '" + TEST_SUBJ_NAME + "') AND (section9 = '" + TEST_SECTION + "')) OR ((subject10 = '" + TEST_SUBJ_NAME + "') AND (section10 = '" + TEST_SECTION + "'))");
            while (IdSoSet1.next()) {
                idlist.add(IdSoSet1.getInt(IdSoSet1.findColumn("ID")));
            }
            String[][] StringArray2d = new String[idlist.size()][currentSubjNumberOfSos];
            Double[][] DoubleArray2d = new Double[idlist.size()][currentSubjNumberOfSos];
            Double[] AverageArray = new Double[currentSubjNumberOfSos]; // array of average values
            ResultSet IdSoSet2 = statement.executeQuery("SELECT * FROM students WHERE ((subject1 = '" + TEST_SUBJ_NAME + "') AND (section1 = '" + TEST_SECTION + "')) OR ((subject2 = '" + TEST_SUBJ_NAME + "') AND (section2 = '" + TEST_SECTION + "')) OR ((subject3 = '" + TEST_SUBJ_NAME + "') AND (section3 = '" + TEST_SECTION + "')) OR ((subject4 = '" + TEST_SUBJ_NAME + "') AND (section4 = '" + TEST_SECTION + "')) OR ((subject5 = '" + TEST_SUBJ_NAME + "') AND (section5 = '" + TEST_SECTION + "')) OR ((subject6 = '" + TEST_SUBJ_NAME + "') AND (section6 = '" + TEST_SECTION + "')) OR ((subject7 = '" + TEST_SUBJ_NAME + "') AND (section7 = '" + TEST_SECTION + "')) OR ((subject8 = '" + TEST_SUBJ_NAME + "') AND (section8 = '" + TEST_SECTION + "')) OR ((subject9 = '" + TEST_SUBJ_NAME + "') AND (section9 = '" + TEST_SECTION + "')) OR ((subject10 = '" + TEST_SUBJ_NAME + "') AND (section10 = '" + TEST_SECTION + "'))");
            while (IdSoSet2.next()) {
                if (IdSoSet2.getString(IdSoSet2.findColumn("subject1")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so1")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so1")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject2")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so2")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so2")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject3")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so3")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so3")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject4")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so4")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so4")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject5")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so5")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so5")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject6")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so6")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so6")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject7")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so7")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so7")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject8")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so8")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so8")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject9")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so9")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so9")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject10")).equals(TEST_SUBJ_NAME)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so10")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so10")).split(",");
                    }
                }
                StringArray2d[p] = tempString;
                p++;
            }

            for (int j = 0; j < idlist.size(); j++) {
                for (int k = 0; k < currentSubjNumberOfSos; k++) {
                    DoubleArray2d[j][k] = Double.parseDouble(StringArray2d[j][k]);
                }
            }
            
            for (int k = 0; k < currentSubjNumberOfSos; k++) {
                
                double sum = 0;
                
                for (int j = 0; j < idlist.size(); j++) {
                    
                    sum = sum + DoubleArray2d[j][k];   
                
                }
                
                AverageArray[k] = sum / idlist.size();
                System.out.println(AverageArray[k]);
                
            }

            double xItemSpace = 60;
            double xGilidSpace = 30;
            double xStart = 100;
            double xEnd = xStart + ((xGilidSpace * 2) + (xItemSpace * currentSubjNumberOfSos));
            double yStart = 50;
            double yEnd = 350;
            GLine xgraphborder = new GLine(xStart, yEnd, xEnd, yEnd);
            GLine ygraphborder = new GLine(xStart, yStart, xStart, yEnd);
            GLabel ylabel0 = new GLabel("0", 90, 350);
            GLabel ylabel10 = new GLabel("10", 85, 320);
            GLabel ylabel20 = new GLabel("20", 85, 290);
            GLabel ylabel30 = new GLabel("30", 85, 260);
            GLabel ylabel40 = new GLabel("40", 85, 230);
            GLabel ylabel50 = new GLabel("50", 85, 200);
            GLabel ylabel60 = new GLabel("60", 85, 170);
            GLabel ylabel70 = new GLabel("70", 85, 140);
            GLabel ylabel80 = new GLabel("80", 85, 110);
            GLabel ylabel90 = new GLabel("90", 85, 80);
            GLabel ylabel100 = new GLabel("100", 79, 50);
            canvas.add(xgraphborder);
            canvas.add(ygraphborder);
            canvas.add(ylabel0);
            canvas.add(ylabel10);
            canvas.add(ylabel20);
            canvas.add(ylabel30);
            canvas.add(ylabel40);
            canvas.add(ylabel50);
            canvas.add(ylabel60);
            canvas.add(ylabel70);
            canvas.add(ylabel80);
            canvas.add(ylabel90);
            canvas.add(ylabel100);
            
            CanvasSOLabel = new JLabel("Average scores of students enrolled in class per SO");
            canvas.add(CanvasSOLabel, xStart + 20, yStart - 30);

            GLabel[] SOLabelArray = new GLabel[idlist.size()];
            GLabel[] gradeLabelArray = new GLabel[idlist.size()];
            GRect[] barArray = new GRect[idlist.size()];
            DecimalFormat dc = new DecimalFormat("0.00");

            for (int i = 0; i < currentSubjNumberOfSos; i++) {
                SOLabelArray[i] = new GLabel("SO-" + SOLettersArrayListInput.get(i), xStart + xGilidSpace + (xItemSpace * i) + 15, yEnd + 15);
                canvas.add(SOLabelArray[i]);
            }

            for (int i = 0; i < SOLettersArrayListInput.size(); i++) {
                    
                gradeLabelArray[i] = new GLabel("" + dc.format(AverageArray[i]), xStart + xGilidSpace + (xItemSpace * i) + 15, yEnd - 2 - (AverageArray[i] * 3));
                canvas.add(gradeLabelArray[i]);
                barArray[i] = new GRect(xStart + xGilidSpace + (xItemSpace * i), yEnd - (AverageArray[i] * 3), xItemSpace, AverageArray[i] * 3);
                canvas.add(barArray[i]);
                
            }
        } catch (SQLException ex) {
            System.out.println("SO grades not obtained");
        }
    }

    public static void main(String[] args) {
        new SOGraph("DYNAMIC", "EI").start(args); // inputs are subject string and section string
    }

}
