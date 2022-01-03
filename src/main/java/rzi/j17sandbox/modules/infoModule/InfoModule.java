/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rzi.j17sandbox.modules.infoModule;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author rene
 */
public class InfoModule extends JPanel {
    
//    private J17SandBox m_parent;
//    private JPanel m_messagePanel;
    private JTextArea m_messageArea = new JTextArea(500, 500);
    
    public InfoModule() {
        jInit();
    }
    
    private void jInit() {
        System.out.println("In InfoModule");
//        m_messagePanel = new JPanel();
        add (new JScrollPane(m_messageArea));
//        m_messagePanel = new JPanel();
//        m_messagePanel.add(new JScrollPane(m_messageArea));
//        m_parent.add(new JScrollPane(m_messageArea), BorderLayout.CENTER);
//        m_parent.add(m_messagePanel, BorderLayout.CENTER);
//        m_parent.revalidate();
//        m_parent.doLayout();
//        m_parent.repaint();
    }

    public void doAction() {
        m_messageArea.append("Informationen: \n");
        m_messageArea.repaint();
    }
//    @Override
//    public void repaint() {
//        super.repaint();
//        if (m_messageArea != null) m_messageArea.repaint();
//    }
}
