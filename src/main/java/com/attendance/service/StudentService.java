package com.attendance.service;

import com.attendance.dao.StudentDAO;
import com.attendance.model.Student;
import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;
    
    public StudentService() {
        studentDAO = new StudentDAO();
    }
    
    public void addStudent(Student student) throws SQLException {
        studentDAO.addStudent(student);
    }
    
    public void updateStudent(Student student) throws SQLException {
        studentDAO.updateStudent(student);
    }
    
    public void deleteStudent(int studentId) throws SQLException {
        studentDAO.deleteStudent(studentId);
    }
    
    public Student getStudentById(int studentId) throws SQLException {
        return studentDAO.getStudentById(studentId);
    }
    
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }
    
    public List<Student> getStudentsByClass(int classId) throws SQLException {
        return studentDAO.getStudentsByClass(classId);
    }
}