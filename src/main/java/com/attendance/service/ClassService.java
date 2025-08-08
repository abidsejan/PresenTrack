package com.attendance.service;

import com.attendance.dao.ClassDAO;
import com.attendance.model.Class;
import java.sql.SQLException;
import java.util.List;

public class ClassService {
    private ClassDAO classDAO;
    
    public ClassService() {
        classDAO = new ClassDAO();
    }
    
    public void addClass(Class classObj) throws SQLException {
        if (classObj == null) {
            throw new IllegalArgumentException("Class object cannot be null");
        }
        if (classObj.getClassName() == null || classObj.getClassName().trim().isEmpty()) {
            throw new IllegalArgumentException("Class name cannot be empty");
        }
        if (classObj.getAcademicYear() == null || classObj.getAcademicYear().trim().isEmpty()) {
            throw new IllegalArgumentException("Academic year cannot be empty");
        }
        
        classDAO.addClass(classObj);
    }
    
    public void updateClass(Class classObj) throws SQLException {
        if (classObj == null) {
            throw new IllegalArgumentException("Class object cannot be null");
        }
        if (classObj.getClassId() <= 0) {
            throw new IllegalArgumentException("Invalid class ID");
        }
        if (classObj.getClassName() == null || classObj.getClassName().trim().isEmpty()) {
            throw new IllegalArgumentException("Class name cannot be empty");
        }
        if (classObj.getAcademicYear() == null || classObj.getAcademicYear().trim().isEmpty()) {
            throw new IllegalArgumentException("Academic year cannot be empty");
        }
        
        classDAO.updateClass(classObj);
    }
    
    public void deleteClass(int classId) throws SQLException {
        if (classId <= 0) {
            throw new IllegalArgumentException("Invalid class ID");
        }
        
        classDAO.deleteClass(classId);
    }
    
    public Class getClassById(int classId) throws SQLException {
        if (classId <= 0) {
            throw new IllegalArgumentException("Invalid class ID");
        }
        
        return classDAO.getClassById(classId);
    }
    
    public List<Class> getAllClasses() throws SQLException {
        return classDAO.getAllClasses();
    }
}