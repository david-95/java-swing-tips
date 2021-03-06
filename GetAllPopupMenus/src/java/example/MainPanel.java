package example;
//-*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
//@homepage@
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public final class MainPanel extends JPanel {
    private MainPanel() {
        super(new BorderLayout());

        String[] columnNames = {"String", "Integer", "Boolean"};
        Object[][] data = {
            {"prev1: Ctrl+1", 1, true}, {"next1: Ctrl+2", 2, false},
            {"prev2: Ctrl+3", 3, true}, {"next2: Ctrl+4", 4, false}
        };
        TableModel model = new DefaultTableModel(data, columnNames) {
            @Override public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("JTable", new JScrollPane(table));
        tabs.addTab("JTree", new JScrollPane(new JTree()));
        tabs.addTab("JSplitPane", new JSplitPane());
        tabs.addTab("JButton", new JButton("button"));

        JMenu menu = new JMenu("Sub");
        menu.add("Item 1");
        menu.add("Item 2");

        JPopupMenu popup = new JPopupMenu();
        popup.add(menu);
        popup.add("Table Item 1");
        popup.add("Table Item 2");
        popup.add("Table Item 3");

        table.setComponentPopupMenu(popup);

        EventQueue.invokeLater(() -> {
            tabs.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "prev1");
            tabs.getActionMap().put("prev1", new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    int s = tabs.getTabCount();
                    tabs.setSelectedIndex((tabs.getSelectedIndex() + s - 1) % s);
                }
            });
            tabs.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "next1");
            tabs.getActionMap().put("next1", new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    tabs.setSelectedIndex((tabs.getSelectedIndex() + 1) % tabs.getTabCount());
                }
            });

            tabs.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "prev2");
            tabs.getActionMap().put("prev2", new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    tabs.dispatchEvent(new MouseEvent(tabs, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false));
                    int s = tabs.getTabCount();
                    tabs.setSelectedIndex((tabs.getSelectedIndex() + s - 1) % s);
                }
            });

            tabs.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "next2");
            tabs.getActionMap().put("next2", new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    for (MenuElement m: MenuSelectionManager.defaultManager().getSelectedPath()) {
                        if (m instanceof JPopupMenu) {
                            ((JPopupMenu) m).setVisible(false);
                        }
                    }
                    tabs.setSelectedIndex((tabs.getSelectedIndex() + 1) % tabs.getTabCount());
                }
            });
        });

        add(tabs);
        setPreferredSize(new Dimension(320, 240));
    }
    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                createAndShowGUI();
            }
        });
    }
    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("@title@");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
