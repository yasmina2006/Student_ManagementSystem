package view;

import model.Student;
import service.StudentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI extends JFrame {
    private StudentManager manager;

    // Components (The "Bricks")
    private JTextField txtId, txtName, txtMarks;
    private JTextArea displayArea;
    private JButton btnAdd, btnView;

    public StudentGUI() {
        manager = new StudentManager();
        manager.loadFromFile(); // Load existing data on startup

        // 1. Window Setup
        setTitle("Smart Student Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Input Panel (The top part of the window)
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Student ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Marks:"));
        txtMarks = new JTextField();
        inputPanel.add(txtMarks);

        btnAdd = new JButton("Add Student");
        inputPanel.add(btnAdd);

        btnView = new JButton("View All");
        inputPanel.add(btnView);

        // 3. Display Area (The bottom part)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add panels to the main window
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // 4. Button Logic (The "Brain" for the clicks)
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String name = txtName.getText();
                    double marks = Double.parseDouble(txtMarks.getText());

                    manager.addStudent(new Student(id, name, marks));
                    manager.saveToFile();
                    JOptionPane.showMessageDialog(null, "Student Added!");

                    // Clear the fields
                    txtId.setText("");
                    txtName.setText("");
                    txtMarks.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers for ID and Marks.");
                }
            }
        });

        btnView.addActionListener(e -> {
            displayArea.setText("");
            for (Student s : manager.getAllStudents()) {
                displayArea.append(s.toString() + "\n");
            }
        });

        setVisible(true);
    }
}
