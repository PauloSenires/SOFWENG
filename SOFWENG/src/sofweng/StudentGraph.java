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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private String currentName = "DISPLAYING: ";
    private final String spaces = "         ";
    private final String[] stringSO = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

    private final JLabel label = new JLabel("NAME: ");
    private JLabel[] jLabelArray = new JLabel[10];
    private final JTextField enterName = new JTextField(20);
    private final JButton search = new JButton("SEARCH");
    private final JButton next = new JButton("NEXT");
    private final JButton previous = new JButton("PREVIOUS");

    private final GCanvas canvas = new GCanvas();
    private GOval[] oval = new GOval[10];
    private GLine[] lineArray = new GLine[numberSO];
    private GLine[] aesLine = new GLine[numberSO];
    
    private Statement stat;

    //initialization process, to create the display when the name is clicked to generate graph
    public StudentGraph() {
        String IDNumber = "11515880"; //actually needs to be an input thrown to this function
        //remove the main here if interfaced correctly with the rest of the program
        // then modify the constructor function: public StudentGraph(String IDNumber)

        //insert code for database connectivity here
        currentName = currentName + IDNumber + spaces;

        this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.setTitle(IDNumber);
        canvas.setSize(WIDTH, HEIGHT);
        add(canvas); //the canvas will be the graph itself, data thrown from the database storage
        JLabel currentDisplay = new JLabel(currentName);
        add(currentDisplay, NORTH);
        add(label, NORTH);
        add(enterName, NORTH);
        add(search, NORTH);
        search.addActionListener(this);
        add(previous, NORTH);
        previous.addActionListener(this);
        add(next, NORTH);
        next.addActionListener(this);
        
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
            System.out.println("use cpe_database");
            
        } catch (SQLException e) {
            System.out.println("ERROR");
        }
        
        drawCanvas(IDNumber);

    }

    public void drawCanvas(String name){
        for (int i = 1; i <= 10; i++) {
            oval[i - 1] = new GOval(ovalDim * i, ovalDim * i);
            oval[i - 1].setColor(java.awt.Color.LIGHT_GRAY);
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
        double[] SOGrade = new double[numberSO];

        double[] SOGrades = new double[numberSO];
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
                            Math.sin(angle * i) * 200 + centerY + offset/2);
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
        for (int i = 1; i < numberSO; i++) {
            aesLine[i] = new GLine(lineArray[i - 1].getEndPoint().getX(),
                    lineArray[i - 1].getEndPoint().getY(),
                    lineArray[i].getEndPoint().getX(),
                    lineArray[i].getEndPoint().getY());
            aesLine[i].setColor(java.awt.Color.BLUE);
            canvas.add(aesLine[i]);
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            canvas.removeAll();
            try {
                stat.execute("SELECT");
            } catch (SQLException ex) {
                System.out.println("ERROR");
            }
        } else if (e.getSource() == previous) {
            canvas.removeAll();
            try {
                stat.execute("SELECT");
            } catch (SQLException ex) {
                System.out.println("ERROR");
            }
        } else if (e.getSource() == search) {
            
        }
    }

    public double[] getGrades(String name) {
        double[] grades = new double[numberSO];
        double[][] initialGrade = new double[numberSO][numberSO];
        
        try {
            String url = "jdbc:mysql://localhost:3306/cpe_database";
            Properties prop = new Properties();
            prop.setProperty("user", "root");
            prop.setProperty("password", "");
            Driver d = new com.mysql.jdbc.Driver();
            Connection con = d.connect(url, prop);
            if (con == null) {
                System.out.println("connection failed");
                return null;
            } else {
                System.out.println("Connected");
            }
            Statement stat = con.createStatement();
            stat.execute("USE cpe_database;");
            System.out.println("use cpe_database");
            stat.execute("SELECT ");
            System.out.println("execute line 1");
            
        } catch (Exception e) {
            System.out.println("ERROR");
        }

        return grades;
    }

    public static void main(String[] args) {
        new StudentGraph().start(args);
    }
}
