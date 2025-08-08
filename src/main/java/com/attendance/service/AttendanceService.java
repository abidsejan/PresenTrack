package com.attendance.service;

import com.attendance.dao.AttendanceDAO;
import com.attendance.model.Attendance;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AttendanceService {
    private AttendanceDAO attendanceDAO;
    
    public AttendanceService() {
        attendanceDAO = new AttendanceDAO();
    }
    
    public void markAttendance(Attendance attendance) throws SQLException {
        attendanceDAO.markAttendance(attendance);
    }
    
    public void updateAttendance(Attendance attendance) throws SQLException {
        attendanceDAO.updateAttendance(attendance);
    }
    
    public void deleteAttendance(int attendanceId) throws SQLException {
        attendanceDAO.deleteAttendance(attendanceId);
    }
    
    public Attendance getAttendanceById(int attendanceId) throws SQLException {
        return attendanceDAO.getAttendanceById(attendanceId);
    }
    
    public List<Attendance> getAttendanceByDateAndClass(LocalDate date, int classId) throws SQLException {
        return attendanceDAO.getAttendanceByDateAndClass(date, classId);
    }
    
    public List<Attendance> getAttendanceByStudentAndDateRange(int studentId, LocalDate startDate, LocalDate endDate) throws SQLException {
        return attendanceDAO.getAttendanceByStudentAndDateRange(studentId, startDate, endDate);
    }
    
    public List<Attendance> getAttendanceByClassAndDateRange(int classId, LocalDate startDate, LocalDate endDate) throws SQLException {
        return attendanceDAO.getAttendanceByClassAndDateRange(classId, startDate, endDate);
    }
    
    public List<Attendance> getAttendanceSummaryByClass(int classId) throws SQLException {
        return attendanceDAO.getAttendanceSummaryByClass(classId);
    }
}