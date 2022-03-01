/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rzi.j17sandbox.modules.jarModule;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import rzi.j17sandbox.J17SandBox;
import rzi.j17sandbox.modules.BasPanel;

/**
 *
 * @author rene
 */
public class CreateTestFilesDialog extends JFrame {

    private JTextField m_startValueTextField = new JTextField(3);
    private JTextField m_stepsValueTextField = new JTextField(3);
    private JFileChooser m_fileChooser = null;
    private JTextArea m_messageTextArea = new JTextArea(8, 40);

    public CreateTestFilesDialog() {
        jInit();
    }

    private void jInit() {
        setTitle("Creating Test Files");
        setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Start: "));
        northPanel.add(m_startValueTextField);
        northPanel.add(new JLabel("Steps: "));
        northPanel.add(m_stepsValueTextField);
        JButton doButton = new JButton("Generate!");
        doButton.addActionListener((ActionEvent e) -> {
            performGenerateFile();
        });
        northPanel.add(doButton);

        add(northPanel, BorderLayout.NORTH);

        m_fileChooser = new JFileChooser();
        m_fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        m_fileChooser.setControlButtonsAreShown(false);
        add(m_fileChooser, BorderLayout.CENTER);
        add(new JScrollPane(m_messageTextArea), BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        J17SandBox.center(this, 500, 700);
    }

    private void performGenerateFile() {
        File selectedFolder = m_fileChooser.getSelectedFile();
        try {
            String folderName = selectedFolder.getCanonicalPath();
            write("Erzeuge Testfiles in " + folderName);
            int startValue = Integer.parseInt(
                    m_startValueTextField.getText());
            int stepsValue = Integer.parseInt(
                    m_stepsValueTextField.getText());
            for (int i=0; i<stepsValue; i++) {
                String fileName = folderName
                        +"/TESTFILE"+(startValue++)+".TXT";
                try (FileWriter fileWriter = 
                        new FileWriter  (new File (fileName))) {
                    for (int l=0; l<10; l++) 
                        fileWriter.write(fileName+ "  - line "+l+"\n");
                    fileWriter.flush();
                };
            }
            write ("Files erzeugt");
        } catch (IOException ex) {
            write ("IOException Details:");
            write (ex.getLocalizedMessage());
        }
    }

    private void write(String text) {
        m_messageTextArea.append(text + "\n");
    }
}
