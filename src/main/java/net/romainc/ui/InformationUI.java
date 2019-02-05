package net.romainc.ui;

import net.romainc.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformationUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel title;
    private JLabel text;

    /**
     * Create a information or error popup
     * @param title The title of the popup
     * @param text The text of the popup
     * @param type The type (static in Variables class)
     */
    public InformationUI(String title, String text, String type) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        setTitle(title);
        this.text.setText(text);
        switch (type) {
            case Variables.UIError:
                setResizable(true);
                break;
            case Variables.UIInfo:
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
