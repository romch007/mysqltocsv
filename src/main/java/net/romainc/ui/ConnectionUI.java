package net.romainc.ui;

import net.romainc.dao.DAO;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;

public class ConnectionUI extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel title;
    private JTextField hostInput;
    private JTextField userInput;
    private JTextField schemaInput;

    public ConnectionUI() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400, 200);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);


        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setResizable(false);
        setVisible(true);
    }

    private void onOK() {
        // add your code here

        submit();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
        System.exit(0);
    }

    private void submit() {
        String host = hostInput.getText();
        String user = userInput.getText();
        String schema = schemaInput.getText();

        DAO dao = new DAO(host, user, schema);
    }
}
