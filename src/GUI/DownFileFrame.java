package GUI;

import console.DataProcessing;
import console.Doc;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;

public class DownFileFrame {
    FileBrowsingFrame fileBrowsingFrame;

    private JButton DownLoadButton;
    private JButton RefreshButton;
    private JTable Table;
    private JPanel OuterPanelDownload;
    private JPanel UppperJPanel;
    private JPanel LowerJPanel;
    JFrame frame;

    public static void main(String[] args) {

    }

    DownFileFrame(FileBrowsingFrame fileBrowsingFrame) {
        this.fileBrowsingFrame = fileBrowsingFrame;

        $$$setupUI$$$();


        RefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ee) {
                JOptionPane.showMessageDialog(null, "Refresh success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // 清空现有的数据
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                model.setRowCount(0);  // 清空所有行

                try {
                    Enumeration e = DataProcessing.listDoc();
                    while (e.hasMoreElements()) {
                        Object[] data = new Object[5];
                        Doc doc = (Doc) e.nextElement();
                        data[0] = doc.getId();
                        data[1] = doc.getCreator();
                        data[2] = doc.getTimestamp();
                        data[3] = doc.getFilename();
                        data[4] = doc.getDescription();
                        ((DefaultTableModel) Table.getModel()).addRow(data);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // 自动调整列宽
                packColumns(Table);

                // 居中文本
                centerAlignCells(Table);

                Table.revalidate();
                Table.repaint();

            }
        });

        DownLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = Table.getSelectedRow();
                if (selectedRow != -1) {
                    Object[] rowData = new Object[5];
                    for (int i = 0; i < 5; i++) {
                        rowData[i] = Table.getValueAt(selectedRow, i);
                    }
                    try {
                        updateDocFileData(rowData);
                    } catch (IOException | SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No row selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        show();
    }

    private void updateDocFileData(Object[] rowData) throws IOException, SQLException {
        byte[] buffer = new byte[1024];

        String uploadpath = "D:\\@Java\\Object-oriented and multithreaded comprehensive experiment\\Manager System\\uploadfile\\";
        String downloadpath = "D:\\@Java\\Object-oriented and multithreaded comprehensive experiment\\Manager System\\downloadfile\\";

        Doc doc = DataProcessing.searchDoc(rowData[0].toString());

        File tempFile = new File(uploadpath + doc.getFilename());
        String filename = tempFile.getName();

        BufferedInputStream infile = new BufferedInputStream(new FileInputStream(tempFile));
        BufferedOutputStream targetfile = new BufferedOutputStream(new FileOutputStream(downloadpath + filename));

        while (true) {
            int byteRead = infile.read(buffer);
            if (byteRead == -1) {
                break;
            }
            targetfile.write(buffer, 0, byteRead);
        }
        infile.close();
        targetfile.close();

        // 创建下载成功对话框
        showSuccessDialog();
    }

    public void show() {
        // 设置选择模式为单选，并且只能选择整行（如果还没有设置）
        if (Table.getSelectionModel().getSelectionMode() != ListSelectionModel.SINGLE_SELECTION) {
            Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Table.setRowSelectionAllowed(true);
            Table.setColumnSelectionAllowed(false);
        }

        String[] columnNames = new String[]{"id", "creator", "time", "filename", "description"};

        DefaultTableModel model;
        if (!(Table.getModel() instanceof DefaultTableModel)) {
            model = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Table.setModel(model);
        } else {
            model = (DefaultTableModel) Table.getModel();
            model.setColumnIdentifiers(columnNames);
        }


        try {
            Enumeration e = DataProcessing.listDoc();
            while (e.hasMoreElements()) {
                Object[] data = new Object[5];
                Doc doc = (Doc) e.nextElement();
                data[0] = doc.getId();
                data[1] = doc.getCreator();
                data[2] = doc.getTimestamp();
                data[3] = doc.getFilename();
                data[4] = doc.getDescription();
                ((DefaultTableModel) Table.getModel()).addRow(data);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 自动调整列宽
        packColumns(Table);

        // 居中文本
        centerAlignCells(Table);

    }

    public void showSuccessDialog() {
        JOptionPane.showMessageDialog(null, "Download success!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        OuterPanelDownload = new JPanel();
        OuterPanelDownload.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        UppperJPanel = new JPanel();
        UppperJPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(10, 5, 5, 5), -1, -1));
        OuterPanelDownload.add(UppperJPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAlignmentY(1.0f);
        scrollPane1.setEnabled(false);
        scrollPane1.putClientProperty("html.disable", Boolean.FALSE);
        UppperJPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder(null, "Download", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        Table = new JTable();
        Table.setCellSelectionEnabled(false);
        Table.setColumnSelectionAllowed(false);
        Table.setEnabled(true);
        Table.setFillsViewportHeight(false);
        Table.setInheritsPopupMenu(false);
        Table.setIntercellSpacing(new Dimension(1, 2));
        Table.setRowHeight(22);
        Table.setRowMargin(2);
        Table.setRowSelectionAllowed(true);
        scrollPane1.setViewportView(Table);
        LowerJPanel = new JPanel();
        LowerJPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(5, 5, 10, 5), -1, -1));
        LowerJPanel.setAlignmentY(1.0f);
        OuterPanelDownload.add(LowerJPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        DownLoadButton = new JButton();
        DownLoadButton.setLabel("DownLoad");
        DownLoadButton.setText("DownLoad");
        LowerJPanel.add(DownLoadButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RefreshButton = new JButton();
        RefreshButton.setText("Refresh");
        LowerJPanel.add(RefreshButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return OuterPanelDownload;
    }

    public JPanel getFileManagerPanel() {
        return OuterPanelDownload;
    }
}
