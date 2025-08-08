package com.attendance.ui;

import com.attendance.model.Teacher;
import com.attendance.service.TeacherService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignUpFrame extends JFrame {
    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;
    private RoundedPasswordField confirmPasswordField;
    private RoundedTextField firstNameField;
    private RoundedTextField lastNameField;
    private RoundedTextField emailField;
    private RoundedTextField phoneField;
    private TeacherService teacherService;

    public SignUpFrame() {
        Font titleFont = new Font("Red Hat Display", Font.PLAIN, 35);
        Font labelFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 15);
        Font buttonFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Color fieldForeground = new Color(26, 46, 53);
        
        teacherService = new TeacherService();
        
        setTitle("Teacher Sign Up");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(70, 130, 180),
                    getWidth(), getHeight(), new Color(135, 206, 235)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add decorative circles
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(getWidth() - 150, 50, 100, 100);
                g2d.fillOval(50, getHeight() - 100, 80, 80);
                g2d.fillOval(200, 100, 120, 120);
            }
        };
        mainPanel.setLayout(null);
        
        // Close button
        RoundedButton closeButton = new RoundedButton("×", 20);
        closeButton.setBounds(850, 10, 40, 40);
        closeButton.setFont(new Font("Arial", Font.BOLD, 24));
        closeButton.setBackground(new Color(255, 255, 255, 100));
        closeButton.setForeground(Color.WHITE);
        closeButton.setHoverBackgroundColor(new Color(255, 255, 255, 150));
        closeButton.addActionListener(_ -> dispose());
        mainPanel.add(closeButton);
        
        // Sign Up card panel
        JPanel signUpCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        signUpCard.setBounds(50, 50, 800, 600);
        signUpCard.setLayout(null);
        signUpCard.setOpaque(false);
        
        // Title
        JLabel titleLabel = new JLabel("Teacher Sign Up");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(50, 30, 700, 46);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        signUpCard.add(titleLabel);
        
        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setBounds(50, 100, 120, 30);
        signUpCard.add(usernameLabel);
        
        usernameField = new RoundedTextField(20);
        usernameField.setBounds(50, 130, 300, 45);
        usernameField.setBackground(new Color(173, 216, 230));
        usernameField.setFont(fieldFont);
        signUpCard.add(usernameField);
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setBounds(50, 190, 120, 30);
        signUpCard.add(passwordLabel);
        
        passwordField = new RoundedPasswordField(20);
        passwordField.setBounds(50, 220, 300, 45);
        passwordField.setBackground(new Color(173, 216, 230));
        passwordField.setFont(fieldFont);
        signUpCard.add(passwordField);
        
        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(labelFont);
        confirmPasswordLabel.setBounds(50, 280, 150, 30);
        signUpCard.add(confirmPasswordLabel);
        
        confirmPasswordField = new RoundedPasswordField(20);
        confirmPasswordField.setBounds(50, 310, 300, 45);
        confirmPasswordField.setBackground(new Color(173, 216, 230));
        confirmPasswordField.setFont(fieldFont);
        signUpCard.add(confirmPasswordField);
        
        // First Name field
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(labelFont);
        firstNameLabel.setBounds(50, 370, 120, 30);
        signUpCard.add(firstNameLabel);
        
        firstNameField = new RoundedTextField(20);
        firstNameField.setBounds(50, 400, 300, 45);
        firstNameField.setBackground(new Color(173, 216, 230));
        firstNameField.setFont(fieldFont);
        signUpCard.add(firstNameField);
        
        // Last Name field
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(labelFont);
        lastNameLabel.setBounds(50, 460, 120, 30);
        signUpCard.add(lastNameLabel);
        
        lastNameField = new RoundedTextField(20);
        lastNameField.setBounds(50, 490, 300, 45);
        lastNameField.setBackground(new Color(173, 216, 230));
        lastNameField.setFont(fieldFont);
        signUpCard.add(lastNameField);
        
        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        emailLabel.setBounds(450, 100, 120, 30);
        signUpCard.add(emailLabel);
        
        emailField = new RoundedTextField(20);
        emailField.setBounds(450, 130, 300, 45);
        emailField.setBackground(new Color(173, 216, 230));
        emailField.setFont(fieldFont);
        signUpCard.add(emailField);
        
        // Phone field
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        phoneLabel.setBounds(450, 190, 120, 30);
        signUpCard.add(phoneLabel);
        
        phoneField = new RoundedTextField(20);
        phoneField.setBounds(450, 220, 300, 45);
        phoneField.setBackground(new Color(173, 216, 230));
        phoneField.setFont(fieldFont);
        signUpCard.add(phoneField);
        
        // Buttons
        RoundedButton backButton = new RoundedButton("Back", 25);
        backButton.setBounds(450, 310, 150, 50);
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(173, 216, 230));
        backButton.setForeground(fieldForeground);
        backButton.setHoverBackgroundColor(new Color(135, 206, 235));
        backButton.addActionListener(_ -> dispose());
        signUpCard.add(backButton);
        
        RoundedButton clearButton = new RoundedButton("Clear", 25);
        clearButton.setBounds(620, 310, 150, 50);
        clearButton.setFont(buttonFont);
        clearButton.setBackground(new Color(173, 216, 230));
        clearButton.setForeground(fieldForeground);
        clearButton.setHoverBackgroundColor(new Color(135, 206, 235));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        signUpCard.add(clearButton);
        
        RoundedButton signUpButton = new RoundedButton("Sign Up", 25);
        signUpButton.setBounds(450, 380, 320, 50);
        signUpButton.setFont(buttonFont);
        signUpButton.setBackground(new Color(173, 216, 230));
        signUpButton.setForeground(fieldForeground);
        signUpButton.setHoverBackgroundColor(new Color(135, 206, 235));
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });
        signUpCard.add(signUpButton);
        
        mainPanel.add(signUpCard);
        add(mainPanel);
    }

    private void signUp() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        
        // Validation
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || 
            firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create teacher object
        Teacher teacher = new Teacher();
        teacher.setUsername(username);
        teacher.setPassword(password);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        teacher.setPhone(phone);
        
        try {
            // Check if username already exists
            Teacher existingTeacher = teacherService.getTeacherByUsername(username);
            if (existingTeacher != null) {
                JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Add teacher to database
            teacherService.addTeacher(teacher);
            JOptionPane.showMessageDialog(this, "Sign up successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
}