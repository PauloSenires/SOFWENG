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
import static acm.program.Program.SOUTH;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Harley Orines
 */
public class SOGraph extends Program implements ActionListener {

    private final int applicationWidth = 800;
    private final int applicationHeight = 800;
    private final int width = 700;
    private final int height = 700;
    private int currentCanvas;

    private final JButton soa = new JButton("SO-A");
    private final JButton sob = new JButton("SO-B");
    private final JButton soc = new JButton("SO-C");
    private final JButton sod = new JButton("SO-D");
    private final JButton soe = new JButton("SO-E");
    private final JButton sof = new JButton("SO-F");
    private final JButton sog = new JButton("SO-G");
    private final JButton soh = new JButton("SO-H");
    private final JButton soi = new JButton("SO-I");
    private final JButton soj = new JButton("SO-J");
    private final JButton sok = new JButton("SO-K");
    private final JButton sol = new JButton("SO-L");

    private final GCanvas canvas = new GCanvas();

    @SuppressWarnings("FieldMayBeFinal")
    private JLabel SOCodeAndDescription;
    private JLabel CanvasSOLabel;
    private String[] SOCodeArray = {"SO-A", "SO-B", "SO-C", "SO-D", "SO-E", "SO-F", "SO-G", "SO-H", "SO-I", "SO-J", "SO-K", "SO-L"};

    private String SOADesc = "An ability to apply knowledge of mathematics, sciences, and engineering sciences to the practice of computer engineering. ";
    private String SOBDesc = "An ability to design and conduct experiments as well as analyse and interpret data. ";
    private String SOCDesc = "An ability to design a system to meet desired needs. ";
    private String SODDesc = "An ability to work effectively in multi-disciplinary and multi-cultural teams. ";
    private String SOEDesc = "An ability to recognize, formulate, and solve computer engineering problems. ";
    private String SOFDesc = "An understanding of professional and ethical responsibility. ";
    private String SOGDesc = "An ability to communicate effectively in verbal and non-verbal communication. ";
    private String SOHDesc = "A broad education necessary to understand impact of engineering solutions in a global/societal context. ";
    private String SOIDesc = "An ability to engage in life-long learning and to keep current of the development in a specific field of specialization. ";
    private String SOJDesc = "A knowledge of contemporary issues. ";
    private String SOKDesc = "An ability to use appropriate techniques, skills, and modern tools necessary for computer engineering practice to be locally and globally competitive. ";
    private String SOLDesc = "An ability to apply acquired computer engineering knowledge and skills for national development. ";

    Panel panelOne = new Panel();
    Panel panelTwo = new Panel();

    ArrayList<String> soLetters = new ArrayList<>();

    private Statement statement;

    private boolean hasSOA = false;
    private boolean hasSOB = false;
    private boolean hasSOC = false;
    private boolean hasSOD = false;
    private boolean hasSOE = false;
    private boolean hasSOF = false;
    private boolean hasSOG = false;
    private boolean hasSOH = false;
    private boolean hasSOI = false;
    private boolean hasSOJ = false;
    private boolean hasSOK = false;
    private boolean hasSOL = false;

    private static String TEST_SUBJ_NAME;
    private static String TEST_SECTION;
    private static int currentSubjNumberOfSos;

    public SOGraph(String subjName, String Section) {

        TEST_SUBJ_NAME = subjName;
        TEST_SECTION = Section;
        this.setSize(applicationWidth, applicationHeight);
        this.setTitle(subjName + " " + Section + " " + "Student Outcome summary");
        canvas.setSize(width, height);
        add(panelOne, NORTH);
        add(panelTwo, SOUTH);
        add(canvas);

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
            while (soLetterRS.next()) {
                String[] var = soLetterRS.getString(2).split(",");
                currentSubjNumberOfSos = var.length;
                for (int i = 0; i < currentSubjNumberOfSos; i++) {
                    if ("A".equals(var[i])) {
                        hasSOA = true;
                    }
                    if ("B".equals(var[i])) {
                        hasSOB = true;
                    }
                    if ("C".equals(var[i])) {
                        hasSOC = true;
                    }
                    if ("D".equals(var[i])) {
                        hasSOD = true;
                    }
                    if ("E".equals(var[i])) {
                        hasSOE = true;
                    }
                    if ("F".equals(var[i])) {
                        hasSOF = true;
                    }
                    if ("G".equals(var[i])) {
                        hasSOG = true;
                    }
                    if ("H".equals(var[i])) {
                        hasSOH = true;
                    }
                    if ("I".equals(var[i])) {
                        hasSOI = true;
                        currentCanvas = 8;
                    }
                    if ("J".equals(var[i])) {
                        hasSOJ = true;
                    }
                    if ("K".equals(var[i])) {
                        hasSOK = true;
                    }
                    if ("L".equals(var[i])) {
                        hasSOL = true;
                    }
                }
                SOCodeAndDescription = new JLabel(" ");
                panelTwo.add(SOCodeAndDescription);
                soLetters.addAll(Arrays.asList(var));
            }
        } catch (SQLException ex) {
            System.out.println("Class SOs not obtained");
        }

        if (hasSOA) {
            panelOne.add(soa);
            soa.addActionListener(this);
        }

        if (hasSOB) {
            panelOne.add(sob);
            sob.addActionListener(this);
        }

        if (hasSOC) {
            panelOne.add(soc);
            soc.addActionListener(this);
        }

        if (hasSOD) {
            panelOne.add(sod);
            sod.addActionListener(this);
        }

        if (hasSOE) {
            panelOne.add(soe);
            soe.addActionListener(this);
        }

        if (hasSOF) {
            panelOne.add(sof);
            sof.addActionListener(this);
        }

        if (hasSOG) {
            panelOne.add(sog);
            sog.addActionListener(this);
        }

        if (hasSOH) {
            panelOne.add(soh);
            soh.addActionListener(this);
        }

        if (hasSOI) {
            panelOne.add(soi);
            soi.addActionListener(this);
        }

        if (hasSOJ) {
            panelOne.add(soj);
            soj.addActionListener(this);
        }

        if (hasSOK) {
            panelOne.add(sok);
            sok.addActionListener(this);
        }

        if (hasSOL) {
            panelOne.add(sol);
            sol.addActionListener(this);
        }

    }

    public void drawCanvas(String[] SOCodeArray, int currentCanvas, String subjName, String section) {

        int p = 0;
        String currentSelectedButton = null;
        CanvasSOLabel = new JLabel(SOCodeArray[currentCanvas]);
        canvas.add(CanvasSOLabel);
        ArrayList<Integer> idlist = new ArrayList<>();
        String[] tempString = new String[currentSubjNumberOfSos];

        if (currentCanvas == 0) {
            currentSelectedButton = "A";
        }
        if (currentCanvas == 1) {
            currentSelectedButton = "B";
        }
        if (currentCanvas == 2) {
            currentSelectedButton = "C";
        }
        if (currentCanvas == 3) {
            currentSelectedButton = "D";
        }
        if (currentCanvas == 4) {
            currentSelectedButton = "E";
        }
        if (currentCanvas == 5) {
            currentSelectedButton = "F";
        }
        if (currentCanvas == 6) {
            currentSelectedButton = "G";
        }
        if (currentCanvas == 7) {
            currentSelectedButton = "H";
        }
        if (currentCanvas == 8) {
            currentSelectedButton = "I";
        }
        if (currentCanvas == 9) {
            currentSelectedButton = "J";
        }
        if (currentCanvas == 10) {
            currentSelectedButton = "K";
        }
        if (currentCanvas == 11) {
            currentSelectedButton = "L";
        }

        try {
            ResultSet IdSoSet1 = statement.executeQuery("SELECT * FROM students WHERE ((subject1 = '" + subjName + "') AND (section1 = '" + section + "')) OR ((subject2 = '" + subjName + "') AND (section2 = '" + section + "')) OR ((subject3 = '" + subjName + "') AND (section3 = '" + section + "')) OR ((subject4 = '" + subjName + "') AND (section4 = '" + section + "')) OR ((subject5 = '" + subjName + "') AND (section5 = '" + section + "')) OR ((subject6 = '" + subjName + "') AND (section6 = '" + section + "')) OR ((subject7 = '" + subjName + "') AND (section7 = '" + section + "')) OR ((subject8 = '" + subjName + "') AND (section8 = '" + section + "')) OR ((subject9 = '" + subjName + "') AND (section9 = '" + section + "')) OR ((subject10 = '" + subjName + "') AND (section10 = '" + section + "'))");
            while (IdSoSet1.next()) {
                idlist.add(IdSoSet1.getInt(IdSoSet1.findColumn("ID")));
            }
            String[][] StringArray2d = new String[idlist.size()][currentSubjNumberOfSos];
            Double[][] DoubleArray2d = new Double[idlist.size()][currentSubjNumberOfSos];
            ResultSet IdSoSet2 = statement.executeQuery("SELECT * FROM students WHERE ((subject1 = '" + subjName + "') AND (section1 = '" + section + "')) OR ((subject2 = '" + subjName + "') AND (section2 = '" + section + "')) OR ((subject3 = '" + subjName + "') AND (section3 = '" + section + "')) OR ((subject4 = '" + subjName + "') AND (section4 = '" + section + "')) OR ((subject5 = '" + subjName + "') AND (section5 = '" + section + "')) OR ((subject6 = '" + subjName + "') AND (section6 = '" + section + "')) OR ((subject7 = '" + subjName + "') AND (section7 = '" + section + "')) OR ((subject8 = '" + subjName + "') AND (section8 = '" + section + "')) OR ((subject9 = '" + subjName + "') AND (section9 = '" + section + "')) OR ((subject10 = '" + subjName + "') AND (section10 = '" + section + "'))");
            while (IdSoSet2.next()) {
                if (IdSoSet2.getString(IdSoSet2.findColumn("subject1")).equals(subjName)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so1")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so1")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject2")).equals(subjName)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so2")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so2")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject3")).equals(subjName)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so3")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so3")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject4")).equals(subjName)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so4")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so4")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject5")).equals(subjName)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so5")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so5")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject6")).equals(subjName)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so6")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so6")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject7")).equals(subjName)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so7")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so7")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject8")).equals(subjName)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so8")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so8")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject9")).equals(subjName)) {
                    if (!IdSoSet2.getString(IdSoSet2.findColumn("so9")).equals("na")) {
                        tempString = IdSoSet2.getString(IdSoSet2.findColumn("so9")).split(",");
                    }
                }

                if (IdSoSet2.getString(IdSoSet2.findColumn("subject10")).equals(subjName)) {
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

            double xItemSpace = 60;
            double xGilidSpace = 30;
            double xStart = 100;
            double xEnd = xStart + ((xGilidSpace * 2) + (xItemSpace * idlist.size()));
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

            GLabel[] idLabelArray = new GLabel[idlist.size()];
            GLabel[] gradeLabelArray = new GLabel[idlist.size()];
            GRect[] barArray = new GRect[idlist.size()];

            for (int i = 0; i < idlist.size(); i++) {
                idLabelArray[i] = new GLabel(Integer.toString(idlist.get(i)), xStart + xGilidSpace + (xItemSpace * i), yEnd + 11);
                canvas.add(idLabelArray[i]);
            }

            for (int i = 0; i < soLetters.size(); i++) {
                if (currentSelectedButton.equals(soLetters.get(i))) {
                    //Create graph i;
                    for (int j = 0; j < idlist.size(); j++) {
                        gradeLabelArray[j] = new GLabel(Double.toString(DoubleArray2d[j][i]), xStart + xGilidSpace + (xItemSpace * j) + 20, yEnd - 2 - (DoubleArray2d[j][i] * 3));
                        canvas.add(gradeLabelArray[j]);
                        barArray[j] = new GRect(xStart + xGilidSpace + (xItemSpace * j), yEnd - (DoubleArray2d[j][i] * 3), xItemSpace, DoubleArray2d[j][i] * 3);
                        canvas.add(barArray[j]);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SO grades not obtained");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == soa) {
            canvas.removeAll();
            currentCanvas = 0;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOADesc);
        } else if (e.getSource() == sob) {
            canvas.removeAll();
            currentCanvas = 1;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOBDesc);
        } else if (e.getSource() == soc) {
            canvas.removeAll();
            currentCanvas = 2;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOCDesc);
        } else if (e.getSource() == sod) {
            canvas.removeAll();
            currentCanvas = 3;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SODDesc);
        } else if (e.getSource() == soe) {
            canvas.removeAll();
            currentCanvas = 4;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOEDesc);
        } else if (e.getSource() == sof) {
            canvas.removeAll();
            currentCanvas = 5;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOFDesc);
        } else if (e.getSource() == sog) {
            canvas.removeAll();
            currentCanvas = 6;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOGDesc);
        } else if (e.getSource() == soh) {
            canvas.removeAll();
            currentCanvas = 7;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOHDesc);
        } else if (e.getSource() == soi) {
            canvas.removeAll();
            currentCanvas = 8;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOIDesc);
        } else if (e.getSource() == soj) {
            canvas.removeAll();
            currentCanvas = 9;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOJDesc);
        } else if (e.getSource() == sok) {
            canvas.removeAll();
            currentCanvas = 10;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOKDesc);
        } else if (e.getSource() == sol) {
            canvas.removeAll();
            currentCanvas = 11;
            drawCanvas(SOCodeArray, currentCanvas, TEST_SUBJ_NAME, TEST_SECTION);
            SOCodeAndDescription.setText(SOCodeArray[currentCanvas] + " : " + SOLDesc);
        }

    }

    public static void main(String[] args) {
        new SOGraph("DYNAMIC", "EI").start(args); // inputs are subject string and section string
    }

}
