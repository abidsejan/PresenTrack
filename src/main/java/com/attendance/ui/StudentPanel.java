package com.attendance.ui;

import com.attendance.model.Student;
import com.attendance.service.ClassService;
import com.attendance.service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class StudentPanel extends BasePanel {
    private StudentService studentService;
    private ClassService classService;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private RoundedTextField rollNumberField, firstNameField, lastNameField, emailField, phoneField;
    private JComboBox<String> classComboBox;

    // Colors
    private final Color PANEL_BG = new Color(70, 130, 180);
    private final Color CARD_BG = Color.WHITE;
    private final Color FIELD_BG = new Color(173, 216, 230);
    private final Color TEXT_FG = new Color(26, 46, 53);
    private final Color BUTTON_HOVER = new Color(135, 206, 235);

    // Fonts
    private final Font LABEL_FONT = new Font("Red Hat Display", Font.PLAIN, 18);
    private final Font FIELD_FONT = new Font("Red Hat Display", Font.PLAIN, 15);
    private final Font BUTTON_FONT = new Font("Red Hat Display", Font.PLAIN, 16);

    public StudentPanel() {
        super("Student Management");
        setBackground(Color.WHITE);

        studentService = new StudentService();
        classService = new ClassService();

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

        // Roll Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setFont(LABEL_FONT);
        formPanel.add(rollLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rollNumberField = new RoundedTextField(20);
        rollNumberField.setBackground(FIELD_BG);
        rollNumberField.setFont(FIELD_FONT);
        rollNumberField.setPreferredSize(new Dimension(200, 35));
        formPanel.add(rollNumberField, gbc);

        // First Name
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(LABEL_FONT);
        formPanel.add(firstNameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        firstNameField = new RoundedTextField(20);
        firstNameField.setBackground(FIELD_BG);
        firstNameField.setFont(FIELD_FONT);
        firstNameField.setPreferredSize(new Dimension(200, 35));
        formPanel.add(firstNameField, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(LABEL_FONT);
        formPanel.add(lastNameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        lastNameField = new RoundedTextField(20);
        lastNameField.setBackground(FIELD_BG);
        lastNameField.setFont(FIELD_FONT);
        lastNameField.setPreferredSize(new Dimension(200, 35));
        formPanel.add(lastNameField, gbc);

        // Email
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.insets.left = 30; // Add left padding
        gbc.fill = GridBagConstraints.NONE;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(LABEL_FONT);
        formPanel.add(emailLabel, gbc);

        gbc.insets.left = 10; // Reset left padding
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new RoundedTextField(20);
        emailField.setBackground(FIELD_BG);
        emailField.setFont(FIELD_FONT);
        emailField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(emailField, gbc);

        // Phone
        gbc.gridx = 2;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.insets.left = 30;
        gbc.fill = GridBagConstraints.NONE;
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(LABEL_FONT);
        formPanel.add(phoneLabel, gbc);

        gbc.insets.left = 10;
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        phoneField = new RoundedTextField(20);
        phoneField.setBackground(FIELD_BG);
        phoneField.setFont(FIELD_FONT);
        phoneField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(phoneField, gbc);

        // Class
        gbc.gridx = 2;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.insets.left = 30;
        gbc.fill = GridBagConstraints.NONE;
        JLabel classLabel = new JLabel("Class:");
        classLabel.setFont(LABEL_FONT);
        formPanel.add(classLabel, gbc);

        gbc.insets.left = 10;
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        classComboBox = new JComboBox<>();
        classComboBox.setFont(FIELD_FONT);
        classComboBox.setBackground(FIELD_BG);
        loadClasses();
        formPanel.add(classComboBox, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        RoundedButton addButton = new RoundedButton("Add", 20);
        addButton.setFont(BUTTON_FONT);
        addButton.setBackground(FIELD_BG);
        addButton.setForeground(TEXT_FG);
        addButton.setHoverBackgroundColor(BUTTON_HOVER);
        addButton.setPreferredSize(new Dimension(120, 40));
        addButton.addActionListener(_ -> addStudent());
        buttonPanel.add(addButton);

        RoundedButton updateButton = new RoundedButton("Update", 20);
        updateButton.setFont(BUTTON_FONT);
        updateButton.setBackground(FIELD_BG);
        updateButton.setForeground(TEXT_FG);
        updateButton.setHoverBackgroundColor(BUTTON_HOVER);
        updateButton.setPreferredSize(new Dimension(120, 40));
        updateButton.addActionListener(_ -> updateStudent());
        buttonPanel.add(updateButton);

        RoundedButton deleteButton = new RoundedButton("Delete", 20);
        deleteButton.setFont(BUTTON_FONT);
        deleteButton.setBackground(FIELD_BG);
        deleteButton.setForeground(TEXT_FG);
        deleteButton.setHoverBackgroundColor(BUTTON_HOVER);
        deleteButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.addActionListener(_ -> deleteStudent());
        buttonPanel.add(deleteButton);

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
        gbc.gridwidth = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(buttonPanel, gbc);

        // Table
        String[] columnNames = {"ID", "Roll Number", "First Name", "Last Name", "Email", "Phone", "Class"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setFont(FIELD_FONT);
        studentTable.setRowHeight(30);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setGridColor(new Color(230, 230, 230));
        studentTable.getTableHeader().setFont(BUTTON_FONT);
        studentTable.getTableHeader().setBackground(FIELD_BG);
        studentTable.getTableHeader().setForeground(TEXT_FG);

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    loadStudentData(selectedRow);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
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

        // Refresh Button
        RoundedButton refreshButton = new RoundedButton("Refresh", 20);
        refreshButton.setFont(BUTTON_FONT);
        refreshButton.setBackground(FIELD_BG);
        refreshButton.setForeground(TEXT_FG);
        refreshButton.setHoverBackgroundColor(BUTTON_HOVER);
        refreshButton.setPreferredSize(new Dimension(120, 40));
        refreshButton.addActionListener(_ -> loadStudents());

        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.setBackground(PANEL_BG);
        refreshPanel.add(refreshButton);
        add(refreshPanel, BorderLayout.SOUTH);

        loadStudents();
    }

    private void loadStudentData(int selectedRow) {
        try {
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            Student student = studentService.getStudentById(studentId);
            if (student != null) {
                rollNumberField.setText(student.getRollNumber());
                firstNameField.setText(student.getFirstName());
                lastNameField.setText(student.getLastName());
                emailField.setText(student.getEmail());
                phoneField.setText(student.getPhone());

                for (int i = 0; i < classComboBox.getItemCount(); i++) {
                    if (classComboBox.getItemAt(i).startsWith(student.getClassName())) {
                        classComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadClasses() {
        try {
            List<com.attendance.model.Class> classes = classService.getAllClasses();
            classComboBox.removeAllItems();
            for (com.attendance.model.Class classObj : classes) {
                classComboBox.addItem(classObj.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading classes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            tableModel.setRowCount(0);

            for (Student student : students) {
                Object[] row = {
                        student.getStudentId(),
                        student.getRollNumber(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getPhone(),
                        student.getClassName()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStudent() {
        try {
            Student student = new Student();
            student.setRollNumber(rollNumberField.getText());
            student.setFirstName(firstNameField.getText());
            student.setLastName(lastNameField.getText());
            student.setEmail(emailField.getText());
            student.setPhone(phoneField.getText());

            String selectedClass = (String) classComboBox.getSelectedItem();
            if (selectedClass != null) {
                String classIdStr = selectedClass.substring(selectedClass.lastIndexOf("(") + 1, selectedClass.lastIndexOf(")"));
                student.setClassId(Integer.parseInt(classIdStr));
            }

            studentService.addStudent(student);
            JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadStudents();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Student student = new Student();
            student.setStudentId((int) tableModel.getValueAt(selectedRow, 0));
            student.setRollNumber(rollNumberField.getText());
            student.setFirstName(firstNameField.getText());
            student.setLastName(lastNameField.getText());
            student.setEmail(emailField.getText());
            student.setPhone(phoneField.getText());

            String selectedClass = (String) classComboBox.getSelectedItem();
            if (selectedClass != null) {
                String classIdStr = selectedClass.substring(selectedClass.lastIndexOf("(") + 1, selectedClass.lastIndexOf(")"));
                student.setClassId(Integer.parseInt(classIdStr));
            }

            studentService.updateStudent(student);
            JOptionPane.showMessageDialog(this, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadStudents();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this student?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                int studentId = (int) tableModel.getValueAt(selectedRow, 0);
                studentService.deleteStudent(studentId);
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadStudents();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        rollNumberField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        classComboBox.setSelectedIndex(-1);
        studentTable.clearSelection();
    }
}