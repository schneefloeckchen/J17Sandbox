package rzi.j17sandbox.modules.layoutModule;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import rzi.j17sandbox.J17SandBox;
import rzi.j17sandbox.modules.layoutModule.zxlayout.ZxSimpleLayout;

/**
 * Startmodule f.d. Testen/Analysieren der Layout Interface.
 *
 * Das Module soll schon
 *
 * @author rene
 */
public class LayoutTester extends JFrame {       // JFrame, weil popup dialog!

    private final JToggleButton m_statusButton1 = new JToggleButton("STAT 1");
    private final JToggleButton m_statusButton2 = new JToggleButton("STAT 2");
    private final JTextArea m_messageArea = new JTextArea(10, 20);

    public LayoutTester() {
        jInit();
    }

    private void jInit() {
        setTitle("LayoutTester -- V01");
        setLayout(new ZxSimpleLayout());
        add(ZxSimpleLayout.INITIAL, new JButton("TEST"));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent e) -> {
            dispose();
        });
        add(ZxSimpleLayout.NEXT, cancelButton);
        add(ZxSimpleLayout.NEW_LINE, new JTextField(10));
        Dimension p = m_statusButton1.getPreferredSize();
        p.width += 20;
        m_statusButton1.setPreferredSize(p);
        m_statusButton2.setPreferredSize(p);
        add(ZxSimpleLayout.NEW_LINE, m_statusButton1);
        add(ZxSimpleLayout.NEXT, m_statusButton2);
        JButton readStatusButton = new JButton("Read Status");
        readStatusButton.addActionListener((ActionEvent e) -> {
            boolean status1 = m_statusButton1.isSelected();
            boolean status2 = m_statusButton2.isSelected();
            m_messageArea.append("Status 1: " + status1
                    + " Status 2: " + status2 + "[\n");
        });
        add(ZxSimpleLayout.NEW_LINE, readStatusButton);
        add(ZxSimpleLayout.NEW_LINE, m_messageArea);
        J17SandBox.packAndCenter(this);

        setVisible(true);

    }
}
