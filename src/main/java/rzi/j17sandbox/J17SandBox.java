/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rzi.j17sandbox;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import rzi.j17sandbox.modules.lafModule.LookAndFeelModule;
import rzi.j17sandbox.modules.infoModule.InfoModule;
import rzi.j17sandbox.modules.layoutModule.LayoutTester;
import rzi.j17sandbox.modules.ntpModule.NTPModule;

/**
 * Einstiegs-Dialog der Sandbox - Prinzipielles Layout:
 *
 * in BoxLayout links sind Buttons zum Starten der verschiedenen Module
 * Menue-Leiste im Top hat 1 festes Menue links: Start, Quit, Setup,... und
 * weitere, die von den Modulen festgelegt werden.
 *
 * In Frame daneben toben sich dann die Module aus.
 * Ohne rzi Bibliotheken, Sandkiste eben
 *
 * Module:
 * -- Info: Gibt irgendwelche Infos zum System aus
 * -- LayoutModule: Popup Window, analysieren der LayoutManager/2 Interface.
 * -- lafModule: Spielen mit verschiedenen Look and Feels.
 *
 * Build-prozess ist Maven.
 *
 * @author rene
 */
public class J17SandBox extends JFrame implements ActionListener {

    private final JButton m_startInfoModulButton = new JButton("<html>System-<br/>Information</html>");
    private final JButton m_startLayoutModulButton = new JButton("<html>Layout test<br/>und Analyse</html>");
    private final JButton m_startLookAndFeelModulButton = new JButton("<html>Look And Feel<br/>Test und Analyse</html>");
    private final JButton m_NTPTimeModulButton = new JButton("<html>NTP Tests</html>");
    private final JButton m_cancelButton = new JButton("Cancel");

    private JPanel m_lastPanel = null;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 700;
    private static final int MODUL_BUTTON_WIDTH = 100;   // Width of the buttons to start the modules

    public J17SandBox() {
        jInit();
    }

    private void jInit() {
        setTitle("Java 17 Sandbox");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());  // Will use only EAST and CENTER

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new GridLayout(5,1));
        m_startInfoModulButton.addActionListener(this);
        m_startLayoutModulButton.addActionListener(this);
        m_startLookAndFeelModulButton.addActionListener(this);
        m_NTPTimeModulButton.addActionListener(this);
        m_cancelButton.addActionListener(this);

        leftButtonPanel.add(m_startInfoModulButton);
        leftButtonPanel.add(m_startLayoutModulButton);
        leftButtonPanel.add(m_startLookAndFeelModulButton);
        leftButtonPanel.add(m_NTPTimeModulButton);
        leftButtonPanel.add(m_cancelButton);
        add(leftButtonPanel, BorderLayout.WEST);
//        int buttonMaxHeight = 0;
//        int buttonMaxWidth = 0;
        add(leftButtonPanel, BorderLayout.WEST);
        center(this, WINDOW_WIDTH, WINDOW_HEIGHT);
//        Component[] allComponents = leftButtonPanel.getComponents();
//        for (Component c: allComponents) {
//            if (c instanceof JButton b) {
//                Dimension size = b.getPreferredSize();
//                buttonMaxHeight = Integer.max(buttonMaxHeight, size.height);
//                buttonMaxWidth = Integer.max(buttonMaxWidth, size.width);
//            }
//        }
//        for (Component c:allComponents)
//            if (c instanceof JButton b) {
//                Dimension dim = new Dimension(buttonMaxWidth, buttonMaxHeight);
//                b.setPreferredSize(dim);
//                b.setSize(dim);
//            }
//
//
// Center the Window on the screen        
        setVisible(true);
    }

    public static void packAndCenter(Window container) {
        container.pack();
        center(container);
    }

    public static void center(Container container) {
        Dimension d = container.getSize();
        center(container, d.width, d.height);
    }

    public static void center(Container container, int width, int height) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        int windowX = (screen.width - width) / 2;
        int windowY = (screen.height - height) / 2;
        container.setPreferredSize(new Dimension(width, height));
        container.setSize(new Dimension(width, height));
        container.setLocation(windowX, windowY);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            new J17SandBox().setVisible(true);
        });
    }

    private void performInfoModule() {
        if (m_lastPanel != null) {
            remove(m_lastPanel);
            System.out.println("performInfoPanel -- removing last panal");
        }
        InfoModule dialog = new InfoModule();
        add(BorderLayout.CENTER, dialog);
        revalidate();
        repaint();
        dialog.doAction();
        m_lastPanel = dialog;
    }

    private void performLayoutModule() {
        LayoutTester dialog = new LayoutTester();
    }

    private void performLookAndFeelModule() {
        if (m_lastPanel != null) {
            remove(m_lastPanel);
            print("performLookAndFeelPanel -- removing last panal");
        }
        LookAndFeelModule dialog = new LookAndFeelModule();
        add(BorderLayout.CENTER, dialog);
        revalidate();
        repaint();
        m_lastPanel = dialog;
    }

    private void performNTPModule() {
        switchTo (new NTPModule());
    }
    
    private void switchTo (JPanel newPanel) {
        if (m_lastPanel != null) {
            remove(m_lastPanel);
            print("performLookAndFeelPanel -- removing last panal");
        }
        add(BorderLayout.CENTER, newPanel);
        revalidate();
        repaint();
        m_lastPanel = newPanel;
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == m_startInfoModulButton) {
            performInfoModule();
        } else if (src == m_startLayoutModulButton) {
            performLayoutModule();
        } else if (src == m_startLookAndFeelModulButton) {
            performLookAndFeelModule();
        } else if (src == m_NTPTimeModulButton) {
            performNTPModule();
        } else if (src == m_cancelButton) {
            dispose();
        } else {
            System.out.println("Not yet implemented");
        }
    }
    
    public static void print(String text) {
        System.out.println(text);
    }

}
