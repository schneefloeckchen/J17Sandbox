/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rzi.j17sandbox;

import java.awt.BorderLayout;
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
import rzi.j17sandbox.modules.annotationModule.AnnotationModule;
import rzi.j17sandbox.modules.annotationModule.AppInfo;
import rzi.j17sandbox.modules.httpModule.HttpRequestModule;
import rzi.j17sandbox.modules.lafModule.LookAndFeelModule;
import rzi.j17sandbox.modules.infoModule.InfoModule;
import rzi.j17sandbox.modules.jarModule.MaintainJarFileModule;
import rzi.j17sandbox.modules.layoutModule.LayoutTester;
import rzi.j17sandbox.modules.ntpModule.NTPModule;
import rzi.j17sandbox.modules.reflectionModule.ReflectionModule;

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
 * -- (neu) : Reflection Review
 * -- (neu) : Classloader und jar Analyse
 *
 * Build-prozess ist Maven.
 *
 * @author rene
 */
@AppInfo (
    authorName = "Rene Zillmann",
    lastEditDate = "Jan2022",
    version = "0.1"
)
public class J17SandBox extends JFrame implements ActionListener {

  private final JButton m_startInfoModuleButton = new JButton("<html>System-<br/>Information</html>");
  private final JButton m_startLayoutModuleButton = new JButton("<html>Layout test<br/>und Analyse</html>");
  private final JButton m_startLookAndFeelModuleButton = new JButton("<html>Look And Feel<br/>Test und Analyse</html>");
  private final JButton m_NTPTimeModuleButton = new JButton("<html>NTP Tests</html>");
  private final JButton m_startReflectionModuleButton = new JButton("<html>Reflection Test</html>");
  private final JButton m_startClassloaderModuleButton = new JButton("<html>Classloader and<br>jar Test</html>");
  private final JButton m_startAnnotationModuleButton = new JButton("<html>Annotation Test</html>");
  private final JButton m_httpRequestModuleButton = new JButton("<html>HTTP Requests</html>");
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
    leftButtonPanel.setLayout(new GridLayout(9, 1, 5, 10));
    m_startInfoModuleButton.addActionListener(this);
    m_startLayoutModuleButton.addActionListener(this);
    m_startLookAndFeelModuleButton.addActionListener(this);
    m_NTPTimeModuleButton.addActionListener(this);
    m_cancelButton.addActionListener(this);
    m_startReflectionModuleButton.addActionListener(this);
    m_startClassloaderModuleButton.addActionListener(this);
    m_startAnnotationModuleButton.addActionListener(this);
    m_httpRequestModuleButton.addActionListener(this);

    leftButtonPanel.add(m_startInfoModuleButton);
    leftButtonPanel.add(m_startLayoutModuleButton);
    leftButtonPanel.add(m_startLookAndFeelModuleButton);
    leftButtonPanel.add(m_NTPTimeModuleButton);
    leftButtonPanel.add(m_startReflectionModuleButton);
    leftButtonPanel.add(m_startClassloaderModuleButton);
    leftButtonPanel.add(m_startAnnotationModuleButton);
    leftButtonPanel.add(m_httpRequestModuleButton);
    leftButtonPanel.add(m_cancelButton);

    add(leftButtonPanel, BorderLayout.WEST);
//        add(leftButtonPanel, BorderLayout.WEST);
    center(this, WINDOW_WIDTH, WINDOW_HEIGHT);
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
    InfoModule dialog = new InfoModule();
    switchTo(dialog);
    dialog.doAction();
  }

  private void performLayoutModule() {
    LayoutTester dialog = new LayoutTester();
  }

  private void performLookAndFeelModule() {
    switchTo(new LookAndFeelModule());
  }

  private void performNTPModule() {
    switchTo(new NTPModule());
  }

  private void performReflectionModule() {
    switchTo(new ReflectionModule());
  }

  private void performClassloaderModule() {
    switchTo(new MaintainJarFileModule());
  }

  private void performAnnotationModule() {
    switchTo(new AnnotationModule());
  }
  
  private void performHttpRequestModule() {
    switchTo (new HttpRequestModule());
  }

  private void switchTo(JPanel newPanel) {
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
    if (src == m_startInfoModuleButton)
      performInfoModule();
    else if (src == m_startLayoutModuleButton)
      performLayoutModule();
    else if (src == m_startLookAndFeelModuleButton)
      performLookAndFeelModule();
    else if (src == m_NTPTimeModuleButton)
      performNTPModule();
    else if (src == m_startReflectionModuleButton)
      performReflectionModule();
    else if (src == m_startClassloaderModuleButton)
      performClassloaderModule();
    else if (src == m_startAnnotationModuleButton)
      performAnnotationModule();
    else if (src == m_httpRequestModuleButton)
      performHttpRequestModule();
    else if (src == m_cancelButton)
      dispose();
    else
      System.out.println("Not yet implemented");
  }

  public static void print(String text) {
    System.out.println(text);
  }

}
