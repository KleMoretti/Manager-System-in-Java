package gui;

import console.DataProcessing;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.sql.SQLException;
import java.util.Locale;


/**
 * @author 贾智超
 */
public class AddNewUserForm {
    FileBrowsingFrame fileBrowsingFrame;

    private JTextField nameJTextField;
    private JTextField passwordJTextField;
    private JTextField passwordInputAgainJtextField;
    private JComboBox rolecomBox;
    private JButton okbutton;
    private JButton exitButton;
    private JLabel nameLabel;
    private JLabel passwordWordLabel;
    private JLabel passWordLAbelInputAgainLabel;
    private JPanel inputpanel;
    private JPanel selectPanel;
    private JPanel buttonPanel;
    private JPanel outerPanel;

    JFrame frame;

    public AddNewUserForm(FileBrowsingFrame fileBrowsingFrame) {
        this.fileBrowsingFrame = fileBrowsingFrame;
        frame = new JFrame("Add New User");

        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Exit success!", "Success", JOptionPane.INFORMATION_MESSAGE);
            nameJTextField.setText("");
            passwordJTextField.setText("");
            passwordInputAgainJtextField.setText("");
            rolecomBox.setSelectedIndex(0);
        });

        okbutton.addActionListener(e -> {
            if (nameJTextField.getText().isEmpty() || passwordJTextField.getText().isEmpty() || passwordInputAgainJtextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please input all information!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (!passwordJTextField.getText().equals(passwordInputAgainJtextField.getText())) {
                JOptionPane.showMessageDialog(null, "The password is not the same!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Add new user success!", "Success", JOptionPane.INFORMATION_MESSAGE);
            try {
                DataProcessing.insertUser(nameJTextField.getText(), passwordJTextField.getText(), rolecomBox.getSelectedItem().toString());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            nameJTextField.setText("");
            passwordJTextField.setText("");
            passwordInputAgainJtextField.setText("");
            rolecomBox.setSelectedIndex(0);
        });
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        outerPanel = new JPanel();
        outerPanel.setLayout(new GridBagLayout());
        inputpanel = new JPanel();
        inputpanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(10, 15, 10, 10), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.6;
        gbc.fill = GridBagConstraints.BOTH;
        outerPanel.add(inputpanel, gbc);
        nameLabel = new JLabel();
        Font NameLabelFont = this.$$$getFont$$$("Century Schoolbook", -1, 18, nameLabel.getFont());
        if (NameLabelFont != null) nameLabel.setFont(NameLabelFont);
        nameLabel.setText("Name: ");
        inputpanel.add(nameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordWordLabel = new JLabel();
        Font PasswordWordLabelFont = this.$$$getFont$$$("Century Schoolbook", -1, 16, passwordWordLabel.getFont());
        if (PasswordWordLabelFont != null) passwordWordLabel.setFont(PasswordWordLabelFont);
        passwordWordLabel.setText("Password: ");
        inputpanel.add(passwordWordLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passWordLAbelInputAgainLabel = new JLabel();
        Font PassWordLAbelInputAgainLabelFont = this.$$$getFont$$$("Century Schoolbook", -1, 16, passWordLAbelInputAgainLabel.getFont());
        if (PassWordLAbelInputAgainLabelFont != null)
            passWordLAbelInputAgainLabel.setFont(PassWordLAbelInputAgainLabelFont);
        passWordLAbelInputAgainLabel.setText("Input Again: ");
        inputpanel.add(passWordLAbelInputAgainLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameJTextField = new JTextField();
        nameJTextField.setName("NameInput");
        inputpanel.add(nameJTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordJTextField = new JTextField();
        passwordJTextField.setName("PasswordInput");
        inputpanel.add(passwordJTextField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordInputAgainJtextField = new JTextField();
        passwordInputAgainJtextField.setName("PasswordInputAgain");
        inputpanel.add(passwordInputAgainJtextField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        selectPanel = new JPanel();
        selectPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 10, 5, 10), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        outerPanel.add(selectPanel, gbc);
        okbutton = new JButton();
        okbutton.setActionCommand("OK");
        okbutton.setName("OK");
        okbutton.setText("OK");
        selectPanel.add(okbutton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setName("Exit");
        exitButton.setText("Exit");
        selectPanel.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(5, 20, 10, 15), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.45;
        gbc.fill = GridBagConstraints.BOTH;
        outerPanel.add(buttonPanel, gbc);
        rolecomBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("administrator");
        defaultComboBoxModel1.addElement("browser");
        defaultComboBoxModel1.addElement("operator");
        rolecomBox.setModel(defaultComboBoxModel1);
        buttonPanel.add(rolecomBox, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return outerPanel;
    }

    public JPanel getChangeUserPanel() {
        return outerPanel;
    }
}