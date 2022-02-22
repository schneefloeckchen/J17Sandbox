package rzi.j17sandbox.modules.twitterModule;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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
public class TwitterModule {

  private Twitter m_twitter = null;

  public TwitterModule() {

  }

  public void displayTimeline() {
    if (m_twitter == null)
      getTwitterInstance();
    try {
      List<String> timeline = m_twitter.getHomeTimeline().stream()
          .map(item -> item.getText())
          .collect(Collectors.toList());
      System.out.println("Laenge der TimeLine: " + timeline.size());
      timeline.forEach(line -> {
        System.out.println(">> " + line);
      });
    } catch (TwitterException ex) {
      System.out.println("TwitterException geworfen... Details:");
      ex.printStackTrace();
    }
  }

  private void getTwitterInstance() {
    String consumerKey;
    String consumerSecret;
    String accessToken;
    String accessSecret;
    try ( InputStream input = new FileInputStream("twitterProperties.properties")) {
      Properties properties = new Properties();
      properties.load(input);
      consumerKey = properties.getProperty("oauth.consumerKey");
      consumerSecret = properties.getProperty("oauth.consumerSecret");
      accessToken = properties.getProperty("oauth.accessToken");
      accessSecret = properties.getProperty("oauth.accessSecret");
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
}
