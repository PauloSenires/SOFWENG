/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sofweng;

import acm.program.*;
import javax.swing.*;
import acm.graphics.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author NeilOliver
 */
public class StudentGraph extends Program {

    private final int APPLICATION_WIDTH = 650;
    private final int APPLICATION_HEIGHT = 650;
    private final int WIDTH = 650;
    private final int HEIGHT = 650;
    private final int ovalDim = 40;
    private final int addWidth = 20;
    private final int numberSO = 12;

    private String display = "DISPLAYING: ";
    private String name;
    private final String spaces = "         ";
    private final String[] stringSO = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

    private final JLabel label = new JLabel("ID NO.: ");
    private JLabel[] jLabelArray = new JLabel[10];
    private JLabel currentDisplay;
    private final JTextField enterName = new JTextField(20);
    private final JButton search = new JButton("SEARCH");
    private final JButton next = new JButton("NEXT");
    private final JButton previous = new JButton("PREVIOUS");

    private final GCanvas canvas = new GCanvas();
    private GOval[] oval = new GOval[10];
    private GLine[] lineArray = new GLine[numberSO];
    private GLine[] aesLine = new GLine[numberSO + 1];

    private Statement stat;

    //initialization process, to create the display when the name is clicked to generate graph
    public StudentGraph(String id) {
        try {
            String url = "jdbc:mysql://localhost:3306/cpe_database";
            Properties prop = new Properties();
            prop.setProperty("user", "root");
            prop.setProperty("password", "");
            Driver d = new com.mysql.jdbc.Driver();
            Connection con = d.connect(url, prop);
            if (con == null) {
                System.out.println("connection failed");
                return;
            } else {
                System.out.println("Connected");
            }
            stat = con.createStatement();
            stat.execute("USE cpe_database;");
            if (id.equals("")) {
                ResultSet resultSet = stat.executeQuery("SELECT id FROM students;");
                resultSet.first();
                name = resultSet.getString(1);
            } else {
                name = id;
            }
        } catch (SQLException e) {
            System.out.println("ERROR");
        }
        //actually needs to be an input thrown to this function
        //remove the main here if interfaced correctly with the rest of the program
        // then modify the constructor function: public StudentGraph(String IDNumber)

        this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.setTitle(name);
        canvas.setSize(WIDTH, HEIGHT);
        add(canvas); //the canvas will be the graph itself, data thrown from the database storage
        currentDisplay = new JLabel(display + name + spaces);
        add(currentDisplay, NORTH);
        add(label, NORTH);
        add(enterName, NORTH);
        add(search, NORTH);
        search.addActionListener(this);
        add(previous, NORTH);
        previous.addActionListener(this);
        add(next, NORTH);
        next.addActionListener(this);

        drawCanvas(name);
    }

    public void drawCanvas(String name) {
        for (int i = 1; i <= 10; i++) {
            oval[i - 1] = new GOval(ovalDim * i, ovalDim * i);
            oval[i - 1].setColor(java.awt.Color.LIGHT_GRAY);
            if (i - 1 == 6) {
                oval[i - 1].setColor(java.awt.Color.RED);
            }
            jLabelArray[i - 1] = new JLabel("" + (i * 10));
        }

        canvas.add(oval[9], canvas.getWidth() / 8, addWidth);
        for (int i = 8; i >= 0; i--) {
            canvas.add(oval[i], oval[i + 1].getX() + addWidth, oval[i + 1].getY() + addWidth);
        }

        for (int i = 1; i <= 10; i++) {
            canvas.add(jLabelArray[i - 1], oval[i - 1].getX() + (20 * i) - 5, oval[i - 1].getY());
        }

        /*equation for the coordinates of the outer circle
        (x-275)^2 + (y-220)^2 = 400 where (h,k) = (275, 220) or (canvas.getX()+(ovalDim*5), addWidth+(ovalDim*5))
        if numberSO = 4, 360/4 = 90. Then the value that the lines will be drawn are at 0, 90, 180 and 270
        the properties will be used are trigonometric equations to obtain the value of the coordinate*/
        double centerX = oval[9].getX() + (ovalDim * 5);
        double centerY = addWidth + (ovalDim * 5);
        double angle = (360 * Math.PI) / (numberSO * 180);
        double[] SOGrades;
        //retrieve SOGrades
        SOGrades = getGrades(name);

        double offset = 20;

        for (int i = 0; i < numberSO; i++) {
            lineArray[i] = new GLine(centerX, centerY,
                    Math.cos(angle * i) * 200 * (SOGrades[i] / 100) + centerX,
                    Math.sin(angle * i) * 200 * (SOGrades[i] / 100) + centerY);
            lineArray[i].setColor(java.awt.Color.BLUE);
            canvas.add(lineArray[i]);
            if (angle * i < (Math.PI / 2) || angle * i > (3 * Math.PI / 2)) {
                canvas.add(new JLabel("SO-" + stringSO[i]),
                        Math.cos(angle * i) * 200 + centerX + offset,
                        Math.sin(angle * i) * 200 + centerY);
            } else if (angle * i > (Math.PI / 2) || angle * i < (3 * Math.PI / 2)) {
                if (angle * i == (Math.PI / 2)) {
                    canvas.add(new JLabel("SO-" + stringSO[i]),
                            Math.cos(angle * i) * 200 + centerX - (offset / 2),
                            Math.sin(angle * i) * 200 + centerY + offset / 2);
                } else if (angle * i == (3 * Math.PI / 2)) {
                    canvas.add(new JLabel("SO-" + stringSO[i]),
                            Math.cos(angle * i) * 200 + centerX - (offset / 2),
                            Math.sin(angle * i) * 200 + centerY - offset);
                } else {
                    canvas.add(new JLabel("SO-" + stringSO[i]),
                            Math.cos(angle * i) * 200 + centerX - 2 * offset,
                            Math.sin(angle * i) * 200 + centerY);
                }
            }
        }
        //creating the aesthetic lines
        for (GLine line : lineArray) {
            GOval oval = new GOval(line.getEndPoint().getX() - 2.5, line.getEndPoint().getY() - 2.5, 5, 5);
            oval.setColor(java.awt.Color.RED);
            oval.setFillColor(java.awt.Color.RED);
            oval.setFilled(true);
            canvas.add(oval);
        }
        for (int i = 1; i <= numberSO; i++) {
            if (i == numberSO) {
                aesLine[i] = new GLine(lineArray[11].getEndPoint().getX(),
                        lineArray[11].getEndPoint().getY(),
                        lineArray[0].getEndPoint().getX(),
                        lineArray[0].getEndPoint().getY());
                aesLine[i].setColor(java.awt.Color.BLUE);
                canvas.add(aesLine[i]);
                break;
            }
            aesLine[i] = new GLine(lineArray[i - 1].getEndPoint().getX(),
                    lineArray[i - 1].getEndPoint().getY(),
                    lineArray[i].getEndPoint().getX(),
                    lineArray[i].getEndPoint().getY());
            aesLine[i].setColor(java.awt.Color.BLUE);
            canvas.add(aesLine[i]);
        }
        for (int i = 0; i < lineArray.length; i++) {
            DecimalFormat dc = new DecimalFormat("0.00");
            GLabel gradeLabel = new GLabel("" + dc.format(SOGrades[i]));
            if (SOGrades[i] != 0 && SOGrades[i] != 100) {
                if (lineArray[i].getEndPoint().getX() < oval[0].getX() + ovalDim / 2) {
                    canvas.add(gradeLabel, lineArray[i].getEndPoint().getX() - offset, lineArray[i].getEndPoint().getY());
                } else {
                    canvas.add(gradeLabel, lineArray[i].getEndPoint().getX(), lineArray[i].getEndPoint().getY());
                }
            }
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            canvas.removeAll();
            try {
                ResultSet resultSet = stat.executeQuery("SELECT id FROM students;");
                while (resultSet.next()) {
                    if (resultSet.getString(1).equals(name)) {
                        if (resultSet.next()) {
                            name = resultSet.getString(1);
                            System.out.println(name);
                            this.setTitle(name);
                            currentDisplay.setText(display + name + spaces);
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "Reached the last ID number");
                            break;
                        }
                    }
                }
                drawCanvas(name);
            } catch (SQLException ex) {
                System.out.println("ERROR");
            }
        } else if (e.getSource() == previous) {
            canvas.removeAll();
            try {
                ResultSet resultSet = stat.executeQuery("SELECT DISTINCT id FROM students;");
                while (resultSet.next()) {
                    if (resultSet.getString(1).equals(name)) {
                        if (resultSet.previous()) {
                            name = resultSet.getString(1);
                            System.out.println(name);
                            this.setTitle(name);
                            currentDisplay.setText(display + name + spaces);
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "Reached the first ID number");
                            break;
                        }
                    }
                }
                drawCanvas(name);
            } catch (SQLException ex) {
                System.out.println("ERROR");
            }
        } else if (e.getSource() == search) {
            canvas.removeAll();
            try {
                ResultSet resultSet = stat.executeQuery("SELECT id FROM students;");
                while (resultSet.next()) {
                    if (resultSet.getString(1).equals(enterName.getText())) {
                        name = resultSet.getString(1);
                        this.setTitle(name);
                        currentDisplay.setText(display + name + spaces);
                        break;
                    }
                }
                if (resultSet.next() == false) {
                    JOptionPane.showMessageDialog(null, "ID number is not found");
                }
                drawCanvas(name);
            } catch (SQLException ex) {
                System.out.println("ERROR");
            }
        }
    }

    public double[] getGrades(String name) {
        double[] grades = new double[numberSO];
        String[][] stringResult = new String[numberSO][numberSO];
        HashMap<String, String[]> classMap = new HashMap<String, String[]>();
        HashMap<String, ArrayList<String>> initialMap = new HashMap<>();
        String[] className = new String[numberSO];

        try {
            ResultSet classSet = stat.executeQuery("SELECT Name, SO FROM classes;");
            System.out.println("SELECT * FROM students WHERE ID = '" + name + "';");
            System.out.println("SELECT Name, SO from classes");

            while (classSet.next()) {
                classMap.put(classSet.getString(1), classSet.getString(2).split(","));
                System.out.println(classSet.getString(1) + " " + classSet.getString(2));
            }

            for (int i = 0; i < numberSO; i++) {
                initialMap.put(stringSO[i], new ArrayList<>());
            }

            ResultSet resultSet = stat.executeQuery("SELECT * FROM students WHERE ID = '" + name + "'");
            while (resultSet.next()) {
                //places the grades into stringResult, classes into className
                for (int SOCount = 0; SOCount <= 9; SOCount++) {
                    if (!resultSet.getString(resultSet.findColumn("so" + (SOCount + 1))).equals("na")) {
                        stringResult[SOCount] = resultSet.getString(resultSet.findColumn("so" + (SOCount + 1))).split(",");
                        className[SOCount] = resultSet.getString(resultSet.findColumn("subject" + (SOCount + 1)));
                        System.out.println(className[SOCount]);
                    }
                }
                System.out.println(classMap.toString());

                for (int i = 0; i < className.length; i++) {
                    if (className[i] == null) {
                        break;
                    }
                    System.out.println(Arrays.toString(classMap.get(className[i])));
                    for (int j = 0; j < classMap.get(className[i]).length; j++) {
                        initialMap.get(classMap.get(className[i])[j]).add(stringResult[i][j]);
                    }
                }
                //averaging
                double sum;
                for (int i = 0; i < initialMap.size(); i++) {
                    sum = 0;
                    for (int j = 0; j < initialMap.get(stringSO[i]).size(); j++) {
                        sum = sum + Double.parseDouble(initialMap.get(stringSO[i]).get(j));
                    }
                    grades[i] = sum / initialMap.get(stringSO[i]).size();
                    if (sum == 0) {
                        grades[i] = 0;
                    }
                    System.out.println("SO-" + stringSO[i] + " Average: " + grades[i]
                            + " Sum: " + sum);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getting grades");
        }
        return grades;
    }

    public static void main(String[] args) {
        new StudentGraph("").start();
    }

}
