package gui;

import console.DataProcessing;
import console.DatabasePool;
import console.DocClient;
import console.ResultSetData;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ChangeUserFrame {
    FileBrowsingFrame fileBrowsingFrame;

    private JComboBox nameComboBox;
    private JComboBox roleComboBox;
    private JPasswordField passwordField;
    private JLabel NameJLabel;
    private JLabel PasswordJLabel;
    private JLabel RoleLabel;
    private JButton oKButton;
    private JButton exitButton;
    private JPanel OuterJPanel;
    private JPanel ChangeJPanel;
    private JPanel ButtonJPanel;

    JFrame frame;


    public ChangeUserFrame(FileBrowsingFrame fileBrowsingFrame) {
        this.fileBrowsingFrame = fileBrowsingFrame;
        initializeUI();
    }

    private void initializeUI() {
        $$$setupUI$$$();
        // 初始化用户名组合框
        CompletableFuture.runAsync(this::populateNameComboBox);

        // 设置OK按钮的动作监听器
        oKButton.addActionListener(e -> updateUserInfo());
    }

    private void populateNameComboBox() {
        try {
            fileBrowsingFrame.mainFrame.client.sendMessage("CLIENT>>> LIST_USERS");
            String response = fileBrowsingFrame.mainFrame.client.receiveMessage().join().toString();
            if (!"LIST_USER_SUCCESS".equals(response)) {
                System.err.println("Failed to list users");
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(frame, "Failed to refresh user list.", "Error", JOptionPane.ERROR_MESSAGE)
                );
                return;
            }

            Object responses = fileBrowsingFrame.mainFrame.client.receiveMessage().join();
            if (responses instanceof ResultSetData) {
                ResultSetData resultSetData = (ResultSetData) responses;
                // 假设用户信息包含 'username'
                List<String[]> data = resultSetData.getData();

                // 在 EDT 中更新 UI 组件
                SwingUtilities.invokeLater(() -> {
                    nameComboBox.removeAllItems();
                    for (String[] row : data) {
                        // 假设每一行只有一个元素，即用户名
                        nameComboBox.addItem(row[0]);
                    }
                });

            } else {
                System.err.println("Unexpected response format from server.");
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(frame, "Unexpected response format from server.", "Error", JOptionPane.ERROR_MESSAGE)
                );
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUserInfo() {
        String name = (String) nameComboBox.getSelectedItem();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (name == null || name.isEmpty() || password == null || password.isEmpty() || role == null || role.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Use actual server address and port
        try {

            fileBrowsingFrame.mainFrame.client.sendMessage("CLIENT>>> UPDATE_USER " + name + " " + password + " " + role);
            String response = fileBrowsingFrame.mainFrame.client.receiveMessage().join().toString();
            if ("UPDATE_SUCCESS".equals(response)) {
                JOptionPane.showMessageDialog(frame, "User information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Update user success!");
                resetFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to update user information.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to connect to server.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        passwordField.setText("");
        nameComboBox.setSelectedIndex(0);
        roleComboBox.setSelectedIndex(0);
    }

    public void show() {
        frame.setContentPane(OuterJPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // 居中显示
        frame.setVisible(true);
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
        nameComboBox = new JComboBox();
        ChangeJPanel.add(nameComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        roleComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("administrator");
        defaultComboBoxModel1.addElement("browser");
        defaultComboBoxModel1.addElement("operator");
        roleComboBox.setModel(defaultComboBoxModel1);
        ChangeJPanel.add(roleComboBox, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        oKButton = new JButton();
        oKButton.setText("OK");
        ButtonJPanel.add(oKButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
