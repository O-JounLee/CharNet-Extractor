package sentiment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
 
class Language {
  private String lang;
  private String text;
  private Double confidence;
 
  public String getLang() {
    return lang;
  }
  public void setLang(String lang) {
    this.lang = lang;
  }
 
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public Double getConfidence() {
    return confidence;
  }
  public void setConfidence(Double confidence) {
    this.confidence = confidence;
  }
}
 
class Hashtags {
  private String[] hashtags;
  private String text;
  public String[] getHashtags() {
    return hashtags;
  }
 
  public void setHashtags(String[] hashtags) {
    this.hashtags = hashtags;
  }
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
}
 

 
public class SentimentAnalysis {
  private static final String APPLICATION_ID = "69cb154d";
  private static final String APPLICATION_KEY ="f8e2076628cc8b8a364390df594ccec4";
 
//  public static void main(String[] args) {
//    String text = "John is not good football player!";
//    String textOrUrl = "text";
////    Language lang = getLanguage(text, textOrUrl);
////    System.out.printf("n%sn",
////        lang.getText());
////    System.out.printf("Language: %s (%f)n",
////        lang.getLang(), lang.getConfidence());
//    Sentiment sent = getSentiment(text,textOrUrl);
//    System.out.printf("n%sn",
//        sent.getText());
//    System.out.printf("Sentiment: %s (%f)n",
//        sent.getPolarity(), sent.getPolarityConfidence());
////    textOrUrl = "url";
////    Hashtags hashtags = getHashtags("http://www.bbc.com/news/science-environment-29701853", textOrUrl);
////    StringBuilder sb = new StringBuilder();
////    for (String s: hashtags.getHashtags()) {
////      sb.append(" " + s);
////    }
////    System.out.printf("nHashtags:%sn", sb.toString());
////    System.out.printf("nThe text analyzed for hashtag suggestions is shown here for reference...n");
////        System.out.printf("n%snn",
////        hashtags.getText());
//  }
 
  public static Language getLanguage(String text, String textOrUrl) {
    final Map<String, String> parameters;
    parameters = new HashMap<String, String>();
    parameters.put(textOrUrl, text);
    Document doc = callAPI("language", parameters);
    Language language = new Language();
    NodeList nodeList = doc.getElementsByTagName("lang");
    Node langNode = nodeList.item(0);
    nodeList = doc.getElementsByTagName("confidence");
    Node confNode = nodeList.item(0);
    nodeList = doc.getElementsByTagName("text");
    Node textNode = nodeList.item(0);
    language.setText(textNode.getTextContent());
    language.setLang(langNode.getTextContent());
     language.setConfidence(Double.parseDouble(confNode.getTextContent()));
 
    return language;
  }
 
  public static Hashtags getHashtags(String text, String textOrUrl) {
    final Map<String, String> parameters;
    parameters = new HashMap<String, String>();
    parameters.put(textOrUrl, text);
    Document doc = callAPI("hashtags", parameters);
     NodeList nodeList = doc.getElementsByTagName("hashtag");
    Hashtags hashtags = new Hashtags();
    List<String> hts = new ArrayList<String>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node currentNode = nodeList.item(i);
      hts.add(currentNode.getTextContent());
    }
 
    hashtags.setHashtags(hts.toArray(new String[hts.size()]));
 
    nodeList = doc.getElementsByTagName("text");
    Node textNode = nodeList.item(0);
    hashtags.setText(textNode.getTextContent());
 
    return hashtags;
  }
 
  public static Sentiments getSentiment(String text, String textOrUrl) {
    final Map<String, String> parameters;
    parameters = new HashMap<String, String>();
    parameters.put(textOrUrl, text);
    Document doc = callAPI("sentiment", parameters);
    Sentiments sentiment = new Sentiments();
    NodeList nodeList = doc.getElementsByTagName("polarity");
    Node polarityNode = nodeList.item(0);
    nodeList = doc.getElementsByTagName("polarity_confidence");
    Node confNode = nodeList.item(0);
    nodeList = doc.getElementsByTagName("text");
    Node textNode = nodeList.item(0);
    sentiment.setText(textNode.getTextContent());
    sentiment.setPolarity(polarityNode.getTextContent());
     sentiment.setPolarityConfidence(Double.parseDouble(confNode.getTextContent()));
 
    return sentiment;
  }
 
  public static Document callAPI(String endpoint, Map<String, String> parameters) {
    URL url;
    HttpURLConnection connection = null;
 
    try {
      String queryString = "";
      StringBuilder sb = new StringBuilder();
      for (Map.Entry<String, String> e: parameters.entrySet()) {
        if (sb.length() > 0) { sb.append('&'); }
        sb.append(URLEncoder.encode(e.getKey(), "UTF-8")).append('=')
          .append(URLEncoder.encode(e.getValue(), "UTF-8"));
      }
      queryString = sb.toString();
      url = new URL("https://api.aylien.com/api/v1/" + endpoint);
      connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty(
          "Content-Type", "application/x-www-form-urlencoded");
      connection.setRequestProperty(
          "Content-Length", Integer.toString(queryString.getBytes().length));
      connection.setRequestProperty("Accept", "text/xml");
      connection.setRequestProperty(
          "X-AYLIEN-TextAPI-Application-ID", APPLICATION_ID);
      connection.setRequestProperty(
          "X-AYLIEN-TextAPI-Application-Key", APPLICATION_KEY);
      connection.setDoInput(true);
      connection.setDoOutput(true);
 
      DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
      dos.writeBytes(queryString);
      dos.flush();
      dos.close();
 
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource xis = new InputSource(connection.getInputStream());
 
      return builder.parse(xis);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
    }
  }
}
