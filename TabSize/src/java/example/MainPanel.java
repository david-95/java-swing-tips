package example;
//-*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
//@homepage@
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public final class MainPanel extends JPanel {
    private final JTextPane textPane = new JTextPane();
    private final JTextArea textArea = new JTextArea();

    public MainPanel() {
        super(new BorderLayout());
        textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        FontMetrics fm = textPane.getFontMetrics(textPane.getFont());
        int charWidth = fm.charWidth('m');
        int tabWidth = charWidth * 4;
        TabStop[] tabs = new TabStop[10];
        for (int j = 0; j < tabs.length; j++) {
            tabs[j] = new TabStop((j + 1) * tabWidth);
        }
        TabSet tabSet = new TabSet(tabs);
        // MutableAttributeSet attributes = new SimpleAttributeSet();
        MutableAttributeSet attributes = textPane.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setTabSet(attributes, tabSet);
        // int length = textPane.getDocument().getLength();
        // textPane.getStyledDocument().setParagraphAttributes(0, length, attributes, false);
        textPane.setParagraphAttributes(attributes, false);
        textPane.setText("JTextPane\naaaa\n\taaaa\n\t\taaaa\n");

        textArea.setTabSize(4);
        textArea.setText("JTextArea\naaaa\n\taaaa\n\t\taaaa\n");

        add(new JScrollPane(textArea), BorderLayout.NORTH);
        add(new JScrollPane(textPane));
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
