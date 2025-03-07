package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DownAndUpLoadSuccess {
    private JPanel DownAndUpLoadSuccessJPanel;

    public DownAndUpLoadSuccess() {
        // 创建登录成功对话框
        JDialog successDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(DownAndUpLoadSuccessJPanel), "Loading Success!", true); // 模态对话框
        successDialog.setLayout(new BorderLayout());
        successDialog.setSize(300, 150);
        successDialog.setLocationRelativeTo(null); // 居中显示

        JLabel messageLabel = new JLabel("Loading successful!", SwingConstants.CENTER);
        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.BOLD, 20f));
        successDialog.add(messageLabel, BorderLayout.CENTER);

        // 设置定时器，在2秒后自动关闭对话框
        Timer timer = new Timer(2000, e -> {
            successDialog.dispose(); // 关闭对话框
        });
        // 只执行一次
        timer.setRepeats(false);
        timer.start();
        // 显示对话框
        successDialog.setVisible(true);
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
        DownAndUpLoadSuccessJPanel = new JPanel();
        DownAndUpLoadSuccessJPanel.setLayout(new BorderLayout(0, 0));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return DownAndUpLoadSuccessJPanel;
    }

}
