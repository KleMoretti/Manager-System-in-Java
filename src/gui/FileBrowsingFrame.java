package gui;

import console.AbstractUser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class FileBrowsingFrame {
    Main mainFrame;
    AbstractUser currentUser;

    private JPanel FileBrowsingFrame;
    private JLabel PersonInfoLabel;
    private JTabbedPane tabbedPane1;
    private JList<String> UserManagerJList;
    private JList<String> FileManagerJList;
    private JList<String> PersonInfoJList;
    private JPanel personInfoPanel;
    private JPanel fileManagerPanel;
    private JPanel userManagerPanel;
    private CardLayout userCardLayout;
    private CardLayout fileCardLayout;
    private CardLayout personCardLayout;
    private JPanel UserManagerCardLayoutPanel;
    private JPanel FileManagerCardLayoutPanel;
    private JPanel personInfoCardLayoutPanel;

    JFrame frame;

    /**
     * 添加 CardManager 实例
     */
    private CardManager userCardManager;
    private CardManager fileCardManager;
    private CardManager personCardManager;


    public FileBrowsingFrame(Main mainFrame) {
        $$$setupUI$$$();

        this.mainFrame = mainFrame;
        // 初始化 CardLayout
        userCardLayout = new CardLayout();
        UserManagerCardLayoutPanel.setLayout(userCardLayout);
        // 初始化 CardLayout
        fileCardLayout = new CardLayout();
        FileManagerCardLayoutPanel.setLayout(fileCardLayout);
        // 初始化 CardLayout
        personCardLayout = new CardLayout();
        personInfoCardLayoutPanel.setLayout(personCardLayout);

        frame = new JFrame("File Browsing Frame");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ex) {
                System.exit(0); // 确保窗口关闭
            }


        });

        setupContentPanels();
    }

    void show() {

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PersonInfoLabel.setText("Name: " + currentUser.getName());

        UserManagerJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        FileManagerJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        PersonInfoJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        frame.setContentPane(FileBrowsingFrame);
        //获取到屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int) ((screenSize.getWidth() - frame.getWidth()) / 2), (int) ((screenSize.getHeight() - frame.getHeight()) / 2));
        frame.setVisible(true);

        switch (currentUser.getRole()) {
            case "administrator":
                break;
            case "browser":
                userCardManager.setListUnabled(UserManagerJList, new HashSet<>(Arrays.asList(0, 1, 2)));
                fileCardManager.setListUnabled(FileManagerJList, new HashSet<>(Arrays.asList(0)));
                break;
            case "operator":
                userCardManager.setListUnabled(UserManagerJList, new HashSet<>(Arrays.asList(0, 1, 2)));
                break;
            default:
                break;
        }


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FileBrowsingFrame");
        frame.setContentPane(new FileBrowsingFrame(new Main()).FileBrowsingFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void setupContentPanels() {
        // 初始化 CardManager 实例
        userCardManager = new CardManager(UserManagerCardLayoutPanel, userCardLayout);
        fileCardManager = new CardManager(FileManagerCardLayoutPanel, fileCardLayout);
        personCardManager = new CardManager(personInfoCardLayoutPanel, personCardLayout);

        // 使用 CardManager 添加卡片内容
        userCardManager.addContentPanel("changeDefault", "Details for change");
        userCardManager.addContentPanel("change", new ChangeUserFrame(this).getChangeUserPanel());

        userCardManager.addContentPanel("deleteDefault", "Details for delete");
        userCardManager.addContentPanel("delete", new DeleteUserFrame(this).getChangeUserPanel());

        userCardManager.addContentPanel("addDefault", "Details for add");
        userCardManager.addContentPanel("add", new AddNewUserForm(this).getChangeUserPanel());

        fileCardManager.addContentPanel("uploadDefault", "Details for upload");
        fileCardManager.addContentPanel("upload", new FileManagerFrame(this).getFileManagerPanel());
        fileCardManager.addContentPanel("downloadDefault", "Details for download");
        fileCardManager.addContentPanel("download", new DownFileFrame(this).getFileManagerPanel());

        personCardManager.addContentPanel("changeInfoDefault", "Details of personInfo");
        personCardManager.addContentPanel("changeInfo", new PersonInfoChange(this).getOuterPanel());

        // 默认显示第一个面板
        userCardManager.showCard("changeDefault");
        fileCardManager.showCard("uploadDefault");
        personCardManager.showCard("changeInfoDefault");

        // 为每个 JList 添加 ListSelectionListener
        addListSelectionListener(UserManagerJList, UserManagerCardLayoutPanel, userCardLayout);
        addListSelectionListener(FileManagerJList, FileManagerCardLayoutPanel, fileCardLayout);
        addListSelectionListener(PersonInfoJList, personInfoCardLayoutPanel, personCardLayout);
    }

    // Method to set the user after login
    public void setUser(AbstractUser user) {
        this.currentUser = user;  // Store the logged-in user
    }

    private void addListSelectionListener(JList<String> list, JPanel cardLayoutPanel, CardLayout cardLayout) {
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedIndex = list.getSelectedIndex();

                if (selectedIndex != -1) {
                    Rectangle cellBounds = list.getCellBounds(selectedIndex, selectedIndex);
                    Point clickPoint = e.getPoint();
                    if (!cellBounds.contains(clickPoint)) {
                        // 点击在列表项之外的区域
                        list.clearSelection();
                        cardLayout.show(cardLayoutPanel, list.getModel().getElementAt(selectedIndex) + "Default");
                    } else {
                        String selectedItem = list.getModel().getElementAt(selectedIndex);
                        cardLayout.show(cardLayoutPanel, selectedItem);
                    }
                }
            }
        });

    }


    /**
     * Method generated by IntelliJ IDEA gui Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        FileBrowsingFrame = new JPanel();
        FileBrowsingFrame.setLayout(new GridBagLayout());
        FileBrowsingFrame.setMinimumSize(new Dimension(100, 100));
        FileBrowsingFrame.setToolTipText("top");
        FileBrowsingFrame.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        FileBrowsingFrame.add(panel1, gbc);
        PersonInfoLabel = new JLabel();
        Font PersonInfoLabelFont = this.$$$getFont$$$(null, -1, 18, PersonInfoLabel.getFont());
        if (PersonInfoLabelFont != null) PersonInfoLabel.setFont(PersonInfoLabelFont);
        PersonInfoLabel.setText("Name:");
        panel1.add(PersonInfoLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 6.0;
        gbc.fill = GridBagConstraints.BOTH;
        FileBrowsingFrame.add(panel2, gbc);
        tabbedPane1 = new JTabbedPane();
        panel2.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(100, 100), null, 0, false));
        userManagerPanel = new JPanel();
        userManagerPanel.setLayout(new BorderLayout(0, 0));
        Font userManagerPanelFont = this.$$$getFont$$$("Arial Black", -1, 18, userManagerPanel.getFont());
        if (userManagerPanelFont != null) userManagerPanel.setFont(userManagerPanelFont);
        tabbedPane1.addTab("UserManager", userManagerPanel);
        UserManagerJList = new JList();
        UserManagerJList.setBackground(new Color(-2362630));
        Font UserManagerJListFont = this.$$$getFont$$$("Britannic Bold", -1, 18, UserManagerJList.getFont());
        if (UserManagerJListFont != null) UserManagerJList.setFont(UserManagerJListFont);
        UserManagerJList.setLayoutOrientation(0);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("change");
        defaultListModel1.addElement("delete");
        defaultListModel1.addElement("add");
        UserManagerJList.setModel(defaultListModel1);
        UserManagerJList.setPreferredSize(new Dimension(100, 100));
        userManagerPanel.add(UserManagerJList, BorderLayout.WEST);
        UserManagerCardLayoutPanel = new JPanel();
        UserManagerCardLayoutPanel.setLayout(new CardLayout(0, 0));
        userManagerPanel.add(UserManagerCardLayoutPanel, BorderLayout.CENTER);
        fileManagerPanel = new JPanel();
        fileManagerPanel.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("FileManager", fileManagerPanel);
        FileManagerJList = new JList();
        FileManagerJList.setBackground(new Color(-2362630));
        Font FileManagerJListFont = this.$$$getFont$$$("Britannic Bold", -1, 18, FileManagerJList.getFont());
        if (FileManagerJListFont != null) FileManagerJList.setFont(FileManagerJListFont);
        FileManagerJList.setLayoutOrientation(0);
        final DefaultListModel defaultListModel2 = new DefaultListModel();
        defaultListModel2.addElement("upload");
        defaultListModel2.addElement("download");
        FileManagerJList.setModel(defaultListModel2);
        FileManagerJList.setPreferredSize(new Dimension(100, 100));
        fileManagerPanel.add(FileManagerJList, BorderLayout.WEST);
        FileManagerCardLayoutPanel = new JPanel();
        FileManagerCardLayoutPanel.setLayout(new CardLayout(0, 0));
        fileManagerPanel.add(FileManagerCardLayoutPanel, BorderLayout.CENTER);
        personInfoPanel = new JPanel();
        personInfoPanel.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("PersonalInfo Change", personInfoPanel);
        PersonInfoJList = new JList();
        PersonInfoJList.setBackground(new Color(-2362630));
        Font PersonInfoJListFont = this.$$$getFont$$$("Britannic Bold", -1, 18, PersonInfoJList.getFont());
        if (PersonInfoJListFont != null) PersonInfoJList.setFont(PersonInfoJListFont);
        final DefaultListModel defaultListModel3 = new DefaultListModel();
        defaultListModel3.addElement("changeInfo");
        PersonInfoJList.setModel(defaultListModel3);
        PersonInfoJList.setPreferredSize(new Dimension(100, 100));
        PersonInfoJList.setToolTipText("PersonInfo Change");
        PersonInfoJList.setVerifyInputWhenFocusTarget(false);
        personInfoPanel.add(PersonInfoJList, BorderLayout.WEST);
        personInfoCardLayoutPanel = new JPanel();
        personInfoCardLayoutPanel.setLayout(new CardLayout(0, 0));
        personInfoPanel.add(personInfoCardLayoutPanel, BorderLayout.CENTER);
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
        return FileBrowsingFrame;
    }

}
