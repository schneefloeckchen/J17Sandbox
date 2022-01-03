package rzi.j17sandbox.modules.lafModule;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;
import mikera.ui.steampunk.SteamPunkStyleFactory;
import rzi.j17sandbox.J17SandBox;

/**
 * Spielen mit der Auswahl von Look and Feels
 *
 * @author rene
 */
public class LookAndFeelModule extends JPanel implements ActionListener {

    private J17SandBox m_parent = null;
    private JComboBox<UIManager.LookAndFeelInfo> m_lufSelectComboBox = null;
    private JButton m_startSteamPunkButton = new JButton("Start Steampunk L&F");

    public LookAndFeelModule() {
//        m_parent = parent;
        jInit();
    }

    private void jInit() {
        JPanel lufPanel = new JPanel();
//        connectSteamLuf();
//
// List installed Look and Feels
        UIManager.LookAndFeelInfo lafInfo[] = UIManager.getInstalledLookAndFeels();
        System.out.println("Following L&F are found");
        for (var info : lafInfo) {
            System.out.println(info.getName());
        }

        lufPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 10;
        gbc.insets = new Insets(20, 10, 2, 2);
        lufPanel.add(new JLabel("Available Look & Feels : "), gbc);
        m_lufSelectComboBox = new JComboBox<>(lafInfo);
        m_lufSelectComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label;
                if (value instanceof UIManager.LookAndFeelInfo luf) {
                    label = new JLabel(luf.getName());
                } else {
                    label = new JLabel("No LUFINFO");
                }
                return label;
            }
        });
        m_lufSelectComboBox.addActionListener(this);
        lufPanel.add(m_lufSelectComboBox, gbc);
        m_startSteamPunkButton.addActionListener(this);
        lufPanel.add(m_startSteamPunkButton, gbc);
        add(lufPanel);
    }

    /**
     * Soll das Steam L&F verfügbar machen. Muster aus der TestApp
     * aus dem mikera.ui.steampunk paket
     */
    private void connectSteamLuf() {
        SynthLookAndFeel laf = new SynthLookAndFeel();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        SynthLookAndFeel.setStyleFactory(new SteamPunkStyleFactory());
    }

    /** ActionListener Implementation */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == m_lufSelectComboBox) {
            Object o = m_lufSelectComboBox.getSelectedItem();
            System.out.println(">> Selected >>  " + o.getClass().getName());
            if (o instanceof UIManager.LookAndFeelInfo lafInfo) {
                System.out.println(lafInfo.getClassName());
                try {
                    UIManager.setLookAndFeel(lafInfo.getClassName());
                } catch (ClassNotFoundException | InstantiationException
                        | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    System.out.println("Fehler beim setzen des L&F -- " + ex.getLocalizedMessage());
                }
            }
        } else if (src == m_startSteamPunkButton) {
            connectSteamLuf();
        }
    }
}
