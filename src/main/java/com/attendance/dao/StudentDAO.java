package com.attendance.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.attendance.model.Student;

public class StudentDAO {
    private Connection connection;
    
    public StudentDAO() {
        connection = DatabaseConnection.getConnection();
    }
    
    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (roll_number, first_name, last_name, email, phone, class_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, student.getRollNumber());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getPhone());
            ps.setInt(6, student.getClassId());
            ps.executeUpdate();
        }
    }
    
    public void updateStudent(Student student) throws SQLException {
        String query = "UPDATE students SET roll_number = ?, first_name = ?, last_name = ?, email = ?, phone = ?, class_id = ? WHERE student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, student.getRollNumber());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getPhone());
            ps.setInt(6, student.getClassId());
            ps.setInt(7, student.getStudentId());
            ps.executeUpdate();
        }
    }
    
    public void deleteStudent(int studentId) throws SQLException {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ps.executeUpdate();
        }
    }
    
    public Student getStudentById(int studentId) throws SQLException {
        String query = "SELECT s.*, c.class_name FROM students s LEFT JOIN classes c ON s.class_id = c.class_id WHERE s.student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setRollNumber(rs.getString("roll_number"));
                    student.setFirstName(rs.getString("first_name"));
                    student.setLastName(rs.getString("last_name"));
                    student.setEmail(rs.getString("email"));
                    student.setPhone(rs.getString("phone"));
                    student.setClassId(rs.getInt("class_id"));
                    student.setClassName(rs.getString("class_name"));
                    return student;
                }
            }
        }
        return null;
    }
    
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, c.class_name FROM students s LEFT JOIN classes c ON s.class_id = c.class_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setRollNumber(rs.getString("roll_number"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setClassId(rs.getInt("class_id"));
                student.setClassName(rs.getString("class_name"));
                students.add(student);
            }
        }
        return students;
    }
    
    public List<Student> getStudentsByClass(int classId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, c.class_name FROM students s LEFT JOIN classes c ON s.class_id = c.class_id WHERE s.class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setRollNumber(rs.getString("roll_number"));
                    student.setFirstName(rs.getString("first_name"));
                    student.setLastName(rs.getString("last_name"));
                    student.setEmail(rs.getString("email"));
                    student.setPhone(rs.getString("phone"));
                    student.setClassId(rs.getInt("class_id"));
                    student.setClassName(rs.getString("class_name"));
                    students.add(student);
                }
            }
        }
        return students;
    }
}