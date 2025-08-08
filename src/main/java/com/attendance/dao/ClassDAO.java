package com.attendance.dao;

import com.attendance.model.Class;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {
    private Connection connection;
    
    public ClassDAO() {
        connection = DatabaseConnection.getConnection();
    }
    
    public void addClass(Class classObj) throws SQLException {
        String query = "INSERT INTO classes (class_name, section, academic_year) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, classObj.getClassName());
            ps.setString(2, classObj.getSection());
            ps.setString(3, classObj.getAcademicYear());
            ps.executeUpdate();
            
            // Get the generated class ID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    classObj.setClassId(rs.getInt(1));
                }
            }
        }
    }
    
    public void updateClass(Class classObj) throws SQLException {
        String query = "UPDATE classes SET class_name = ?, section = ?, academic_year = ? WHERE class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, classObj.getClassName());
            ps.setString(2, classObj.getSection());
            ps.setString(3, classObj.getAcademicYear());
            ps.setInt(4, classObj.getClassId());
            ps.executeUpdate();
        }
    }
    
    public void deleteClass(int classId) throws SQLException {
        String query = "DELETE FROM classes WHERE class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classId);
            ps.executeUpdate();
        }
    }
    
    public Class getClassById(int classId) throws SQLException {
        String query = "SELECT * FROM classes WHERE class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Class classObj = new Class();
                    classObj.setClassId(rs.getInt("class_id"));
                    classObj.setClassName(rs.getString("class_name"));
                    classObj.setSection(rs.getString("section"));
                    classObj.setAcademicYear(rs.getString("academic_year"));
                    return classObj;
                }
            }
        }
        return null;
    }
    
    public List<Class> getAllClasses() throws SQLException {
        List<Class> classes = new ArrayList<>();
        String query = "SELECT * FROM classes ORDER BY class_name, section";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Class classObj = new Class();
                classObj.setClassId(rs.getInt("class_id"));
                classObj.setClassName(rs.getString("class_name"));
                classObj.setSection(rs.getString("section"));
                classObj.setAcademicYear(rs.getString("academic_year"));
                classes.add(classObj);
            }
        }
        return classes;
    }
}