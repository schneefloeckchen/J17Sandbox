package rzi.j17sandbox.modules.httpModule;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import rzi.j17sandbox.modules.BasPanel;
import rzi.utils.TablePopup;

/**
 * Check HTTP-Request on the examples of the NASA Horizons System.
 * API Docu at: https://ssd-api.jpl.nasa.gov/doc/horizons.html
 *
 * Further can be kicker.de, host is ovsyndication.kicker.de
 * Decu/examples: https://stuff.jangxx.com/kicker-api/
 *
 * @author rene
 */
public class HttpRequestModule extends BasPanel {

  private JPanel m_nasaButtonPanel = null;
  private JPanel m_kickerButtonPanel = null;

  private JComboBox m_siteSelector = new JComboBox(new String[]{"Nasa -Horizons", "Kicker"});

  private static final String NASA_TEST_COMMAND
      = "https://ssd.jpl.nasa.gov/api/horizons.api?format=text"
      + "&COMMAND='499'&OBJ_DATA='YES'&MAKE_EPHEM='YES'"
      + "&EPHEM_TYPE='OBSERVER'&CENTER='500@399'"
      + "&START_TIME='2006-01-01'&STOP_TIME='2006-01-20'"
      + "&STEP_SIZE='1%20d'&QUANTITIES='1,9,20,23,24,29'";

  private static final String KICKER_LEAGUE_LIST_COMMAND = 
      "http://ovsyndication.kicker.de/API/android/1.0/LeagueList/3.json";
  private static final String KICKER_CATALOGUE_COMMAND = 
      "http://ovsyndication.kicker.de/API/android/1.0/Catalogue/3.json";
  private static String KICKER_SECRET = "sR6qEPgsf97y";

  private JTextField m_commandTextField = new JTextField(10);

  public HttpRequestModule() {
    jInit();
  }

  private void jInit() {
    write("Starte httpRequestModule");
    m_siteSelector.addActionListener((ActionEvent e) -> {
      JComboBox source = (JComboBox) e.getSource();
      int selected = source.getSelectedIndex();
      System.out.println("Selected >> : " + selected);
      switch (selected) {
        case 0 ->
          switchToNasa();
        case 1 ->
          switchToKicker();
        default ->
          write("ungueltiges Ergebnis aus der Site Combobox: " + selected);
      }
    });
//    GridBagConstraints gbc = new GridBagConstraints();
    m_nasaButtonPanel = jInitForNasa();
//    initialRequest();
    add(m_nasaButtonPanel, BorderLayout.NORTH);
  }

  private void switchToNasa() {
    removeNorthComponent();
    /* if (m_nasaButtonPanel == null) */
    m_nasaButtonPanel = jInitForNasa();
    add(m_nasaButtonPanel, BorderLayout.NORTH);
    revalidate();
    repaint();
  }

  private void switchToKicker() {
    removeNorthComponent();
    /*if (m_kickerButtonPanel == null)*/
    m_kickerButtonPanel = jInitForKicker();
    add(m_kickerButtonPanel, BorderLayout.NORTH);
    revalidate();
    repaint();
  }

  private void removeNorthComponent() {
    BorderLayout layout = (BorderLayout) getLayout();
    remove(layout.getLayoutComponent(BorderLayout.NORTH));
  }

  private JPanel jInitForNasa() {
    JPanel buttonPanel = createButtonPanel();
    GridBagConstraints gbc = new GridBagConstraints();
    buttonPanel.add(m_siteSelector, gbc);
    buttonPanel.add(new JLabel("COMMAND: "), gbc);
    buttonPanel.add(m_commandTextField, gbc);
    m_commandTextField.setText("499");
    JButton nasaLoadButton = new JButton("Load");
    nasaLoadButton.addActionListener((var e) -> {
      performNasaLoadOperation();
    });
    JButton initialNasaTestRequestButton = new JButton("Initial Test Request");
    initialNasaTestRequestButton.addActionListener((var e) -> {
      initialRequest();
    });
    JButton testNasaRequestButton = new JButton("Test Request");
    testNasaRequestButton.addActionListener((var e) -> {
      executeHttpRequest(NASA_TEST_COMMAND);
    });
    buttonPanel.add(nasaLoadButton, gbc);
    buttonPanel.add(initialNasaTestRequestButton, gbc);
    buttonPanel.add(testNasaRequestButton, gbc);
    return buttonPanel;
  }

  private JPanel jInitForKicker() {
    JPanel buttonPanel = createButtonPanel();
    GridBagConstraints gbc = new GridBagConstraints();
    buttonPanel.add(m_siteSelector, gbc);
    
    JButton getTokenButton = new JButton("Create Token");
    getTokenButton.addActionListener((var e) -> {
      performCreateKickerToken();
    });
    JButton test1Button = new JButton("List Liegen");
    test1Button.addActionListener((ActionEvent e) -> {
      performKickerLeagueListQuery();
    });
    JButton test2Button = new JButton("Catalogs");
    test2Button.addActionListener((ActionEvent e) -> {
      executeHttpRequest(KICKER_CATALOGUE_COMMAND);
    });
    buttonPanel.add(getTokenButton, gbc);
    buttonPanel.add(test1Button);
    buttonPanel.add(test2Button);
    return buttonPanel;
  }

  private JPanel createButtonPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    return panel;
  }

  /*
  * Kicker Functions
   */
  /**
   * Need the date (GMT +1) as usually written in German: DD.MM.YYYY HH:mm:ss
   *
   * @return
   */
  private String performCreateKickerToken() {
    write("Try to obtain the token from kicker.de, formatting date");
    Instant now = Instant.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD.MM.YYY HH:mm:ss");
    ZonedDateTime time = ZonedDateTime.ofInstant(now, ZoneId.of("GMT"));
    String timeString = formatter.format(time);
    write(timeString);
    String authCode = "3userpwd" + timeString;
    write (authCode);
    byte[] secretBytes = KICKER_SECRET.getBytes();
    SecretKeySpec signingKey = new SecretKeySpec(secretBytes, "HmacSHA1");
    String authString;
    try {
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(signingKey);
      byte[] rawBytes = mac.doFinal(authCode.getBytes());
      byte[] hexBytes = new Hex().encode(rawBytes);
      authString = new String(hexBytes, "UTF-8");
      write("Auth String is", authString);
    } catch (NoSuchAlgorithmException | InvalidKeyException
        | UnsupportedEncodingException ex) {
      write(ex.getLocalizedMessage());
      return "Error";
    }
    String httpRequest = "http://ovsyndication.kicker.de/AUTH/3/" +
        authString + "/3.json";
    executeHttpRequest(httpRequest);
    return "";
  }

  private void performKickerLeagueListQuery() {
    String jsonData = executeHttpRequest(KICKER_LEAGUE_LIST_COMMAND);
//    File workFile1 = new File("WOrkFIle1.json");
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("WorkFIle1.json"));
      writer.write(jsonData);
      writer.close();
    } catch (IOException ex) {
      Logger.getLogger(HttpRequestModule.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    try {
//      JSONObject jsonObject = new JSONObject(jsonData);
      LeagueListTableModel model = new LeagueListTableModel();
      model.load(jsonData);
      TablePopup dialog = new TablePopup(model);
      dialog.setVisible(true);
    } catch (JSONException ex) {
      write ("Fehler beim Erzeugen des JSON Objektes", ex.getLocalizedMessage());
    }
    
  }
  /*
   * NASA Functions 
   */
  private static final String SSD_URL = "https://ssd.jpl.nasa.gov/api/horizons.api?";
  private static final String FORMAT_LABEL = "format=";
  private static final String COMMAND_LABEL = "COMMAND=";

  private void performNasaLoadOperation() {
    String commandString = m_commandTextField.getText();
    String httpString = SSD_URL
        + FORMAT_LABEL + "json&"
        + COMMAND_LABEL + "'" + commandString + "'&"
        + "OBJ_DATA='YES'&MAKE_EPHEM='NO'";
    //System.out.println(httpString);
    executeHttpRequest(httpString);
  }

  /**
   * Site unabhaengige Ausfuehrung des HTTP Requests
   *
   * Beispiel:
   * https://www.twilio.com/blog/5-moglichkeiten-fur-http-anfragen-java
   *
   * @param httpCommand
   */
  private String executeHttpRequest(String httpCommand) {
    System.out.println("Starte Request at Horizons");
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().
        uri(URI.create(httpCommand)).build();
    var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
    System.out.println(response.getClass().getName());
    System.out.println(">> " + response.toString());
    String body = response.body();
    System.out.println(body);
    return body;
  }

  /**
   * Erster, vorkonfigurierter Request
   */
  private void initialRequest() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().
        //        uri(URI.create("https://ssd.jpl.nasa.gov/api/horizons.api?format=text"
        //            + "&COMMAND='499'&OBJ_DATA='YES'&MAKE_EPHEM='YES'"
        //            + "&EPHEM_TYPE='OBSERVER'&CENTER='500@399'"
        //            + "&START_TIME='2006-01-01'&STOP_TIME='2006-01-20'"
        //            + "&STEP_SIZE='1%20d'&QUANTITIES='1,9,20,23,24,29'")).build();
        uri(URI.create(NASA_TEST_COMMAND)).build();
    client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).
        thenApply(HttpResponse::body).
        thenAccept(System.out::println).join();
  }
}
