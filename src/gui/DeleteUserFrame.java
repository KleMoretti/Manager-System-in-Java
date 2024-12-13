package gui;

import console.DataProcessing;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author 贾智超
 */
public class DeleteUserFrame {

    FileBrowsingFrame fileBrowsingFrame;

    private JTable userShowTable;
    private JButton okbutton;
    private JButton refreshButton;
    private JPanel outerPanel;
    private JPanel showUserPanel;
    private JPanel buttonPanel;


    public static void main(String[] args) {
        JFrame frame = new JFrame("DeleteUserFrame");
        frame.setContentPane(new DeleteUserFrame(new FileBrowsingFrame(new Main())).outerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public DeleteUserFrame(FileBrowsingFrame fileBrowsingFrame) {
        this.fileBrowsingFrame = fileBrowsingFrame;

        $$$setupUI$$$();

        okbutton.addActionListener(e -> {
            int selectedRow = userShowTable.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = new Object[3];
                rowData[0] = userShowTable.getValueAt(selectedRow, 0);
                try {
                    DataProcessing.deleteUser(String.valueOf(rowData[0]));
                    DefaultTableModel model = (DefaultTableModel) userShowTable.getModel();
                    model.removeRow(selectedRow);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No row selected!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });


        refreshButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Exit success!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // 清空现有的数据
            DefaultTableModel model = (DefaultTableModel) userShowTable.getModel();
            model.setRowCount(0);  // 清空所有行

            try {
                ResultSet rs = DataProcessing.listUser();
                while (rs.next()) {
                    Object[] data = new Object[3];
                    data[0] = rs.getString("username");
                    data[1] = rs.getString("password");
                    data[2] = rs.getString("role");
                    ((DefaultTableModel) userShowTable.getModel()).addRow(data);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            // 自动调整列宽
            packColumns(userShowTable);

            // 居中文本
            centerAlignCells(userShowTable);

            userShowTable.revalidate();
            userShowTable.repaint();

        });

        show();
    }

    public void show() {
        // 设置选择模式为单选，并且只能选择整行（如果还没有设置）
        if (userShowTable.getSelectionModel().getSelectionMode() != ListSelectionModel.SINGLE_SELECTION) {
            userShowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            userShowTable.setRowSelectionAllowed(true);
            userShowTable.setColumnSelectionAllowed(false);
        }

        String[] columnNames = new String[]{"name", "password", "role"};

        DefaultTableModel model;
        if (!(userShowTable.getModel() instanceof DefaultTableModel)) {
            model = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            userShowTable.setModel(model);
        } else {
            model = (DefaultTableModel) userShowTable.getModel();
            model.setColumnIdentifiers(columnNames);
        }


        try {
            ResultSet rs = DataProcessing.listUser();
            while (rs.next()) {
                Object[] data = new Object[3];
                data[0] = rs.getString("username");
                data[1] = rs.getString("password");
                data[2] = rs.getString("role");
                ((DefaultTableModel) userShowTable.getModel()).addRow(data);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 自动调整列宽
        packColumns(userShowTable);

        // 居中文本
        centerAlignCells(userShowTable);

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
        outerPanel = new JPanel();
        outerPanel.setLayout(new GridBagLayout());
        showUserPanel = new JPanel();
        showUserPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 4.0;
        gbc.fill = GridBagConstraints.BOTH;
        outerPanel.add(showUserPanel, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        showUserPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        userShowTable = new JTable();
        userShowTable.setName("Show");
        userShowTable.setToolTipText("Show");
        scrollPane1.setViewportView(userShowTable);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 10, 10, 10), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        outerPanel.add(buttonPanel, gbc);
        okbutton = new JButton();
        okbutton.setText("OK");
        buttonPanel.add(okbutton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        buttonPanel.add(refreshButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
