/*import model.Student;
import service.StudentManager;

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        manager.loadFromFile();
        Student s1 = new Student(20, "Yasmina", 95.0);
        Student s2 = new Student(21,"Dilbar",89);
        manager.addStudent(s1);
        manager.addStudent(s2);
        System.out.println("--- Current Student List ---");
        manager.displayAll();
        manager.saveToFile();
    }
} */
import view.StudentGUI;

public class Main {
    public static void main(String[] args) {
        new StudentGUI();
    }
}

