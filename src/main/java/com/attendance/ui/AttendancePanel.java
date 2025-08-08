package com.attendance.ui;

import com.attendance.model.Attendance;
import com.attendance.model.Class;
import com.attendance.model.Student;
import com.attendance.model.Teacher;
import com.attendance.service.AttendanceService;
import com.attendance.service.ClassService;
import com.attendance.service.StudentService;
import com.attendance.service.TeacherService;
import com.attendance.util.DateUtils;
//import com.attendance.util.UIUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AttendancePanel extends BasePanel {
    private AttendanceService attendanceService;
    private ClassService classService;
    private StudentService studentService;
    private TeacherService teacherService;
    private Teacher teacher;

    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> classComboBox;
    private RoundedTextField dateField;
    private JComboBox<String> statusComboBox;
    private JTextArea remarksArea;

    // Colors
    //private final Color PANEL_BG = new Color(70, 130, 180);
    private final Color CARD_BG = Color.WHITE;
    private final Color FIELD_BG = new Color(173, 216, 230);
    private final Color TEXT_FG = new Color(26, 46, 53);
    private final Color BUTTON_HOVER = new Color(135, 206, 235);
    
    // Fonts
    private final Font LABEL_FONT = new Font("Red Hat Display", Font.PLAIN, 18);
    private final Font FIELD_FONT = new Font("Red Hat Display", Font.PLAIN, 15);
    private final Font BUTTON_FONT = new Font("Red Hat Display", Font.PLAIN, 16);

    public AttendancePanel(Teacher teacher) {
        super("Attendance Management");
        setBackground(Color.WHITE);

        this.teacher = teacher;
        attendanceService = new AttendanceService();
        classService = new ClassService();
        studentService = new StudentService();
        teacherService = new TeacherService();

        // Form Panel with rounded corners
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(CARD_BG);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel classLabel = new JLabel("Class:");
        classLabel.setFont(LABEL_FONT);
        formPanel.add(classLabel, gbc);
        
        gbc.gridx = 1;
        classComboBox = new JComboBox<>();
        classComboBox.setFont(FIELD_FONT);
        classComboBox.setBackground(FIELD_BG);
        loadTeacherClasses();
        formPanel.add(classComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(LABEL_FONT);
        formPanel.add(dateLabel, gbc);
        
        gbc.gridx = 1;
        dateField = new RoundedTextField(20);
        dateField.setBackground(FIELD_BG);
        dateField.setFont(FIELD_FONT);
        dateField.setText(DateUtils.format(LocalDate.now()));
        formPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(LABEL_FONT);
        formPanel.add(statusLabel, gbc);
        
        gbc.gridx = 1;
        statusComboBox = new JComboBox<>();
        statusComboBox.setFont(FIELD_FONT);
        statusComboBox.setBackground(FIELD_BG);
        statusComboBox.addItem("Present");
        statusComboBox.addItem("Absent");
        statusComboBox.addItem("Late");
        formPanel.add(statusComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel remarksLabel = new JLabel("Remarks:");
        remarksLabel.setFont(LABEL_FONT);
        formPanel.add(remarksLabel, gbc);
        
        gbc.gridx = 1;
        remarksArea = new JTextArea(3, 20);
        remarksArea.setFont(FIELD_FONT);
        remarksArea.setLineWrap(true);
        remarksArea.setWrapStyleWord(true);
        remarksArea.setBackground(FIELD_BG);
        remarksArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JScrollPane remarksScrollPane = new JScrollPane(remarksArea);
        remarksScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            )
        ));
        formPanel.add(remarksScrollPane, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        RoundedButton markAttendanceButton = new RoundedButton("Mark Attendance", 20);
        markAttendanceButton.setFont(BUTTON_FONT);
        markAttendanceButton.setBackground(FIELD_BG);
        markAttendanceButton.setForeground(TEXT_FG);
        markAttendanceButton.setHoverBackgroundColor(BUTTON_HOVER);
        markAttendanceButton.setPreferredSize(new Dimension(160, 40));
        markAttendanceButton.addActionListener(_ -> markAttendanceForClass());
        buttonPanel.add(markAttendanceButton);

        RoundedButton loadButton = new RoundedButton("Load Attendance", 20);
        loadButton.setFont(BUTTON_FONT);
        loadButton.setBackground(FIELD_BG);
        loadButton.setForeground(TEXT_FG);
        loadButton.setHoverBackgroundColor(BUTTON_HOVER);
        loadButton.setPreferredSize(new Dimension(160, 40));
        loadButton.addActionListener(_ -> loadAttendanceForClass());
        buttonPanel.add(loadButton);

        RoundedButton clearButton = new RoundedButton("Clear", 20);
        clearButton.setFont(BUTTON_FONT);
        clearButton.setBackground(FIELD_BG);
        clearButton.setForeground(TEXT_FG);
        clearButton.setHoverBackgroundColor(BUTTON_HOVER);
        clearButton.setPreferredSize(new Dimension(120, 40));
        clearButton.addActionListener(_ -> clearForm());
        buttonPanel.add(clearButton);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Table
        String[] columnNames = {"ID", "Roll Number", "Student Name", "Date", "Status", "Remarks"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        attendanceTable = new JTable(tableModel);
        attendanceTable.setFont(FIELD_FONT);
        attendanceTable.setRowHeight(30);
        attendanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attendanceTable.setGridColor(new Color(230, 230, 230));
        attendanceTable.getTableHeader().setFont(BUTTON_FONT);
        attendanceTable.getTableHeader().setBackground(FIELD_BG);
        attendanceTable.getTableHeader().setForeground(TEXT_FG);
        
        attendanceTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = attendanceTable.getSelectedRow();
                if (selectedRow != -1) {
                    loadAttendanceData(selectedRow);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Main content panel with gradient background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(70, 130, 180),
                    getWidth(), getHeight(), new Color(135, 206, 235)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BorderLayout(20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    // Rest of the methods remain the same as they implement functionality
    private void loadAttendanceData(int selectedRow) {
        try {
            int attendanceId = (int) tableModel.getValueAt(selectedRow, 0);
            Attendance attendance = attendanceService.getAttendanceById(attendanceId);
            if (attendance != null) {
                for (int i = 0; i < classComboBox.getItemCount(); i++) {
                    String item = (String) classComboBox.getItemAt(i);
                    if (item.contains("(" + attendance.getClassId() + ")")) {
                        classComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                dateField.setText(DateUtils.format(attendance.getDate()));
                statusComboBox.setSelectedItem(attendance.getStatus());
                remarksArea.setText(attendance.getRemarks());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading attendance: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTeacherClasses() {
        try {
            List<Integer> classIds = teacherService.getTeacherClassIds(teacher.getTeacherId());
            classComboBox.removeAllItems();

            for (Integer classId : classIds) {
                Class classObj = classService.getClassById(classId);
                if (classObj != null) {
                    classComboBox.addItem(classObj.toString() + " (" + classObj.getClassId() + ")");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading classes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markAttendanceForClass() {
        String selectedClass = (String) classComboBox.getSelectedItem();
        if (selectedClass == null) {
            JOptionPane.showMessageDialog(this, "Please select a class", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dateStr = dateField.getText();
        if (!DateUtils.isValidDate(dateStr)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid date in YYYY-MM-DD format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate date = DateUtils.parse(dateStr);
        String status = (String) statusComboBox.getSelectedItem();
        String remarks = remarksArea.getText();

        try {
            String classIdStr = selectedClass.substring(selectedClass.lastIndexOf("(") + 1, selectedClass.lastIndexOf(")"));
            int classId = Integer.parseInt(classIdStr);

            List<Student> students = studentService.getStudentsByClass(classId);

            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No students found in this class", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JCheckBox[] checkBoxes = new JCheckBox[students.size()];
            JPanel checkPanel = new JPanel(new GridLayout(0, 1));
            checkPanel.setBackground(Color.WHITE);
            checkPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            for (int i = 0; i < students.size(); i++) {
                checkBoxes[i] = new JCheckBox(students.get(i).toString());
                checkBoxes[i].setFont(FIELD_FONT);
                checkBoxes[i].setBackground(Color.WHITE);
                checkPanel.add(checkBoxes[i]);
            }

            JScrollPane scrollPane = new JScrollPane(checkPanel);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            // Custom dialog with rounded corners
            JDialog dialog = new JDialog();
            dialog.setTitle("Select Students to Mark as " + status);
            dialog.setModal(true);
            dialog.setLayout(new BorderLayout());
            
            JPanel dialogContentPanel = new JPanel(new BorderLayout(10, 10));
            dialogContentPanel.setBackground(Color.WHITE);
            dialogContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JLabel titleLabel = new JLabel("Select Students to Mark as " + status);
            titleLabel.setFont(new Font("Red Hat Display", Font.BOLD, 18));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            dialogContentPanel.add(titleLabel, BorderLayout.NORTH);
            dialogContentPanel.add(scrollPane, BorderLayout.CENTER);
            
            JPanel dialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            dialogButtonPanel.setBackground(Color.WHITE);
            
            RoundedButton confirmButton = new RoundedButton("Confirm", 20);
            confirmButton.setFont(BUTTON_FONT);
            confirmButton.setBackground(FIELD_BG);
            confirmButton.setForeground(TEXT_FG);
            confirmButton.setHoverBackgroundColor(BUTTON_HOVER);
            confirmButton.setPreferredSize(new Dimension(120, 40));
            
            RoundedButton cancelButton = new RoundedButton("Cancel", 20);
            cancelButton.setFont(BUTTON_FONT);
            cancelButton.setBackground(FIELD_BG);
            cancelButton.setForeground(TEXT_FG);
            cancelButton.setHoverBackgroundColor(BUTTON_HOVER);
            cancelButton.setPreferredSize(new Dimension(120, 40));
            
            dialogButtonPanel.add(confirmButton);
            dialogButtonPanel.add(cancelButton);
            dialogContentPanel.add(dialogButtonPanel, BorderLayout.SOUTH);
            
            dialog.add(dialogContentPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            
            final boolean[] result = {false};
            
            confirmButton.addActionListener(_ -> {
                result[0] = true;
                dialog.dispose();
            });
            
            cancelButton.addActionListener(_ -> dialog.dispose());
            
            dialog.setVisible(true);

            if (result[0]) {
                int markedCount = 0;
                for (int i = 0; i < students.size(); i++) {
                    if (checkBoxes[i].isSelected()) {
                        Attendance attendance = new Attendance();
                        attendance.setStudentId(students.get(i).getStudentId());
                        attendance.setClassId(classId);
                        attendance.setDate(date);
                        attendance.setStatus(status);
                        attendance.setRemarks(remarks);

                        attendanceService.markAttendance(attendance);
                        markedCount++;
                    }
                }

                if (markedCount > 0) {
                    JOptionPane.showMessageDialog(this, "Attendance marked for " + markedCount + " student(s)", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadAttendanceForClass();
                } else {
                    JOptionPane.showMessageDialog(this, "No students were selected", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error marking attendance: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAttendanceForClass() {
        String selectedClass = (String) classComboBox.getSelectedItem();
        if (selectedClass == null) {
            JOptionPane.showMessageDialog(this, "Please select a class", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dateStr = dateField.getText();
        if (!DateUtils.isValidDate(dateStr)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid date in YYYY-MM-DD format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate date = DateUtils.parse(dateStr);

        try {
            String classIdStr = selectedClass.substring(selectedClass.lastIndexOf("(") + 1, selectedClass.lastIndexOf(")"));
            int classId = Integer.parseInt(classIdStr);

            List<Attendance> attendanceList = attendanceService.getAttendanceByDateAndClass(date, classId);
            tableModel.setRowCount(0);

            for (Attendance attendance : attendanceList) {
                Object[] row = {
                        attendance.getAttendanceId(),
                        attendance.getRollNumber(),
                        attendance.getStudentName(),
                        DateUtils.format(attendance.getDate()),
                        attendance.getStatus(),
                        attendance.getRemarks()
                };
                tableModel.addRow(row);
            }

            if (attendanceList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No attendance records found for this class and date", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading attendance: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        classComboBox.setSelectedIndex(-1);
        dateField.setText(DateUtils.format(LocalDate.now()));
        statusComboBox.setSelectedIndex(0);
        remarksArea.setText("");
        attendanceTable.clearSelection();
    }
}