
package rzi.j17sandbox.modules.httpModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author rene
 */
public class LeagueListTableModel extends JsonTableModel {

  private JSONArray m_leagueList = null;
  private int m_leagueListLength = 0;
  private static final String[] TABLE_LABELS = {"Id", "Short Name", "Long Name", "Current Season ID", "Country ID"};
  private static final String[] TABLE_KEYS = {"id", "shortName", "longName", "currentSeasonId", "countryId"};
  
  public LeagueListTableModel() {
    
  }
  
  public void load (String countryData) throws JSONException {
    JSONObject countryJson = new JSONObject(countryData);
    JSONObject leagues = countryJson.getJSONObject("leagues");
    m_leagueList = leagues.getJSONArray("league");
    System.out.println(m_leagueList);
    m_leagueListLength = m_leagueList.length();  // Sort of cache, don't need to
    System.out.println("Laenge League List: "+m_leagueListLength);
                                  // re-evaluate it per call
  }
  
  @Override
  public int getRowCount() {
    return m_leagueListLength;
  }

  @Override
  public int getColumnCount() {
    return TABLE_KEYS.length;
  }

  @Override
  public String getColumnName(int i) {
    return TABLE_LABELS[i];
  }

  @Override
  public Class<?> getColumnClass(int i) {
    return String.class;
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  @Override
  public Object getValueAt(int row, int column) {
    JSONObject rowData ;
    try {
      rowData = m_leagueList.getJSONObject(row);
//    } catch (JSONException ex) {
//      System.out.println("Error in row "+row+" :  "+ex.getLocalizedMessage());
//    }
//    try {
      return rowData.getString(TABLE_KEYS[column]);
    } catch (JSONException ex) {
      System.out.println ("Error in row "+row + "  / "+ ex.getLocalizedMessage());
      return ex.getLocalizedMessage();
    } catch (NullPointerException ex) {
      return "Null Pointer Ex";
    }
  }

  @Override
  public void setValueAt(Object o, int i, int i1) {
  }

}
