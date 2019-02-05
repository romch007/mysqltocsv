package net.romainc.ui;

import net.romainc.dao.DAO;

import javax.swing.*;
import java.awt.event.*;

public class ConnectionUI extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField hostInput;
    private JTextField userInput;
    private JTextField schemaInput;
    private JTextField tableInput;
    private JTextField passwordInput;
    private JComboBox modeCombo;

    public ConnectionUI() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400, 250);
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
        modeCombo.addActionListener(e -> {
            if (modeCombo.getSelectedIndex() == 0) {
                tableInput.setEnabled(false);
                tableInput.setEditable(false);
            } else {
                tableInput.setEnabled(true);
                tableInput.setEditable(true);
            }
        });
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
        String table = tableInput.getText();
        String password = passwordInput.getText();
        int mode = modeCombo.getSelectedIndex();

        DAO dao = new DAO(host, user, password, schema, table, mode == 1);
    }
}
