package rzi.j17sandbox.modules.jarModule;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarFile;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import rzi.j17sandbox.modules.BasPanel;

/**
 *
 * @author rene
 */
public class MaintainJarFileModule extends BasPanel {

  public MaintainJarFileModule() {
    jInit();
  }

  private void jInit() {
    JPanel buttonPanel = new JPanel();

    JButton loadJarFileButton = new JButton("Load Jar");
    loadJarFileButton.addActionListener((ActionEvent e) -> {
      performLoadJarFile();
    });
    buttonPanel.add(loadJarFileButton);
    add(buttonPanel, BorderLayout.NORTH);
  }

  private void performLoadJarFile() {
    JFileChooser fc = new JFileChooser("/home/rene/technik/SandBox/");
    int answer = fc.showOpenDialog(this);
    if (answer == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      try {
        write("Loaded File", file.getCanonicalPath());
        write ("Entries:");
        JarFile jarFile = new JarFile(file);
        Enumeration jarEnties = jarFile.entries();
        while (jarEnties.hasMoreElements()) {
          write (jarEnties.nextElement().toString());
        }
      } catch (IOException ex) {
        write("Error during getPath", ex.getLocalizedMessage());
      }
    }

  }

}
