package com.attendance.ui;

import com.attendance.model.Teacher;
import com.attendance.service.ClassService;
import com.attendance.service.TeacherService;
import com.attendance.util.DateUtils;
import com.attendance.util.ReportGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportPanel extends BasePanel {
    private ClassService classService;
    private TeacherService teacherService;
    private Teacher teacher;
    private ReportGenerator reportGenerator;

    private JComboBox<String> classComboBox;
    private RoundedTextField startDateField, endDateField;
    private JFileChooser fileChooser;
    
    public ReportPanel(Teacher teacher) {
        super("Report Generation");
        
        // Initialize services and utilities
        this.teacher = teacher;
        classService = new ClassService();
        teacherService = new TeacherService();
        reportGenerator = new ReportGenerator();
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(70, 130, 180)); // SteelBlue
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form card with white background
        JPanel formCard = new JPanel();
        formCard.setLayout(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Form components
        Font labelFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 15);
        Color fieldBg = new Color(173, 216, 230); // Light blue
        
        // Class selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel classLabel = new JLabel("Class:");
        classLabel.setFont(labelFont);
        formCard.add(classLabel, gbc);
        
        gbc.gridx = 1;
        classComboBox = new JComboBox<>();
        classComboBox.setFont(fieldFont);
        classComboBox.setBackground(fieldBg);
        loadTeacherClasses();
        formCard.add(classComboBox, gbc);
        
        // Start date
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
        startDateLabel.setFont(labelFont);
        formCard.add(startDateLabel, gbc);
        
        gbc.gridx = 1;
        startDateField = new RoundedTextField(20);
        startDateField.setFont(fieldFont);
        startDateField.setBackground(fieldBg);
        startDateField.setText(DateUtils.format(LocalDate.now().minusDays(30)));
        formCard.add(startDateField, gbc);
        
        // End date
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
        endDateLabel.setFont(labelFont);
        formCard.add(endDateLabel, gbc);
        
        gbc.gridx = 1;
        endDateField = new RoundedTextField(20);
        endDateField.setFont(fieldFont);
        endDateField.setBackground(fieldBg);
        endDateField.setText(DateUtils.format(LocalDate.now()));
        formCard.add(endDateField, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        Font buttonFont = new Font("Red Hat Display", Font.PLAIN, 16);
        Color buttonBg = new Color(173, 216, 230);
        Color buttonHover = new Color(135, 206, 235);
        Color textFg = new Color(26, 46, 53);
        
        RoundedButton attendanceReportBtn = new RoundedButton("Generate Attendance Report", 20);
        attendanceReportBtn.setFont(buttonFont);
        attendanceReportBtn.setBackground(buttonBg);
        attendanceReportBtn.setForeground(textFg);
        attendanceReportBtn.setHoverBackgroundColor(buttonHover);
        attendanceReportBtn.setPreferredSize(new Dimension(240, 40));
        attendanceReportBtn.addActionListener(_ -> generateAttendanceReport());
        buttonPanel.add(attendanceReportBtn);
        
        RoundedButton summaryReportBtn = new RoundedButton("Generate Class Summary Report", 20);
        summaryReportBtn.setFont(buttonFont);
        summaryReportBtn.setBackground(buttonBg);
        summaryReportBtn.setForeground(textFg);
        summaryReportBtn.setHoverBackgroundColor(buttonHover);
        summaryReportBtn.setPreferredSize(new Dimension(240, 40));
        summaryReportBtn.addActionListener(_ -> generateClassSummaryReport());
        buttonPanel.add(summaryReportBtn);
        
        formCard.add(buttonPanel, gbc);
        
        // Instructions card
        JPanel instructionsCard = new JPanel();
        instructionsCard.setLayout(new BorderLayout(10, 10));
        instructionsCard.setBackground(new Color(240, 240, 240));
        instructionsCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel instructionsTitle = new JLabel("Instructions", SwingConstants.CENTER);
        instructionsTitle.setFont(new Font("Red Hat Display", Font.BOLD, 18));
        instructionsTitle.setForeground(textFg);
        instructionsCard.add(instructionsTitle, BorderLayout.NORTH);
        
        JTextArea instructionsText = new JTextArea(
            "1. Select a class from the dropdown\n" +
            "2. For attendance reports, specify start and end dates\n" +
            "3. Click 'Generate Attendance Report' to create a detailed report\n" +
            "4. Click 'Generate Class Summary Report' to create a summary report\n" +
            "5. Choose a location to save the report file"
        );
        instructionsText.setFont(fieldFont);
        instructionsText.setBackground(new Color(240, 240, 240));
        instructionsText.setEditable(false);
        instructionsText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        instructionsCard.add(instructionsText, BorderLayout.CENTER);
        
        // Add components to main panel
        mainPanel.add(formCard, BorderLayout.CENTER);
        mainPanel.add(instructionsCard, BorderLayout.SOUTH);
        
        // Add to this panel
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadTeacherClasses() {
        try {
            java.util.List<Integer> classIds = teacherService.getTeacherClassIds(teacher.getTeacherId());
            classComboBox.removeAllItems();

            for (Integer classId : classIds) {
                com.attendance.model.Class classObj = classService.getClassById(classId);
                if (classObj != null) {
                    classComboBox.addItem(classObj.toString() + " (" + classObj.getClassId() + ")");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading classes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateAttendanceReport() {
        String selectedClass = (String) classComboBox.getSelectedItem();
        if (selectedClass == null) {
            JOptionPane.showMessageDialog(this, "Please select a class", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String startDateStr = startDateField.getText();
        if (!DateUtils.isValidDate(startDateStr)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid start date in YYYY-MM-DD format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String endDateStr = endDateField.getText();
        if (!DateUtils.isValidDate(endDateStr)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid end date in YYYY-MM-DD format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate startDate = DateUtils.parse(startDateStr);
        LocalDate endDate = DateUtils.parse(endDateStr);

        if (startDate.isAfter(endDate)) {
            JOptionPane.showMessageDialog(this, "Start date must be before end date", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String classIdStr = selectedClass.substring(selectedClass.lastIndexOf("(") + 1, selectedClass.lastIndexOf(")"));
            int classId = Integer.parseInt(classIdStr);

            String defaultFileName = "Attendance_Report_" + selectedClass.replace(" ", "_") + "_" +
                    startDateStr.replace("-", "") + "_to_" + endDateStr.replace("-", "") + ".xlsx";
            fileChooser.setSelectedFile(new File(defaultFileName));

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                reportGenerator.generateAttendanceReport(classId, startDate, endDate, filePath);
                JOptionPane.showMessageDialog(this, 
                    "Attendance report generated successfully!\nSaved to: " + filePath, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateClassSummaryReport() {
        String selectedClass = (String) classComboBox.getSelectedItem();
        if (selectedClass == null) {
            JOptionPane.showMessageDialog(this, "Please select a class", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String classIdStr = selectedClass.substring(selectedClass.lastIndexOf("(") + 1, selectedClass.lastIndexOf(")"));
            int classId = Integer.parseInt(classIdStr);

            String defaultFileName = "Class_Summary_Report_" + selectedClass.replace(" ", "_") + ".xlsx";
            fileChooser.setSelectedFile(new File(defaultFileName));

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                reportGenerator.generateClassSummaryReport(classId, filePath);
                JOptionPane.showMessageDialog(this, 
                    "Class summary report generated successfully!\nSaved to: " + filePath, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}