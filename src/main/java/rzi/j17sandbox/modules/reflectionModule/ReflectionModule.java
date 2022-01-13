package rzi.j17sandbox.modules.reflectionModule;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import rzi.j17sandbox.modules.BasPanel;

/**
 *
 * @author rene
 */
public class ReflectionModule extends BasPanel {

  private JTextField m_classNameTextField = new JTextField(30);

  public ReflectionModule() {
    jInit();
  }

  /**
   * is doing all the work
   */
  private void jInit() {
    JPanel buttonPanel = new JPanel();

    JButton simpleAnalysisButton = new JButton("Simple Analysis of Vector");
    simpleAnalysisButton.addActionListener((ActionEvent e) -> {
      performSimpleAnalysisForVector();
    });
    buttonPanel.add(simpleAnalysisButton);
    
    JButton startClassAnalysisButton = new JButton ("Analyze");
    startClassAnalysisButton.addActionListener((ActionEvent e) -> {
      performClassAnalysis();
    });
    buttonPanel.add (m_classNameTextField);
    buttonPanel.add(startClassAnalysisButton);
    
    add(buttonPanel, BorderLayout.NORTH);
    write("Reflection Test started");
  }

  private void performSimpleAnalysisForVector() {
    m_reportArea.setText("");
    try {
      Class underTest = Class.forName("java.util.Vector");
      write(underTest.getName());
      Method[] methods = underTest.getDeclaredMethods();
      write(">> Methods of class are: ");
      for (var method : methods) {
//        Parameter[] parameters = method.getParameters();
        String methodString = method.toGenericString();
        write(methodString);
      }
      // Create an instance of the class
      write (">> Creating one Object...");
      Object obj = underTest.getConstructor().newInstance();
      write (">> created.");
      var method = underTest.getMethod("add", Object.class);
      write (">> Method add found.. : "+method.toString());
      method.invoke (obj, "Para1");
      method.invoke (obj, "Para2");
      method.invoke (obj, "Para3");
      write (obj.toString());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
        NoSuchMethodException | SecurityException | InvocationTargetException ex) {
      write("Exception caught: " + ex.getLocalizedMessage());
    }
  }

  private void performClassAnalysis() {
    String className = m_classNameTextField.getText();
    m_reportArea.setText("Analyzing "+className);
    
  }
}
