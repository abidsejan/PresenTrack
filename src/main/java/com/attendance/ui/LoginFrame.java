package com.attendance.ui;

import com.attendance.model.Teacher;
import com.attendance.service.TeacherService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;
    private TeacherService teacherService;
    
    public LoginFrame() {
        Font titleFont = new Font("Red Hat Display", Font.PLAIN, 35);
        Font labelFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 15);
        Font buttonFont = new Font("Red Hat Display", Font.PLAIN, 18);
        //Color fieldBackground = new Color(205, 247, 229);
        Color fieldForeground = new Color(26, 46, 53);
        //Color hoverColor = new Color(140, 169, 157);
        
        teacherService = new TeacherService();
        
        setTitle("PresenTrack");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        // Main panel with custom background
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
        
        // Login card panel
        JPanel loginCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        loginCard.setBounds(225, 75, 450, 450);
        loginCard.setLayout(null);
        loginCard.setOpaque(false);
        
        // Title
        JLabel titleLabel = new JLabel("PresenTrack");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(50, 40, 350, 46);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginCard.add(titleLabel);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Welcome Back!");
        subtitleLabel.setFont(new Font("Red Hat Display", Font.PLAIN, 20));
        subtitleLabel.setBounds(50, 90, 350, 30);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setForeground(new Color(100, 100, 100));
        loginCard.add(subtitleLabel);
        
        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setBounds(50, 150, 120, 30);
        loginCard.add(usernameLabel);
        
        usernameField = new RoundedTextField(20);
        usernameField.setBounds(50, 185, 350, 45);
        usernameField.setBackground(new Color(173, 216, 230));
        usernameField.setFont(fieldFont);
        loginCard.add(usernameField);
        
        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setBounds(50, 250, 120, 30);
        loginCard.add(passwordLabel);
        
        passwordField = new RoundedPasswordField(20);
        passwordField.setBounds(50, 285, 350, 45);
        passwordField.setBackground(new Color(173, 216, 230));
        passwordField.setFont(fieldFont);
        loginCard.add(passwordField);
        
        // Login button
        RoundedButton loginButton = new RoundedButton("Login", 25);
        loginButton.setBounds(50, 360, 165, 50);
        loginButton.setFont(buttonFont);
        loginButton.setBackground(new Color(173, 216, 230)); // light blue color
        loginButton.setForeground(fieldForeground);
        loginButton.setHoverBackgroundColor(new Color(135, 206, 235)); // darker blue on hover
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Please enter username", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Please enter password", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    Teacher teacher = teacherService.authenticateTeacher(username, password);
                    if (teacher != null) {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        MainFrame mainFrame = new MainFrame(teacher);
                        mainFrame.setVisible(true);
                        dispose(); // Close login frame
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginFrame.this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        loginCard.add(loginButton);
        
        // Sign Up button
        RoundedButton signUpButton = new RoundedButton("Sign Up", 25);
        signUpButton.setBounds(235, 360, 165, 50);
        signUpButton.setFont(buttonFont);
        signUpButton.setBackground(new Color(173, 216, 230)); // Light blue
        signUpButton.setForeground(fieldForeground);
        signUpButton.setHoverBackgroundColor(new Color(135, 206, 235));
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpFrame signUpFrame = new SignUpFrame();
                signUpFrame.setVisible(true);
            }
        });
        loginCard.add(signUpButton);
        
        mainPanel.add(loginCard);
        add(mainPanel);
        
        // Add Enter key functionality
        getRootPane().setDefaultButton(loginButton);
    }
}