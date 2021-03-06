package ua.nure.kn.teteruk.usermanagment.gui;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;
import ua.nure.kn.teteruk.usermanagment.gui.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.util.Objects.isNull;

public class DetailPanel extends JPanel implements ActionListener {

    private static final int ROWS = 3;
    private static final int COLS = 2;

    private MainFrame parent;
    private User selectedUser;
    private JTextField dateOfBirthField;
    private JPanel fieldPanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JTextField firstNameField;
    private JTextField lastNameField;

    public DetailPanel(MainFrame parent, Long selectedUserId) {
        this.parent = parent;
        setUser(selectedUserId);
        initialize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
        parent.showBrowsePanel();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        add(getFieldPanel(), BorderLayout.NORTH);
        add(getButtonsPanel(), BorderLayout.SOUTH);
        setName("redactPanel");
    }

    private JPanel getFieldPanel() {
        if (isNull(fieldPanel)) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(ROWS, COLS));
            addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getDateOfBirthField());
        }
        return fieldPanel;
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    private JPanel getButtonsPanel() {
        if (isNull(buttonPanel)) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
        }
        return buttonPanel;
    }

    private JButton getOkButton() {
        if (isNull(okButton)) {
            okButton = new JButton();
            okButton.setText(Messages.getString("AddPanel.ok"));
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }

    private JTextField getFirstNameField() {
        if (isNull(firstNameField)) {
            firstNameField = new JTextField();
            firstNameField.setText(selectedUser.getFirstName());
            firstNameField.setEditable(false);
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    private JTextField getLastNameField() {
        if (isNull(lastNameField)) {
            lastNameField = new JTextField();
            lastNameField.setText(selectedUser.getLastName());
            lastNameField.setEditable(false);
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    private JTextField getDateOfBirthField() {
        if (isNull(dateOfBirthField)) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setText(selectedUser.getDateOfBirth().toString());
            dateOfBirthField.setEditable(false);
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    public void setUser(Long id) {
        try {
            selectedUser = parent.getDao().find(id);
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
