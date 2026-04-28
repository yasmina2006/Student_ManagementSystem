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
    private JButton btnDelete, btnUpdate, btnSearch;

    public StudentGUI() {
        manager = new StudentManager();
        manager.loadFromFile(); // Load existing data on startup

        // 1. Window Setup
        setTitle("Smart Student Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Input Panel (The top part of the window)
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
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

        btnSearch = new JButton("Search by ID");
        inputPanel.add(btnSearch);

        btnUpdate = new JButton("Update Marks");
        inputPanel.add(btnUpdate);

        btnDelete = new JButton("Delete Student");
        inputPanel.add(btnDelete);


        // 3. Display Area (The bottom part)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);


        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Adding student
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
        btnSearch.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Student s = manager.searchById(id);
                if (s != null) {
                    displayArea.setText("Found: " + s.toString());
                    txtName.setText(s.getName());
                    txtMarks.setText(String.valueOf(s.getMarks()));
                }  else {
                    JOptionPane.showMessageDialog(null, "Student not found!");
                } } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a valid ID.");
                }
        });
        // for updating
        btnUpdate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                double marks = Double.parseDouble(txtMarks.getText());
                manager.updateStudentMarks(id, marks);
                manager.saveToFile();
                JOptionPane.showMessageDialog(null, "Updated!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid ID/Marks.");
            }
        });
        // Listener for deleting
        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (manager.deleteStudent(id)) {
                    manager.saveToFile();
                    JOptionPane.showMessageDialog(null, "Deleted!");
                } else {
                    JOptionPane.showMessageDialog(null, "Not Found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Enter valid ID.");
            }
        });
        // View Listener
        btnView.addActionListener(e -> {
            displayArea.setText("");
            for (Student s : manager.getAllStudents()) {
                displayArea.append(s.toString() + "\n");
            }
        });

        setVisible(true);
    }
}
