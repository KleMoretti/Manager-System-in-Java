package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

public class CardManager {
    private CardLayout cardLayout;
    private JPanel cardLayoutPanel;

    public CardManager(JPanel cardLayoutPanel, CardLayout cardLayout) {
        this.cardLayoutPanel = cardLayoutPanel;
        this.cardLayout = cardLayout;
        cardLayoutPanel.setLayout(cardLayout);
    }

    public void addContentPanel(String key,String newDetails){
        JPanel panel=createContentPanel(key,newDetails);
        cardLayoutPanel.add(panel,key);
    }

    public void addContentPanel(String key,JPanel panel){
        cardLayoutPanel.add(key,panel);
        cardLayout.addLayoutComponent(panel,key);
    }

    public void showCard(String key){
        if(cardLayout!=null&&cardLayoutPanel!=null){
            cardLayout.show(cardLayoutPanel,key);
        }
    }

    public void updateContentPanel(String key,String newDetails){
        Component component = getComponentByKey(key);
        if (component instanceof JPanel) {
            JPanel panel = (JPanel) component;
            // 假设每个面板都有一个中心组件，通常是JLabel
            for (Component c : panel.getComponents()) {
                if (c instanceof JLabel) {
                    ((JLabel) c).setText(newDetails);
                }
            }
        }
    }

    private JPanel createContentPanel(String title, String details) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        JLabel label = new JLabel(details, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(label, BorderLayout.CENTER);
        panel.setName(title);  // 设置面板的名称
        return panel;
    }

    private Component getComponentByKey(String key) {
        for (int i = 0; i < cardLayoutPanel.getComponentCount(); i++) {
            Component comp = cardLayoutPanel.getComponent(i);
            if (comp.getName() != null && comp.getName().equals(key)) {
                return comp;
            }
        }
        return null;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCardLayoutPanel() {
        return cardLayoutPanel;
    }

    public void setListUnabled(JList myList, Set<Integer> disabledIndices) {
        myList.setSelectionModel(new DefaultListSelectionModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (disabledIndices.contains(index0) || disabledIndices.contains(index1)) {
                    return;
                }
                super.setSelectionInterval(index0, index1);
            }

            @Override
            public void addSelectionInterval(int index0, int index1) {
                for (int i = Math.min(index0, index1); i <= Math.max(index0, index1); i++) {
                    if (disabledIndices.contains(i)) {
                        return;
                    }
                }
                super.addSelectionInterval(index0, index1);
            }
        });

        myList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = myList.locationToIndex(e.getPoint());
                if (disabledIndices.contains(index)) {
                    // 如果点击的是禁用索引，则清除选择或不做任何操作
                    myList.clearSelection();
                }
            }
        });
        setJListCellRenderer(myList, disabledIndices);
    }


    // 设置JList的单元格渲染器，使得某些单元格不可用
    private  void setJListCellRenderer(JList myList, Set<Integer> disabledIndices){
        myList.setCellRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (disabledIndices.contains(index)) {
                    c.setEnabled(false);
                    c.setForeground(Color.GRAY); // 或者改变其他属性来表示禁用状态
                } else {
                    c.setEnabled(true);
                    c.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
                }
                return c;
            }
        });
    }
}
