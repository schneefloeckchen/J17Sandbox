package rzi.j17sandbox.modules.jarModule;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import rzi.j17sandbox.modules.BasPanel;

/**
 *
 * @author rene
 */
public class MaintainJarFileModule extends BasPanel {

    private static final String TEST_FOLDER
            = "/home/rene/technik/sandbox/Java/J17SandBox/J17Sandbox/testData/";

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

        JButton createJarButton = new JButton("Create Jar");
        createJarButton.addActionListener((ActionEvent e) -> {
            performCreateJarFile();
        });
        buttonPanel.add(createJarButton);

        JButton createTestFilesButton = new JButton("Create Testfiles");
        createTestFilesButton.addActionListener((var e)
                -> {
            CreateTestFilesDialog dialog = new CreateTestFilesDialog();
            dialog.setVisible(true);
        });
        buttonPanel.add(createTestFilesButton);

        add(buttonPanel, BorderLayout.NORTH);
    }  

    private void performLoadJarFile() {
        JFileChooser fc = new JFileChooser(TEST_FOLDER);
        int answer = fc.showOpenDialog(this);
        if (answer == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                write("Loaded File", file.getCanonicalPath());
                write("Entries:");
                JarFile jarFile = new JarFile(file);
                Enumeration jarEnties = jarFile.entries();
                while (jarEnties.hasMoreElements()) {
                    write(jarEnties.nextElement().toString());
                }
            } catch (IOException ex) {
                write("Error during getPath", ex.getLocalizedMessage());
            }
        }
    }

    private void performCreateJarFile() {
        JFileChooser fc = new JFileChooser(TEST_FOLDER + "jarTest1");
//        int answer = fc.showOpenDialog(this);
        try {
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                write("Selected File" + file.getCanonicalPath());
                file.createNewFile();
//                JarOutputStream jos = new JarOutputStream(new OutputStream(file) {
//                    @Override
//                    public void write(int b) throws IOException {
//                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                    }
//                });
                JarFile jarFile = new JarFile(file);
                File txtFiles[] = new File(TEST_FOLDER + "jarTest1").listFiles();
                for (File textfile : txtFiles) {

                }
            }
        } catch (IOException ex) {
            write("IOException during jar create ", ex.getLocalizedMessage());
        }
    }
}
