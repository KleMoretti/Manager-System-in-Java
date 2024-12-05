package GUI;

import console.AbstractUser;
import console.DataProcessing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Enumeration;

public class DeleteUserFrame {

    FileBrowsingFrame fileBrowsingFrame;

    private JTable UserShowTable;
    private JButton OKButton;
    private JButton exitButton;
    private JPanel OuterPanel;
    private JPanel ShowUserPanel;
    private JPanel ButtonPanel;


    public static void main(String[] args) {
        JFrame frame = new JFrame("DeleteUserFrame");
        frame.setContentPane(new DeleteUserFrame(new FileBrowsingFrame(new Main())).OuterPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public DeleteUserFrame(FileBrowsingFrame fileBrowsingFrame) {
        this.fileBrowsingFrame = fileBrowsingFrame;

        $$$setupUI$$$();

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = UserShowTable.getSelectedRow();
                if (selectedRow != -1) {
                    Object[] rowData = new Object[3];
                    rowData[0] = UserShowTable.getValueAt(selectedRow, 0);
                    try {
                        DataProcessing.deleteUser(String.valueOf(rowData[0]));
                        DefaultTableModel model = (DefaultTableModel) UserShowTable.getModel();
                        model.removeRow(selectedRow);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No row selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Exit success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // 清空现有的数据
                DefaultTableModel model = (DefaultTableModel) UserShowTable.getModel();
                model.setRowCount(0);  // 清空所有行

                try {
                    Enumeration users = DataProcessing.listUser();
                    while (users.hasMoreElements()) {
                        Object[] data = new Object[3];
                        AbstractUser user = (AbstractUser) users.nextElement();
                        data[0] = user.getName();
                        data[1] = user.getPassword();
                        data[2] = user.getRole();
                        ((DefaultTableModel) UserShowTable.getModel()).addRow(data);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                // 自动调整列宽
                packColumns(UserShowTable);

                // 居中文本
                centerAlignCells(UserShowTable);

                UserShowTable.revalidate();
                UserShowTable.repaint();

            }
        });

        show();
    }

    public void show() {
        // 设置选择模式为单选，并且只能选择整行（如果还没有设置）
        if (UserShowTable.getSelectionModel().getSelectionMode() != ListSelectionModel.SINGLE_SELECTION) {
            UserShowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            UserShowTable.setRowSelectionAllowed(true);
            UserShowTable.setColumnSelectionAllowed(false);
        }

        String[] columnNames = new String[]{"name", "password", "role"};

        DefaultTableModel model;
        if (!(UserShowTable.getModel() instanceof DefaultTableModel)) {
            model = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            UserShowTable.setModel(model);
        } else {
            model = (DefaultTableModel) UserShowTable.getModel();
            model.setColumnIdentifiers(columnNames);
        }


        try {
            Enumeration e = DataProcessing.listUser();
            while (e.hasMoreElements()) {
                Object[] data = new Object[3];
                AbstractUser user = (AbstractUser) e.nextElement();
                data[0] = user.getName();
                data[1] = user.getPassword();
                data[2] = user.getRole();
                ((DefaultTableModel) UserShowTable.getModel()).addRow(data);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 自动调整列宽
        packColumns(UserShowTable);

        // 居中文本
        centerAlignCells(UserShowTable);

    }

    private void packColumns(JTable table) {
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            int preferredWidth = calculateColumnWidth(table, i);
            column.setPreferredWidth(preferredWidth);
        }
    }

    private int calculateColumnWidth(JTable table, int columnIndex) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        TableCellRenderer headerRenderer = column.getHeaderRenderer();
        if (headerRenderer == null) {
            headerRenderer = table.getTableHeader().getDefaultRenderer();
        }
        Component headerComp = headerRenderer.getTableCellRendererComponent(
                table, column.getHeaderValue(), false, false, 0, 0);
        double width = headerComp.getPreferredSize().getWidth();

        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, columnIndex);
            Component comp = renderer.getTableCellRendererComponent(
                    table, table.getValueAt(row, columnIndex), false, false, row, columnIndex);
            double cellWidth = comp.getPreferredSize().getWidth() + 1;
            width = Math.max(width, cellWidth);
        }

        return (int) width;
    }

    private void centerAlignCells(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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
        OuterPanel.setLayout(new GridBagLayout());
        ShowUserPanel = new JPanel();
        ShowUserPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 4.0;
        gbc.fill = GridBagConstraints.BOTH;
        OuterPanel.add(ShowUserPanel, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        ShowUserPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        UserShowTable = new JTable();
        UserShowTable.setName("Show");
        UserShowTable.setToolTipText("Show");
        scrollPane1.setViewportView(UserShowTable);
        ButtonPanel = new JPanel();
        ButtonPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 10, 10, 10), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        OuterPanel.add(ButtonPanel, gbc);
        OKButton = new JButton();
        OKButton.setText("OK");
        ButtonPanel.add(OKButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Exit");
        ButtonPanel.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
