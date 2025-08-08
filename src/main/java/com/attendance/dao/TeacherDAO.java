package com.attendance.dao;

import com.attendance.model.Teacher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private Connection connection;
    
    public TeacherDAO() {
        connection = DatabaseConnection.getConnection();
    }
    
    public void addTeacher(Teacher teacher) throws SQLException {
        String query = "INSERT INTO teachers (username, password, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, teacher.getUsername());
            ps.setString(2, teacher.getPassword());
            ps.setString(3, teacher.getFirstName());
            ps.setString(4, teacher.getLastName());
            ps.setString(5, teacher.getEmail());
            ps.setString(6, teacher.getPhone());
            ps.executeUpdate();
            
            // Get the generated teacher ID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    teacher.setTeacherId(rs.getInt(1));
                }
            }
        }
    }
    
    public void updateTeacher(Teacher teacher) throws SQLException {
        String query = "UPDATE teachers SET username = ?, password = ?, first_name = ?, last_name = ?, email = ?, phone = ? WHERE teacher_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, teacher.getUsername());
            ps.setString(2, teacher.getPassword());
            ps.setString(3, teacher.getFirstName());
            ps.setString(4, teacher.getLastName());
            ps.setString(5, teacher.getEmail());
            ps.setString(6, teacher.getPhone());
            ps.setInt(7, teacher.getTeacherId());
            ps.executeUpdate();
        }
    }
    
    public void deleteTeacher(int teacherId) throws SQLException {
        String query = "DELETE FROM teachers WHERE teacher_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            ps.executeUpdate();
        }
    }
    
    public Teacher getTeacherById(int teacherId) throws SQLException {
        String query = "SELECT * FROM teachers WHERE teacher_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Teacher teacher = new Teacher();
                    teacher.setTeacherId(rs.getInt("teacher_id"));
                    teacher.setUsername(rs.getString("username"));
                    teacher.setPassword(rs.getString("password"));
                    teacher.setFirstName(rs.getString("first_name"));
                    teacher.setLastName(rs.getString("last_name"));
                    teacher.setEmail(rs.getString("email"));
                    teacher.setPhone(rs.getString("phone"));
                    return teacher;
                }
            }
        }
        return null;
    }
    
    public Teacher authenticateTeacher(String username, String password) throws SQLException {
        String query = "SELECT * FROM teachers WHERE username = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Teacher teacher = new Teacher();
                    teacher.setTeacherId(rs.getInt("teacher_id"));
                    teacher.setUsername(rs.getString("username"));
                    teacher.setPassword(rs.getString("password"));
                    teacher.setFirstName(rs.getString("first_name"));
                    teacher.setLastName(rs.getString("last_name"));
                    teacher.setEmail(rs.getString("email"));
                    teacher.setPhone(rs.getString("phone"));
                    return teacher;
                }
            }
        }
        return null;
    }
    
    public Teacher getTeacherByUsername(String username) throws SQLException {
        String query = "SELECT * FROM teachers WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Teacher teacher = new Teacher();
                    teacher.setTeacherId(rs.getInt("teacher_id"));
                    teacher.setUsername(rs.getString("username"));
                    teacher.setPassword(rs.getString("password"));
                    teacher.setFirstName(rs.getString("first_name"));
                    teacher.setLastName(rs.getString("last_name"));
                    teacher.setEmail(rs.getString("email"));
                    teacher.setPhone(rs.getString("phone"));
                    return teacher;
                }
            }
        }
        return null;
    }
    
    public List<Teacher> getAllTeachers() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM teachers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setUsername(rs.getString("username"));
                teacher.setPassword(rs.getString("password"));
                teacher.setFirstName(rs.getString("first_name"));
                teacher.setLastName(rs.getString("last_name"));
                teacher.setEmail(rs.getString("email"));
                teacher.setPhone(rs.getString("phone"));
                teachers.add(teacher);
            }
        }
        return teachers;
    }
    
    public void assignTeacherToClass(int teacherId, int classId) throws SQLException {
        String query = "INSERT INTO teacher_classes (teacher_id, class_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            ps.setInt(2, classId);
            ps.executeUpdate();
        }
    }
    
    public void removeTeacherFromClass(int teacherId, int classId) throws SQLException {
        String query = "DELETE FROM teacher_classes WHERE teacher_id = ? AND class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            ps.setInt(2, classId);
            ps.executeUpdate();
        }
    }
    
    public List<Integer> getTeacherClassIds(int teacherId) throws SQLException {
        List<Integer> classIds = new ArrayList<>();
        String query = "SELECT class_id FROM teacher_classes WHERE teacher_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    classIds.add(rs.getInt("class_id"));
                }
            }
        }
        return classIds;
    }
}