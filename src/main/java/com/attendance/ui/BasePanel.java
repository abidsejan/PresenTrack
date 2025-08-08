package com.attendance.ui;

import com.attendance.util.UIUtils;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {

    public BasePanel(String title) {
        setBackground(UIUtils.BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = UIUtils.createTitleLabel(title);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
    }
}
