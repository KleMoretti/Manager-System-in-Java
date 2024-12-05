package GUI;

import console.DataProcessing;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Locale;


public class AddNewUserForm {
    FileBrowsingFrame fileBrowsingFrame;

    private JTextField NameJTextField;
    private JTextField PasswordJTextField;
    private JTextField PasswordInputAgainJtextField;
    private JComboBox RolecomBox;
    private JButton OKButton;
    private JButton ExitButton;
    private JLabel NameLabel;
    private JLabel PasswordWordLabel;
    private JLabel PassWordLAbelInputAgainLabel;
    private JPanel InputPAnel;
    private JPanel SelectPanel;
    private JPanel ButtonPanel;
    private JPanel OuterPanel;

    JFrame frame;

    public AddNewUserForm(FileBrowsingFrame fileBrowsingFrame) {
        this.fileBrowsingFrame = fileBrowsingFrame;
        frame = new JFrame("Add New User");

        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Exit success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                NameJTextField.setText("");
                PasswordJTextField.setText("");
                PasswordInputAgainJtextField.setText("");
                RolecomBox.setSelectedIndex(0);
            }
        });

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (NameJTextField.getText().equals("") || PasswordJTextField.getText().equals("") || PasswordInputAgainJtextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please input all information!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (!PasswordJTextField.getText().equals(PasswordInputAgainJtextField.getText())) {
                    JOptionPane.showMessageDialog(null, "The password is not the same!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null, "Add new user success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                try {
                    DataProcessing.insertUser(NameJTextField.getText(), PasswordJTextField.getText(), RolecomBox.getSelectedItem().toString());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                NameJTextField.setText("");
                PasswordJTextField.setText("");
                PasswordInputAgainJtextField.setText("");
                RolecomBox.setSelectedIndex(0);
            }
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
        OuterPanel = new JPanel();
        OuterPanel.setLayout(new GridBagLayout());
        InputPAnel = new JPanel();
        InputPAnel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(10, 5, 10, 5), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.6;
        gbc.fill = GridBagConstraints.BOTH;
        OuterPanel.add(InputPAnel, gbc);
        NameLabel = new JLabel();
        Font NameLabelFont = this.$$$getFont$$$("Century Schoolbook", -1, 18, NameLabel.getFont());
        if (NameLabelFont != null) NameLabel.setFont(NameLabelFont);
        NameLabel.setText("Name: ");
        InputPAnel.add(NameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PasswordWordLabel = new JLabel();
        Font PasswordWordLabelFont = this.$$$getFont$$$("Century Schoolbook", -1, 16, PasswordWordLabel.getFont());
        if (PasswordWordLabelFont != null) PasswordWordLabel.setFont(PasswordWordLabelFont);
        PasswordWordLabel.setText("Password: ");
        InputPAnel.add(PasswordWordLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PassWordLAbelInputAgainLabel = new JLabel();
        Font PassWordLAbelInputAgainLabelFont = this.$$$getFont$$$("Century Schoolbook", -1, 16, PassWordLAbelInputAgainLabel.getFont());
        if (PassWordLAbelInputAgainLabelFont != null)
            PassWordLAbelInputAgainLabel.setFont(PassWordLAbelInputAgainLabelFont);
        PassWordLAbelInputAgainLabel.setText("Input Again: ");
        InputPAnel.add(PassWordLAbelInputAgainLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NameJTextField = new JTextField();
        NameJTextField.setName("NameInput");
        InputPAnel.add(NameJTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PasswordJTextField = new JTextField();
        PasswordJTextField.setName("PasswordInput");
        InputPAnel.add(PasswordJTextField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PasswordInputAgainJtextField = new JTextField();
        PasswordInputAgainJtextField.setName("PasswordInputAgain");
        InputPAnel.add(PasswordInputAgainJtextField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        SelectPanel = new JPanel();
        SelectPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 10, 5, 10), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        OuterPanel.add(SelectPanel, gbc);
        OKButton = new JButton();
        OKButton.setActionCommand("OK");
        OKButton.setName("OK");
        OKButton.setText("OK");
        SelectPanel.add(OKButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ExitButton = new JButton();
        ExitButton.setName("Exit");
        ExitButton.setText("Exit");
        SelectPanel.add(ExitButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonPanel = new JPanel();
        ButtonPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(5, 20, 10, 15), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.45;
        gbc.fill = GridBagConstraints.BOTH;
        OuterPanel.add(ButtonPanel, gbc);
        RolecomBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Administrator");
        defaultComboBoxModel1.addElement("Browser");
        defaultComboBoxModel1.addElement("Operator");
        RolecomBox.setModel(defaultComboBoxModel1);
        ButtonPanel.add(RolecomBox, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return OuterPanel;
    }

    public JPanel getChangeUserPanel() {
        return OuterPanel;
    }
}
