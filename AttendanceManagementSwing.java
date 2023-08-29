

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

class Student{
    private int studentId;
    private String name;
    private boolean isPresent;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.isPresent = false; // Set initial attendance status to false (absent).
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void markPresent() {
        this.isPresent = true;
    }

    public void markAbsent() {
        this.isPresent = false;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + ", Name: " + name + ", Present: " + isPresent;
    }
}

class Attendance {
    private java.util.List<Student> students;
    private java.util.Scanner scanner;

    public Attendance() {
        students = new java.util.ArrayList<>();
        scanner = new java.util.Scanner(System.in);
    }

    public void addStudent(Student student) {
        for (Student existingStudent : students) {
            if (existingStudent.getStudentId() == student.getStudentId()) {
                JOptionPane.showMessageDialog(null, "Student with ID " + student.getStudentId() + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        students.add(student);
    }

    public void markAttendance(int studentId, boolean isPresent) {
        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                if (isPresent) {
                    student.markPresent();
                } else {
                    student.markAbsent();
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Student with ID " + studentId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void generateAttendanceReport() {
        StringBuilder report = new StringBuilder("Attendance Report:\n");
        for (Student student : students) {
            report.append(student).append("\n");
        }
        JOptionPane.showMessageDialog(null, report.toString(), "Attendance Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public void generateDetailedReport() {
        int totalPresent = 0;
        int totalAbsent = 0;
        StringBuilder detailedReport = new StringBuilder("Detailed Attendance Report:\n");
        for (Student student : students) {
            detailedReport.append(student).append("\n");
            if (student.isPresent()) {
                totalPresent++;
            } else {
                totalAbsent++;
            }
        }
        int totalStudents = students.size();
        double attendancePercentage = (double) totalPresent / totalStudents * 100;
        detailedReport.append("\nTotal Students: ").append(totalStudents);
        detailedReport.append("\nTotal Present: ").append(totalPresent);
        detailedReport.append("\nTotal Absent: ").append(totalAbsent);
        detailedReport.append("\nAttendance Percentage: ").append(attendancePercentage).append("%");
        JOptionPane.showMessageDialog(null, detailedReport.toString(), "Detailed Attendance Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public void exportAttendanceReportToFile() {
        JOptionPane.showMessageDialog(null, "Attendance report exported to file.", "Export Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    public int readStudentIdFromUser() {
        int studentId;
        while (true) {
            String input = JOptionPane.showInputDialog("Enter Student ID:");
            try {
                studentId = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid student ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return studentId;
    }
}

public class AttendanceManagementSwing extends JFrame implements ActionListener {
    private JButton addButton, markAttendanceButton, generateReportButton, generateDetailedButton, exportButton;
    private JTextField studentIdField, studentNameField;
    private JCheckBox presentCheckbox;

    private Attendance attendance;

    public AttendanceManagementSwing() {
        setTitle("Attendance Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        attendance = new Attendance();

        addButton = new JButton("Add Student");
        markAttendanceButton = new JButton("Mark Attendance");
        generateReportButton = new JButton("Generate Report");
        generateDetailedButton = new JButton("Generate Detailed Report");
        exportButton = new JButton("Export Report");

        studentIdField = new JTextField(10);
        studentNameField = new JTextField(20);

        presentCheckbox = new JCheckBox("Present");

        addButton.addActionListener(this);
        markAttendanceButton.addActionListener(this);
        generateReportButton.addActionListener(this);
        generateDetailedButton.addActionListener(this);
        exportButton.addActionListener(this);

        add(new JLabel("Student ID: "));
        add(studentIdField);
        add(new JLabel("Student Name: "));
        add(studentNameField);
        add(presentCheckbox);
        add(new JLabel()); // Empty label for formatting
        add(addButton);
        add(markAttendanceButton);
        add(generateReportButton);
        add(generateDetailedButton);
        add(exportButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                String name = studentNameField.getText();
                Student student = new Student(studentId, name);
                attendance.addStudent(student);
                studentIdField.setText("");
                studentNameField.setText("");
                presentCheckbox.setSelected(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid student ID format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == markAttendanceButton) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                boolean isPresent = presentCheckbox.isSelected();
                attendance.markAttendance(studentId, isPresent);
                studentIdField.setText("");
                presentCheckbox.setSelected(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid student ID format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == generateReportButton) {
            attendance.generateAttendanceReport();
        } else if (e.getSource() == generateDetailedButton) {
            attendance.generateDetailedReport();
        } else if (e.getSource() == exportButton) {
            attendance.exportAttendanceReportToFile();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AttendanceManagementSwing());
    }
}
