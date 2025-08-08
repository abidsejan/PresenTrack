package com.attendance.model;
public class Class {
    private int classId;
    private String className;
    private String section;
    private String academicYear;
    
    public Class() {
    }
    
    public Class(int classId, String className, String section, String academicYear) {
        this.classId = classId;
        this.className = className;
        this.section = section;
        this.academicYear = academicYear;
    }
    
    // Getters and Setters
    public int getClassId() {
        return classId;
    }
    
    public void setClassId(int classId) {
        this.classId = classId;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getSection() {
        return section;
    }
    
    public void setSection(String section) {
        this.section = section;
    }
    
    public String getAcademicYear() {
        return academicYear;
    }
    
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    
    @Override
    public String toString() {
        return className + (section != null && !section.isEmpty() ? " - " + section : "") + " (" + academicYear + ")";
    }
}