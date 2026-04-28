package service;
import model.Student;
import java.io.*;
import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> studentList;
    private final String FILE_NAME = "students.dot";

    public StudentManager() {
        this.studentList = new ArrayList<>();
    }
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
            System.out.println("Data saved permanently to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            studentList = (ArrayList<Student>) ois.readObject();
            System.out.println("Data loaded from last session.");
        }  catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading: " + e.getMessage());
        }

    }
    public void addStudent(Student student) {
        studentList.add(student);
        System.out.println("Student added successfully");
    }
    public void displayAll() {
        if (studentList.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : studentList) {
                System.out.println(s);
            }
        }
    }
    // searching for student
    public Student searchById(int id) {
        for (Student s : studentList) {
            if (s.getId() == id) {
                return s; // found
            }
        }
        return null;  // if not found
    }
    // deleting student
    public boolean deleteStudent(int id) {
        Student toRemove = searchById(id);
        if (toRemove != null) {
            studentList.remove(toRemove);
            return true; // deleted
        }
        return false; // not exist
    }
    public void updateStudentMarks(int id, double newMarks) {
        Student s = searchById(id);
        if (s != null) {
            s.setMarks(newMarks);
            System.out.println("Marks updated for Student ID: " + id);
        } else {
            System.out.println("Student not found. Update failed.");
        }
    }
    public ArrayList<Student> getAllStudents() {
        return studentList;
    }
}
