package com.attendance.ui;

import com.attendance.model.Class;
import com.attendance.service.ClassService;
//import com.attendance.util.UIUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ClassPanel extends BasePanel {
    private ClassService classService;
    private JTable classTable;
    private DefaultTableModel tableModel;
    private RoundedTextField classNameField, sectionField, academicYearField;

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

    public ClassPanel() {
        super("Class Management");
        setBackground(Color.WHITE);

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
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel classNameLabel = new JLabel("Class Name:");
        classNameLabel.setFont(LABEL_FONT);
        formPanel.add(classNameLabel, gbc);
        
        gbc.gridx = 1;
        classNameField = new RoundedTextField(20);
        classNameField.setBackground(FIELD_BG);
        classNameField.setFont(FIELD_FONT);
        formPanel.add(classNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setFont(LABEL_FONT);
        formPanel.add(sectionLabel, gbc);
        
        gbc.gridx = 1;
        sectionField = new RoundedTextField(20);
        sectionField.setBackground(FIELD_BG);
        sectionField.setFont(FIELD_FONT);
        formPanel.add(sectionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel academicLabel = new JLabel("Academic Year:");
        academicLabel.setFont(LABEL_FONT);
        formPanel.add(academicLabel, gbc);
        
        gbc.gridx = 1;
        academicYearField = new RoundedTextField(20);
        academicYearField.setBackground(FIELD_BG);
        academicYearField.setFont(FIELD_FONT);
        formPanel.add(academicYearField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        RoundedButton addButton = new RoundedButton("Add", 20);
        addButton.setFont(BUTTON_FONT);
        addButton.setBackground(FIELD_BG);
        addButton.setForeground(TEXT_FG);
        addButton.setHoverBackgroundColor(BUTTON_HOVER);
        addButton.setPreferredSize(new Dimension(120, 40));
        addButton.addActionListener(_ -> addClass());
        buttonPanel.add(addButton);

        RoundedButton updateButton = new RoundedButton("Update", 20);
        updateButton.setFont(BUTTON_FONT);
        updateButton.setBackground(FIELD_BG);
        updateButton.setForeground(TEXT_FG);
        updateButton.setHoverBackgroundColor(BUTTON_HOVER);
        updateButton.setPreferredSize(new Dimension(120, 40));
        updateButton.addActionListener(_ -> updateClass());
        buttonPanel.add(updateButton);

        RoundedButton deleteButton = new RoundedButton("Delete", 20);
        deleteButton.setFont(BUTTON_FONT);
        deleteButton.setBackground(FIELD_BG);
        deleteButton.setForeground(TEXT_FG);
        deleteButton.setHoverBackgroundColor(BUTTON_HOVER);
        deleteButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.addActionListener(_ -> deleteClass());
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
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Table
        String[] columnNames = {"ID", "Class Name", "Section", "Academic Year"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        classTable = new JTable(tableModel);
        classTable.setFont(FIELD_FONT);
        classTable.setRowHeight(30);
        classTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        classTable.setGridColor(new Color(230, 230, 230));
        classTable.getTableHeader().setFont(BUTTON_FONT);
        classTable.getTableHeader().setBackground(FIELD_BG);
        classTable.getTableHeader().setForeground(TEXT_FG);
        
        classTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = classTable.getSelectedRow();
                if (selectedRow != -1) {
                    loadClassData(selectedRow);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(classTable);
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
        refreshButton.addActionListener(_ -> loadClasses());
        
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.setBackground(PANEL_BG);
        refreshPanel.add(refreshButton);
        add(refreshPanel, BorderLayout.SOUTH);

        loadClasses();
    }

    // Rest of the methods remain the same as they implement functionality
    private void loadClassData(int selectedRow) {
        try {
            int classId = (int) tableModel.getValueAt(selectedRow, 0);
            Class classObj = classService.getClassById(classId);
            if (classObj != null) {
                classNameField.setText(classObj.getClassName());
                sectionField.setText(classObj.getSection());
                academicYearField.setText(classObj.getAcademicYear());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading class: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadClasses() {
        try {
            List<Class> classes = classService.getAllClasses();
            tableModel.setRowCount(0);

            for (Class classObj : classes) {
                Object[] row = {
                        classObj.getClassId(),
                        classObj.getClassName(),
                        classObj.getSection(),
                        classObj.getAcademicYear()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading classes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addClass() {
        if (classNameField.getText().trim().isEmpty() || sectionField.getText().trim().isEmpty() || academicYearField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Class classObj = new Class();
            classObj.setClassName(classNameField.getText().trim());
            classObj.setSection(sectionField.getText().trim());
            classObj.setAcademicYear(academicYearField.getText().trim());

            classService.addClass(classObj);
            JOptionPane.showMessageDialog(this, "Class added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadClasses();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding class: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateClass() {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a class to update", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (classNameField.getText().trim().isEmpty() || sectionField.getText().trim().isEmpty() || academicYearField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Class classObj = new Class();
            classObj.setClassId((int) tableModel.getValueAt(selectedRow, 0));
            classObj.setClassName(classNameField.getText().trim());
            classObj.setSection(sectionField.getText().trim());
            classObj.setAcademicYear(academicYearField.getText().trim());

            classService.updateClass(classObj);
            JOptionPane.showMessageDialog(this, "Class updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadClasses();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating class: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteClass() {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a class to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this class?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                int classId = (int) tableModel.getValueAt(selectedRow, 0);
                classService.deleteClass(classId);
                JOptionPane.showMessageDialog(this, "Class deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadClasses();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting class: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        classNameField.setText("");
        sectionField.setText("");
        academicYearField.setText("");
        classTable.clearSelection();
    }
}