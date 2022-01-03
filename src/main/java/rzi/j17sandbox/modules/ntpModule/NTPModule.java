package rzi.j17sandbox.modules.ntpModule;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

/**
 * Play around with the NTP Server and protocoll
 *
 * @author rene
 */
public class NTPModule extends JPanel implements ActionListener {

    private JTextField m_ntpUrlTextField = new JTextField(20);
    private JButton m_readTimeServerButton = new JButton("Read TimeServer");

    public NTPModule() {
        jInit();
    }

    private void jInit() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        add(new JLabel("NTP Server URL: "), gbc);
        add(m_ntpUrlTextField, gbc);
        m_ntpUrlTextField.setText("time-a.nist.gov");
        m_readTimeServerButton.addActionListener(this);
        add(m_readTimeServerButton);
    }

    private void performReadTimeServer() {
        try {
            NTPUDPClient client = new NTPUDPClient();
            client.setDefaultTimeout(1000);
            InetAddress inetAddy = InetAddress.getByName(m_ntpUrlTextField.getText());
            System.out.println("Host is "+inetAddy);
            TimeInfo timeInfo = client.getTime(inetAddy);
            System.out.println("Return: >> "+timeInfo);
            long retTime = timeInfo.getReturnTime();
            System.out.println(" >> Date "+ new Date(retTime));
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == m_readTimeServerButton) {
            performReadTimeServer();
        } else {
            System.out.println("Event nicht definiert");
        }
    }

}
