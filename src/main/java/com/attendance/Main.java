package com.attendance;

import com.attendance.ui.LoginFrame;
//import com.attendance.ui.SignUpFrame;
import com.attendance.dao.DatabaseConnection;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            loginFrame.setVisible(true);
            
            // Add window listener to close database connection when application exits
            loginFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    DatabaseConnection.closeConnection();
                }
            });
        });
    }
}