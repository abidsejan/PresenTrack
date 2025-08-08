package com.attendance.model;
import java.time.LocalDate;

public class Attendance {
    private int attendanceId;
    private int studentId;
    private int classId;
    private LocalDate date;
    private String status; // Present, Absent, Late
    private String remarks;
    private String studentName;
    private String rollNumber;
    
    public Attendance() {
    }
    
    public Attendance(int attendanceId, int studentId, int classId, LocalDate date, String status, String remarks) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.classId = classId;
        this.date = date;
        this.status = status;
        this.remarks = remarks;
    }
    
    // Getters and Setters
    public int getAttendanceId() {
        return attendanceId;
    }
    
    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public int getClassId() {
        return classId;
    }
    
    public void setClassId(int classId) {
        this.classId = classId;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getRollNumber() {
        return rollNumber;
    }
    
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
}