
package rzi.j17sandbox.modules.httpModule;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author rene
 */
public abstract class JsonTableModel implements TableModel {

  protected List<TableModelListener> m_tableModelListener = new ArrayList<>();
  
  @Override
  public void addTableModelListener(TableModelListener l) {
    m_tableModelListener.add(l);
  }

  @Override
  public void removeTableModelListener(TableModelListener l) {
    m_tableModelListener.remove(l);
  }


}
