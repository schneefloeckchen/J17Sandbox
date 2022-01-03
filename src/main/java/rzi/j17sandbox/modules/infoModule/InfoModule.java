/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rzi.j17sandbox.modules.infoModule;

import java.awt.BorderLayout;
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
    private JTextArea m_messageArea = new JTextArea(50, 50);

    public InfoModule() {
        jInit();
    }

    private void jInit() {
        System.out.println("In InfoModule");
        setLayout(new BorderLayout());
        add(new JScrollPane(m_messageArea), BorderLayout.CENTER);
    }

    public void doAction() {
        m_messageArea.append("Informationen: \n");
        m_messageArea.append("Java-Version: " + System.getProperty("java.version")+"\n");
        m_messageArea.append("Java Release Date : " + System.getProperty("java.version.date")+"\n");
        m_messageArea.append("Java Vendor : " + System.getProperty("java.vendor")+"\n");
        m_messageArea.append("Homepage Vendor : " + System.getProperty("java.vendor.url")+"\n");
        m_messageArea.append("Java Spec Version : " + System.getProperty("java.specification.version")+"\n");
        m_messageArea.append("VM Name : " + System.getProperty("java.vm.name") + "\n");
        m_messageArea.append("VM Version : " + System.getProperty("java.vm.version") + "\n");
        m_messageArea.append("JDK Debug Info : " + System.getProperty("jdk.debug") + "\n");
        m_messageArea.append("Sun Launcher : " + System.getProperty("sun.java.launcher") + "\n");
        report ("Java Class Version", "java.class.version");
        report ("Class Path", "java.class.path");
        report ("List of paths to search when loading libraries", "java.library.path");
        report ("Default temp file path", "java.io.tmpdir");
        report ("JIT compiler to use", "java.compiler");
        report ("os.name", "os.name");
        report ("Operating system architecture", "os.arch");
        report ("Operating system version", "os.version");
        report ("User Name", "user.name");
        report ("User Home Folder", "user.home");
        report ("Current user working directory", "user.dir");
    }
    
    private void report (String label, String info) {
        m_messageArea.append(label + " : " + System.getProperty(info) + "\n");
    }
}
