package gui;

import console.ResultSetData;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * @author 贾智超
 */
public class DeleteUserFrame {

    FileBrowsingFrame fileBrowsingFrame;

    private JTable userShowTable;
    private JButton okButton;
    private JButton refreshButton;
    private JPanel outerPanel;
    private JPanel showUserPanel;
    private JPanel buttonPanel;


    public DeleteUserFrame(FileBrowsingFrame fileBrowsingFrame) {
        this.fileBrowsingFrame = fileBrowsingFrame;
        $$$setupUI$$$();
        initializeListeners();
        showUsers();
    }

    private void initializeListeners() {
        okButton.addActionListener(e -> attemptDeleteUser());
        refreshButton.addActionListener(e -> refreshUserList());
    }

    private void attemptDeleteUser() {
        int selectedRow = userShowTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = userShowTable.getValueAt(selectedRow, 0).toString();
            String password = userShowTable.getValueAt(selectedRow, 1).toString();
            String role = userShowTable.getValueAt(selectedRow, 2).toString();

            if (username.equals(fileBrowsingFrame.mainFrame.user.getName())) {
                JOptionPane.showMessageDialog(null, "You can't delete yourself!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                try {
                    fileBrowsingFrame.mainFrame.client.sendMessage("CLIENT>>> DELETE_USER" + " " + username + " " + password + " " + role);
                    String response = fileBrowsingFrame.mainFrame.client.receiveMessage().join().toString();
                    if (!"DELETE_SUCCESS".equals(response)) {
                        System.err.println("Failed to delete user");
                        JOptionPane.showMessageDialog(null, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Delete user success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No row selected!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        refreshUserList();
    }


    private void refreshUserList() {
        DefaultTableModel model = (DefaultTableModel) userShowTable.getModel();
        SwingUtilities.invokeLater(() -> model.setRowCount(0));  // 清空所有行

        CompletableFuture.runAsync(() -> {
                    try {
                        fileBrowsingFrame.mainFrame.client.sendMessage("CLIENT>>> LIST_USER");
                        String response = fileBrowsingFrame.mainFrame.client.receiveMessage().join().toString();

                        if (!"LIST_USER_SUCCESS".equals(response)) {
                            handleError("Failed to list users", "Failed to refresh user list.");
                        } else {
                            Object responses = fileBrowsingFrame.mainFrame.client.receiveMessage().join();
                            handleSuccess(responses, model);
                        }
                    } catch (IOException e) {
                        handleError(e.getMessage(), "Failed to refresh user list.");
                    }
                }).thenRunAsync(this::updateUI, SwingUtilities::invokeLater)
                .exceptionally(ex -> {
                    handleError("An error occurred during refreshing the user list.", "Attention");
                    return null;
                });
    }


    private void handleError(String errorMessage, String dialogTitle) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, errorMessage, dialogTitle, JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void handleSuccess(Object responses, DefaultTableModel model) {
        if (responses instanceof ResultSetData) {
            ResultSetData resultSetData = (ResultSetData) responses;
            final String[] columnNames = resultSetData.getColumnNames();
            final List<String[]> data = resultSetData.getData();

            SwingUtilities.invokeLater(() -> {
                model.setColumnIdentifiers(columnNames);
                for (String[] row : data) {
                    model.addRow(row);
                }
                JOptionPane.showMessageDialog(null, "Refresh success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Refresh success!");
            });
        }
    }

    private void updateUI() {
        packColumns(userShowTable);
        centerAlignCells(userShowTable);
        userShowTable.revalidate();
        userShowTable.repaint();
    }


    private CompletableFuture<Void> loadUsersFromDatabase() {
        return CompletableFuture.runAsync(() -> {
            refreshUserList();
        });
    }

    private void showUsers() {
        String[] columnNames = {"Name", "Password", "Role"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userShowTable.setModel(model);
        userShowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userShowTable.setRowSelectionAllowed(true);
        userShowTable.setColumnSelectionAllowed(false);

        loadUsersFromDatabase()
                .thenRun(() -> packColumns(userShowTable))
                .thenRun(() -> centerAlignCells(userShowTable))
                .thenRun(() -> SwingUtilities.invokeLater(() -> {
                    userShowTable.revalidate();
                    userShowTable.repaint();
                }));
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
        okButton = new JButton();
        okButton.setText("OK");
        buttonPanel.add(okButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
