package com.attendance.ui;

import com.attendance.model.Teacher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cards;
    private SidebarPanel sidebar;
    private JPanel contentArea;
    private CardLayout contentLayout;
    private Teacher teacher;
    private StudentPanel studentPanel;
    private ClassPanel classPanel;
    private AttendancePanel attendancePanel;
    private ReportPanel reportPanel;

    // Cool blue color palette
    private final Color DARK_BLUE = new Color(28, 40, 65);
    private final Color MEDIUM_BLUE = new Color(42, 72, 117);
    private final Color ACCENT_BLUE = new Color(71, 125, 190);
    private final Color LIGHT_BLUE = new Color(185, 215, 255);
    //private final Color HIGHLIGHT_BLUE = new Color(93, 173, 226);

    public MainFrame(Teacher teacher) {
        super("PresenTrack");
        this.teacher = teacher;
        
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        
        // Initialize main card layout
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.add(createLoginPanel(), "Login");
        getContentPane().add(cards);
    }

    private JPanel createLoginPanel() {
        // This would be your actual login panel
        // For now, just create a placeholder that will immediately show the main UI
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BLUE);
        
        JLabel loginLabel = new JLabel("Login panel would be here");
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setFont(new Font("Red Hat Display", Font.BOLD, 18));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(loginLabel, BorderLayout.CENTER);
        
        // Simulate successful login
        SwingUtilities.invokeLater(() -> initMainUI());
        
        return panel;
    }

    public void initMainUI() {
        cards.removeAll();
        
        // Split pane for sidebar + content
        JSplitPane split = new JSplitPane();
        split.setDividerLocation(250); // Sidebar width
        split.setEnabled(false); // Disable divider movement
        split.setDividerSize(1);
        split.setBorder(null);

        // Create sidebar panel
        sidebar = new SidebarPanel(teacher.getFirstName());
        
        // Content area with CardLayout for different panels
        contentLayout = new CardLayout();
        contentArea = new JPanel(contentLayout);
        contentArea.setBackground(new Color(240, 245, 250));

        // Initialize all panels
        studentPanel = new StudentPanel();
        classPanel = new ClassPanel();
        attendancePanel = new AttendancePanel(teacher);
        reportPanel = new ReportPanel(teacher);

        // Add all panels to content area
        contentArea.add(studentPanel, "Students");
        contentArea.add(classPanel, "Classes");
        contentArea.add(attendancePanel, "Attendance");
        contentArea.add(reportPanel, "Reports");

        // Add action listeners to sidebar buttons
        sidebar.getStudentsBtn().addActionListener(_ -> {
            contentLayout.show(contentArea, "Students");
            updateActiveButton(sidebar.getStudentsBtn());
        });

        sidebar.getClassesBtn().addActionListener(_ -> {
            contentLayout.show(contentArea, "Classes");
            updateActiveButton(sidebar.getClassesBtn());
        });

        sidebar.getAttendanceBtn().addActionListener(_ -> {
            contentLayout.show(contentArea, "Attendance");
            updateActiveButton(sidebar.getAttendanceBtn());
        });

        sidebar.getReportsBtn().addActionListener(_ -> {
            contentLayout.show(contentArea, "Reports");
            updateActiveButton(sidebar.getReportsBtn());
        });

        sidebar.getProfileBtn().addActionListener(_ -> {
            showProfileDialog();
        });

        sidebar.getLogoutBtn().addActionListener(_ -> {
            logout();
        });

        split.setLeftComponent(sidebar);
        split.setRightComponent(contentArea);
        
        // Create main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient background - cool blue to slightly lighter blue
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(22, 50, 92),
                    getWidth(), getHeight(), new Color(43, 88, 150)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add subtle pattern overlay
                g2d.setColor(new Color(255, 255, 255, 10));
                for (int i = 0; i < getHeight(); i += 4) {
                    g2d.drawLine(0, i, getWidth(), i);
                }
            }
        };
        
        mainPanel.add(split, BorderLayout.CENTER);
        cards.add(mainPanel);
        
        // Show students panel by default and highlight the button
        contentLayout.show(contentArea, "Students");
        updateActiveButton(sidebar.getStudentsBtn());
        
        revalidate();
        repaint();
    }
    
    private void updateActiveButton(JButton activeButton) {
        // Reset all buttons
        sidebar.getStudentsBtn().setBackground(DARK_BLUE);
        sidebar.getClassesBtn().setBackground(DARK_BLUE);
        sidebar.getAttendanceBtn().setBackground(DARK_BLUE);
        sidebar.getReportsBtn().setBackground(DARK_BLUE);
        
        // Highlight active button
        activeButton.setBackground(ACCENT_BLUE);
    }

    private void showProfileDialog() {
        JDialog profileDialog = new JDialog(this, "Profile", true);
        profileDialog.setSize(500, 400);
        profileDialog.setLocationRelativeTo(this);
        profileDialog.setUndecorated(true);
        
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient background for profile dialog
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(42, 72, 117),
                    getWidth(), getHeight(), new Color(71, 125, 190)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add subtle pattern
                g2d.setColor(new Color(255, 255, 255, 15));
                for (int i = 0; i < getHeight(); i += 5) {
                    g2d.drawLine(0, i, getWidth(), i);
                }
            }
        };
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 80), 1));
        
        // Top panel with close button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 24));
        closeButton.setForeground(Color.WHITE);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(_ -> profileDialog.dispose());
        topPanel.add(closeButton);
        
        // Profile content panel
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        profilePanel.setBackground(Color.WHITE);
        
        // Title with avatar
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        
        // Circle avatar with initials
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(ACCENT_BLUE);
                g2d.fillOval(0, 0, 60, 60);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Red Hat Display", Font.BOLD, 24));
                String initials = String.valueOf(teacher.getFirstName().charAt(0)) + 
                                 String.valueOf(teacher.getLastName().charAt(0));
                FontMetrics fm = g2d.getFontMetrics();
                int stringWidth = fm.stringWidth(initials);
                int stringHeight = fm.getAscent();
                g2d.drawString(initials, (60 - stringWidth)/2, (60 - stringHeight)/2 + stringHeight);
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(60, 60);
            }
        };
        
        titlePanel.add(avatarPanel);
        
        JLabel titleLabel = new JLabel("Teacher Profile");
        titleLabel.setFont(new Font("Red Hat Display", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profilePanel.add(titlePanel);
        profilePanel.add(titleLabel);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Profile fields with modern styling
        profilePanel.add(createProfileField("Username:", teacher.getUsername()));
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profilePanel.add(createProfileField("First Name:", teacher.getFirstName()));
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profilePanel.add(createProfileField("Last Name:", teacher.getLastName()));
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profilePanel.add(createProfileField("Email:", teacher.getEmail()));
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profilePanel.add(createProfileField("Phone:", teacher.getPhone()));
        profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Logout button with modern styling
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Red Hat Display", Font.BOLD, 16));
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(200, 40));
        
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(203, 67, 53));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(new Color(231, 76, 60));
            }
        });
        
        logoutButton.addActionListener(_ -> {
            profileDialog.dispose();
            logout();
        });
        
        profilePanel.add(logoutButton);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(profilePanel, BorderLayout.CENTER);
        profileDialog.add(mainPanel);
        profileDialog.setVisible(true);
    }
    
    private JPanel createProfileField(String label, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(400, 50));
        
        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(new Font("Red Hat Display", Font.BOLD, 12));
        labelLbl.setForeground(new Color(100, 100, 100));
        
        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Red Hat Display", Font.PLAIN, 16));
        
        panel.add(labelLbl);
        panel.add(valueLbl);
        
        return panel;
    }
    
    private void logout() {
        dispose();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
    
    // Inner class for SidebarPanel
    private class SidebarPanel extends JPanel {
        private JButton studentsBtn, classesBtn, attendanceBtn, reportsBtn, profileBtn, logoutBtn;
        
        public SidebarPanel(String teacherName) {
            setLayout(new BorderLayout());
            setBackground(DARK_BLUE);
            setPreferredSize(new Dimension(250, getHeight()));
            
            // Top panel with logo and welcome message
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(new Color(22, 36, 59));
            topPanel.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
            
            // App logo/name
            JLabel appLabel = new JLabel("PresenTrack");
            appLabel.setFont(new Font("Red Hat Display", Font.BOLD, 18));
            appLabel.setForeground(Color.WHITE);
            
            // Teacher welcome
            JLabel welcomeLabel = new JLabel("Welcome, " + teacherName);
            welcomeLabel.setFont(new Font("Red Hat Display", Font.PLAIN, 14));
            welcomeLabel.setForeground(LIGHT_BLUE);
            
            JPanel namePanel = new JPanel(new BorderLayout());
            namePanel.setOpaque(false);
            namePanel.add(appLabel, BorderLayout.NORTH);
            namePanel.add(welcomeLabel, BorderLayout.SOUTH);
            
            topPanel.add(namePanel, BorderLayout.CENTER);
            
            // Center panel with buttons
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBackground(DARK_BLUE);
            centerPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 20, 0));
            
            // Navigation section label
            JLabel navLabel = new JLabel("NAVIGATION");
            navLabel.setFont(new Font("Red Hat Display", Font.BOLD, 12));
            navLabel.setForeground(new Color(120, 145, 170));
            navLabel.setBorder(new EmptyBorder(0, 20, 10, 0));
            navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            centerPanel.add(navLabel);
            
            studentsBtn = createSidebarButton("Students", "👥");
            classesBtn = createSidebarButton("Classes", "📚");
            attendanceBtn = createSidebarButton("Attendance", "✓");
            reportsBtn = createSidebarButton("Reports", "📊");
            
            centerPanel.add(studentsBtn);
            centerPanel.add(classesBtn);
            centerPanel.add(attendanceBtn);
            centerPanel.add(reportsBtn);
            centerPanel.add(Box.createVerticalGlue());
            
            // Bottom panel with profile and logout
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
            bottomPanel.setBackground(DARK_BLUE);
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            
            // Settings section label
            JLabel settingsLabel = new JLabel("USER");
            settingsLabel.setFont(new Font("Red Hat Display", Font.BOLD, 12));
            settingsLabel.setForeground(new Color(120, 145, 170));
            settingsLabel.setBorder(new EmptyBorder(0, 20, 10, 0));
            settingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            bottomPanel.add(settingsLabel);
            
            profileBtn = createSidebarButton("Profile", "👤");
            logoutBtn = createSidebarButton("Logout", "🚪");
            
            bottomPanel.add(profileBtn);
            bottomPanel.add(logoutBtn);
            
            add(topPanel, BorderLayout.NORTH);
            add(centerPanel, BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);
        }
        
        private JButton createSidebarButton(String text, String icon) {
            JButton button = new JButton("  " + icon + "  " + text);
            button.setFont(new Font("Red Hat Display", Font.PLAIN, 16));
            button.setForeground(Color.WHITE);
            button.setBackground(DARK_BLUE);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (button.getBackground() != ACCENT_BLUE) {
                        button.setBackground(MEDIUM_BLUE);
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (button.getBackground() != ACCENT_BLUE) {
                        button.setBackground(DARK_BLUE);
                    }
                }
            });
            
            return button;
        }
        
        public JButton getStudentsBtn() { return studentsBtn; }
        public JButton getClassesBtn() { return classesBtn; }
        public JButton getAttendanceBtn() { return attendanceBtn; }
        public JButton getReportsBtn() { return reportsBtn; }
        public JButton getProfileBtn() { return profileBtn; }
        public JButton getLogoutBtn() { return logoutBtn; }
    }
}