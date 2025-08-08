package com.attendance.service;

import com.attendance.dao.TeacherDAO;
import com.attendance.model.Teacher;
import java.sql.SQLException;
import java.util.List;

public class TeacherService {
    private TeacherDAO teacherDAO;
    
    public TeacherService() {
        teacherDAO = new TeacherDAO();
    }
    
    public void addTeacher(Teacher teacher) throws SQLException {
        teacherDAO.addTeacher(teacher);
    }
    
    public void updateTeacher(Teacher teacher) throws SQLException {
        teacherDAO.updateTeacher(teacher);
    }
    
    public void deleteTeacher(int teacherId) throws SQLException {
        teacherDAO.deleteTeacher(teacherId);
    }
    
    public Teacher getTeacherById(int teacherId) throws SQLException {
        return teacherDAO.getTeacherById(teacherId);
    }
    
    public Teacher authenticateTeacher(String username, String password) throws SQLException {
        return teacherDAO.authenticateTeacher(username, password);
    }
    
    public Teacher getTeacherByUsername(String username) throws SQLException {
        return teacherDAO.getTeacherByUsername(username);
    }
    
    public List<Teacher> getAllTeachers() throws SQLException {
        return teacherDAO.getAllTeachers();
    }
    
    public void assignTeacherToClass(int teacherId, int classId) throws SQLException {
        teacherDAO.assignTeacherToClass(teacherId, classId);
    }
    
    public void removeTeacherFromClass(int teacherId, int classId) throws SQLException {
        teacherDAO.removeTeacherFromClass(teacherId, classId);
    }
    
    public List<Integer> getTeacherClassIds(int teacherId) throws SQLException {
        return teacherDAO.getTeacherClassIds(teacherId);
    }
}