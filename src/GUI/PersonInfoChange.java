package GUI;

import console.AbstractUser;
import console.DataProcessing;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Locale;

public class PersonInfoChange {
    FileBrowsingFrame fileBrowsingFrame;

    private JPanel OuterPanel;
    private JPanel InnerPanel;
    private JLabel Title;
    private JTextField userNameInputField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JButton OK;
    private JButton No;
    private JPanel buttonJPanel;
    private JRadioButton administorButton;
    private JRadioButton browserButton;
    private JRadioButton operatorButton;

    String passwordDefaultPesonChange;
    String passwordPersonChangeFirst;
    String passwordPersonChangeSecond;
    String rolePersonChange;

    public JPanel getOuterPanel() {
        return OuterPanel;
    }

    public PersonInfoChange(FileBrowsingFrame fileBrowsingFrame) {
        this.fileBrowsingFrame = fileBrowsingFrame;

        $$$setupUI$$$();

        passwordField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case KeyEvent.VK_ENTER:
                        passwordField1.transferFocus();
                        break;
                    default:
                        break;
                }
            }
        });
        passwordField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case KeyEvent.VK_ENTER:
                        passwordField2.transferFocus();
                        break;
                    default:
                        break;
                }
            }
        });
        passwordField3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case KeyEvent.VK_ENTER:
                        passwordField3.transferFocus();
                        break;
                    default:
                        break;
                }
            }
        });

        OK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passwordDefaultPesonChange = new String(passwordField1.getPassword());
                passwordPersonChangeFirst = new String(passwordField2.getPassword());
                passwordPersonChangeSecond = new String(passwordField3.getPassword());
                try {
                    if (passwordPersonChangeFirst.compareTo(passwordPersonChangeSecond) == 0) {
                        DataProcessing.updateUser(fileBrowsingFrame.currentUser.getName(), passwordPersonChangeFirst, fileBrowsingFrame.currentUser.getRole());
                        ChangeInfoSuccessDialog();
                    } else if (DataProcessing.searchUser(fileBrowsingFrame.currentUser.getName(), passwordDefaultPesonChange) == null) {
                        matchDataFalsePersonDialog();
                    } else {
                        nullDataPersonMatchFalseDialog();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }

    public void ChangeInfoSuccessDialog() {
        // 创建登录成功对话框
        JDialog ChangerInfoSuccessDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(OuterPanel), "Change Success", true); // 模态对话框
        ChangerInfoSuccessDialog.setLayout(new BorderLayout());
        ChangerInfoSuccessDialog.setSize(300, 150);
        ChangerInfoSuccessDialog.setLocationRelativeTo(null); // 居中显示

        JLabel messageLabel = new JLabel("Change Info successful!", SwingConstants.CENTER);
        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.BOLD, 20f));
        ChangerInfoSuccessDialog.add(messageLabel, BorderLayout.CENTER);


        // 设置定时器，在2秒后自动关闭对话框
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangerInfoSuccessDialog.dispose(); // 关闭对话框
                passwordField1.setText("");
                passwordField2.setText("");
                passwordField3.setText("");
            }
        });
        // 只执行一次
        timer.setRepeats(false);
        timer.start();
        // 显示对话框
        ChangerInfoSuccessDialog.setVisible(true);
    }

    public void matchDataFalsePersonDialog() {
        JButton errorReturnButton = new JButton("OK");
        JFrame tempframe = new JFrame();
        Dialog dialog = new Dialog(tempframe, "Error", true);
        dialog.add(new JLabel("Invalid username or password!"), BorderLayout.CENTER);
        dialog.add(errorReturnButton, BorderLayout.SOUTH);
        dialog.setSize(200, 100);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  //获取到屏幕尺寸
        dialog.setLocation((int) ((screenSize.getWidth() - dialog.getWidth()) / 2), // 使用对话框本身的宽度
                (int) ((screenSize.getHeight() - dialog.getHeight()) / 2) // 使用对话框本身的高度
        );
        errorReturnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.setVisible(false);
                userNameInputField.setText("");
                passwordField1.setText("");
                passwordField2.setText("");
                passwordField3.setText("");
            }
        });
        dialog.setVisible(true);
    }

    public void matchPasswordFalsePersonDialog() {
        JButton errorReturnButton = new JButton("OK");
        JFrame tempframe = new JFrame();
        Dialog dialog = new Dialog(tempframe, "Error", true);
        dialog.add(new JLabel("Can't match new password!"), BorderLayout.CENTER);
        dialog.add(errorReturnButton, BorderLayout.SOUTH);
        dialog.setSize(200, 100);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  //获取到屏幕尺寸
        dialog.setLocation((int) ((screenSize.getWidth() - dialog.getWidth()) / 2), // 使用对话框本身的宽度
                (int) ((screenSize.getHeight() - dialog.getHeight()) / 2) // 使用对话框本身的高度
        );
        errorReturnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.setVisible(false);
                passwordField2.setText("");
                passwordField3.setText("");
            }
        });
        dialog.setVisible(true);
    }

    public void nullDataPersonMatchFalseDialog() {
        JButton errorReturnButton = new JButton("OK");
        JFrame tempframe = new JFrame();
        Dialog dialog = new Dialog(tempframe, "Error", true);
        dialog.add(new JLabel("Having Null Data!"), BorderLayout.CENTER);
        dialog.add(errorReturnButton, BorderLayout.SOUTH);
        dialog.setSize(200, 100);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  //获取到屏幕尺寸
        dialog.setLocation((int) ((screenSize.getWidth() - dialog.getWidth()) / 2), // 使用对话框本身的宽度
                (int) ((screenSize.getHeight() - dialog.getHeight()) / 2) // 使用对话框本身的高度
        );
        errorReturnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.setVisible(false);
                userNameInputField.setText("");
                passwordField1.setText("");
                passwordField2.setText("");
                passwordField3.setText("");
            }
        });
        dialog.setVisible(true);
    }

    void updateUserName(AbstractUser user) {
        userNameInputField.setText(user.getName());
        switch (user.getRole()) {
            case "administrator":
                administorButton.setSelected(true);
                break;
            case "browser":
                browserButton.setSelected(true);
                break;
            case "operator":
                operatorButton.setSelected(true);
                break;
            default:
                break;
        }
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
        OuterPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        InnerPanel = new JPanel();
        InnerPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(5, 0, 0, 0), -1, -1));
        OuterPanel.add(InnerPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        Title = new JLabel();
        Font TitleFont = this.$$$getFont$$$(null, -1, 24, Title.getFont());
        if (TitleFont != null) Title.setFont(TitleFont);
        Title.setText("Personal Info Change");
        InnerPanel.add(Title, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(5, 30, 5, 40), -1, -1));
        OuterPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Input Again: ");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Origin Password:");
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("New Password: ");
        panel1.add(label3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordField1 = new JPasswordField();
        panel1.add(passwordField1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField2 = new JPasswordField();
        panel1.add(passwordField2, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField3 = new JPasswordField();
        panel1.add(passwordField3, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 20, 10, 20), -1, -1));
        OuterPanel.add(buttonJPanel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        OK = new JButton();
        OK.setText("Done");
        buttonJPanel.add(OK, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), null, 0, false));
        No = new JButton();
        No.setText("Exit");
        buttonJPanel.add(No, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

}
