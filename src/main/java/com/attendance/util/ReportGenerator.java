package com.attendance.util;

import com.attendance.model.Attendance;
//import com.attendance.model.Class;
import com.attendance.service.AttendanceService;
//import com.attendance.service.ClassService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportGenerator {
    private AttendanceService attendanceService;
   // private ClassService classService;
    
    public ReportGenerator() {
        attendanceService = new AttendanceService();
        // classService = new ClassService();
    }
    
    public void generateAttendanceReport(int classId, LocalDate startDate, LocalDate endDate, String filePath) throws SQLException, IOException {
        List<Attendance> attendanceList = attendanceService.getAttendanceByClassAndDateRange(classId, startDate, endDate);
        //Class classObj = classService.getClassById(classId);
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Attendance Report");
            
            // Create a font for headers
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            
            // Create a cell style for headers
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            
            // Create header cells
            String[] headers = {"Roll Number", "Student Name", "Date", "Status", "Remarks"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }
            
            // Create data rows
            int rowNum = 1;
            for (Attendance attendance : attendanceList) {
                Row row = sheet.createRow(rowNum++);
                
                // Roll Number
                row.createCell(0).setCellValue(attendance.getRollNumber());
                
                // Student Name
                row.createCell(1).setCellValue(attendance.getStudentName());
                
                // Date
                row.createCell(2).setCellValue(DateUtils.format(attendance.getDate()));
                
                // Status
                row.createCell(3).setCellValue(attendance.getStatus());
                
                // Remarks
                row.createCell(4).setCellValue(attendance.getRemarks());
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        }
    }
    
    public void generateClassSummaryReport(int classId, String filePath) throws SQLException, IOException {
        List<Attendance> attendanceList = attendanceService.getAttendanceSummaryByClass(classId);
        //Class classObj = classService.getClassById(classId);
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Class Summary Report");
            
            // Create a font for headers
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            
            // Create a cell style for headers
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            
            // Create header cells
            String[] headers = {"Roll Number", "Student Name", "Attendance Summary"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }
            
            // Create data rows
            int rowNum = 1;
            for (Attendance attendance : attendanceList) {
                Row row = sheet.createRow(rowNum++);
                
                // Roll Number
                row.createCell(0).setCellValue(attendance.getRollNumber());
                
                // Student Name
                row.createCell(1).setCellValue(attendance.getStudentName());
                
                // Attendance Summary
                row.createCell(2).setCellValue(attendance.getRemarks());
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        }
    }
}