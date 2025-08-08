package com.attendance.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIUtils {
    // Color Scheme
    public static final Color PRIMARY_COLOR = new Color(0, 122, 255);    // Vibrant Blue
    public static final Color SECONDARY_COLOR = new Color(52, 199, 89);  // Green
    public static final Color ACCENT_COLOR = new Color(255, 59, 48);     // Red
    public static final Color BACKGROUND_COLOR = new Color(242, 242, 247); // Light Gray
    public static final Color CARD_COLOR = Color.WHITE;
    public static final Color TEXT_COLOR = new Color(28, 28, 30);       // Dark Gray
    public static final Color LIGHT_TEXT_COLOR = new Color(142, 142, 147); // Light Gray
    public static final Color FIELD_BACKGROUND_COLOR = new Color(229, 229, 234);

    // Fonts
    public static final Font TITLE_FONT = new Font("Inter", Font.BOLD, 28);
    public static final Font HEADER_FONT = new Font("Inter", Font.BOLD, 20);
    public static final Font SUBHEADER_FONT = new Font("Inter", Font.BOLD, 18);
    public static final Font BUTTON_FONT = new Font("Inter", Font.BOLD, 16);
    public static final Font LABEL_FONT = new Font("Inter", Font.PLAIN, 16);
    public static final Font TEXT_FIELD_FONT = new Font("Inter", Font.PLAIN, 16);

    // Borders
    public static final Border ROUNDED_BORDER = BorderFactory.createLineBorder(new Color(229, 229, 234), 1, true);
    public static final Border FOCUSED_BORDER = BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true);
    public static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(12, 18, 12, 18);

    // Create a styled button with hover effects
    public static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effects
        Color originalColor = bgColor;
        Color hoverColor = brightenColor(bgColor, 15);
        Color pressedColor = darkenColor(bgColor, 15);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hoverColor);
            }
        });

        return button;
    }

    // Create a rounded button
    public static JButton createRoundedButton(String text, Color bgColor, int radius) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                }
                super.paintComponent(g);
            }
        };

        button.setFont(BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effects
        Color originalColor = bgColor;
        Color hoverColor = brightenColor(bgColor, 15);
        Color pressedColor = darkenColor(bgColor, 15);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hoverColor);
            }
        });

        return button;
    }

    // Create a primary button
    public static JButton createPrimaryButton(String text) {
        return createRoundedButton(text, PRIMARY_COLOR, 25);
    }

    // Create a secondary button
    public static JButton createSecondaryButton(String text) {
        return createRoundedButton(text, SECONDARY_COLOR, 25);
    }

    // Create an accent button
    public static JButton createAccentButton(String text) {
        return createRoundedButton(text, ACCENT_COLOR, 25);
    }

    // Create a rounded text field
    public static JTextField createRoundedTextField(int radius) {
        JTextField textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                }
                super.paintComponent(g);
            }
        };

        textField.setFont(TEXT_FIELD_FONT);
        textField.setBackground(FIELD_BACKGROUND_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(PRIMARY_COLOR);
        textField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        textField.setOpaque(false);

        return textField;
    }

    // Create a rounded password field
    public static JPasswordField createRoundedPasswordField(int radius) {
        JPasswordField passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                }
                super.paintComponent(g);
            }
        };

        passwordField.setFont(TEXT_FIELD_FONT);
        passwordField.setBackground(FIELD_BACKGROUND_COLOR);
        passwordField.setForeground(TEXT_COLOR);
        passwordField.setCaretColor(PRIMARY_COLOR);
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        passwordField.setOpaque(false);

        return passwordField;
    }

    // Create a rounded combo box
    public static JComboBox<String> createRoundedComboBox(int radius) {
        JComboBox<String> comboBox = new JComboBox<String>() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                }
                super.paintComponent(g);
            }
        };

        comboBox.setFont(TEXT_FIELD_FONT);
        comboBox.setBackground(FIELD_BACKGROUND_COLOR);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        comboBox.setOpaque(false);

        return comboBox;
    }

    // Create a text field
    public static JTextField createTextField() {
        return createRoundedTextField(15);
    }

    // Create a password field
    public static JPasswordField createPasswordField() {
        return createRoundedPasswordField(15);
    }

    // Create a combo box
    public static JComboBox<String> createComboBox() {
        return createRoundedComboBox(15);
    }

    // Create a label
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    // Create a header label
    public static JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HEADER_FONT);
        label.setForeground(PRIMARY_COLOR);
        return label;
    }

    // Create a subheader label
    public static JLabel createSubheaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SUBHEADER_FONT);
        label.setForeground(SECONDARY_COLOR);
        return label;
    }

    // Create a title label
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(PRIMARY_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    // Create a card panel
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                ROUNDED_BORDER
        ));
        return panel;
    }

    // Create a rounded panel
    public static RoundedPanel createRoundedPanel(int radius, Color bgColor) {
        return new RoundedPanel(radius, bgColor);
    }

    // Helper method to brighten a color
    private static Color brightenColor(Color color, int percent) {
        int r = Math.min(255, color.getRed() + (255 - color.getRed()) * percent / 100);
        int g = Math.min(255, color.getGreen() + (255 - color.getGreen()) * percent / 100);
        int b = Math.min(255, color.getBlue() + (255 - color.getBlue()) * percent / 100);
        return new Color(r, g, b);
    }

    // Helper method to darken a color
    private static Color darkenColor(Color color, int percent) {
        int r = Math.max(0, color.getRed() - color.getRed() * percent / 100);
        int g = Math.max(0, color.getGreen() - color.getGreen() * percent / 100);
        int b = Math.max(0, color.getBlue() - color.getBlue() * percent / 100);
        return new Color(r, g, b);
    }
}

// Custom rounded panel class
class RoundedPanel extends JPanel {
    private int cornerRadius;
    private Color backgroundColor;

    public RoundedPanel(int radius, Color backgroundColor) {
        super();
        this.cornerRadius = radius;
        this.backgroundColor = backgroundColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
    }
}