package com.attendance.dao;

import com.attendance.model.Attendance;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private Connection connection;
    
    public AttendanceDAO() {
        connection = DatabaseConnection.getConnection();
    }
    
    public void markAttendance(Attendance attendance) throws SQLException {
        String query = "INSERT INTO attendance (student_id, class_id, date, status, remarks) VALUES (?, ?, ?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE status = ?, remarks = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, attendance.getStudentId());
            ps.setInt(2, attendance.getClassId());
            ps.setDate(3, Date.valueOf(attendance.getDate()));
            ps.setString(4, attendance.getStatus());
            ps.setString(5, attendance.getRemarks());
            ps.setString(6, attendance.getStatus());
            ps.setString(7, attendance.getRemarks());
            ps.executeUpdate();
        }
    }
    
    public void updateAttendance(Attendance attendance) throws SQLException {
        String query = "UPDATE attendance SET status = ?, remarks = ? WHERE attendance_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, attendance.getStatus());
            ps.setString(2, attendance.getRemarks());
            ps.setInt(3, attendance.getAttendanceId());
            ps.executeUpdate();
        }
    }
    
    public void deleteAttendance(int attendanceId) throws SQLException {
        String query = "DELETE FROM attendance WHERE attendance_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, attendanceId);
            ps.executeUpdate();
        }
    }
    
    public Attendance getAttendanceById(int attendanceId) throws SQLException {
        String query = "SELECT a.*, s.first_name, s.last_name, s.roll_number FROM attendance a " +
                       "JOIN students s ON a.student_id = s.student_id WHERE a.attendance_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, attendanceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setAttendanceId(rs.getInt("attendance_id"));
                    attendance.setStudentId(rs.getInt("student_id"));
                    attendance.setClassId(rs.getInt("class_id"));
                    attendance.setDate(rs.getDate("date").toLocalDate());
                    attendance.setStatus(rs.getString("status"));
                    attendance.setRemarks(rs.getString("remarks"));
                    attendance.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    attendance.setRollNumber(rs.getString("roll_number"));
                    return attendance;
                }
            }
        }
        return null;
    }
    
    public List<Attendance> getAttendanceByDateAndClass(LocalDate date, int classId) throws SQLException {
        List<Attendance> attendanceList = new ArrayList<>();
        String query = "SELECT a.*, s.first_name, s.last_name, s.roll_number FROM attendance a " +
                       "JOIN students s ON a.student_id = s.student_id WHERE a.date = ? AND a.class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(date));
            ps.setInt(2, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setAttendanceId(rs.getInt("attendance_id"));
                    attendance.setStudentId(rs.getInt("student_id"));
                    attendance.setClassId(rs.getInt("class_id"));
                    attendance.setDate(rs.getDate("date").toLocalDate());
                    attendance.setStatus(rs.getString("status"));
                    attendance.setRemarks(rs.getString("remarks"));
                    attendance.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    attendance.setRollNumber(rs.getString("roll_number"));
                    attendanceList.add(attendance);
                }
            }
        }
        return attendanceList;
    }
    
    public List<Attendance> getAttendanceByStudentAndDateRange(int studentId, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Attendance> attendanceList = new ArrayList<>();
        String query = "SELECT a.*, s.first_name, s.last_name, s.roll_number FROM attendance a " +
                       "JOIN students s ON a.student_id = s.student_id WHERE a.student_id = ? AND a.date BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setAttendanceId(rs.getInt("attendance_id"));
                    attendance.setStudentId(rs.getInt("student_id"));
                    attendance.setClassId(rs.getInt("class_id"));
                    attendance.setDate(rs.getDate("date").toLocalDate());
                    attendance.setStatus(rs.getString("status"));
                    attendance.setRemarks(rs.getString("remarks"));
                    attendance.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    attendance.setRollNumber(rs.getString("roll_number"));
                    attendanceList.add(attendance);
                }
            }
        }
        return attendanceList;
    }
    
    public List<Attendance> getAttendanceByClassAndDateRange(int classId, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Attendance> attendanceList = new ArrayList<>();
        String query = "SELECT a.*, s.first_name, s.last_name, s.roll_number FROM attendance a " +
                       "JOIN students s ON a.student_id = s.student_id WHERE a.class_id = ? AND a.date BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classId);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setAttendanceId(rs.getInt("attendance_id"));
                    attendance.setStudentId(rs.getInt("student_id"));
                    attendance.setClassId(rs.getInt("class_id"));
                    attendance.setDate(rs.getDate("date").toLocalDate());
                    attendance.setStatus(rs.getString("status"));
                    attendance.setRemarks(rs.getString("remarks"));
                    attendance.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    attendance.setRollNumber(rs.getString("roll_number"));
                    attendanceList.add(attendance);
                }
            }
        }
        return attendanceList;
    }
    
    public List<Attendance> getAttendanceSummaryByClass(int classId) throws SQLException {
        List<Attendance> attendanceList = new ArrayList<>();
        String query = "SELECT s.student_id, s.first_name, s.last_name, s.roll_number, " +
                       "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) as present_count, " +
                       "SUM(CASE WHEN a.status = 'Absent' THEN 1 ELSE 0 END) as absent_count, " +
                       "SUM(CASE WHEN a.status = 'Late' THEN 1 ELSE 0 END) as late_count, " +
                       "COUNT(a.attendance_id) as total_days " +
                       "FROM students s " +
                       "LEFT JOIN attendance a ON s.student_id = a.student_id AND s.class_id = a.class_id " +
                       "WHERE s.class_id = ? " +
                       "GROUP BY s.student_id, s.first_name, s.last_name, s.roll_number";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setStudentId(rs.getInt("student_id"));
                    attendance.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    attendance.setRollNumber(rs.getString("roll_number"));
                    // We'll use remarks to store summary information
                    attendance.setRemarks("Present: " + rs.getInt("present_count") + 
                                        ", Absent: " + rs.getInt("absent_count") + 
                                        ", Late: " + rs.getInt("late_count") + 
                                        ", Total: " + rs.getInt("total_days"));
                    attendanceList.add(attendance);
                }
            }
        }
        return attendanceList;
    }
}