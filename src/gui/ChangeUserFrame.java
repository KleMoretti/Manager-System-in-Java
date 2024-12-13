package gui;

import console.DataProcessing;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class ChangeUserFrame {
    FileBrowsingFrame fileBrowsingFrame;

    private JComboBox NamecomboBox;
    private JComboBox RolecomboBox;
    private JPasswordField passwordField;
    private JLabel NameJLabel;
    private JLabel PasswordJLabel;
    private JLabel RoleLabel;
    private JButton OKButton;
    private JButton exitButton;
    private JPanel OuterJPanel;
    private JPanel ChangeJPanel;
    private JPanel ButtonJPanel;

    String name;
    String password;
    String role;
    JFrame frame;


    public ChangeUserFrame(FileBrowsingFrame fileBrowsingFrame) {
        this.fileBrowsingFrame = fileBrowsingFrame;

        frame = new JFrame("ChangeUserFrame");

        try {
            show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = (String) NamecomboBox.getSelectedItem();
                password = passwordField.getText();
                role = RolecomboBox.getSelectedItem().toString();
                try {
                    DataProcessing.updateUser(name, password, role);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Change successfully!", "Change", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Exit successfully!", "Exit", JOptionPane.INFORMATION_MESSAGE);
                passwordField.setText("");
                NamecomboBox.setSelectedIndex(0);
                RolecomboBox.setSelectedIndex(0);
            }
        });
    }

    public void show() throws SQLException {
        ResultSet rs = DataProcessing.listUser();
        while (rs.next()) {
            NamecomboBox.addItem(rs.getString("username"));
        }
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
        OuterJPanel = new JPanel();
        OuterJPanel.setLayout(new GridBagLayout());
        ChangeJPanel = new JPanel();
        ChangeJPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(10, 10, 5, 10), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.5;
        gbc.fill = GridBagConstraints.BOTH;
        OuterJPanel.add(ChangeJPanel, gbc);
        NameJLabel = new JLabel();
        Font NameJLabelFont = this.$$$getFont$$$("Century Schoolbook", -1, 16, NameJLabel.getFont());
        if (NameJLabelFont != null) NameJLabel.setFont(NameJLabelFont);
        NameJLabel.setText("Name");
        ChangeJPanel.add(NameJLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PasswordJLabel = new JLabel();
        Font PasswordJLabelFont = this.$$$getFont$$$("Century Schoolbook", -1, 16, PasswordJLabel.getFont());
        if (PasswordJLabelFont != null) PasswordJLabel.setFont(PasswordJLabelFont);
        PasswordJLabel.setText("Password");
        ChangeJPanel.add(PasswordJLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RoleLabel = new JLabel();
        Font RoleLabelFont = this.$$$getFont$$$("Century Schoolbook", -1, 16, RoleLabel.getFont());
        if (RoleLabelFont != null) RoleLabel.setFont(RoleLabelFont);
        RoleLabel.setText("Role");
        ChangeJPanel.add(RoleLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NamecomboBox = new JComboBox();
        ChangeJPanel.add(NamecomboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RolecomboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("administrator");
        defaultComboBoxModel1.addElement("browser");
        defaultComboBoxModel1.addElement("operator");
        RolecomboBox.setModel(defaultComboBoxModel1);
        ChangeJPanel.add(RolecomboBox, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordField = new JPasswordField();
        ChangeJPanel.add(passwordField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ButtonJPanel = new JPanel();
        ButtonJPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 10, 5, 10), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        OuterJPanel.add(ButtonJPanel, gbc);
        OKButton = new JButton();
        OKButton.setText("OK");
        ButtonJPanel.add(OKButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Exit");
        ButtonJPanel.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return OuterJPanel;
    }

    public JPanel getChangeUserPanel() {
        return OuterJPanel;
    }
}