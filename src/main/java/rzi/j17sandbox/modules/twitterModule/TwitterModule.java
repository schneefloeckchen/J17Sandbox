package rzi.j17sandbox.modules.twitterModule;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import rzi.j17sandbox.modules.BasPanel;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Testing the twitter4J interface to the twitter API.
 *
 * Dokus and HowTos:
 * https://www.baeldung.com/twitter4j
 *
 * Ansonsten developer.twitter.com
 *
 *
 *
 *
 *
 * @author rene
 */
public class TwitterModule extends BasPanel implements ActionListener {

  private final JButton m_homeTimeLineButton = new JButton(
      "Home-Timeline");
  private final JButton m_homeTimeLineDetailsButton = new JButton(
      "Home-Timeline w. Details");
  private final JButton m_userMeButton = new JButton("User Me");
  private Twitter m_twitter = null;

  private Properties m_properties;

  public TwitterModule() {
    jInit();
  }

  private void jInit() {
    write("Starte Twitter Modul");
    JPanel buttonPanel = new JPanel();
    m_homeTimeLineButton.addActionListener(this);
    m_homeTimeLineDetailsButton.addActionListener(this);
    m_userMeButton.addActionListener(this);
    buttonPanel.add(m_homeTimeLineButton);
    buttonPanel.add(m_homeTimeLineDetailsButton);
    buttonPanel.add(m_userMeButton);
    add(buttonPanel, BorderLayout.NORTH);
  }

  public void displayTimeline() {
    if (m_twitter == null)
      getTwitterInstance();
    try {
      List<String> timeline = m_twitter.getHomeTimeline().stream()
          .map(item -> item.getText())
          .collect(Collectors.toList());
      write("Laenge der TimeLine: " + timeline.size());
      timeline.forEach(line -> {
        write(line);
      });
    } catch (TwitterException ex) {
      write("TwitterException geworfen... Details:");
      write(ex.getLocalizedMessage());
//      ex.printStackTrace();
    }
  }

  public void displayTimeLineWithDetails() {
    if (m_twitter == null)
      getTwitterInstance();
    try {
      ResponseList<Status> responeList = m_twitter.getHomeTimeline();
      write("Länge Responselist ist: " + responeList.size());
      for (Status entry : responeList) {
        write("");
        User user = entry.getUser();
        write(
            "User Name: " + user.getName()
            + //            "  User Description: " + user.getDescription() +
            "  Location: " + user.getLocation()
            + "  Timezone: " + user.getTimeZone()
            + "  Email: " + user.getEmail()
        );
        String[] countries = user.getWithheldInCountries();
        write("Number Withheld: " + countries.length);
        for (String country : countries)
          write(country);

      }
    } catch (TwitterException ex) {
      write("TwitterExeption geworfen, Details:");
      write(ex.getLocalizedMessage());
    }
  }

  /**
   * This function shall read the data without the twitter4J interface,
   * using the Twitter V2 Api via HTTP Request.
   *
   */
//  private void displayUserMe() throws URISyntaxException {
//    String bearerToken;
//    try {
//      loadProperties();
//      bearerToken = m_properties.getProperty("oauth.bearerToken");
//      write(bearerToken);
//      HttpClient httpClient = HttpClients.custom()
//        .setDefaultRequestConfig(RequestConfig.custom()
//            .setCookieSpec(CookieSpecs.STANDARD).build());
////        .build();
//      HttpClient httpClient = HttpClient.newHttpClient();
//      URIBuilder uriBuilder
//          = new URIBuilder("https://api.twitter.com/2/users/me");
//      ArrayList<NameValuePair> qp = new ArrayList<>();
//      qp.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));
//      uriBuilder.addParameters(qp);
//      HttpGet httpGet = new HttpGet(uriBuilder.build());
//      httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
//      httpGet.setHeader("Content-Type", "application/json");
//
//      HttpResponse response = httpClient.sendAsync(hr, bh)
//
//    } catch (IOException ex) {
//      System.out.println("Error reading properties file");
//      System.out.println(ex.getLocalizedMessage());
//      return;
//    }
//  }

  /**
   * This function shall read the data without the twitter4J interface,
   * using the Twitter V2 Api via HTTP Request.
   * 
   * Java example in:
   * https://github.com/twitterdev/Twitter-API-v2-sample-code/blob/main/User-Lookup/UsersDemo.java
   * 
   */
  private void displayUserMe() {
    String bearerToken;
    try {
      loadProperties();
      bearerToken = m_properties.getProperty("oauth.bearerToken");
      write(bearerToken);
// Create http Client, note: here is org.apache.http.... package used,
// Not the java.net.http package - do not mix
      Builder builder = RequestConfig.custom();
      builder.setCookieSpec(CookieSpecs.STANDARD);
      RequestConfig requestConfig = builder.build();
      
      HttpClientBuilder httpClientBuilder = HttpClients.custom();
      httpClientBuilder.setDefaultRequestConfig(requestConfig);

      HttpClient httpClient = httpClientBuilder.build();
//
      URIBuilder uriBuilder
//          = new URIBuilder("https://api.twitter.com/2/users/me");
          = new URIBuilder("https://api.twitter.com/2/users/by");
      ArrayList<NameValuePair> qp = new ArrayList<>();
      qp.add(new BasicNameValuePair("usernames", "renezillmann"));
      qp.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));
      uriBuilder.addParameters(qp);
      HttpGet httpGet = new HttpGet(uriBuilder.build());
//      httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
      httpGet.setHeader("Authorization",
          String.format("Bearer %s", bearerToken));
      httpGet.setHeader("Content-Type", "application/json");
      
      HttpResponse response = httpClient.execute(httpGet);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
      String answer = EntityUtils.toString(entity, "UTF-8");
      write ("Antwort erhalten");
      write (answer);
      } else {
        write ("null as ansswer recived");
      }
    } catch (IOException ex) {
      System.out.println("Error reading properties file");
      System.out.println(ex.getLocalizedMessage());
    } catch (URISyntaxException ex) {
      write("URI Syntax eception thrown");
      write (ex.getLocalizedMessage());
    }
    
  }
  
  private void getTwitterInstance() {
    String consumerKey;
    String consumerSecret;
    String accessToken;
    String accessSecret;
    try {
      loadProperties();
      consumerKey = m_properties.getProperty("oauth.consumerKey");
      consumerSecret = m_properties.getProperty("oauth.consumerSecret");
      accessToken = m_properties.getProperty("oauth.accessToken");
      accessSecret = m_properties.getProperty("oauth.accessSecret");
      // System.out.println(consumerKey);
    } catch (IOException ex) {
      System.out.println("Error reading properties file");
      System.out.println(ex.getLocalizedMessage());
      return;
    }
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    configurationBuilder.setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessSecret);
    TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
    m_twitter = twitterFactory.getInstance();
  }

  private void loadProperties() throws IOException {
    try ( InputStream input = new FileInputStream("twitterProperties.properties")) {
      m_properties = new Properties();
      m_properties.load(input);
    }
  }

  // ActionListener Implementation
  @Override
  public void actionPerformed(ActionEvent e) {
    Object src = e.getSource();
    if (src == m_homeTimeLineButton)
      displayTimeline();
    else if (src == m_homeTimeLineDetailsButton)
      displayTimeLineWithDetails();
    else if (src == m_userMeButton)
      displayUserMe();
  }
}
