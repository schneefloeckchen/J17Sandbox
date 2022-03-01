
package rzi.j17sandbox.modules;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Little Base Class for the UI
 * 
 * @author rene
 */
public class BasPanel extends JPanel {
  protected JTextArea m_reportArea = new JTextArea(50, 50);

  protected BasPanel() {
    jInit();
  }
  
  private void jInit() {
    setLayout(new BorderLayout());
    add(new JScrollPane(m_reportArea), BorderLayout.CENTER);

  }
  
  protected void center() {
      
      Toolkit toolkit = Toolkit.getDefaultToolkit();
      Dimension screnSize = toolkit.getScreenSize();
  }
  
  
  protected void write(String text) {
    m_reportArea.append(text + "\n");
  }
  
  protected void write(String label, String value) {
    write (label + " : "+value);
  }


}
