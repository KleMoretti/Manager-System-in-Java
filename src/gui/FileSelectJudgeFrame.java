package gui;

import console.DataProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class FileSelectJudgeFrame {
    FileManagerFrame fileManagerFrame;

    private JPanel BackgroundPanel;
    private JTextField numberLabelFieldTextField;
    private JTextField DescriptionLabelField;
    private JButton FileSelectedJudgeOKButton;
    private JButton FileSelectedJudeExitButton;
    private JLabel NumberLabel;
    private JLabel DescriptionLabel;
    JFrame frame;

    String NumberLabelFieldText;
    String DescriptionLabelFieldText;

    public void FileSelectFrameShow() {
        frame = new JFrame("FileSelectJudgeFrame");
        frame.setContentPane(this.BackgroundPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 200);
        frame.pack();
        frame.setVisible(true);
    }

    public FileSelectJudgeFrame(FileManagerFrame fileManagerFrame) {
        this.fileManagerFrame = fileManagerFrame;
        FileSelectedJudgeOKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberLabelFieldText = numberLabelFieldTextField.getText();
                DescriptionLabelFieldText = DescriptionLabelField.getText();
                fileManagerFrame.NumberInFileManagerFrame = NumberLabelFieldText;
                fileManagerFrame.DescriptionInFileManagerFrame = DescriptionLabelFieldText;

                try {
                    if (DataProcessing.searchDoc(NumberLabelFieldText) == null) {
                        if (NumberLabelFieldText != null || DescriptionLabelFieldText != null) {
                            int result = fileManagerFrame.getFileChooser().showOpenDialog(null);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                fileManagerFrame.getSelectedFileLabel().setText("Selected: " + fileManagerFrame.getFileChooser().getSelectedFile().getAbsolutePath());
                                try {
                                    fileManagerFrame.uploadFile(fileManagerFrame.getFileChooser().getSelectedFile());
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                fileManagerFrame.getSelectedFileLabel().setText("No file selected");
                            }
                        } else {
                            frame.dispose();
                        }
                    } else {
                        MatchDocIDFalse();
                        frame.dispose();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });

        FileSelectedJudeExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public void MatchDocIDFalse() {
        JButton errorReturnButton = new JButton("OK");
        Dialog dialog = new Dialog(new Frame(), "Error", true);
        dialog.add(new JLabel("This id has exist!"), BorderLayout.CENTER);
        dialog.add(errorReturnButton, BorderLayout.SOUTH);
        dialog.setSize(200, 100);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  //获取到屏幕尺寸
        dialog.setLocation((int) ((screenSize.getWidth() - frame.getWidth()) / 2), (int) ((screenSize.getHeight() - frame.getHeight()) / 2));
        errorReturnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.setVisible(false);
                NumberLabel.setText("");
                DescriptionLabelField.setText("");
            }
        });
        dialog.setVisible(true);
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
        BackgroundPanel = new JPanel();
        BackgroundPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        BackgroundPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        FileSelectedJudgeOKButton = new JButton();
        FileSelectedJudgeOKButton.setText("OK");
        panel1.add(FileSelectedJudgeOKButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        FileSelectedJudeExitButton = new JButton();
        FileSelectedJudeExitButton.setText("Exit");
        panel1.add(FileSelectedJudeExitButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 5, 0, 5), -1, -1));
        BackgroundPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        NumberLabel = new JLabel();
        NumberLabel.setText("Number: ");
        panel2.add(NumberLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(102, 17), null, 0, false));
        numberLabelFieldTextField = new JTextField();
        numberLabelFieldTextField.setText("");
        panel2.add(numberLabelFieldTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        DescriptionLabel = new JLabel();
        DescriptionLabel.setText("Description: ");
        panel2.add(DescriptionLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(102, 17), null, 0, false));
        DescriptionLabelField = new JTextField();
        panel2.add(DescriptionLabelField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return BackgroundPanel;
    }

}