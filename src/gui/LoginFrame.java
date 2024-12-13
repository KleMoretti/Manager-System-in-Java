package gui;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Locale;


/**
 * @author 贾智超
 */
public class LoginFrame {
    private Main mainFrame;

    private JFrame frame;
    private JPanel OuterPanel;
    private JPanel InnerPanel_1;
    private JPanel InnerPanel_2;
    private JPanel InnerPanel_3;
    private JTextField userNametextField;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton exitButton;

    String userName;
    String userPassword;


    public LoginFrame(Main frame) {
        this.mainFrame = frame;
        initializeUI();
    }

    private void initializeUI() {

        $$$setupUI$$$();

        passwordField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userPassword = new String(passwordField1.getPassword());
                System.out.println("Password: " + userPassword);
            }
        });

        // 设置登录按钮的动作监听器
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName = userNametextField.getText();
                userPassword = new String(passwordField1.getPassword());

                try {
                    if (mainFrame.searchUser(userName, userPassword)) {
                        // 登录成功，可以显示文件浏览界面
                        loginSuccessfullDialog();
                       /* try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                                "D:\\@Java\\Object-oriented and multithreaded comprehensive experiment\\Manager System\\uploadfile\\doc.ser"))) {
                            while (true) {
                                try {
                                    Doc doc = (Doc) ois.readObject();
                                    docs.put(doc.getId(), doc);
                                } catch (EOFException ex) {
                                    break; // End of file reached
                                }
                            }
                        } catch (FileNotFoundException exx) {
                            System.err.println("File not found: " + exx.getMessage());
                        } catch (IOException | ClassNotFoundException exxx) {
                            System.err.println("Error reading file: " + exxx.getMessage());
                        }*/

                    } else {
                        // 登录失败，显示错误对话框
                        loginFalseDialog();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginFrame.this.$$$getRootComponent$$$(), "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // 设置退出按钮的动作监听器
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        userNametextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case KeyEvent.VK_ENTER:
                        userNametextField.transferFocus();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void loginFalseDialog() {
        JButton errorReturnButton = new JButton("OK");
        Dialog dialog = new Dialog(new Frame(), "Error", true);
        dialog.add(new JLabel("Invalid username or password!"), BorderLayout.CENTER);
        dialog.add(errorReturnButton, BorderLayout.SOUTH);
        dialog.setSize(200, 100);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  //获取到屏幕尺寸
        dialog.setLocation((int) ((screenSize.getWidth() - frame.getWidth()) / 2), (int) ((screenSize.getHeight() - frame.getHeight()) / 2));
        errorReturnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.setVisible(false);
                userNametextField.setText("");
                passwordField1.setText("");
            }
        });
        dialog.setVisible(true);

    }

    public void loginSuccessfullDialog() {
        // 创建登录成功对话框
        JDialog successDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(OuterPanel), "Login Success", true); // 模态对话框
        successDialog.setLayout(new BorderLayout());
        successDialog.setSize(300, 150);
        successDialog.setLocationRelativeTo(null); // 居中显示

        JLabel messageLabel = new JLabel("Login successful!", SwingConstants.CENTER);
        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.BOLD, 20f));
        successDialog.add(messageLabel, BorderLayout.CENTER);

        // 设置定时器，在2秒后自动关闭对话框
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                successDialog.dispose(); // 关闭对话框
                mainFrame.closeLoginFrame(); // 关闭登录界面
                mainFrame.loginSuccess(userName, userPassword);
            }
        });
        // 只执行一次
        timer.setRepeats(false);
        timer.start();
        // 显示对话框
        successDialog.setVisible(true);
    }

    void show() {
        frame = new JFrame("LoginFrame");
        frame.setContentPane(OuterPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  //获取到屏幕尺寸
        frame.setLocation((int) ((screenSize.getWidth() - frame.getWidth()) / 2), (int) ((screenSize.getHeight() - frame.getHeight()) / 2));
        frame.pack();
        frame.setVisible(true);
    }


    public void dispose() {
        frame.dispose();
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
        OuterPanel.setLayout(new BorderLayout(0, 0));
        InnerPanel_1 = new JPanel();
        InnerPanel_1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        InnerPanel_1.setOpaque(false);
        InnerPanel_1.setPreferredSize(new Dimension(148, 80));
        OuterPanel.add(InnerPanel_1, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-1442564));
        Font label1Font = this.$$$getFont$$$(null, -1, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16777216));
        label1.setText("File Manager System");
        InnerPanel_1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        InnerPanel_2 = new JPanel();
        InnerPanel_2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 50, 0, 50), -1, -1));
        OuterPanel.add(InnerPanel_2, BorderLayout.CENTER);
        userNametextField = new JTextField();
        InnerPanel_2.add(userNametextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Password：");
        InnerPanel_2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordField1 = new JPasswordField();
        InnerPanel_2.add(passwordField1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("User name：");
        InnerPanel_2.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        InnerPanel_3 = new JPanel();
        InnerPanel_3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(20, 50, 20, 50), -1, -1));
        OuterPanel.add(InnerPanel_3, BorderLayout.SOUTH);
        exitButton = new JButton();
        Font exitButtonFont = this.$$$getFont$$$(null, -1, 18, exitButton.getFont());
        if (exitButtonFont != null) exitButton.setFont(exitButtonFont);
        exitButton.setText("Exit");
        InnerPanel_3.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loginButton = new JButton();
        Font loginButtonFont = this.$$$getFont$$$(null, -1, 18, loginButton.getFont());
        if (loginButtonFont != null) loginButton.setFont(loginButtonFont);
        loginButton.setText("Login");
        InnerPanel_3.add(loginButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
