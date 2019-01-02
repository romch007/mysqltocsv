package net.romainc.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformationUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel title;
    private JLabel text;

    public InformationUI(String title, String text, String type) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        setTitle(title);
        this.text.setText(text);
        switch (type) {
            case "error":
                setResizable(true);
                break;
            case "info":
                setResizable(false);
                break;
        }
        setSize(100, 120);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
