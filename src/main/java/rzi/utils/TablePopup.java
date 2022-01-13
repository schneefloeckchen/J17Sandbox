
package rzi.utils;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author rene
 */
public class TablePopup extends JDialog {

  private JTable m_table = new JTable();
  private TableModel m_model = null;
  public TablePopup(TableModel model) {
    jInit(model);
  }
  
  private void jInit(TableModel model) {
    m_model = model;
    m_table.setModel(m_model);
    setTitle("Display of TableData");
    setLayout(new BorderLayout());
    add (new JScrollPane(m_table));
    
    JPanel buttonPanel = new JPanel();
    JButton closeButton = new JButton("close");
    closeButton.addActionListener((var e) -> {
      dispose();
    });
    buttonPanel.add(closeButton);
    add(buttonPanel, BorderLayout.SOUTH);
  }
}
